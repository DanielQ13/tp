package seedu.address.model.person;

import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Person}'s {@code Interviewed status} matches true/false.
 */
public class IsInterviewedPredicate implements Predicate<Person> {
    private final boolean isInterviewed;

    /**
     * Constructs a predicate for checking a {@code Person}'s {@code Interviewed status}
     * @param isInterviewed  boolean to match {@code Interviewed status} to
     */
    public IsInterviewedPredicate(boolean isInterviewed) {
        this.isInterviewed = isInterviewed;
    }

    @Override
    public boolean test(Person person) {
        return (person.isInterviewed() == this.isInterviewed);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof IsInterviewedPredicate)) {
            return false;
        }

        IsInterviewedPredicate otherIsInterviewedPredicate = (IsInterviewedPredicate) other;
        return (this.isInterviewed == otherIsInterviewedPredicate.isInterviewed);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("isInterviewed", this.isInterviewed).toString();
    }
}
