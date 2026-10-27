package alertingSystem.service;

import alertingSystem.client.ExotelClient;
import alertingSystem.enums.IncidentStatus;
import alertingSystem.model.AlertRequest;
import alertingSystem.model.Incident;
import alertingSystem.model.NotificationDetails;
import alertingSystem.repository.IncidentRepository;
import alertingSystem.response.ExotelResponse;
import org.springframework.beans.factory.annotation.Autowired;

public class IncidentService {

    @Autowired
    IncidentRepository incidentRepository;

    @Autowired
    ExotelClient exotelClient;

    public Incident processAlert(AlertRequest alertRequest){
        Incident incident = new Incident(alertRequest.getIncidentId(), alertRequest.getServiceId(), alertRequest.getSeverity(), alertRequest.getTitle(), alertRequest.getStatus());
        incident.setStatus(IncidentStatus.PENDING);
        incidentRepository.save(incident);
        switch (alertRequest.getSeverity()){
            case CRITICAL:
                handleCriticalIncident(incident);
                break;
            case INFO:

                handleInfoIncident(incident);
                break;
            case WARNING:

                handleWarningIncident(incident);
                break;
            default:
                // Handle unknown severity
                System.out.println("Unknown severity for incident: " + incident.getIncidentId());
        }
        return incidentRepository.save(incident);
    }

    public void  handleCriticalIncident(Incident incident){
            try{
                ExotelResponse response = exotelClient.triggerCall(incident.getIncidentId(), incident.getTitle());
                incident.setNotification(NotificationDetails.phoneSent(response.getCallid()));
                incident.setStatus(IncidentStatus.NOTIFIED);
            }catch (Exception e){
                incident.setStatus(IncidentStatus.FAILED);
                System.out.println("Failed to send notification for incident: " + incident.getIncidentId());
            }
    }
}
