package networking.server;
import networking.worker.AbstractWorker;
import networking.worker.MasterJsonWorker;
import services.IMasterService;

import java.net.Socket;

public class JsonConcurrentServer extends AbstractConcurrentServer {
	private final IMasterService masterService;

	public JsonConcurrentServer(int port, IMasterService masterService) {
		super(port);
		this.masterService = masterService;
		System.out.println("JsonConcurrentServer");
	}

	@Override
	protected Thread createWorker(Socket client) {
		AbstractWorker worker = new MasterJsonWorker(masterService, client);
		return new Thread(worker);
	}
}
