package com.suslov.jetbrains.readability_score.models;

import com.suslov.jetbrains.readability_score.exceptions.ReadabilityException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Mikhail Suslov
 */
public class FleschKincaidIndexTest {
    private ReadabilityIndex index;

    @Before
    public void setUp() {
        index = new FleschKincaidIndex(4, 46, 65);
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
        double expectedScore = (0.39 * 46 / 4) + (11.8 * 65 / 46) - 15.59;
        Assert.assertEquals(expectedScore, index.score(), 0.000001);
    }

    @Test(expected = ReadabilityException.class)
    public void scoreWithZeroWords() {
        index = new ColemanLiauIndex(4, 0, 65);
        index.score();
    }

    @Test(expected = ReadabilityException.class)
    public void scoreWithZeroSentences() {
        index = new ColemanLiauIndex(0, 46, 65);
        index.score();
    }

    @Test
    public void testToString() {
        double expectedScore = (0.39 * 46 / 4) + (11.8 * 65 / 46) - 15.59; // value = 5.57
        index.score();

        String expected = "Flesch–Kincaid readability tests: 5,57 (about 11-year-olds).";

        Assert.assertEquals(expected, index.toString());
    }


}