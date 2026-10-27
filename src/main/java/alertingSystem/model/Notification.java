package alertingSystem.model;

public class Notification {

    private  String channel;
    private String status;
    private  int providerCallId;

    public String getChannel() {
        return channel;
    }

    public String getStatus() {
        return status;
    }

    public int getProviderCallId() {
        return providerCallId;
    }

    public String getLastError() {
        return lastError;
    }

    public Notification(String channel, String status, int providerCallId, String lastError) {
        this.channel = channel;
        this.status = status;
        this.providerCallId = providerCallId;
        this.lastError = lastError;
    }

    private  String lastError;

}
