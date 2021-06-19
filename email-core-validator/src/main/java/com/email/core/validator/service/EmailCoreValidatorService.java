package com.email.core.validator.service;



import com.email.batch.config.MessagingConfig;
import com.email.core.validator.config.RestTemplateConfig;
import com.email.batch.model.EmailRequest;
import com.email.batch.model.EmailResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.URISyntaxException;

@Service
@EnableAsync
@Slf4j
public class EmailCoreValidatorService {

    @Autowired
    RestTemplateConfig restTemplate;

    @Autowired
    public RabbitTemplate template;


    @RabbitListener(queues = com.email.core.validator.config.EmailCoreMessagingConfig.INPUT_QUEUE)
    @Async
    public void consumeMessageFromInputQueue(EmailRequest emailRequest) throws URISyntaxException, InterruptedException {
        if(!StringUtils.isEmpty(emailRequest.getEmail()))	{
            log.info("Email id retrieved"+emailRequest.getEmail());

            URI uri = new URI("http://ec2-3-87-204-188.compute-1.amazonaws.com:9000/validate");
            Thread.sleep(10000);
            EmailResponse response = restTemplate.getRestTemplate().postForObject(uri, emailRequest,
                    EmailResponse.class);

            template.convertAndSend("", MessagingConfig.OUTPUT_QUEUE, response);
            log.info("Gateway response for retrieved email id"+response.getInput()+" Is reachable"+response.getIs_reachable());
        }
    }
}
