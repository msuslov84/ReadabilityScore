package com.suslov.jetbrains.readability_score.models;

import com.suslov.jetbrains.readability_score.exceptions.ReadabilityException;

/**
 * @author Mikhail Suslov
 */
public class SMOGIndex extends ReadabilityIndex {
    private final int polysyllables;

    public SMOGIndex(int sentences, int words, int polysyllables) {
        super(sentences, words);
        this.polysyllables = polysyllables;
    }

    @Override
    public double score() throws ReadabilityException {
        if (sentences == 0) {
            throw new ReadabilityException("No sentences was found in the text. Index couldn't be scored");
        }
        score = 1.043 * Math.sqrt(1.0 * polysyllables * 30 / sentences) + 3.1291;
        setUpperBoundAge(score);
        return score;
    }

    @Override
    public String toString() {
        return String.format("Simple Measure of Gobbledygook: %.2f (about %d-year-olds).", score, upperBoundAge);
    }
}
