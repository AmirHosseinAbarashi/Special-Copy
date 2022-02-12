import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class CopyDirectory {

    private String in, out;
    private File fileIn, fileOut;            // fileOut -> out


    public CopyDirectory(String in, String out) {
        this.in = in;
        this.out = out;

        StringBuilder inSBLD = new StringBuilder(in);
        StringBuilder outSBLD = new StringBuilder(out);

        for (int i = 0; i < inSBLD.length(); i++)                         // conversion to a standard path (in)
            if (inSBLD.charAt(i) == '\\')
                inSBLD.setCharAt(i, '/');

        for (int i = 0; i < outSBLD.length(); i++)                       // conversion to a standard path (out)
            if (outSBLD.charAt(i) == '\\')
                outSBLD.setCharAt(i, '/');

        fileIn = new File(String.valueOf(inSBLD));
        fileOut = new File(String.valueOf(outSBLD));

    }

    public void copy() throws IOException {
        copyDirectoryCompatibityMode(fileIn, fileOut);
    }

    private void copyDirectoryCompatibityMode(File source, File destination) throws IOException {
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
            if (new File(sourceDirectory, f).isDirectory())       // if f is a directory
                copyDirectoryCompatibityMode(new File(sourceDirectory, f), destinationDirectory);
            else                                                   // if f is a file
                copyDirectoryCompatibityMode(new File(sourceDirectory, f), new File(destinationDirectory, f));
        }
    }

}