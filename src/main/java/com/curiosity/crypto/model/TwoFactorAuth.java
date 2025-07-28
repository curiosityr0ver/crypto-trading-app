package com.curiosity.crypto.model;


import com.curiosity.crypto.domain.VERIFICATION_TYPE;
import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class TwoFactorAuth {
    private boolean isEnabled = false;
    private VERIFICATION_TYPE sendTo;
}
