package com.tonic.j2cs;

import com.tonic.j2cs.cli.Cli;

public final class Main {

    private Main() {
    }

    public static void main(String[] args) {
        System.exit(new Cli().run(args));
    }
}
