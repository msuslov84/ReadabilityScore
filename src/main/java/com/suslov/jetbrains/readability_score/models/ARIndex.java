package com.suslov.jetbrains.readability_score.models;

import com.suslov.jetbrains.readability_score.exceptions.ReadabilityException;

/**
 * @author Mikhail Suslov
 */
public class ARIndex extends ReadabilityIndex {
    private final int characters;

    public ARIndex(int sentences, int words, int characters) {
        super(sentences, words);
        this.characters = characters;
    }

    @Override
    public double score() throws ReadabilityException {
        if (words == 0) {
            throw new ReadabilityException("No words were found in the text. Index couldn't be scored");
        } else if (sentences == 0) {
            throw new ReadabilityException("No sentences was found in the text. Index couldn't be scored");
        }
        score = (4.71 * characters / words) + (0.5 * words / sentences) - 21.43;
        setUpperBoundAge(score);
        return score;
    }

    @Override
    public String toString() {
        return String.format("Automated Readability Index: %.2f (about %d-year-olds).", score, upperBoundAge);
    }
}
