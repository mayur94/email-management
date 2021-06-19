package com.email.batch.config;


import com.email.batch.model.EmailRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@EnableBatchProcessing
public class EmailRequestItemProcesser implements ItemProcessor<EmailRequest, EmailRequest> {


    @Autowired
    public RabbitTemplate template;
    EmailRequest emailRequest= new EmailRequest();
    List<EmailRequest> emailIdPublishToQueue= new ArrayList<EmailRequest>();

    @Override
    public EmailRequest process(EmailRequest emailRequestBulkFromDB) throws Exception {
        /* emailRequest.setEmail(emailRequestBulkFromDB.getEmail());
        emailIdPublishToQueue.add(emailRequest);*/
        return emailRequestBulkFromDB;
    }

  /*  @Scheduled(fixedRate = 10000)
    public void queuePublisher() {
        if (emailIdPublishToQueue.size() > 0) {
            log.info("Email Id published to queue" + emailIdPublishToQueue);
            for(EmailRequest emailRequest: emailIdPublishToQueue) {
                template.convertAndSend("", MessagingConfig.INPUT_QUEUE, emailRequest.getEmail());
            }
        }
*/
        }



