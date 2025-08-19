package org.factoriaf5.project_inside_out.views;

import org.factoriaf5.project_inside_out.models.Emotion;
import org.factoriaf5.project_inside_out.models.Moment;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;


class ConsoleMenuTest {

    @Test
    void readInt_shouldReturnEnteredNumber() {
        String input = "42\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        ConsoleMenu menu = new ConsoleMenu();

        int result = menu.readInt();

        assertThat(result, is(42));
    }

    @Test
    void readLine_shouldReturnEnteredText() {
        String input = "Hola mundo\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        ConsoleMenu menu = new ConsoleMenu();

        String result = menu.readLine("Escribe algo: ");

        assertThat(result, is("Hola mundo"));
    }

    @Test
    void printMoments_shouldShowMomentDetails() {
        Moment moment = new Moment(
                1, "Título", "Desc", Emotion.ALEGRIA,
                LocalDate.of(2025, 8, 10),
                LocalDateTime.now(), LocalDateTime.now()
        );

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        ConsoleMenu menu = new ConsoleMenu();

        menu.printMoments(List.of(moment));

        assertThat(outContent.toString(), containsString("Título"));
        assertThat(outContent.toString(), containsString("10/08/2025"));
    }
}
