package com.text.search.engine.program;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class FileOperationsTest {

    @InjectMocks
    private FileOperations fileOperations;

    @Spy
    private WordBank wordBank;

    String currentWorkingDir = Paths.get("").toAbsolutePath().toString();
    String directory = currentWorkingDir.replace("\\", "/") + "/src/test/files";

    @Test
    public void readFilesFromDirectory() {
        fileOperations.readFilesFromDirectory(directory);

        Assert.assertEquals(4, fileOperations.getReadFileCount());
    }

    @Test
    public void searchCurrentSentence() {
        Map<String, Long> wordBankMapResult = new HashMap<>();
        wordBankMapResult.put("file1", 6L);
        wordBankMapResult.put("file2", 0L);
        wordBankMapResult.put("file3", 3L);
        wordBankMapResult.put("file4", 1L);

        when(wordBank.searchWord(anyMap())).thenReturn(wordBankMapResult);
        Map<String, Integer> resultMap = fileOperations.searchCurrentSentence("to be or not to be");

        Assert.assertEquals(100, resultMap.get("file1").intValue());
        Assert.assertEquals(0, resultMap.get("file2").intValue());
        Assert.assertEquals(50, resultMap.get("file3").intValue());
        Assert.assertEquals(16, resultMap.get("file4").intValue());
    }
}
