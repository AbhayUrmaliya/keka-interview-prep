package alertingSystem.repository;

import alertingSystem.enums.IncidentStatus;
import alertingSystem.model.Incident;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class IncidentRepository {

    private final Map<String , Incident> incidentMap = new HashMap<>();

    public Incident save(Incident incident){
        incidentMap.put(incident.getIncidentId(), incident);
        return incident;
    }

    private List<Incident> findALL(IncidentStatus incidentStatus){
        if(incidentStatus == null){
            return new ArrayList<>(incidentMap.values());
        }
        return incidentMap.values().stream()
                .filter(incident -> incident.getStatus() == incidentStatus)
                .collect(Collectors.toList());
    }
}
