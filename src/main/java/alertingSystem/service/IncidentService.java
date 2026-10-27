package alertingSystem.service;

import alertingSystem.model.AlertRequest;
import alertingSystem.model.Incident;
import alertingSystem.repository.IncidentRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class IncidentService {

    @Autowired
    IncidentRepository incidentRepository;

    public Incident processAlert(AlertRequest alertRequest){
        Incident incident = new Incident(alertRequest.getIncidentId(), alertRequest.getServiceId(), alertRequest.getSeverity(), alertRequest.getTitle(), alertRequest.getStatus());
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

    public void  handleCriticalIncident(incident){

    }
}
