package alertingSystem.model;

public class NotificationDetails {

    private  String channel;
    private String providerCallerId;
    private String status;

    public String getChannel() {
        return channel;
    }

    public String getProviderCallerId() {
        return providerCallerId;
    }

    public String getStatus() {
        return status;
    }

    public String getLastError() {
        return lastError;
    }

    public NotificationDetails(String channel, String providerCallerId, String status, String lastError) {
        this.channel = channel;
        this.providerCallerId = providerCallerId;
        this.status = status;
        this.lastError = lastError;
    }

    private String lastError;
}
