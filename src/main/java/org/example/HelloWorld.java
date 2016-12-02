package org.example;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.AbstractHandler;

public class HelloWorld extends AbstractHandler {
	final AtomicBoolean fourBytesRead = new AtomicBoolean(false);
	final AtomicBoolean earlyEOFException = new AtomicBoolean(false);

	public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		System.out.println("test 1");
		response.setContentType("text/html;charset=utf-8");
		response.setStatus(HttpServletResponse.SC_OK);
		baseRequest.setHandled(true);

		String contentType = baseRequest.getContentType();
		// ServletInputStream inputStream = baseRequest.getInputStream();
		String requestedSessionId = baseRequest.getRequestedSessionId();
		baseRequest.getTimeStamp();
		//
		String readPayloadAsUtf8 = readPayloadAsUtf8(baseRequest);
		System.out.println("test " + readPayloadAsUtf8);
		//
		// int contentLength = request.getContentLength();
		// ServletInputStream inputStream = request.getInputStream();
		// for (int i = 0; i < contentLength; i++) {
		// try {
		// inputStream.read();
		// } catch (EofException e) {
		//
		// }
		//
		// }

		response.getWriter().println("<h1>Hello Fuckers</h1>");

	}

	public static void main(String[] args) throws Exception {
		Server server = new Server(8080);
		server.setHandler(new HelloWorld());

		server.start();
		server.join();
	}

	protected String readPayloadAsUtf8(HttpServletRequest request) throws IOException, ServletException {
		int contentLength = request.getContentLength();
		if (contentLength == -1) {
			// Content length must be known.
			throw new ServletException("Content-Length must be specified");
		}

		String contentType = request.getContentType();
		boolean contentTypeIsOkay = false;
		// Content-Type must be specified.
		if (contentType != null) {
			// The type must be plain text.
			if (contentType.startsWith("text/plain")) {
				// And it must be UTF-8 encoded (or unspecified, in which case we assume
				// that it's either UTF-8 or ASCII).
				if (contentType.indexOf("charset=") == -1) {
					contentTypeIsOkay = true;
				} else if (contentType.indexOf("charset=utf-8") != -1) {
					contentTypeIsOkay = true;
				}
			}
		}
		if (!contentTypeIsOkay) {
			throw new ServletException("Content-Type must be 'text/plain' with 'charset=utf-8' (or unspecified charset)");
		}
		InputStream in = request.getInputStream();
		try {
			byte[] payload = new byte[contentLength];
			int offset = 0;
			int len = contentLength;
			int byteCount;
			while (offset < contentLength) {
				byteCount = in.read(payload, offset, len);
				if (byteCount == -1) {
					throw new ServletException("Client did not send " + contentLength + " bytes as expected");
				}
				offset += byteCount;
				len -= byteCount;
			}
			return new String(payload, "UTF-8");
		} finally {
			if (in != null) {
				in.close();
			}
		}
	}
}