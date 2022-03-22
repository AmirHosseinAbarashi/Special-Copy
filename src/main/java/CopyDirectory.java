import java.io.File;
import java.util.Objects;
import java.util.concurrent.*;

public class CopyDirectory {

    ExecutorService es = Executors.newFixedThreadPool(4);

    public void copy(String sourceDirectory, String destinationDirectory) throws ExecutionException, InterruptedException {
        for (String f : Objects.requireNonNull(new File(sourceDirectory).list())) {
            Callable<Void> callable = new CopyDirectoryThread(sourceDirectory, destinationDirectory, f);
            Future<Void> tasks = es.submit(callable);
            tasks.get();
        }
        es.shutdown();
    }
}