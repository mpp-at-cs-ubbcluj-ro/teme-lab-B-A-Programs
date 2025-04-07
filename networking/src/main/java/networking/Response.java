package networking;

import domain.DTO.TrialDTO;
import java.util.List;


public class Response {
	private ResponseType type;
	private String errorMessage;

	private List<TrialDTO> trials;

	public Response() {
	}

	public ResponseType getType() {
		return type;
	}

	public void setType(ResponseType type) {
		this.type = type;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public List<TrialDTO> getTrials() {
		return trials;
	}

	public void setTrials(List<TrialDTO> trials) {
		this.trials = trials;
	}

	@Override
	public String toString() {
		return "Response{" +
				"type=" + type +
				", errorMessage='" + errorMessage + '\'' +
				", trials=" + trials +
				'}';
	}
}
