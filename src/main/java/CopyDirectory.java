import java.io.File;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class CopyDirectory {

    private final File fileIn;
    private final File fileOut;
    ExecutorService es = Executors.newFixedThreadPool(4);

    public CopyDirectory(String in, String out) {
        fileIn = new File(String.valueOf(in));
        fileOut = new File(String.valueOf(out));
    }

    public void copy() throws InterruptedException {
        for (String f : Objects.requireNonNull(fileIn.list())) {
            Runnable runnable = new CopyDirectoryThread(fileIn, fileOut, f);
            es.execute(runnable);
        }
        es.shutdown();
        es.awaitTermination(30000, TimeUnit.MILLISECONDS);
    }
}