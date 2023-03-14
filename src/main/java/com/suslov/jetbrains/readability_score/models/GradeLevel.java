package com.suslov.jetbrains.readability_score.models;

import com.suslov.jetbrains.readability_score.exceptions.ReadabilityException;

import java.util.Arrays;
import java.util.Optional;

/**
 * @author Mikhail Suslov
 */
public enum GradeLevel {
    KINDERGARTEN(1, 5, 6),
    FIRST(2, 6, 7),
    SECOND(3, 7, 8),
    THIRD(4, 8, 9),
    FOURTH(5, 9, 10),
    FIFTH(6, 10, 11),
    SIXTH(7, 11, 12),
    SEVENTH(8, 12, 13),
    EIGHTH(9, 13, 14),
    NINTH(10, 14, 15),
    TENTH(11, 15, 16),
    ELEVENTH(12, 16, 17),
    TWELFTH(13, 17, 18),
    COLLEGE(14, 18, 22);

    private final int ageFrom;
    private final int ageTo;
    private final int score;

    GradeLevel(int score, int ageFrom, int ageTo) {
        this.score = score;
        this.ageFrom = ageFrom;
        this.ageTo = ageTo;
    }

    public static int getAgeToByScore(int score) {
        Optional<GradeLevel> gradeLevel = Arrays.stream(GradeLevel.values()).filter(gl -> gl.score == score).findAny();
        if (!gradeLevel.isPresent()) {
            throw new ReadabilityException(String.format("Invalid input score: %d. No corresponding grade level.\n", score));
        }
        return gradeLevel.get().ageTo;
    }
}
