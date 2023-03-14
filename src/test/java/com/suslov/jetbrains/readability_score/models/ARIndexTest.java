package com.suslov.jetbrains.readability_score.models;

import com.suslov.jetbrains.readability_score.exceptions.ReadabilityException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Mikhail Suslov
 */
public class ARIndexTest {
    private ReadabilityIndex index;

    @Before
    public void setUp() {
        index = new ARIndex(4, 46, 217);
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
        double expectedScore = (4.71 * 217 / 46) + (0.5 * 46 / 4) - 21.43;
        Assert.assertEquals(expectedScore, index.score(), 0.000001);
    }

    @Test(expected = ReadabilityException.class)
    public void scoreWithZeroWords() {
        index = new ARIndex(4, 0, 217);
        index.score();
    }

    @Test(expected = ReadabilityException.class)
    public void scoreWithZeroSentences() {
        index = new ARIndex(0, 17, 117);
        index.score();
    }

    @Test
    public void testToString() {
        double expectedScore = (4.71 * 217 / 46) + (0.5 * 46 / 4) - 21.43; // value = 6.54
        index.score();

        String expected = "Automated Readability Index: 6,54 (about 12-year-olds).";

        Assert.assertEquals(expected, index.toString());
    }
}