package alertingSystem.client;

import alertingSystem.response.ExotelResponse;
import org.springframework.web.client.RestTemplate;

public class ExotelClient {

    RestTemplate restTemplate = new RestTemplate();

    private  String to;

    public ExotelClient(String to, String message) {
        this.to = to;
        this.message = message;
    }

    public String getTo() {
        return to;
    }

    public String getMessage() {
        return message;
    }

    private String message;

    public ExotelResponse triggerCall(String incidentId, String title) {
    }
}
