package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class FileWriterImplTest {
    private static String data;
    private static final String DESTINATION_FILE = "src/test/java/testfiles/testresult.txt";
    private static final String SOURCE_FILE = "src/test/java/testfiles/SourceFileTest.txt";
    private final FileWriterImpl fileWriter = new FileWriterImpl();

    @Before
    public void setUp() throws Exception {
        List<String> dataTest = Files.readAllLines(Path.of(SOURCE_FILE));
        StringBuilder stringBuilder = new StringBuilder();
        dataTest.remove("type,fruit,quantity");
        for (String line: dataTest) {
            stringBuilder.append(line).append(System.lineSeparator());
        }
        data = stringBuilder.toString().trim();
    }

    @Test(expected = RuntimeException.class)
    public void recordDataToFile_resultFileNull_Ok() {
        fileWriter.write(null, data);
    }

    @Test(expected = RuntimeException.class)
    public void recordDataToFile_dataNull_Ok() {
        fileWriter.write(DESTINATION_FILE, null);
    }

    @Test
    public void recordDataToFile_validData_Ok() {
        fileWriter.write(DESTINATION_FILE, data);
        StringBuilder result = new StringBuilder();
        try {
            List<String> actual = Files.readAllLines(Path.of(DESTINATION_FILE));
            for (String line: actual) {
                result.append(line).append(System.lineSeparator());
            }
            assertEquals(data, result.toString().trim());
        } catch (IOException e) {
            throw new RuntimeException("Unable to read test file " + DESTINATION_FILE);
        }
    }
}