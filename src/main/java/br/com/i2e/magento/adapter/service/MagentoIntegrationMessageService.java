package br.com.i2e.magento.adapter.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.i2e.common.model.catalog.Produto;
import br.com.i2e.magento.common.model.MagentoIntegrationMessage;
import br.com.i2e.magento.common.util.JsonUtils;

@Service("messageService")
public class MagentoIntegrationMessageService {
	
	@Autowired
    private CatalogoService catalogoService;
	
	public void receive( MagentoIntegrationMessage msg ) {
		
	}

	public void receiveProduto( Produto msg ) {
		
		catalogoService.receiveProduto( msg );
	}
}
