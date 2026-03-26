package seedu.address.commons.util;

import java.util.HashMap;
import java.util.Map;

/**
 * Utility class that computes the cosine similarity between two strings
 * using character n-grams.
 * <p>
 * The strings are first normalized by converting to lowercase and removing
 * whitespace. Each string is then converted into a vector of n-gram frequencies.
 * Cosine similarity is computed between these vectors to measure how similar the
 * two strings are.
 */
public class CosineNGramUtil implements SimilarityMetric {

    private final int nGramSize;

    /**
     * Constructs a {@code CosineNGramUtil} with the specified n-gram size.
     *
     * @param nGramSize The size of each n-gram (e.g. 2 for bigrams, 3 for trigrams)
     * @throws IllegalArgumentException if {@code nGramSize <= 0}
     */
    public CosineNGramUtil(int nGramSize) {
        if (nGramSize <= 0) {
            throw new IllegalArgumentException("nGramSize > 0");
        }

        this.nGramSize = nGramSize;
    }

    /**
     * Computes the cosine similarity between two strings using n-gram vectors.
     * <p>
     * The process consists of:
     * <ol>
     *     <li>Normalizing both strings</li>
     *     <li>Generating n-gram frequency vectors</li>
     *     <li>Computing the cosine similarity between the vectors</li>
     * </ol>
     *
     * @param first  The first string to compare
     * @param second The second string to compare
     * @return a similarity score between {@code 0.0} and {@code 1.0},
     *          where {@code 1.0} indicates identical n-gram composition and
     *          {@code 0.0} indicates no similarity.
     */
    @Override
    public double similarity(String first, String second) {
        if (first == null || second == null) {
            return 0.0;
        }

        Map<String, Integer> vector1 = buildVector(first);
        Map<String, Integer> vector2 = buildVector(second);

        double dotProduct = computeDotProduct(vector1, vector2);
        double normA = computeNorm(vector1);
        double normB = computeNorm(vector2);

        if (normA == 0 || normB == 0) {
            return 0.0;
        }

        double denominator = Math.sqrt(normA) * Math.sqrt(normB);
        return dotProduct / denominator;
    }

    /**
     * Builds an n-gram frequency vector from a given text.
     * <p>
     * The text is first normalized before extracting all possible
     * contiguous substrings of length {@code nGramSize}. Each n-gram
     * occurrence increases the frequency count in the resulting vector.
     *
     * @param text The input text
     * @return a map representing the n-gram frequency vector
     */
    private Map<String, Integer> buildVector(String text) {
        String normalized = normalize(text);
        Map<String, Integer> vector = new HashMap<>();

        int limit = normalized.length() - nGramSize + 1;
        for (int i = 0; i < limit; i++) {
            String gram = normalized.substring(i, i + nGramSize);
            vector.merge(gram, 1, Integer::sum);
        }

        return vector;
    }

    /**
     * Computes the dot product of two n-gram vectors.
     * <p>
     * Only shared n-grams contribute to the dot product.
     *
     * @param vector1 First n-gram vector
     * @param vector2 Second n-gram vector
     * @return the dot product of the two vectors
     */
    private double computeDotProduct(Map<String, Integer> vector1,
                                     Map<String, Integer> vector2) {
        Map<String, Integer> smaller = vector1.size() < vector2.size() ? vector1 : vector2;
        Map<String, Integer> larger = smaller == vector1 ? vector2 : vector1;

        double dot = 0.0;

        for (Map.Entry<String, Integer> entry : smaller.entrySet()) {
            int value1 = entry.getValue();
            int value2 = larger.getOrDefault(entry.getKey(), 0);
            dot += value1 * value2;
        }

        return dot;
    }

    /**
     * Computes the squared Euclidean norm of a vector.
     * <p>
     * The squared norm is the sum of the squares of all
     * frequency values in the vector.
     *
     * @param vector The vector whose norm is to be computed
     * @return the squared norm value
     */
    public double computeNorm(Map<String, Integer> vector) {
        double sum = 0.0;

        for (int value : vector.values()) {
            sum += value * value;
        }

        return sum;
    }

    /**
     * Normalizes the input text by converting it to lowercase
     * and removing all whitespace characters.
     *
     * @param text The input text
     * @return the normalized text
     */
    private String normalize(String text) {
        return text.toLowerCase().replaceAll("\\s+", "");
    }
}
