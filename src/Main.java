import java.io.*;
import java.util.Scanner;

class Main {
    public static void main(String[] args) throws IOException {

        Scanner scanner = new Scanner(System.in);

        CopyDirectory copyDirectory = new CopyDirectory(scanner.nextLine(), scanner.nextLine());
        copyDirectory.copy();
    }

}