import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;

public class WatchDemo {
    public static void main(String[] args) throws Exception {
        Path dir = Paths.get("watch_demo_dir");
        Files.createDirectories(dir);
        WatchService ws = FileSystems.getDefault().newWatchService();
        dir.register(ws, StandardWatchEventKinds.ENTRY_CREATE);
        Thread.sleep(300);
        Files.writeString(dir.resolve("newfile.txt"), "hi");
        WatchKey key = ws.take();
        for (WatchEvent<?> ev : key.pollEvents()) {
            if (ev.kind() == StandardWatchEventKinds.ENTRY_CREATE) {
                System.out.println(ev.kind().name() + ": " + ev.context());
            }
        }
        ws.close();
        Files.delete(dir.resolve("newfile.txt"));
        Files.delete(dir);
    }
}
