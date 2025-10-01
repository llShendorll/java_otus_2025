package ru.otus.listener.homework;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import ru.otus.listener.Listener;
import ru.otus.model.Message;
import ru.otus.model.ObjectForMessage;

public class HistoryListener implements Listener, HistoryReader {

    private final Map<Long, Message> history = new HashMap<>();

    @Override
    public void onUpdated(Message msg) {
        Message messageCopy = copy(msg);
        history.put(msg.getId(), messageCopy);
    }

    @Override
    public Optional<Message> findMessageById(long id) {
        return Optional.ofNullable(history.get(id));
    }

    private Message copy(Message message) {
        var builder = message.toBuilder();

        if (message.getField13() != null) {
            ObjectForMessage originalField13 = message.getField13();
            ObjectForMessage copiedField13 = new ObjectForMessage();

            if (originalField13.getData() != null) {
                copiedField13.setData(new ArrayList<>(originalField13.getData()));
            }

            builder.field13(copiedField13);
        }

        return builder.build();
    }
}
