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
    public VerificationCode sendVerificationCode(User user, VERIFICATION_TYPE verificationType) {
        VerificationCode verificationCode1 = new VerificationCode();

        verificationCode1.setOtp(OtpUtils.generateOtp());
        verificationCode1.setVerificationType(verificationType);
        verificationCode1.setUser(user);

        return verificationCodeRepository.save(verificationCode1);
    }

    @Override
    public VerificationCode getVerificationCodeById(Long id) {
        Optional<VerificationCode> verificationCodeOptional = verificationCodeRepository.findById(id);
        if(verificationCodeOptional.isPresent()) {
            return verificationCodeOptional.get();
        }
        throw new RuntimeException("Verification Code Not Found");
    }

    @Override
    public VerificationCode getVerificationCodeByUser(Long userId) {
        return verificationCodeRepository.findByUserId(userId);
    }

    @Override
    public void deleteVerificationCodeById(VerificationCode verificationCode) {
        verificationCodeRepository.delete(verificationCode);
    }
}
