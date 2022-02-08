import java.io.*;
import java.util.Scanner;

class Main
{
    public static void main(String[] args) throws IOException
    {

        Scanner scanner = new Scanner(System.in);

        StringBuilder in = new StringBuilder(scanner.nextLine());     // get path of begin
        StringBuilder out = new StringBuilder(scanner.nextLine());    // get path of end

        for (int i = 0; i < in.length(); i++)                         // conversion to a standard path (in)
            if (in.charAt(i) == '\\')
                in.setCharAt(i , '/');

        for (int i = 0; i < out.length(); i++)                       // conversion to a standard path (out)
            if (out.charAt(i) == '\\')
                out.setCharAt(i , '/');


        File fileIn = new File(String.valueOf(in));                 // fileIn -> in
        File fileOut = new File(String.valueOf(out));               // fileOut -> out



        if (fileIn.exists())
            copyDirectoryCompatibityMode(fileIn , fileOut);        // if fileIn is exist
        else
            System.out.println("File not exist.");                 // if fileIn not exist

    }

    static void copyDirectoryCompatibityMode(File source, File destination) throws IOException
    {
        if (source.isDirectory()) {                             // source is a directory
            copyDirectory(source, destination);
        } else {                                                // source is a file
            copyFile(source, destination);
        }
    }

    static void copyDirectory(File sourceDirectory, File destinationDirectory) throws IOException
    {
        if (!destinationDirectory.exists())                    // if path end not exist -> will build it
            destinationDirectory.mkdir();

        for (String f : sourceDirectory.list())
        {
            if (new  File(sourceDirectory , f).isDirectory())       // if f is a directory
                copyDirectoryCompatibityMode(new File(sourceDirectory, f), destinationDirectory);
            else                                                   // if f is a file
                copyDirectoryCompatibityMode(new File(sourceDirectory, f), new File(destinationDirectory , f));
        }
    }

    static void copyFile(File sourceFile, File destinationFile)      // copy file
            throws IOException {
        try (InputStream in = new FileInputStream(sourceFile);
             OutputStream out = new FileOutputStream(destinationFile))
        {
            byte[] buf = new byte[1024];
            int length;
            while ((length = in.read(buf)) > 0) {
                out.write(buf, 0, length);
            }
        }
    }
}