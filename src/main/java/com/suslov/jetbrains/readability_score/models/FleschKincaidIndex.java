package com.suslov.jetbrains.readability_score.models;

import com.suslov.jetbrains.readability_score.exceptions.ReadabilityException;

/**
 * @author Mikhail Suslov
 */
public class FleschKincaidIndex extends ReadabilityIndex {
    private final int syllables;

    public FleschKincaidIndex(int sentences, int words, int syllables) {
        super(sentences, words);
        this.syllables = syllables;
    }

    @Override
    public double score() throws ReadabilityException {
        if (words == 0) {
            throw new ReadabilityException("No words were found in the text. Index couldn't be scored");
        } else if (sentences == 0) {
            throw new ReadabilityException("No sentences was found in the text. Index couldn't be scored");
        }
        score = (0.39 * words / sentences) + (11.8 * syllables / words) - 15.59;
        setUpperBoundAge(score);
        return score;
    }

    @Override
    public String toString() {
        return String.format("Flesch–Kincaid readability tests: %.2f (about %d-year-olds).", score, upperBoundAge);
    }
}
