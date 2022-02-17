import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.logging.Logger;

public class CopyDirectory {

    private final File fileIn;
    private final File fileOut;
    private final Logger logger = Logger.getLogger("copy");
    private final String in;
    private final String out;


    public CopyDirectory(String in, String out) {
        this.in = in;
        this.out = out;

        StringBuilder inSBLD = new StringBuilder(in);
        StringBuilder outSBLD = new StringBuilder(out);

        for (int i = 0; i < inSBLD.length(); i++)                         // conversion to a standard path (in) [  \  ->  /  ]
            if (inSBLD.charAt(i) == '\\')
                inSBLD.setCharAt(i, '/');

        for (int i = 0; i < outSBLD.length(); i++)                       // conversion to a standard path (out) [  \  ->  /  ]
            if (outSBLD.charAt(i) == '\\')
                outSBLD.setCharAt(i, '/');

        fileIn = new File(String.valueOf(inSBLD));
        fileOut = new File(String.valueOf(outSBLD));

    }

    public void copy() throws IOException {
        logger.info("The copy order was registered by the user.");
        copyDirectoryCompatibityMode(fileIn, fileOut);
    }

    private void copyDirectoryCompatibityMode(File source, File destination) throws IOException {
        logger.info("copyDirectoryCompatibityMode:" + source.toString());
        if (source.isDirectory()) {                             // source is a directory
            copyDirectory(source, destination);
        } else {                                                // source is a file
            Files.copy(source.toPath(), destination.toPath());
        }
    }

    private void copyDirectory(File sourceDirectory, File destinationDirectory) throws IOException {
        if (!destinationDirectory.exists())                    // if path end not exist -> will build it
            destinationDirectory.mkdir();

        for (String f : sourceDirectory.list()) {
            if (new File(destinationDirectory, f).isFile() && new File(destinationDirectory, f).exists()) {       // (f is file?) and (f is exist?)
                logger.info("file " + f + " in " + out + " is exist.");
                if (new File(destinationDirectory, sourceDirectory.hashCode() + "_Copy" + f).exists()) {    // f was copied!
                    logger.info(f + " was copied. Delete and Copy again.");
                    Files.delete(new File(destinationDirectory, sourceDirectory.hashCode() + "_Copy" + f).toPath());
                }
                copyDirectoryCompatibityMode(new File(sourceDirectory, f), new File(destinationDirectory, sourceDirectory.hashCode() + "_Copy" + f));
            } else if (new File(sourceDirectory, f).isDirectory())       // if f is a directory
                copyDirectoryCompatibityMode(new File(sourceDirectory, f), destinationDirectory);
            else                                                   // if f is a file
                copyDirectoryCompatibityMode(new File(sourceDirectory, f), new File(destinationDirectory, f));
        }
    }

}