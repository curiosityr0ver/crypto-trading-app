package com.curiosity.crypto.model;


import com.curiosity.crypto.domain.VERIFICATION_TYPE;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

@Data
@Embeddable
public class TwoFactorAuth {
    private boolean isEnabled = false;
    @Enumerated(EnumType.STRING)
    private VERIFICATION_TYPE sendTo;
}
