package alertingSystem.model;

import alertingSystem.enums.IncidentStatus;
import alertingSystem.enums.severtity;

public class AlertRequest {

    private String incidentId;
    private int serviceId;
    private String title;
    private String description;
    private IncidentStatus status;

    public AlertRequest(String incidentId, int serviceId, String title, String description, IncidentStatus status, severtity severity, Notification notification) {
        this.incidentId = incidentId;
        this.serviceId = serviceId;
        this.title = title;
        this.description = description;
        this.status = status;
        this.severity = severity;
        this.notification = notification;
    }

    private severtity severity;
    private Notification notification;

    public String getIncidentId() {
        return incidentId;
    }

    public int getServiceId() {
        return serviceId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public IncidentStatus getStatus() {
        return status;
    }

    public severtity getSeverity() {
        return severity;
    }

    public Notification getNotification() {
        return notification;
    }
}
