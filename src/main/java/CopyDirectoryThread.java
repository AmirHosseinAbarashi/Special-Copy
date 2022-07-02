import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.logging.Logger;

public class CopyDirectoryThread implements Callable<Void> {

    private final Logger logger = Logger.getLogger(getClass().getName());
    private final String fileIn;
    private final String fileOut;
    private final String f;

    public CopyDirectoryThread(String fileIn, String fileOut, String f) {
        this.fileIn = fileIn;
        this.fileOut = fileOut;
        this.f = f;
    }

    @Override
    public Void call() throws Exception {
        if (new File(fileIn, f).isDirectory()) {
            try {
                copyDirectoryCompatibilityMode(new File(fileIn, f), new File(fileOut));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                Files.copy(new File(fileIn, f).toPath(), new File(fileOut, f).toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private void copyDirectoryCompatibilityMode(File source, File destination) throws IOException, ExecutionException, InterruptedException {
        logger.info("copying " + source + " to " + destination);
        CopyDirectory cd = new CopyDirectory();
        cd.copy(source.toPath().toString(), destination.toPath().toString());
    }
}
