package com.text.search.engine.program;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.HashMap;
import java.util.Map;


@RunWith(MockitoJUnitRunner.class)
public class WordBankTest {

    @InjectMocks
    private WordBank wordBank;

    @Test
    public void addWordIntoFileBank() {
        wordBank.addWordIntoFileBank("file1", "word1");
        wordBank.addWordIntoFileBank("file1", "word1");
        wordBank.addWordIntoFileBank("file1", "word2");

        wordBank.addWordIntoFileBank("file2", "word2");
        Map<String, Long> file1Map = wordBank.getFileWordMap().get("file1");
        Map<String, Long> file2Map = wordBank.getFileWordMap().get("file2");

        Assert.assertEquals(2, file1Map.get("word1").intValue());
        Assert.assertEquals(1, file1Map.get("word2").intValue());

        Assert.assertEquals(1, file2Map.get("word2").intValue());
    }

    @Test
    public void searchWord() {
        Map<String, Long> searchedWords = new HashMap<>();
        searchedWords.put("to", 2L);
        searchedWords.put("be", 2L);
        searchedWords.put("or", 1L);
        searchedWords.put("not", 1L);

        wordBank.addWordIntoFileBank("file1", "to");
        wordBank.addWordIntoFileBank("file1", "be");
        wordBank.addWordIntoFileBank("file1", "or");
        wordBank.addWordIntoFileBank("file1", "not");
        wordBank.addWordIntoFileBank("file1", "to");
        wordBank.addWordIntoFileBank("file1", "be");

        wordBank.addWordIntoFileBank("file2", "to");
        wordBank.addWordIntoFileBank("file2", "be");

        Map<String, Long> fileResults = wordBank.searchWord(searchedWords);
        Assert.assertEquals(6, fileResults.get("file1").intValue());

        Assert.assertEquals(2, fileResults.get("file2").intValue());
    }
}