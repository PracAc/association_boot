package org.oz.association_boot.mail.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MailTxtSendDTO {

    private String emailAddr;
    private String subject;
    private String content;
}
