import java.io.File;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CopyDirectory {

    ExecutorService es = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    public void copy(String sourceDirectory, String destinationDirectory) {
        for (String f : Objects.requireNonNull(new File(sourceDirectory).list())) {
            es.submit(new CopyDirectoryThread(sourceDirectory, destinationDirectory, f));
        }
        es.shutdown();
    }
}