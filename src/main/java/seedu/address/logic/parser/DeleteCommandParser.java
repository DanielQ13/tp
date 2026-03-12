package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public DeleteCommand parse(String args) throws ParseException {
    String trimmed = args.trim();
    if (trimmed.isEmpty()) {
        throw new ParseException(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
    }

    String[] indexTokens = trimmed.split("\\s+");
    List<Index> indexes = new ArrayList<>();

    for (String token : indexTokens) {
        try {
            indexes.add(ParserUtil.parseIndex(token));
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE), pe);
        }
    }

    // Reject duplicate indices
    long uniqueCount = indexes.stream().map(Index::getZeroBased).distinct().count();
    if (uniqueCount != indexes.size()) {
        throw new ParseException(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
    }

    return new DeleteCommand(indexes);
}
