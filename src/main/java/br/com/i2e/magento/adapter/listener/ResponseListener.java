package br.com.i2e.magento.adapter.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import br.com.i2e.common.model.catalog.Produto;
import br.com.i2e.magento.adapter.service.MagentoIntegrationMessageService;
import br.com.i2e.magento.common.util.JsonUtils;

@Component
public class ResponseListener {

	private static final Logger logger = LoggerFactory.getLogger( ResponseListener.class ); 

	public final String I2E_RESPONSE_QUEUE = "i2e.response.queue";
	
    @Autowired
    private MagentoIntegrationMessageService messageService; 
    
	@RabbitListener( queues = I2E_RESPONSE_QUEUE )
	public void onMessage( @Payload String jsonMessage ) {
		
		var msg =  JsonUtils.fromJson( jsonMessage, Produto.class );
		messageService.receiveProduto( msg );
	}
}
