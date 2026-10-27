package alertingSystem.model;

import alertingSystem.enums.IncidentStatus;
import alertingSystem.enums.severtity;
import lombok.Data;

@Data
public class Incident {
    private String incidentId;
    private String serviceId;
    private severtity severity;
    private String title;
    private NotificationDetails notification;

    public String getIncidentId() {
        return incidentId;
    }

    public String getServiceId() {
        return serviceId;
    }

    public severtity getSeverity() {
        return severity;
    }

    public String getTitle() {
        return title;
    }

    public IncidentStatus getStatus() {
        return status;
    }

    public Incident(String incidentId, int serviceId, severtity severity, String title, IncidentStatus status) {
        this.incidentId = incidentId;
        this.serviceId = serviceId;
        this.severity = severity;
        this.title = title;
        this.status = status;
    }

    private IncidentStatus status;

}
