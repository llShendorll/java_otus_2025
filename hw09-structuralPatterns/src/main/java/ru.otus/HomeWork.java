package ru.otus;

import java.time.LocalDateTime;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.handler.ComplexProcessor;
import ru.otus.listener.homework.HistoryListener;
import ru.otus.model.Message;
import ru.otus.processor.LoggerProcessor;
import ru.otus.processor.ProcessorConcatFields;
import ru.otus.processor.ProcessorUpperField10;
import ru.otus.processor.homework.ProcessorEvenSecondException;
import ru.otus.processor.homework.ProcessorSwapField11Field12;

public class HomeWork {
    private static final Logger logger = LoggerFactory.getLogger(HomeWork.class);

    /*
    Реализовать to do:
      1. Добавить поля field11 - field13 (для field13 используйте класс ObjectForMessage)
      2. Сделать процессор, который поменяет местами значения field11 и field12
      3. Сделать процессор, который будет выбрасывать исключение в четную секунду (сделайте тест с гарантированным результатом)
            Секунда должна определяьться во время выполнения.
            Тест - важная часть задания
            Обязательно посмотрите пример к паттерну Мементо!
      4. Сделать Listener для ведения истории (подумайте, как сделать, чтобы сообщения не портились)
         Уже есть заготовка - класс HistoryListener, надо сделать его реализацию
         Для него уже есть тест, убедитесь, что тест проходит
    */

    public static void main(String[] args) {
        var processors = List.of(
                new ProcessorConcatFields(),
                new ProcessorSwapField11Field12(),
                new LoggerProcessor(new ProcessorUpperField10()),
                new ProcessorEvenSecondException(LocalDateTime::now));

        var complex = new ComplexProcessor(processors, ex -> {});

        var history = new HistoryListener();
        complex.addListener(history);

        var message = new Message.Builder(1L)
                .field1("field1")
                .field2("field2")
                .field3("field3")
                .field10("field10")
                .field11("field11")
                .field12("field12")
                .build();

        var result = complex.handle(message);
        logger.info("result:{}", result);

        complex.removeListener(history);
    }
}
