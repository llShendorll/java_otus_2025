package ru.otus.processor.homework;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.model.Message;

@ExtendWith(MockitoExtension.class)
public class ProcessorEvenSecondExceptionTest {
    @Mock
    private DateTimeProvider dateTimeProvider;

    @Test
    void evenSecondThrows() {
        when(dateTimeProvider.now()).thenReturn(LocalDateTime.of(2025, 10, 1, 12, 0, 2));
        var p = new ProcessorEvenSecondException(dateTimeProvider);
        var msg = new Message.Builder(1L).build();
        assertThatThrownBy(() -> p.process(msg))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("second");
    }

    @Test
    void oddSecondPassesThrough() {
        when(dateTimeProvider.now()).thenReturn(LocalDateTime.of(2025, 10, 1, 12, 0, 3));
        var p = new ProcessorEvenSecondException(dateTimeProvider);
        var msg = new Message.Builder(1L).field10("field10").build();
        assertThat(p.process(msg)).isSameAs(msg);
    }
}
