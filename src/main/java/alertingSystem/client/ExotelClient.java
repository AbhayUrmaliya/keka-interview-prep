package alertingSystem.client;

public class ExotelClient {

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

}
