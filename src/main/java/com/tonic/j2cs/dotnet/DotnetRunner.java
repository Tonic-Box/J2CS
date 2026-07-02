package com.tonic.j2cs.dotnet;

import com.tonic.j2cs.J2csException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Runs dotnet commands (and published exes) with captured output and timeouts.
 */
public final class DotnetRunner {

    private static final long PUBLISH_TIMEOUT_MS = 10 * 60 * 1000;
    private static final long EXEC_TIMEOUT_MS = 60 * 1000;

    public ExecResult build(Path projectDir) {
        return run(List.of("dotnet", "build", "-c", "Release"), projectDir, PUBLISH_TIMEOUT_MS);
    }

    public ExecResult publish(Path projectDir, PublishMode mode, Path outputDir) {
        List<String> command = new ArrayList<>(List.of(
                "dotnet", "publish", "-c", "Release", "-r", "win-x64", "-o", outputDir.toString()));
        if (mode == PublishMode.SELF_CONTAINED) {
            command.add("--self-contained");
            command.add("true");
            command.add("-p:PublishAot=false");
        }
        return run(command, projectDir, PUBLISH_TIMEOUT_MS);
    }

    public ExecResult exec(Path executable, List<String> args, Path workDir) {
        List<String> command = new ArrayList<>();
        command.add(executable.toString());
        command.addAll(args);
        return run(command, workDir, EXEC_TIMEOUT_MS);
    }

    public ExecResult run(List<String> command, Path workDir, long timeoutMs) {
        try {
            Process process = new ProcessBuilder(command)
                    .directory(workDir.toFile())
                    .start();
            StreamReader stdout = new StreamReader(process.getInputStream());
            StreamReader stderr = new StreamReader(process.getErrorStream());
            stdout.start();
            stderr.start();
            if (!process.waitFor(timeoutMs, TimeUnit.MILLISECONDS)) {
                process.destroyForcibly();
                throw new J2csException("timed out after " + timeoutMs + " ms: " + String.join(" ", command));
            }
            stdout.join();
            stderr.join();
            return new ExecResult(process.exitValue(), stdout.text(), stderr.text());
        } catch (IOException e) {
            throw new J2csException("failed to run " + String.join(" ", command) + ": " + e.getMessage(), e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new J2csException("interrupted while running " + String.join(" ", command), e);
        }
    }

    public static void requireSuccess(ExecResult result, String what) {
        if (!result.succeeded()) {
            throw new J2csException(what + " failed (exit " + result.exitCode() + "):\n"
                    + result.combinedTail(50));
        }
    }

    private static final class StreamReader extends Thread {

        private final InputStream in;
        private final ByteArrayOutputStream buffer = new ByteArrayOutputStream();

        private StreamReader(InputStream in) {
            this.in = in;
            setDaemon(true);
        }

        @Override
        public void run() {
            try {
                in.transferTo(buffer);
            } catch (IOException ignored) {
            }
        }

        private String text() {
            return buffer.toString(StandardCharsets.UTF_8);
        }
    }
}
