package seedu.address.commons.util;

/**
 * Represents a generic string similarity algorithm.
 * Implementations returns a similarity score between 0.0 and 1.0.
 */
public interface SimilarityMetric {

    /**
     * Computes similarity between two strings.
     *
     * @param first  First string to compare
     * @param second Second string to compare
     * @return similarity score in range [0.0, 1.0]
     */
    double similarity(String first, String second);
}
