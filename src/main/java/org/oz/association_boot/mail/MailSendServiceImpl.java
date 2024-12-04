package org.oz.association_boot.mail;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.oz.association_boot.mail.dto.MailHtmlSendDTO;
import org.oz.association_boot.mail.dto.MailTxtSendDTO;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

@Service
@RequiredArgsConstructor
@Log4j2
public class MailSendServiceImpl implements MailSendService {

    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;

    @Override
    public void sendHtmlMail(MailHtmlSendDTO mailHtmlSendDTO) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");

            // 이메일 템플릿에 전달할 변수 설정
            Context context = new Context();
            context.setVariable("subject", mailHtmlSendDTO.getSubject());
            context.setVariable("content", mailHtmlSendDTO.getContent());
            context.setVariable("name", mailHtmlSendDTO.getName());
            context.setVariable("htmlMsg", mailHtmlSendDTO.getHtmlMsg());

            // 1. 로고 이미지 또는 기타 이미지 파일을 Base64로 인코딩하여 이메일에 첨부
            String base64Image = encodeImageToBase64("static/images/test.jpg");
            context.setVariable("logoImage", base64Image);

            // 2. 이메일 템플릿을 처리하여 HTML 내용 생성
            String htmlContent = templateEngine.process("email", context);

            // 3. 이메일 보내기
            messageHelper.setTo(mailHtmlSendDTO.getEmailAddr());
            messageHelper.setSubject(mailHtmlSendDTO.getSubject());
            messageHelper.setText(htmlContent, true); // HTML 콘텐츠 설정
            mailSender.send(message);

            log.info("이메일 전송 성공!");

        } catch (Exception e) {
            log.info("[-] 이메일 전송 중 오류 발생: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public void sendCaptChaMail(MailHtmlSendDTO mailHtmlSendDTO ,String captchaText) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");

            // 이메일 템플릿에 필요한 변수 설정
            Context context = new Context();
            context.setVariable("subject", mailHtmlSendDTO.getSubject());
            context.setVariable("content", mailHtmlSendDTO.getContent());
            context.setVariable("cname", mailHtmlSendDTO.getName());
            // React 인증 페이지 경로
            context.setVariable("authLink", "http://localhost:5173/auth/"+ mailHtmlSendDTO.getAno());
            context.setVariable("captchaText", captchaText);

            // 이메일 템플릿 처리 및 전송
            String htmlContent = templateEngine.process("email", context);
            messageHelper.setTo(mailHtmlSendDTO.getEmailAddr());
            messageHelper.setSubject(mailHtmlSendDTO.getSubject());
            messageHelper.setText(htmlContent, true);
            mailSender.send(message);

            log.info("이메일 전송 성공!");

        } catch (Exception e) {
            log.info("[-] 이메일 전송 중 오류 발생: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public void sendFileMail(MailTxtSendDTO mailTxtSendDTO, MultipartFile[] multipartFiles) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");

            messageHelper.setTo(mailTxtSendDTO.getEmailAddr());
            messageHelper.setSubject(mailTxtSendDTO.getSubject());
            messageHelper.setText(mailTxtSendDTO.getContent());

            if (multipartFiles != null && multipartFiles.length != 0) {
                for (MultipartFile multipartFile : multipartFiles) {
                    messageHelper.addAttachment(multipartFile.getOriginalFilename(), multipartFile);
                }
            }

            // 이메일 전송
            mailSender.send(message);
            log.info("첨부파일이 포함된 이메일 전송 성공!");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    // 버퍼 이미지를 Base64로 인코딩하는 메서드
    private String encodeImageToBase64(BufferedImage image) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        javax.imageio.ImageIO.write(image, "PNG", baos);  // PNG 형식으로 이미지 저장
        byte[] imageBytes = baos.toByteArray();
        return Base64.getEncoder().encodeToString(imageBytes);  // Base64로 인코딩
    }

    // 이미지를 Base64로 인코딩하는 메서드
    private String encodeImageToBase64(String imagePath) throws IOException {
        Resource resource = new ClassPathResource(imagePath); // 이미지 경로를 로드
        byte[] bytes = StreamUtils.copyToByteArray(resource.getInputStream()); // 이미지 파일을 바이트 배열로 변환
        return Base64.getEncoder().encodeToString(bytes); // Base64로 인코딩 후 반환
    }

}
