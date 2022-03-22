import java.util.Scanner;
import java.util.concurrent.ExecutionException;

class Main {
    public static void main(String[] args) throws InterruptedException, ExecutionException {

        Scanner scanner = new Scanner(System.in);

        CopyDirectory copyDirectory = new CopyDirectory();
        copyDirectory.copy(scanner.nextLine(), scanner.nextLine());
    }
}