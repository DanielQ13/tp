package seedu.address.logic.commands;

import java.util.function.Predicate;
import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Finds and lists all persons in address book whose details match the given predicate
 */
public class FilterCommand extends PredicateCommand {

    public static final String COMMAND_WORD = "filter";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Filters the current list of persons by the given arguments";
            // + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            // + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            // + "Example: " + COMMAND_WORD + " alice bob charlie";

    public FilterCommand(Predicate<Person> predicate) {
        super(predicate);
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonList(this.getPredicate());
        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FindCommand)) {
            return false;
        }

        FindCommand otherFindCommand = (FindCommand) other;
        return this.getPredicate().equals(otherFindCommand.getPredicate());
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", this.getPredicate())
                .toString();
    }
}
