package org.oz.association_boot.mail;

import org.oz.association_boot.mail.dto.MailHtmlSendDTO;
import org.oz.association_boot.mail.dto.MailTxtSendDTO;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public interface MailService {
    void sendHtmlMail(MailHtmlSendDTO mailHtmlSendDTO);

    void sendAuthCodeMail(MailHtmlSendDTO mailHtmlSendDTO, String AuthCode);

    void sendFileMail(MailTxtSendDTO mailTxtSendDTO, MultipartFile[] file);

    void sendEmailAuthCodeMail(MailHtmlSendDTO mailHtmlSendDTO, String authCode);

}
