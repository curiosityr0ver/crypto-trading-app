package com.curiosity.crypto;

import com.curiosity.crypto.domain.VERIFICATION_TYPE;
import lombok.Data;

@Data
public class ForgotPasswordTokenRequest {
    private String sendTo;
    private VERIFICATION_TYPE verificationType;
}
