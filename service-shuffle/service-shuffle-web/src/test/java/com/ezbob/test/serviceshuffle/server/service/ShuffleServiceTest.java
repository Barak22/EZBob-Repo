package com.ezbob.test.serviceshuffle.server.service;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.ezbob.test.serviceshuffle.server.service.HasTheSameElementMatcher.hasTheSameElementsAs;
import static org.junit.Assert.assertThat;

class ShuffleServiceTest {

    ShuffleService shuffleService = new ShuffleService();

    @Test
    public void randomArrayForNumberOne() {
        List<Integer> list = shuffleService.generateRandomArray(1);
        List<Integer> expected = Collections.singletonList(1);

        assertThat("The size of the lists is not equal", list, hasTheSameElementsAs(expected));
    }

    @Test
    public void randomArrayForNumberTwo() {
        List<Integer> list = shuffleService.generateRandomArray(2);
        List<Integer> expected = Arrays.asList(1, 2);

        assertThat("The size of the lists is not equal", list, hasTheSameElementsAs(expected));
    }

    @Test
    public void randomArrayForNumberFive() {
        List<Integer> list = shuffleService.generateRandomArray(5);
        List<Integer> expected = Arrays.asList(1, 2, 3, 4, 5);

        assertThat("Lists are not equal", list, hasTheSameElementsAs(expected));
    }

    @Test
    public void randomArrayForNumberTen() {
        List<Integer> list = shuffleService.generateRandomArray(10);
        List<Integer> expected = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        assertThat("Lists are not equal", list, hasTheSameElementsAs(expected));
    }
}

class HasTheSameElementMatcher extends TypeSafeMatcher<List<Integer>> {

    List<Integer> expected;

    public HasTheSameElementMatcher(List<Integer> expected) {
        this.expected = expected;
    }

    @Override
    protected boolean matchesSafely(List<Integer> actual) {
        Collections.sort(actual);
        return expected.equals(actual);
    }

    @Override
    public void describeTo(Description description) {
        description.appendText(expected.toString());
    }

    public static Matcher<List<Integer>> hasTheSameElementsAs(List<Integer> expected) {
        return new HasTheSameElementMatcher(expected);
    }
}