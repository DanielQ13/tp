package seedu.address.commons.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CosineNGramUtilTest {

    private CosineNGramUtil cosineNGramUtil;

    @BeforeEach
    public void setup() {
        cosineNGramUtil = new CosineNGramUtil(2);
    }

    @Test
    public void similarity_identicalStrings_returnsOne() {
        double result = cosineNGramUtil.similarity("hello", "hello");
        assertEquals(1.0, result);
    }

    @Test
    public void similarity_similarStrings_returnsHighSimilarity() {
        double result = cosineNGramUtil.similarity("hello", "hell");
        assertTrue(result > 0.5);
    }

    @Test
    public void similarity_neverGreaterThanOne() {
        double result = cosineNGramUtil.similarity("banana", "banana");
        assertTrue(result <= 1.0);
    }

    @Test
    public void similarity_neverNegative() {
        double result = cosineNGramUtil.similarity("abc", "xyz");
        assertTrue(result >= 0.0);
    }

    @Test
    public void similarity_differentStrings_returnsLowSimilarity() {
        double result = cosineNGramUtil.similarity("hello", "world");
        assertTrue(result < 0.5);
    }

    @Test
    public void similarity_repeatedCharacterHandledCorrectly() {
        double result = cosineNGramUtil.similarity("aaaa", "aaaa");
        assertEquals(1.0, result);
    }

    @Test
    public void similarity_repeatedCharacterPartialMatch() {
        double result = cosineNGramUtil.similarity("aaaa", "aaab");
        assertTrue(result > 0.0);
        assertTrue(result < 1.0);
    }

    @Test
    public void similarity_partialOverlap_returnsImmediateScore() {
        double result = cosineNGramUtil.similarity("night", "nacht");
        assertTrue(result > 0.0);
        assertTrue(result < 1.0);
    }

    @Test
    public void similarity_caseInsensitive() {
        double result = cosineNGramUtil.similarity("HELLO", "hello");
        assertEquals(1.0, result);
    }

    @Test
    public void similarity_whitespaceIgnored() {
        double result = cosineNGramUtil.similarity("he llo", "hello");
        assertEquals(1.0, result);
    }

    @Test
    public void similarity_multipleSpaceIgnored() {
        double result = cosineNGramUtil.similarity("h e    l l o", "hello");
        assertEquals(1.0, result);
    }

    @Test
    public void similarity_bothEmpty_returnsZero() {
        double result = cosineNGramUtil.similarity("", "");
        assertEquals(0.0, result);
    }

    @Test
    public void similarity_oneEmpty_returnsZero() {
        double result = cosineNGramUtil.similarity("", "hello");
        assertEquals(0.0, result);
    }

    @Test
    public void similarity_whitespacesOnly_returnsZero() {
        double result = cosineNGramUtil.similarity("  ", "hello");
        assertEquals(0.0, result);
    }

    @Test
    public void similarity_stringShorterThanNGram_returnsZero() {
        CosineNGramUtil cosineNGramUtil1 = new CosineNGramUtil(3);
        double result = cosineNGramUtil1.similarity("hi", "hi");
        assertEquals(0.0, result);
    }

    @Test
    public void similarity_singleCharacterStrings_returnsZero() {
        double result = cosineNGramUtil.similarity("a", "a");
        assertEquals(0.0, result);
    }

    @Test
    public void similarity_noSharedNgrams_returnsZero() {
        double result = cosineNGramUtil.similarity("abcd", "wxyz");
        assertEquals(0.0, result);
    }

    @Test
    public void similarity_isSymmetric() {
        double ab = cosineNGramUtil.similarity("hello", "world");
        double ba = cosineNGramUtil.similarity("world", "hello");
        assertEquals(ab, ba);
    }

    @Test
    public void similarity_specialCharacters() {
        double result = cosineNGramUtil.similarity("hello!", "hello");
        assertTrue(result < 1.0);
    }

    @Test
    public void similarity_longStrings_runCorrectly() {
        String s1 = "a".repeat(1000);
        String s2 = "a".repeat(1000);
        double result = cosineNGramUtil.similarity(s1, s2);
        assertEquals(1.0, result);
    }

    @Test
    public void computeNorm_validVector_correctNorm() {
        Map<String, Integer> vector = Map.of(
                "ab", 2,
                "bc", 1
        );

        double norm = cosineNGramUtil.computeNorm(vector);
        assertEquals(5.0, norm);
    }

    @Test
    public void computeNorm_emptyVector_returnsZero() {
        double norm = cosineNGramUtil.computeNorm(Map.of());
        assertEquals(0.0, norm);
    }

}
