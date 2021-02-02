package pl.coderslab.rentaapartment.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import pl.coderslab.rentaapartment.model.Message;
import pl.coderslab.rentaapartment.repository.MessageRepository;

@Component
public class MessageConverter implements Converter<String, Message> {

    @Autowired
    private MessageRepository messageRepository;

    @Override
    public Message convert(String s) {
        return messageRepository.findById(Long.parseLong(s)).orElse(new Message());
    }
}
