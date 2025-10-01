package ru.otus.processor.homework;

import ru.otus.model.Message;
import ru.otus.processor.Processor;

public class ProcessorEvenSecondException implements Processor {
    private final DateTimeProvider dateTimeProvider;

    public ProcessorEvenSecondException(DateTimeProvider dateTimeProvider) {
        this.dateTimeProvider = dateTimeProvider;
    }

    @Override
    public Message process(Message message) {
        int sec = dateTimeProvider.now().getSecond();
        if (sec % 2 == 0) {
            throw new RuntimeException("second");
        }
        return message;
    }
}
