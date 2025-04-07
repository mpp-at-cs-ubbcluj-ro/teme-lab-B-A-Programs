package networking.proxy;

import domain.AgeGroup;
import domain.DTO.ChildDTO;
import domain.User;
import domain.Child;
import domain.DTO.TrialDTO;
import networking.Request;
import networking.RequestType;
import networking.Response;
import networking.ResponseType;
import services.IMasterService;
import services.IObserver;

import java.util.List;

public class MasterJsonProxy extends AbstractJsonProxy implements IMasterService {
	private IObserver user;

	public MasterJsonProxy(String host, int port) {
		super(host, port);
	}

	protected boolean isUpdate(Response response) {
		return response.getType() == ResponseType.UPDATE_TRIALS;
	}

	protected void handleUpdate(Response response) {
		if (response.getType() == ResponseType.UPDATE_TRIALS) {
			try {
				user.notifyEnrollment();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void logIn(User user, IObserver obsUser) throws Exception {
		initializeConnection();
		Request req = new Request();
		req.setUser(user);
		req.setType(RequestType.LOGIN);
		sendRequest(req);
		Response response = getResponse();
		if (response.getType() == ResponseType.OK) {
			this.user = obsUser;
			return;
		}
		if (response.getType() == ResponseType.ERROR) {
//			closeConnection();
			String err = response.getErrorMessage();
			throw new Exception(err);
		}
	}

	@Override
	public void logOut(IObserver user) throws Exception {
		Request req = new Request();
		req.setType(RequestType.LOGOUT);
		sendRequest(req);
		closeConnection();
	}

	@Override
	public void enrollChild(long trialId, Child child) throws Exception {
		Request req = new Request();
		req.setChild(child);
		req.setTrialId(trialId);
		req.setType(RequestType.SIGN_UP_CHILD);
		sendRequest(req);
		Response response = getResponse();
		if (response.getType() == ResponseType.ERROR) {
//			closeConnection();
			String err = response.getErrorMessage();
			throw new Exception(err);
		}
	}

	@Override
	public List<TrialDTO> getTrials() throws Exception {
		Request req = new Request();
		req.setType(RequestType.GET_TRIALS);
		sendRequest(req);
		Response response = getResponse();
		if (response.getType() == ResponseType.ERROR) {
//			closeConnection();
			String err = response.getErrorMessage();
			throw new Exception(err);
		}
		return response.getTrials();
	}
}
