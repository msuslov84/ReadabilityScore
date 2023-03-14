package com.suslov.jetbrains.readability_score.models;

import com.suslov.jetbrains.readability_score.exceptions.ReadabilityException;

/**
 * @author Mikhail Suslov
 */
public class ColemanLiauIndex extends ReadabilityIndex {
    private final int characters;

    public ColemanLiauIndex(int sentences, int words, int characters) {
        super(sentences, words);
        this.characters = characters;
    }

    @Override
    public double score() throws ReadabilityException {
        if (words == 0) {
            throw new ReadabilityException("No words were found in the text. Index couldn't be scored");
        }
        score = (0.0588 * 100 * characters / words) - 0.296 * 100 * sentences / words - 15.8;
        setUpperBoundAge(score);
        return score;
    }

    @Override
    public String toString() {
        return String.format("Coleman–Liau index: %.2f (about %d-year-olds).", score, upperBoundAge);
    }
}
