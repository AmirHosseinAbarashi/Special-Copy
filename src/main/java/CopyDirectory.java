import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Objects;
import java.util.logging.Logger;

public class CopyDirectory {

    private final Logger logger = Logger.getLogger(getClass().getName());
    private final File fileIn;
    private final File fileOut;


    public CopyDirectory(String in, String out) {
        fileIn = new File(String.valueOf(in));
        fileOut = new File(String.valueOf(out));
    }

    public void copy() {
        for (String f : Objects.requireNonNull(fileIn.list())) {
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        copyDirectoryCompatibilityMode(new File(fileIn , f) , fileOut);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                private void copyDirectoryCompatibilityMode(File source, File destination) throws IOException {
                    logger.info("copying " + source + " to " + destination);
                    if (source.isDirectory()) {                             // source is a directory
                        copyDirectory(source, destination);
                    } else {                                                // source is a file
                        Files.copy(source.toPath(), destination.toPath());
                    }
                }


            });
            t.start();
        }
    }

    private void copyDirectoryCompatibilityMode(File source, File destination) throws IOException {
        logger.info("copying " + source + " to " + destination);
        if (source.isDirectory()) {                             // source is a directory
            copyDirectory(source, destination);
        } else {                                                // source is a file
            Files.copy(source.toPath(), destination.toPath());
        }
    }

    private void copyDirectory(File sourceDirectory, File destinationDirectory) throws IOException {
        if (!destinationDirectory.exists())                    // if path end not exist -> will build it
            if (!destinationDirectory.mkdir())
                throw new IOException("Could not created directory");

        for (String f : Objects.requireNonNull(sourceDirectory.list())) {
            if (new File(destinationDirectory, f).isFile() && new File(destinationDirectory, f).exists()) {       // (f is file?) and (f is exist?)
                logger.info("file " + f + " in " + fileOut.toString() + " is exist.");
                File file = new File(destinationDirectory, sourceDirectory.hashCode() + "_Copy" + f);
                if (file.exists()) {
                    logger.info(f + " was copied.");
                    Files.delete(file.toPath());
                }
                copyDirectoryCompatibilityMode(new File(sourceDirectory, f), file);
            } else if (new File(sourceDirectory, f).isDirectory())       // if f is a directory
                copyDirectoryCompatibilityMode(new File(sourceDirectory, f), destinationDirectory);
            else                                                   // if f is a file
                copyDirectoryCompatibilityMode(new File(sourceDirectory, f), new File(destinationDirectory, f));
        }
    }
}