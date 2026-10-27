package alertingSystem.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ExotelResponse {

    private int callid;
    private String status;
}
