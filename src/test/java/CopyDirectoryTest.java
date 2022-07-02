import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.fail;

class CopyDirectoryTest {

    @ParameterizedTest
    @ValueSource(strings = {"/folder1", "/folder2", ""})
    void testFilesCopy1(String subDirectory) throws IOException, InterruptedException, ExecutionException {
        String pathIn = "test-file/test-in" + subDirectory;
        String pathOut = Files.createTempDirectory("copyTest").toString();
        CopyDirectory copyDirectory = new CopyDirectory();
        copyDirectory.copy(pathIn, pathOut);
        Thread.sleep(100);
        assertCopied(pathIn, pathOut);
        deleteFiles(new File(pathOut));
        Files.delete(Paths.get(pathOut));
    }

    private void assertCopied(String sourceDirectoryPath, String destinationDirectoryPath) {     // test
        File sourceDirectory = new File(sourceDirectoryPath);
        File destinationDirectory = new File(destinationDirectoryPath);

        for (String f : Objects.requireNonNull(sourceDirectory.list())) {
            if (new File(sourceDirectory, f).isDirectory())
                assertCopied(new File(sourceDirectory, f).toString(), destinationDirectory.toString());
            else if (!new File(destinationDirectory, f).exists())
                fail(f + " did not exist in destination!");
        }
    }

    private void deleteFiles(File file) throws IOException {                        // Delete all files in a folder [test]
        for (String f : Objects.requireNonNull(file.list()))
            Files.delete(new File(file, f).toPath());
    }
}