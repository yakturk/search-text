package com.text.search.engine.program;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * realizes general operations about reading from file,
 * writing into memory,
 * calculating ranking
 */
public class FileOperations {

    private static final Logger logger = LoggerFactory.getLogger(FileOperations.class);
    private WordBank wordBank;

    public FileOperations() {
        wordBank = new WordBank();
    }

    public void readFilesFromDirectory(String directory) {
        try (Stream<Path> paths = Files.walk(Paths.get(directory))) {
            paths.forEach(filePath -> {
                if (Files.isRegularFile(filePath)) {
                    try {
                        readFromFileAndSaveToMemory(filePath.toFile());
                    } catch (IOException exc) {
                        logger.error("Trouble reading from the file: ", filePath);
                    }
                }
            });
        } catch (IOException exc) {
            logger.error("Trouble reading from the directory: ", directory);
        }
    }

    private void readFromFileAndSaveToMemory(File file) throws IOException {
        try (Scanner s = new Scanner(new BufferedReader(new FileReader(file)))) {
            String fileName = file.getName();

            while (s.hasNext()) {
                wordBank.addWordIntoFileBank(fileName, normalizeWord(s.next()));
            }
        }
    }

    public Map<String, Integer> searchCurrentSentence(String sentence) {
        String[] words = sentence.split(" ");
        Map<String, Long> searchedWordsMap = Stream.of(words).map(word -> normalizeWord(word)).collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        Map<String, Long> resultOfFiles = wordBank.searchWord(searchedWordsMap);
        Map<String, Integer> finalResult = new HashMap<>();

        int searchedWordCount = words.length;
        resultOfFiles.entrySet().stream().forEach(e -> finalResult.put(e.getKey(), getRank(searchedWordCount, e.getValue())));

        return finalResult.entrySet()
                .stream()
                .sorted((Map.Entry.<String, Integer>comparingByValue().reversed()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

    }

    private int getRank(int searchedWordCount, long countInFile) {
        return (int) countInFile * 100 / searchedWordCount;
    }


    private static String normalizeWord(String word) {
        return word.replaceAll("[^\\p{IsAlphabetic}]", "").toLowerCase().trim();
    }

    public int getReadFileCount() {
        return wordBank.getFileWordMap().size();
    }
}
