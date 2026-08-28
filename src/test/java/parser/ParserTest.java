package parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import exception.JoeBidenException;
import task.Deadline;

public class ParserTest {

    @Test
    public void parseDeadline_validInput_returnsDeadline()
            throws JoeBidenException {

        Deadline deadline =
                Parser.parseDeadline(
                        "submit report /by 2026-09-15 1800"
                );

        assertEquals("submit report", deadline.getName());
        assertEquals(
                LocalDateTime.of(2026, 9, 15, 18, 0),
                deadline.getBy()
        );
    }

    @Test
    public void parseDeadline_invalidDate_throwsException() {

        assertThrows(
                JoeBidenException.class,
                () -> Parser.parseDeadline(
                        "submit report /by abc"
                )
        );
    }

    @Test
    public void parseDeadline_missingBy_throwsException() {

        assertThrows(
                JoeBidenException.class,
                () -> Parser.parseDeadline(
                        "submit report"
                )
        );
    }
}