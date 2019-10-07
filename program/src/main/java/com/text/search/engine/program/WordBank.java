package com.text.search.engine.program;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


/**
 * Keeps all words with a file name
 */
public class WordBank {

    private Map<String, Map<String, Long>> fileWordMap;

    public WordBank() {
        fileWordMap = new HashMap<>();
    }

    public void addWordIntoFileBank(String fileName, String word) {
        if (fileWordMap.containsKey(fileName)) {
            Map<String, Long> wordCountMap = fileWordMap.get(fileName);
            wordCountMap.put(word, wordCountMap.containsKey(word) ? wordCountMap.get(word) + 1 : 1L);
            fileWordMap.put(fileName, wordCountMap);
        } else {
            Map<String, Long> wordCountMap = new HashMap<>();
            wordCountMap.put(word, 1L);
            fileWordMap.put(fileName, wordCountMap);
        }
    }

    public Map<String, Long> searchWord(Map<String, Long> searchedWords) {
        Map<String, Long> fileResults = new HashMap<>();

        Iterator iterator = fileWordMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry map = (Map.Entry) iterator.next();
            String fileName = (String) map.getKey();
            Map<String, Long> wordsInCurrentFile = (Map<String, Long>) map.getValue();

            Long total = 0L;

            Iterator iterator2 = searchedWords.entrySet().iterator();
            while (iterator2.hasNext()) {
                Map.Entry wordEntry = (Map.Entry) iterator2.next();
                String word = (String) wordEntry.getKey();
                long count = ((Long) wordEntry.getValue());

                if (wordsInCurrentFile.containsKey(word)) {
                    long numberFoundInFile = wordsInCurrentFile.get(word);
                    total += numberFoundInFile > count ? count : numberFoundInFile;
                }
            }
            if (total > 0)
                fileResults.put(fileName, total);
        }
        return fileResults;
    }

    public Map<String, Map<String, Long>> getFileWordMap() {
        return fileWordMap;
    }

    public void setFileWordMap(Map<String, Map<String, Long>> fileWordMap) {
        this.fileWordMap = fileWordMap;
    }
}