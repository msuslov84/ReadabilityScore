package com.suslov.jetbrains.readability_score.util;

import com.suslov.jetbrains.readability_score.exceptions.ReadabilityException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Mikhail Suslov
 */
public class TextUtilTest {
    private String fileName;
    private final List<String> sentences = new ArrayList<>();
    private final List<String> words = new ArrayList<>();

    @Before
    public void setUp() {
        fileName = "src/test/resources/test.txt";

        sentences.add("This is the front page of the Simple English Wikipedia.");
        sentences.add("We use Simple English words and grammar here.");
        sentences.add("There are 142,262 articles on the Simple English Wikipedia!");
        sentences.add("All of the pages are free to use?");

        words.addAll(Arrays.asList("This", "is", "the", "front", "page", "of", "the", "Simple", "English", "Wikipedia.",
                "We", "use", "Simple", "English", "words", "and", "grammar", "here.", "There", "are", "142,262",
                "articles", "on", "the", "Simple", "English", "Wikipedia!", "All", "of", "the", "pages", "are", "free",
                "to", "use?"));
    }

    @Test
    public void divideTextFromFileToSentences() {
        Assert.assertEquals(sentences, TextUtil.divideTextFromFileToSentences(fileName));
    }

    @Test(expected = ReadabilityException.class)
    public void divideTextFromNonExistentFileToSentences() {
        TextUtil.divideTextFromFileToSentences("notExistent.txt");
    }

    @Test
    public void divideSentencesToWords() {
        Assert.assertEquals(words, TextUtil.divideSentencesToWords(sentences));
    }

    @Test
    public void calculateCharactersInWords() {
        int expectedCount = 0;
        for (String word : words) {
            expectedCount += word.length();
        }

        Assert.assertEquals(expectedCount, TextUtil.calculateCharactersInWords(words));
    }

    @Test
    public void calculateSyllablesInWords() {
        int expectedCount = 48;

        Assert.assertEquals(expectedCount, TextUtil.calculateSyllablesInWords(words));
    }

    @Test
    public void calculatePolysyllablesWords() {
        int expectedCount = 3;

        Assert.assertEquals(expectedCount, TextUtil.calculatePolysyllablesWords(words));
    }

    @Test
    public void countVowelsInWordWithDoubleVowels() {
        String word = "Wikipedia";
        int expectedCount = 4;

        Assert.assertEquals(expectedCount, TextUtil.countVowelsInWord(word));
    }

    @Test
    public void countVowelsInWordWithLastLetterE() {
        String word = "Simple";
        int expectedCount = 1;

        Assert.assertEquals(expectedCount, TextUtil.countVowelsInWord(word));
    }

    @Test
    public void countVowelsInWordWithoutSyllables() {
        String word = "The";
        int expectedCount = 1;

        Assert.assertEquals(expectedCount, TextUtil.countVowelsInWord(word));
    }

    @Test
    public void countVowelsInWordWithMarkInTheEnd() {
        String word = "Comfortable!";
        int expectedCount = 3;

        Assert.assertEquals(expectedCount, TextUtil.countVowelsInWord(word));
    }
}