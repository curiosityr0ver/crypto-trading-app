package com.curiosity.crypto.model;

import com.curiosity.crypto.domain.TWO_FACTOR_AUTH;
import lombok.Data;

@Data
public class TwoFactorAuth {
    private boolean isEnabled = false;
    private TWO_FACTOR_AUTH sendTo;
}
