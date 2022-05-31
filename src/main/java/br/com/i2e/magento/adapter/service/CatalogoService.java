package br.com.i2e.magento.adapter.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;

import br.com.i2e.common.model.catalog.Foto;
import br.com.i2e.magento.common.util.JsonUtils;
import br.com.i2e.magento.common.ws.model.ArrayOfString;
import br.com.i2e.magento.common.ws.model.CatalogProductAttributeMediaCreateRequestParam;
import br.com.i2e.magento.common.ws.model.CatalogProductCreateEntity;
import br.com.i2e.magento.common.ws.model.CatalogProductCreateRequestParam;

@Service
public class CatalogoService {
	
	private static final Logger logger = LoggerFactory.getLogger( CatalogoService.class ); 

	public final String MAGENTO_REQUEST_QUEUE = "magento.request.queue";
	public final String MAGENTO_RESPONSE_QUEUE = "magento.response.queue";
	public final String I2E_RESPONSE_QUEUE = "i2e.response.queue";
	
	@Autowired
	private RabbitTemplate rabbitTemplate;

	public void receiveProduto( br.com.i2e.common.model.catalog.Produto pd ) {
		
		var pc = new CatalogProductCreateEntity();
		var cat = new ArrayOfString();
		cat.getComplexObjectArray().add( "213" );
		pc.setCategories( cat );
		pc.setDescription( pd.getDescricao() );
		pc.setName( pd.getNome() );
		pc.setShortDescription( pd.getDescicaoResumida() );
		pc.setWeight( pd.getPeso().toString() );
		pc.setPrice( "1,00" );
		pc.setVisibility( "4" );
		pc.setStatus( "1" );
		
		var cpc = new CatalogProductCreateRequestParam();
		var sku =  (long) ( Math.random() * 10000 );
		cpc.setSku( pd.getCodigoSKU() +  sku);
		cpc.setSet( "4" );
		cpc.setType( "simple" );
		cpc.setProductData( pc );
		logger.info( "Processando Produto: {} -> {}", pd, cpc );
		try {
			
			
			rabbitTemplate.convertAndSend( MAGENTO_REQUEST_QUEUE, JsonUtils.toJson( cpc ) );
//			enviaFotos( pd.getFotos() );
		} catch ( JsonProcessingException | AmqpException e ) {
			e.printStackTrace();
		}
	}

	private void enviaFotos( br.com.i2e.common.model.catalog.Produto pd ) {
		List<Foto> fotos = pd.getFotos();
		if ( fotos == null || fotos.isEmpty() ) {
			return;
		}
		
		fotos.stream().forEach( f -> {
			var cm = new CatalogProductAttributeMediaCreateRequestParam();
			cm.setProductId( I2E_RESPONSE_QUEUE );
		});
	}
}