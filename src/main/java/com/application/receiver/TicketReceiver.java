package com.application.receiver;

import java.util.function.Consumer;

import lombok.extern.slf4j.Slf4j;

import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import com.application.service.TicketService;
import com.application.vo.TicketVo;
import com.rabbitmq.client.Channel;

@Component
@Slf4j
public class TicketReceiver {
	
	@Autowired
	TicketService ticketService;
	
	/*@RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "ticket-queue", durable = "true"),
            exchange = @Exchange(value = "ti1cket-exchange",
                    durable = "true",
                    type = "direct",
                    ignoreDeclarationExceptions = "true"),
            key = "routingKey-1"))*/
	@RabbitListener(queues = "ticket-queue")
    @RabbitHandler
    public void qrMessage(Message message, Channel channel) throws Exception {
		
		System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxx");
		
        Consumer<TicketVo> genQRCode = vo -> ticketService.processTicket(vo);
        
        String msg = message.getPayload().toString();
        
        System.out.println("==================接收到消息==================");
        System.out.println(msg);
        System.out.println("==================接收到消息==================");
    }

}
