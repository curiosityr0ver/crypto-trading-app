package com.curiosity.crypto.service;

import com.curiosity.crypto.domain.VERIFICATION_TYPE;
import com.curiosity.crypto.model.User;
import com.curiosity.crypto.model.VerificationCode;
import com.curiosity.crypto.repository.VerificationCodeRepository;
import com.curiosity.crypto.utils.OtpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VerificationCodeServiceImpl implements VerificationCodeService {

    @Autowired
    private VerificationCodeRepository verificationCodeRepository;


    @Override
    public VerificationCode sendVerificationOTP(User user, VERIFICATION_TYPE verificationType) {
        VerificationCode verificationCode = new VerificationCode();

        verificationCode.setOtp(OtpUtils.generateOtp());
        verificationCode.setUser(user);
        verificationCode.setVerificationType(verificationType);
        return verificationCodeRepository.save(verificationCode);
    }

    @Override
    public VerificationCode findVerificationById(Long id) throws Exception {
        Optional<VerificationCode> verificationCode = verificationCodeRepository.findById(id);

        if (verificationCode.isEmpty()) {
            throw new Exception("Verification code not found");
        }
        return verificationCode.get();
    }

    @Override
    public VerificationCode findUsersVerification(User user) throws Exception {
        return verificationCodeRepository.findByUserId(user.getId());
    }

    @Override
    public Boolean VerifyOtp(String opt, VerificationCode verificationCode) {
       return opt.equals(verificationCode.getOtp());
    }

    @Override
    public void deleteVerification(VerificationCode verificationCode) {
        verificationCodeRepository.delete(verificationCode);
    }
}
