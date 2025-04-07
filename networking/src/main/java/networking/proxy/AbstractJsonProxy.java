package networking.proxy;

import com.google.gson.Gson;
import networking.Request;
import networking.Response;

public abstract class AbstractJsonProxy extends AbstractProxy {
	private final Gson gsonFormatter;

	public AbstractJsonProxy(String host, int port) {
		super(host, port);
		gsonFormatter = new Gson();
	}

	protected void sendRequest(Request request) throws Exception {
		String reqLine = gsonFormatter.toJson(request);
		System.out.println("request sent " + reqLine);
		try {
			output.println(reqLine);
			output.flush();
		} catch (Exception e) {
			System.out.println(e);
			throw new Exception("Error sending object " + e);
		}
	}

	protected Response readResponse() throws Exception {
		String responseLine = input.readLine();
		System.out.println("response received " + responseLine);
		return gsonFormatter.fromJson(responseLine, Response.class);
	}
}
