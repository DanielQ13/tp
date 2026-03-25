package seedu.address.commons.util;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Utility class for computing string similarity using the
 * Levenshtein Distance algorithm.
 * <p>
 * The Levenshtein distance measures the minimum
 * number of single-character edits required to transform one string into another.
 * <p>
 * This implementation converts the edit distance into a similarity score
 * between {@code 0.0} and {@code 1.0}
 * <ul>
 *     <li>{@code 1.0} indicates identical string</li>
 *     <li>{@code 0.0} indicates completely different strings</li>
 * </ul>
 *
 * The edit distance is computed using a dynamic programming approach
 * with a two-dimensional table.
 */
public class LevenshteinDistanceUtil implements SimilarityMetric {

    private static final Logger logger = Logger.getLogger(LevenshteinDistanceUtil.class.getName());


    /**
     * Compute the similarity between two strings using Levenshtein Distance.
     * <p>
     * The similarity score is derived from the edit distance and
     * normalized based on the maximum length of the two strings.
     * <pre>
     *     similarity = 1 - (distance / maxlength)
     * </pre>
     * If either input string is {@code null}, it is treated as an empty string.
     * The comparison is case-insensitive.
     *
     * @param first  First string to compare
     * @param second Second string to compare
     * @return a similarity score between 0.0 and 1.0
     */
    @Override
    public double similarity(String first, String second) {
        assert first != null : "String 'first' must not be null";
        assert second != null : "String 'second' must not be null";

        String string1 = normalize(first);
        String string2 = normalize(second);

        if (string1.equals(string2)) {
            logger.log(Level.FINE, "Strings are identical after normalization.");
            return 1.0;
        }

        if (string1.length() == 0 && string2.length() == 0) {
            logger.log(Level.FINE, "Both strings are empty");
            return 1.0;
        }

        int[][] dp = createDistanceTable(string1.length(), string2.length());
        fillDistanceTable(dp, string1, string2);

        int distance = dp[string1.length()][string2.length()];
        int maxLength = Math.max(string1.length(), string2.length());

        if (maxLength == 0) {
            return 1.0;
        }

        return 1.0 - ((double) distance / maxLength);
    }

    private String normalize(String input) {
        return input == null ? "" : input.toLowerCase();
    }

    /**
     * Creates and initialize the dynamic programming table used to compute the
     * edit distance.
     * <p>
     * The first row represents the cost of converting an empty string into prefixes
     * of the second string. The first column represents the cost of converting prefixes
     * of the first string into an empty string
     *
     * @param rows length of the first string
     * @param cols length of the second string
     * @return the initialized distance table
     */
    private int[][] createDistanceTable(int rows, int cols) {
        int[][] table = new int[rows + 1][cols + 1];

        for (int i = 0; i <= rows; i++) {
            table[i][0] = i;
        }

        for (int i = 0; i <= cols; i++) {
            table[0][i] = i;
        }

        return table;
    }

    /**
     * Fills the dynamic programming table using the Levenshtein distance
     * recurrence relation.
     * <p>
     * <pre>
     * table[i][j] = min(
     *       table[i - 1][j] + 1,     // deletion
     *       table[i][j - 1] + 1,    // insertion
     *       table[i - 1][j - 1] + cost // substitution
     * )
     * </pre>
     * where {@code cost} is {@code 0} if the characters are equal,
     *  otherwise {@code 1}.
     *
     * @param table    The dynamic programming table storing the immediate edit
     *                 distance
     * @param string1  The normalized first string
     * @param string2  The normalized second string
     */
    private void fillDistanceTable(int[][] table, String string1, String string2) {

        for (int i = 1; i <= string1.length(); i++) {
            for (int j = 1; j <= string2.length(); j++) {
                table[i][j] = Math.min(
                        Math.min(table[i - 1][j] + 1,
                                    table[i][j - 1] + 1),
                        table[i - 1][j - 1]
                                + (string1.charAt(i - 1) == string2.charAt(j - 1) ? 0 : 1)
                );
            }
        }
    }
}
