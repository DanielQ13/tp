package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INTERVIEWED;

import seedu.address.logic.commands.FilterCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.IsInterviewedPredicate;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FilterCommandParser implements Parser<FilterCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FilterCommand
     * and returns a FilterCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FilterCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_INTERVIEWED);

        if (!argMultimap.getValue(PREFIX_INTERVIEWED).isPresent()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE)
            );
        }

        String interviewedValue = argMultimap.getValue(PREFIX_INTERVIEWED).get().trim();
        boolean interviewedBoolean = ParserUtil.parseBoolean(interviewedValue);

        FilterCommand command = new FilterCommand(new IsInterviewedPredicate(interviewedBoolean));

        assert(command.getPredicate().equals(new IsInterviewedPredicate(ParserUtil.parseBoolean(interviewedValue))));

        return command;
    }

}
