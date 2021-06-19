package com.email.batch.publisher;



import com.email.batch.config.MessagingConfig;
import com.email.batch.model.EmailRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Slf4j
public class EmailWriter implements ItemWriter<EmailRequest> {

    @Autowired
    public RabbitTemplate template;

    @Override
    public void write(List<? extends EmailRequest> list) throws Exception {

        for(EmailRequest emailRequest: list){
            Thread.sleep(10000);
            log.info("Email Published to the queue"+emailRequest.getEmail());
            template.convertAndSend("", MessagingConfig.INPUT_QUEUE, emailRequest);
        }

    }

}
