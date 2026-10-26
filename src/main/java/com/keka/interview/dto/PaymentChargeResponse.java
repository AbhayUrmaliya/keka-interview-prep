package com.keka.interview.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentChargeResponse {

    private boolean success;
    private String paymentReference;
    private String message;
    private boolean fallback;
}
