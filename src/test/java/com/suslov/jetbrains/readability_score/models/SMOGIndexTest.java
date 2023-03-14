package com.suslov.jetbrains.readability_score.models;

import com.suslov.jetbrains.readability_score.exceptions.ReadabilityException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Mikhail Suslov
 */
public class SMOGIndexTest {
    private ReadabilityIndex index;

    @Before
    public void setUp() {
        index = new SMOGIndex(4, 46, 5);
    }

    @Test
    public void setUpperBoundAge() {
        index.setUpperBoundAge(6.54D);
        Assert.assertEquals(12, index.getUpperBoundAge());
    }

    @Test(expected = ReadabilityException.class)
    public void setUpperBoundAgeInvalidScore() {
        index.setUpperBoundAge(18D);
    }

    @Test
    public void score() {
        double expectedScore = 1.043 * Math.sqrt(1.0 * 5 * 30 / 4) + 3.1291;
        Assert.assertEquals(expectedScore, index.score(), 0.000001);
    }

    @Test(expected = ReadabilityException.class)
    public void scoreWithZeroSentences() {
        index = new SMOGIndex(0, 46, 5);
        index.score();
    }

    @Test
    public void testToString() {
        double expectedScore = 1.043 * Math.sqrt(1.0 * 5 * 30 / 4) + 3.1291; // value = 9.52
        index.score();

        String expected = "Simple Measure of Gobbledygook: 9,52 (about 15-year-olds).";

        Assert.assertEquals(expected, index.toString());
    }

}