package com.text.search.engine.program;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


import java.util.Map;
import java.util.Scanner;


@SpringBootApplication
public class ProgramApplication {

    private static final Logger logger = LoggerFactory.getLogger(FileOperations.class);
    private static final int TOP_NUMBER_OF_RANK = 10;

    public static void main(String[] args) {
        SpringApplication.run(ProgramApplication.class, args);

        if (args == null || args.length <= 0) {
            logger.info("Directory could not find");

            throw new IllegalArgumentException("There is no directory!!!");
        }

        String directory = args[0];
        FileOperations fileOperations = new FileOperations();
        fileOperations.readFilesFromDirectory(directory);

        int readFileCount = fileOperations.getReadFileCount();
        System.out.println(readFileCount + " files read in directory " + directory);

        if (readFileCount > 0) {
            logger.info(readFileCount + " files read");

            readFromCommandLine(fileOperations);
        }
        else {
            logger.info("Any file could not be read");
            System.out.println("Any file could not be read");
        }

    }

    public static void readFromCommandLine(FileOperations fileOperations) {
        Scanner keyboard = new Scanner(System.in);

        System.out.println("\n Welcome to Text Search Engine. You can search a text inside of files under a folder.");
        System.out.println("Write a text to search and then click Enter.");
        System.out.println("To quit from system, write qqqq \n");

        while (true) {
            System.out.println("search> ");
            final String line = keyboard.nextLine();

            if (line.equals("quit"))
                break;

            Map<String, Integer> result = fileOperations.searchCurrentSentence(line);
            if (result == null || result.size() == 0) {
                System.out.println("no matches found");
            } else {
                result.entrySet().stream().limit(TOP_NUMBER_OF_RANK).forEach(e -> System.out.println(e.toString() + "%"));
            }
        }
    }
}
