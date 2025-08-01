package com.curiosity.crypto.service;

import com.curiosity.crypto.model.TwoFactorOTP;
import com.curiosity.crypto.model.User;

public class TwoFactorOtpServiceImpl implements TwoFactorOtpService {
    @Override
    public TwoFactorOTP createTwoFactorOtp(User user, String otp, String jwt) {
        return null;
    }

    @Override
    public TwoFactorOTP findByUser(Long userId) {
        return null;
    }

    @Override
    public TwoFactorOTP findById(String id) {
        return null;
    }

    @Override
    public boolean verifyTwoFactorOtp(TwoFactorOTP twoFactorOtp, String otp) {
        return false;
    }

    @Override
    public void deleteTwoFactorOtp(TwoFactorOTP twoFactorOtp) {

    }
}
