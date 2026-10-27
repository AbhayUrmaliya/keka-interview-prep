package alertingSystem.controller;

import alertingSystem.model.AlertRequest;
import alertingSystem.model.Incident;
import alertingSystem.service.IncidentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AlertController {

        @Autowired
        private  IncidentService incidentService;

        @PostMapping("/alert")
        public ResponseEntity<Incident> ingestAlert(AlertRequest alertRequest) {
            Incident incident = incidentService.processAlert(alertRequest);
            return ResponseEntity.status(HttpStatus.OK).body(incident);
        }

}
