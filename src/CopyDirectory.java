import java.io.*;

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
            copyFile(source, destination);
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

    private void copyFile(File sourceFile, File destinationFile)      // copy file
            throws IOException {
        try (InputStream in = new FileInputStream(sourceFile);
             OutputStream out = new FileOutputStream(destinationFile)) {
            byte[] buf = new byte[1024];
            int length;
            while ((length = in.read(buf)) > 0) {
                out.write(buf, 0, length);
            }
        }
    }

}
