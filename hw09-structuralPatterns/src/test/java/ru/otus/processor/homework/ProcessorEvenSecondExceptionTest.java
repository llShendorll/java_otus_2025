package ru.otus.processor.homework;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import ru.otus.model.Message;

public class ProcessorEvenSecondExceptionTest {
    @Test
    void evenSecondThrows() {
        DateTimeProvider tp = () -> LocalDateTime.of(2025, 10, 1, 12, 0, 2);
        var p = new ProcessorEvenSecondException(tp);
        var msg = new Message.Builder(1L).build();
        assertThatThrownBy(() -> p.process(msg))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("second");
    }

    @Test
    void oddSecondPassesThrough() {
        DateTimeProvider tp = () -> LocalDateTime.of(2025, 10, 1, 12, 0, 3);
        var p = new ProcessorEvenSecondException(tp);
        var msg = new Message.Builder(1L).field10("field10").build();
        assertThat(p.process(msg)).isSameAs(msg);
    }
}
