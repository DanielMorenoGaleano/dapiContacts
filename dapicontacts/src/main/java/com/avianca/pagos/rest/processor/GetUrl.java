package com.avianca.pagos.rest.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

@Component("getURL")
public class GetUrl implements Processor{
	@Override
	public void process(Exchange exchange) throws Exception {
		// TODO Auto-generated method stub
		String url=exchange.getProperty("url").toString();
		String orderId=exchange.getIn().getHeader("orderId").toString();
		String lastName=exchange.getIn().getHeader("lastName").toString().replace(" ", "%20");
		
		url = url.replace(":orderId:", orderId).replace(":lastName:",lastName);
		
		exchange.getIn().setHeader("url.contacts", url);
	}

}
