package com.avianca.pagos.rest.handler;

import javax.ws.rs.core.Response;

import org.apache.camel.Exchange;
import org.apache.camel.Handler;
import org.springframework.stereotype.Component;


/**
 * 
 * @author Assert Solutions S.A.S <info@assertsolutions.com> <br/>
 *         Date: 9/04/2018 8:11:28 a.m.
 *
 */
@Component
public class ResponseHandler {
	@Handler
	public void process(Exchange exchange) throws Exception {

//		Map<String, Object> map = (Map<String, Object>) exchange.getIn().getHeader("CamelCxfMessage");
		String code = exchange.getIn().getHeader("code").toString();
//				map.get("org.apache.cxf.message.Message.RESPONSE_CODE").toString();
		String response = exchange.getIn().getBody().toString();

		if (code.equals("200")) {

			exchange.getOut().setHeader("BOperacionExitosa", true);
			exchange.getOut().setHeader("SCodigo", code);
			exchange.getOut().setHeader("SMensaje", "Operacion Exitosa");
			exchange.getOut().setHeader("SMensajeTecnico", "Operacion Exitosa al procesar la solicitud");

		} else if (code.equals("204")) {
			exchange.getOut().setHeader("BOperacionExitosa", false);
			exchange.getOut().setHeader("SCodigo", code);
			exchange.getOut().setHeader("SMensaje", "Operación Exitosa sin resultados");
			exchange.getOut().setHeader("SMensajeTecnico", "No se encuentran resultados");
		} else if (code.equals("408")) {
			exchange.getOut().setHeader("BOperacionExitosa", false);
			exchange.getOut().setHeader("SCodigo", code);
			exchange.getOut().setHeader("SMensaje", "Error de timeout");
			exchange.getOut().setHeader("SMensajeTecnico", "Conexion no exitosa");
		} else if (code.equals("500")) {
//			ResponseDTO res = new ResponseDTO();
//			res.setCode(code);
//			res.setError(exchange.getIn().getHeader("error").toString());
//			res.setBanks(null);
			exchange.getOut().setHeader("BOperacionExitosa", false);
			exchange.getOut().setHeader("SCodigo", code);
			exchange.getOut().setHeader("SMensaje", "Error no controlado");
			exchange.getOut().setHeader("SMensajeTecnico", "Error no controlado se envia notificacion");
		} else if (code.equals("400")) {
			exchange.getOut().setHeader("BOperacionExitosa", false);
			exchange.getOut().setHeader("SCodigo", code);
			exchange.getOut().setHeader("SMensaje", "Request erroneo o vacio");
			exchange.getOut().setHeader("SMensajeTecnico", "Request erroneo o vacio");
		}
		exchange.getOut().setBody(response, String.class);
		exchange.getOut().setHeader(Exchange.HTTP_RESPONSE_CODE, code);
		exchange.getOut().setHeader(Exchange.ACCEPT_CONTENT_TYPE, "application/json; charset=UTF-8");
		exchange.getOut().setHeader(Exchange.CONTENT_TYPE, "application/json; charset=UTF-8");

	}
}
