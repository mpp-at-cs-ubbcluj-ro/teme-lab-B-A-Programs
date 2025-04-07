package networking.worker;

import domain.Child;
import domain.User;
import networking.Request;
import networking.RequestType;
import networking.Response;
import networking.ResponseType;
import services.IMasterService;
import services.IObserver;

import java.net.Socket;


public class MasterJsonWorker extends AbstractJsonWorker implements IObserver {
	protected final IMasterService masterService;

	public MasterJsonWorker(IMasterService masterService, Socket connection) {
		super(connection);
		this.masterService = masterService;
	}

	@Override
	protected Response handleRequest(Request request) {
		Response response = null;
		System.out.println(request);
		if (request.getType() == RequestType.LOGIN) {
			System.out.println("Login request ..." + request.getType());
			User user = request.getUser();
			try {
				masterService.logIn(user, this);
				return okResponse;
			} catch (Exception e) {
//				connected = false;
				response = new Response();
				response.setType(ResponseType.ERROR);
				response.setErrorMessage(e.getMessage());
				return response;
			}
		} else if (request.getType() == RequestType.GET_TRIALS) {
			System.out.println("Getting trials ..." + request.getType());
			try {
				response = new Response();
				response.setType(ResponseType.TRIALS_LIST);
				response.setTrials(masterService.getTrials());
				return response;
			} catch (Exception e) {
//				connected = false;
				response = new Response();
				response.setType(ResponseType.ERROR);
				response.setErrorMessage(e.getMessage());
				return response;
			}
		} else if (request.getType() == RequestType.SIGN_UP_CHILD) {
			System.out.println("Sign up child ..." + request.getType());
			Child child = request.getChild();
			Long trialId = request.getTrialId();
			try {
				masterService.enrollChild(trialId, child);
				return okResponse;
			} catch (Exception e) {
//				connected = false;
				response = new Response();
				response.setType(ResponseType.ERROR);
				response.setErrorMessage(e.getMessage());
				return response;
			}
		} else if (request.getType() == RequestType.LOGOUT) {
			connected = false;
			try {
				masterService.logOut(this);
			} catch (Exception ignored) {
			}
		}
		return response;
	}

	@Override
	public void notifyEnrollment() {
		Response response = new Response();
		response.setType(ResponseType.UPDATE_TRIALS);
		System.out.println("Sent update trials notification");
		try {
			sendResponse(response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
