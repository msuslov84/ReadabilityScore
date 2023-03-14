package com.suslov.jetbrains.readability_score;

import com.suslov.jetbrains.readability_score.exceptions.ReadabilityException;
import com.suslov.jetbrains.readability_score.models.*;
import com.suslov.jetbrains.readability_score.util.TextUtil;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class ReadabilityScore {
    private int wordsNumber;
    private int sentencesNumber;
    private int charactersNumber;
    private int syllablesNumber;
    private int polysyllablesNumber;

    public static void main(String[] args) {
        if (args.length == 0) {
            throw new IllegalArgumentException("The file for reading is absent in the input parameters");
        }
        String fileName = args[0];

        ReadabilityScore score = new ReadabilityScore();
        score.calculate(fileName);
    }

    public ReadabilityScore() {
    }

    void calculate(String fileName) {
        try {
            parseTextFile(fileName);
        } catch (ReadabilityException exp) {
            System.out.println(exp.getMessage());
            return;
        }
        calculateReadabilityIndices();
    }

    void parseTextFile(String fileName) throws ReadabilityException {
        List<String> sentences = TextUtil.divideTextFromFileToSentences(fileName);
        sentencesNumber = sentences.size();

        List<String> words = TextUtil.divideSentencesToWords(sentences);
        wordsNumber = words.size();

        charactersNumber = TextUtil.calculateCharactersInWords(words);
        syllablesNumber = TextUtil.calculateSyllablesInWords(words);
        polysyllablesNumber = TextUtil.calculatePolysyllablesWords(words);

        System.out.printf("Words: %d\n", wordsNumber);
        System.out.printf("Sentences: %d\n", sentencesNumber);
        System.out.printf("Characters: %d\n", charactersNumber);
        System.out.printf("Syllables: %d\n", syllablesNumber);
        System.out.printf("Polysyllables: %d\n", polysyllablesNumber);
    }

    void calculateReadabilityIndices() {
        Scanner scanner = new Scanner(System.in);
        boolean invalidInput = false;
        do {
            System.out.println("Enter the score you want to calculate (ARI, FK, SMOG, CL, all):");
            switch (scanner.nextLine()) {
                case "ARI":
                    representReadableIndex(new ARIndex(sentencesNumber, wordsNumber, charactersNumber));
                    break;
                case "FK":
                    representReadableIndex(new FleschKincaidIndex(sentencesNumber, wordsNumber, syllablesNumber));
                    break;
                case "SMOG":
                    representReadableIndex(new SMOGIndex(sentencesNumber, wordsNumber, polysyllablesNumber));
                    break;
                case "CL":
                    representReadableIndex(new ColemanLiauIndex(sentencesNumber, wordsNumber, charactersNumber));
                    break;
                case "all":
                    ARIndex ari = new ARIndex(sentencesNumber, wordsNumber, charactersNumber);
                    FleschKincaidIndex fk = new FleschKincaidIndex(sentencesNumber, wordsNumber, syllablesNumber);
                    SMOGIndex smog = new SMOGIndex(sentencesNumber, wordsNumber, polysyllablesNumber);
                    ColemanLiauIndex cl = new ColemanLiauIndex(sentencesNumber, wordsNumber, charactersNumber);

                    representReadableIndex(ari);
                    representReadableIndex(fk);
                    representReadableIndex(smog);
                    representReadableIndex(cl);

                    System.out.printf("This text should be understood in average by %.2f-year-olds.\n",
                            getAverageAgeForUnderstandText(ari, fk, smog, cl));
                    break;
                default:
                    System.out.println("Invalid type of index was chosen. Please, try again...");
                    invalidInput = true;
            }
        } while (invalidInput);
        scanner.close();
    }

    void representReadableIndex(ReadabilityIndex index) {
        try {
            index.score();
            System.out.println(index);
        } catch (ReadabilityException exp) {
            System.out.println(exp.getMessage());
        }
    }

    double getAverageAgeForUnderstandText(ReadabilityIndex... indices) {
        return Arrays.stream(indices).mapToInt(ReadabilityIndex::getUpperBoundAge).average().orElse(0);
    }
}