package com.stockbit.test.solution;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class SolutionTest {

    private Solution solution;

    @BeforeEach
    public void setUp() {
        solution = new Solution();
    }

    @Test
    @DisplayName("Should return array of grouped anagram")
    public void test1() {
        String[][] actualResult = solution.numberFour(new String[]{"kita", "atik", "tika", "aku", "kia", "makan", "kua"});
        assertThat(actualResult, equalTo(new String[][]{{"kita", "atik", "tika"}, {"aku", "kua"}, {"kia"}, {"makan"}}));
    }

    @Test
    @DisplayName("Should return empty string in the bracket")
    public void test2() {
        String actualResult = solution.findFirstStringInBracket("");
        assertThat(actualResult, equalTo(""));
    }

    @Test
    @DisplayName("Should return test in the bracket")
    public void test3() {
        String actualResult = solution.findFirstStringInBracket("(test)");
        assertThat(actualResult, equalTo("test"));
    }
}
