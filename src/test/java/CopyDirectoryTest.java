import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertTrue;

class CopyDirectoryTest {

    CopyDirectory copyDirectory = new CopyDirectory();

    @Test
    void testFilesCopy1() throws IOException {
        copyDirectory.setIn("test-file\\test-in\\folder1");
        copyDirectory.setOut("test-file\\test-out");
        copyDirectory.copy();
        assertTrue(copyDirectory.checkFiles(copyDirectory.getFileIn(), copyDirectory.getFileOut()));
        copyDirectory.deleteFiles(copyDirectory.getFileOut());
    }

    @Test
    void testFileCopy2() throws IOException {
        copyDirectory.setIn("test-file\\test-in\\folder2");
        copyDirectory.setOut("test-file\\test-out");
        copyDirectory.copy();
        assertTrue(copyDirectory.checkFiles(copyDirectory.getFileIn(), copyDirectory.getFileOut()));
        copyDirectory.deleteFiles(copyDirectory.getFileOut());
    }

    @Test
    void testFileCopy3() throws IOException {
        copyDirectory.setIn("test-file\\test-in");
        copyDirectory.setOut("test-file\\test-out");
        copyDirectory.copy();
        assertTrue(copyDirectory.checkFiles(copyDirectory.getFileIn(), copyDirectory.getFileOut()));
        copyDirectory.deleteFiles(copyDirectory.getFileOut());
    }
}