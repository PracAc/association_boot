package org.oz.association_boot.mail;

import org.oz.association_boot.mail.dto.MailHtmlSendDTO;
import org.oz.association_boot.mail.dto.MailTxtSendDTO;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public interface MailSendService {
    void sendHtmlMail(MailHtmlSendDTO mailHtmlSendDTO);

    void sendCaptChaMail(MailHtmlSendDTO mailHtmlSendDTO, String captchaText);

    void sendFileMail(MailTxtSendDTO mailTxtSendDTO, MultipartFile[] file);
}
