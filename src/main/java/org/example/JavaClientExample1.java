package org.example;

import org.eclipse.jetty.client.Address;
import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.HttpExchange;
import org.eclipse.jetty.io.Buffer;

public class JavaClientExample1 {

	public static void main(String[] args) throws Exception {
		HttpClient client = new HttpClient();
		client.start();

		Address address = new Address("localhost", 8080);

		HttpExchange exchange = new HttpExchange();

		// Optionally set the HTTP method
		exchange.setMethod("GET");

		exchange.setAddress(address);
		exchange.setURI("/hello");

		// Or, equivalently, this:
		// exchange.setURL("http://ping.host.com/ping");

		client.send(exchange);

		System.out.println("Exchange sent");

		Buffer responseContent = exchange.getRequestContent();
	}
}
