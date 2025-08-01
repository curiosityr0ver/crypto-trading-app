package com.curiosity.crypto.service;

import com.curiosity.crypto.model.TwoFactorOTP;
import com.curiosity.crypto.model.User;
import com.curiosity.crypto.repository.TwoFactorOtpRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Optional;
import java.util.UUID;

@Service
public class TwoFactorOtpServiceImpl implements TwoFactorOtpService {

    @Autowired
    private TwoFactorOtpRepository twoFactorOtpRepository;

    @Override
    public TwoFactorOTP createTwoFactorOtp(User user, String otp, String jwt) {
        UUID uuid = UUID.randomUUID();
        String id  = uuid.toString();
        TwoFactorOTP twoFactorOtp = new TwoFactorOTP();
        twoFactorOtp.setOtp(otp);
        twoFactorOtp.setUser(user);
        twoFactorOtp.setId(id);
        twoFactorOtp.setJwt(jwt);
        twoFactorOtpRepository.save(twoFactorOtp);
        return twoFactorOtp;
    }

    @Override
    public TwoFactorOTP findByUser(Long userId) {
        return twoFactorOtpRepository.findByUserId(userId);
    }

    @Override
    public TwoFactorOTP findById(String id) {
        Optional<TwoFactorOTP> twoFactorOtp = twoFactorOtpRepository.findById(id);
        return twoFactorOtp.orElse(null);
    }

    @Override
    public boolean verifyTwoFactorOtp(TwoFactorOTP twoFactorOtp, String otp) {
        return twoFactorOtp.getJwt().equals(otp);
    }

    @Override
    public void deleteTwoFactorOtp(TwoFactorOTP twoFactorOtp) {
        twoFactorOtpRepository.delete(twoFactorOtp);
    }
}
