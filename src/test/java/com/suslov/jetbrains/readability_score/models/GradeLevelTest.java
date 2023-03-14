package com.suslov.jetbrains.readability_score.models;

import com.suslov.jetbrains.readability_score.exceptions.ReadabilityException;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Mikhail Suslov
 */
public class GradeLevelTest {

    @Test
    public void getAgeToByScore() {
        Assert.assertEquals(14, GradeLevel.getAgeToByScore(9));
        Assert.assertEquals(7, GradeLevel.getAgeToByScore(2));
        Assert.assertEquals(22, GradeLevel.getAgeToByScore(14));
    }

    @Test(expected = ReadabilityException.class)
    public void getAgeToByInvalidScore() {
        GradeLevel.getAgeToByScore(19);
    }
}