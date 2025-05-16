package client;

import domain.Trial;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.RestClient;

import java.io.IOException;
import java.util.concurrent.Callable;

import static org.springframework.http.MediaType.APPLICATION_JSON;

public class TrialClient {
    RestClient restClient = RestClient.builder()
            .requestInterceptor(new CustomRestClientInterceptor())
            .build();

    public static final String URL = "http://localhost:8080/competition/trials";

    private <T> T execute(Callable<T> callable) {
        try {
            return callable.call();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Trial[] get() {
        return execute(() -> restClient.get().uri(URL).retrieve().body(Trial[].class));
    }

    public Trial getById(Long id) {
        return execute(() -> restClient.get().uri(String.format("%s/%d", URL, id)).retrieve().body(Trial.class));
    }

    public Trial create(Trial trial) {
        return execute(() -> restClient.post().uri(URL).contentType(APPLICATION_JSON).body(trial).retrieve().body(Trial.class));
    }

    public void modify(Trial trial) {
        execute(() -> restClient.put().uri(String.format("%s/%d", URL, trial.getId()))
                .contentType(APPLICATION_JSON).body(trial).retrieve().toBodilessEntity());
    }

    public void delete(Long id) {
        execute(() -> restClient.delete().uri(String.format("%s/%d", URL, id)).retrieve().toBodilessEntity());
    }

    public class CustomRestClientInterceptor implements ClientHttpRequestInterceptor {
        @Override
        public ClientHttpResponse intercept(
                org.springframework.http.HttpRequest request,
                byte[] body,
                ClientHttpRequestExecution execution) throws IOException {
            System.out.println("Sending " + request.getMethod() + " to " + request.getURI() + " with body: " + new String(body));
            ClientHttpResponse response = execution.execute(request, body);
            System.out.println("Response code: " + response.getStatusCode());
            return response;
        }
    }
}
