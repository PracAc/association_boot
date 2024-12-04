package org.oz.association_boot.mail.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MailHtmlSendDTO {

    private Long ano;
    private String emailAddr;
    private String subject;
    private String content;
    private String name;
    private String htmlMsg;
}
