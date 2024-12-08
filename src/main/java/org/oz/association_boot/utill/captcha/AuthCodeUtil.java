package org.oz.association_boot.utill.captcha;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
@Log4j2
public class AuthCodeUtil {
    // 랜덤 영문 대소문자 + 숫자 length 만큼 자릿수로 생성
    public String generateTextAuthCode(int length) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        SecureRandom secureRandom = new SecureRandom();
        StringBuilder captcha = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int randomIndex = secureRandom.nextInt(chars.length());
            captcha.append(chars.charAt(randomIndex));
        }
        return captcha.toString();
    }
}
