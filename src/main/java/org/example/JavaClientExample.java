package org.example;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.eclipse.jetty.client.Address;
import org.eclipse.jetty.client.ContentExchange;
import org.eclipse.jetty.client.HttpClient;

public class JavaClientExample {
	static InputStream in;

	public static void main(String[] args) throws Exception {
		HttpClient httpClient = new HttpClient();
		httpClient.start();

		String url = "http://localhost:8080/hello";

		ContentExchange exchange = new ContentExchange();

		exchange.setMethod("POST");

		Address ad = new Address("localhost", 8080);
		exchange.setAddress(ad);
		exchange.setURI("/hello");

		exchange.setRequestHeader("Content-Type", "text/plain");
		exchange.setRequestContentSource(new ByteArrayInputStream("test".getBytes("UTF-8")));
		exchange.setRequestHeader("Content-Length", String.valueOf(5));

		exchange.setRequestContentSource(new ByteArrayInputStream("baba".getBytes("UTF-8")));

		exchange.setRequestContentSource(new ByteArrayInputStream("baba1".getBytes("UTF-8")));
		exchange.setRequestContentSource(new ByteArrayInputStream("baba2".getBytes("UTF-8")));

		httpClient.send(exchange);

		// httpClient.send(exchange);
		exchange.waitForDone();

		String responseContent = exchange.getResponseContent();
		System.out.println(responseContent);

	}
}
