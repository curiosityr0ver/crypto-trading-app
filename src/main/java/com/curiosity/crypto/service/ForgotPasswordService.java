package com.curiosity.crypto.service;

import com.curiosity.crypto.domain.VERIFICATION_TYPE;
import com.curiosity.crypto.model.ForgotPasswordToken;
import com.curiosity.crypto.model.User;

public interface ForgotPasswordService {

    ForgotPasswordToken createToken(User user, String id, String otp, VERIFICATION_TYPE verificationType, String sendTo);

    ForgotPasswordToken findById(String id);

    ForgotPasswordToken findByUser(Long userId);

    void deleteToken(ForgotPasswordToken token);

}
