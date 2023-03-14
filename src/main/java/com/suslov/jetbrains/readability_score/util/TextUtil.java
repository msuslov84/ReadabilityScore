package com.suslov.jetbrains.readability_score.util;

import com.suslov.jetbrains.readability_score.exceptions.ReadabilityException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Mikhail Suslov
 */
public class TextUtil {

    private TextUtil() {
    }

    public static List<String> divideTextFromFileToSentences(String fileName) throws ReadabilityException {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            return br.lines()
                    .filter(s -> s.length() > 0)
                    // We need to keep a full stop, exclamation and question marks at the end of sentences
                    .map(s -> s.replaceAll("\\.\\s+", ".. "))
                    .map(s -> s.replaceAll("!\\s+", "!! "))
                    .map(s -> s.replaceAll("\\?\\s+", "?? "))
                    .flatMap(s -> Stream.of(s.trim().split("[.!?]\\s+")))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new ReadabilityException("Error by reading input file.", e.getCause());
        }
    }

    public static List<String> divideSentencesToWords(List<String> sentences) {
        return sentences.stream()
                .flatMap(s -> Stream.of(s.split("\\s+")))
                .collect(Collectors.toList());
    }

    public static int calculateCharactersInWords(List<String> words) {
        return words.stream()
                .map(String::length)
                .reduce(Integer::sum)
                .orElse(0);
    }

    public static int calculateSyllablesInWords(List<String> words) {
        return words.stream()
                .map(TextUtil::countVowelsInWord)
                .reduce(Integer::sum)
                .orElse(0);
    }

    public static int calculatePolysyllablesWords(List<String> words) {
        return (int) words.stream()
                .map(TextUtil::countVowelsInWord)
                .filter(i -> i > 2)
                .count();
    }

    public static int countVowelsInWord(String word) {
        Pattern compiler = Pattern.compile("[aeiouy]+");
        List<String> vowels = new ArrayList<>();

        String[] split = word.toLowerCase().split("e\\.$|e!$|e[?]$|e,|e |e[),]|e$|[,.!?]$");
        for (String str : split) {
            Matcher matcher = compiler.matcher(str);
            while (matcher.find()) {
                vowels.add(matcher.group());
            }
        }
        return vowels.size() == 0 ? 1 : vowels.size();
    }
}
