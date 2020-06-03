package com.avianca.pagos.test;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.camel.ExchangePattern;
import org.apache.camel.builder.AdviceWithRouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.spring.CamelSpringTestSupport;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 
 * @author Assert Solutions S.A.S <info@assertsolutions.com>
 *         <br/>
 *         Date: 9/04/2018 9:17:11 a.m.
 *
 */
public class dapicontactsTest extends CamelSpringTestSupport {

    private static final String PROPERTIES_FILE_DIR = "src/test/resources/";
    private static Properties testProperties = new Properties();
    
    @Test
    public void testRoute() throws Exception {
	final String fromRoute = "direct:fromRoute";

	context.getRouteDefinition("restServerRoute").adviceWith(context, new AdviceWithRouteBuilder() {
	    @Override
	    public void configure() throws Exception {
		replaceFromWith(fromRoute);
		weaveAddLast().log("Finishing the unit test of the route ").to("mock://endroute");
	    }
	});
	context.start();
	// Agregamos un mock endpoint
	MockEndpoint mockEndpoint = getMockEndpoint("mock://endroute");
	mockEndpoint.expectedMinimumMessageCount(1);
	Map<String, Object> map = new HashMap<>();
	map.put("orderId", "J6FZ3G");
	map.put("lastName", "Perez A");
	map.put("Auth", "sxEe8c98GGjY1RRoauDLmnmS3w3L");
	 
	template.sendBodyAndHeaders(fromRoute, "", map);
//	Object routeResponse = template.sendBodyAndHeaders(fromRoute, ExchangePattern.InOut, null, null);

	mockEndpoint.assertIsSatisfied(2000L);
    }

    /**
     * Carga del archivo de propiedades para los Test Unitarios
     * 
     * @throws Exception
     */
    @BeforeClass
    public static void init() throws Exception {
	testProperties.load(dapicontactsTest.class.getResourceAsStream("/dapicontacts.properties"));
    }
    
    @BeforeClass
    public static void setUpProperties() throws Exception {
	System.setProperty("karaf.home", PROPERTIES_FILE_DIR);
	System.setProperty("project.artifactId", "Test-Maven-Artifact");
    }

    @Override
    protected AbstractApplicationContext createApplicationContext() {
	return new ClassPathXmlApplicationContext("META-INF/spring/camel-context.xml",
		"META-INF/spring/properties-beans.xml");
    }

}
