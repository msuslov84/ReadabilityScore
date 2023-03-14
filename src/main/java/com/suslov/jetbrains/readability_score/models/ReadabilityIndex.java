package com.suslov.jetbrains.readability_score.models;

import com.suslov.jetbrains.readability_score.exceptions.ReadabilityException;

/**
 * @author Mikhail Suslov
 */
public abstract class ReadabilityIndex {
    final int sentences;
    final int words;
    double score;
    int upperBoundAge;

    public ReadabilityIndex(int sentences, int words) {
        this.sentences = sentences;
        this.words = words;
    }

    public int getUpperBoundAge() {
        return upperBoundAge;
    }

    public abstract double score() throws ReadabilityException;

    void setUpperBoundAge(double score) {
        upperBoundAge = GradeLevel.getAgeToByScore((int) Math.round(score));
    }
}
