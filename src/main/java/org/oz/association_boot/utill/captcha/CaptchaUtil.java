package org.oz.association_boot.utill.captcha;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.security.SecureRandom;
import java.util.Random;

@Component
@Log4j2
public class CaptchaUtil {
    // 랜덤 영문 대소문자 + 숫자 length 만큼 자릿수로 생성
    public String generateCaptchaText(int length) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        SecureRandom secureRandom = new SecureRandom();
        StringBuilder captcha = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int randomIndex = secureRandom.nextInt(chars.length());
            captcha.append(chars.charAt(randomIndex));
        }
        return captcha.toString();
    }
    // CAPTCHA 이미지를 생성
    public BufferedImage generateCaptchaImage(String captchaText) {
        int width = 200;
        int height = 50;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = image.createGraphics();

        // 배경 색상 설정
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, width, height);

        // 텍스트 색상 및 폰트 설정
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 40));

        // CAPTCHA 텍스트를 이미지에 그리기
        g.drawString(captchaText, 20, 40);

        g.dispose();

        return image;
    }
}
