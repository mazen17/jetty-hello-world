package org.example;

import org.eclipse.jetty.client.Address;
import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.HttpDestination;

public class JettyClient {

	public static void main(String[] args) throws Exception {
		// Instantiate HttpClient
		HttpClient httpClient = new HttpClient();

		Address ad = new Address("localhost", 8080);

		HttpDestination destination = httpClient.getDestination(ad, false);

		// Configure HttpClient, for example:
		httpClient.start();

		// Start HttpClient
	}
}
