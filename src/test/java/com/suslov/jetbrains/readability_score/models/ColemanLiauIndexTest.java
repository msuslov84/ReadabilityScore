package com.suslov.jetbrains.readability_score.models;

import com.suslov.jetbrains.readability_score.exceptions.ReadabilityException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Mikhail Suslov
 */
public class ColemanLiauIndexTest {
    private ReadabilityIndex index;

    @Before
    public void setUp() {
        index = new ColemanLiauIndex(4, 46, 217);
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
        double expectedScore = (0.0588 * 100 * 217 / 46) - 0.296 * 100 * 4 / 46 - 15.8;
        Assert.assertEquals(expectedScore, index.score(), 0.000001);
    }

    @Test(expected = ReadabilityException.class)
    public void scoreWithZeroWords() {
        index = new ColemanLiauIndex(4, 0, 217);
        index.score();
    }

    @Test
    public void testToString() {
        double expectedScore = (0.0588 * 100 * 217 / 46) - 0.296 * 100 * 4 / 46 - 15.8; // value = 9.36
        index.score();

        String expected = "Coleman–Liau index: 9,36 (about 14-year-olds).";

        Assert.assertEquals(expected, index.toString());
    }
}