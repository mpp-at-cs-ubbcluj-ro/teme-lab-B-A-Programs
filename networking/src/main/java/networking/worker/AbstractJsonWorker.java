package networking.worker;

import com.google.gson.Gson;
import networking.Request;
import networking.Response;

import java.io.IOException;
import java.net.Socket;

public abstract class AbstractJsonWorker extends AbstractWorker {
	private final Gson gsonFormatter;

	public AbstractJsonWorker(Socket connection) {
		super(connection);
		gsonFormatter = new Gson();
	}

	protected Request readRequest() throws IOException {
		String requestLine = input.readLine();
		System.out.println("request received " + requestLine);
		return gsonFormatter.fromJson(requestLine, Request.class);
	}

	protected void sendResponse(Response response) {
		String responseLine = gsonFormatter.toJson(response);
		System.out.println("sending response " + responseLine);
		synchronized (output) {
			output.println(responseLine);
			output.flush();
		}
	}
}
