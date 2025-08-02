package com.curiosity.crypto.service;

import com.curiosity.crypto.domain.VERIFICATION_TYPE;
import com.curiosity.crypto.model.User;
import com.curiosity.crypto.model.VerificationCode;

public interface VerificationCodeService {

    VerificationCode sendVerificationCode(User user, VERIFICATION_TYPE verificationType);

    VerificationCode getVerificationCodeById(Long id);

    VerificationCode getVerificationCodeByUser(Long userId);

    void deleteVerificationCodeById(VerificationCode verificationCode);
}
