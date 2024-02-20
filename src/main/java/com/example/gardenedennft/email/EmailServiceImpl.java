package com.example.gardenedennft.email;

import com.example.gardenedennft.exception.ResourceNotFoundException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService{

    private final static Logger LOGGER = LoggerFactory
            .getLogger(EmailService.class);

    private final JavaMailSender mailSender;

    @Override
    public void send(String to, String email, String subject) {
        try
        {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper =
                    new MimeMessageHelper(mimeMessage,"utf-8");
            helper.setText(email,true);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setFrom("gardeneden.try@gmail.com","Garden Eden");
            mailSender.send(mimeMessage);
        }
        catch (MessagingException e)
        {
            LOGGER.error("failed to send email", e);
            throw new IllegalStateException("failed to send email");
        }
        catch (UnsupportedEncodingException e)
        {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String buildEmail(String email, String link, boolean isValidEmail) {

        String maskedEmail1 = email
                .replace("@", "&#x40;")
                .replace(".", "&#x2E;");

        String[] emailParts = email.split("@");
        String maskedEmail2 = emailParts[0] + "&#x40;" + emailParts[1].replace(".", "&#x2E;");


        return "<tbody style=\"background-color: #000000\">" +
                "<tr valign=\"top\" style=\"vertical-align:top\">" +
                "<td valign=\"top\" style=\"word-break:break-word;vertical-align:top\">" +

                "<div>" +
                "<div style=\"Margin:0 auto;min-width:320px;max-width:500px;word-wrap:break-word;word-break:break-word\" class=\"m_-55359595955564775block-grid\">" +
                "<div style=\"border-collapse:collapse;display:table;width:100%\">" +

                "<div style=\"min-width:320px;max-width:500px;display:table-cell;vertical-align:top;width:500px\" class=\"m_-55359595955564775col\">" +
                "<div style=\"width:100%!important\">" +

                "<div style=\"border-top:0px solid transparent;border-left:0px solid transparent;border-bottom:20px solid transparent;border-right:0px solid transparent;padding-top:0px;padding-bottom:0px;padding-right:0px;padding-left:0px\">" +

                "<div></div>" +

                "</div>" +

                "</div>" +
                "</div>" +

                "</div>" +
                "</div>" +
                "<div>" +
                "<div style=\"Margin:0 auto;min-width:320px;max-width:500px;word-wrap:break-word;word-break:break-word\" class=\"m_-55359595955564775block-grid\">" +
                "<div style=\"border-collapse:collapse;display:table;width:100%\">" +

                "<div style=\"min-width:320px;max-width:500px;display:table-cell;vertical-align:top;width:500px\" class=\"m_-55359595955564775col\">" +
                "<div style=\"width:100%!important\">" +

                "<div style=\"border-top:0px solid transparent;border-left:0px solid transparent;border-bottom:0px solid transparent;border-right:0px solid transparent;padding-top:0px;padding-bottom:15px;padding-right:50px;padding-left:50px\">" +

                "<div align=\"left\" style=\"padding-right:0px;padding-left:0px\">" +

                "<img alt=\"Logo\" src=\"https://ci3.googleusercontent.com/meips/ADKq_NZdXr8t4EMLfMxPYS3lCOa-qkGNbaeH3BvWyCQaFP3XmhKnI7TxbzxqLOHpERyyBzQopAEukJfiU--HalTD1QKIKUK8ojVnXMW-ttCJ5YRbnZ6wxZ6NqJsovC1XgdDo9KuiC5_KrM68pmPmxd0XhUgspn_w6uMUa7wKadEiHaFzcYAm=s0-d-e1-ft#https://assets.fortmatic.com/MagicLogos/b146580fc7674e2d1df63364da1b2c2e/ee899a3edc7b329249bd1947c0eea95d.png\" border=\"0\" style=\"text-decoration:none;border:0;display:block;border-radius:6px;max-width:200px;max-height:69px;width:auto;height:auto;object-fit:contain\" class=\"CToWUd\" data-bit=\"iit\">" +

                "<div style=\"font-size:1px;line-height:10px\">&nbsp;</div>" +

                "</div>" +

                "<div style=\"font-family:'Helvetica Neue',Helvetica,Arial,sans-serif;line-height:1.8;padding-top:5px;padding-right:0px;padding-bottom:0px;padding-left:0px\">" +
                "<div style=\"line-height:1.8;font-family:'Helvetica Neue',Helvetica,Arial,sans-serif;font-size:12px\">" +
                "<p style=\"line-height:1.8;font-size:12px;margin:0\">" +
                "<span style=\"font-size:34px\">" +
                "<strong>" +
                "<a href=\"#m_-55359595955564775_\" style=\"color:#ffffff!important;text-decoration:none!important\">creators</a>" +
                "</strong>" +
                "</span>" +
                "</p>" +
                "</div>" +
                "</div>" +

                "<div style=\"font-family:'Helvetica Neue',Helvetica,Arial,sans-serif;line-height:1.8;padding-top:15px;padding-right:0px;padding-bottom:15px;padding-left:0px\">" +
                "<div style=\"font-size:12px;line-height:1.8;font-family:'Helvetica Neue',Helvetica,Arial,sans-serif\">" +
                "<p style=\"font-size:14px;line-height:1.8;margin:0\">" +
                "<span style=\"font-size:14px\">" +
                "<a href=\"#m_-55359595955564775_\" style=\"color:#ffffff!important;text-decoration:none!important\">Click the button below to log in to <strong>creators</strong>.</a>" +
                "</span>" +
                "</p>" +
                "<p style=\"font-size:14px;color:#ffffff!important;line-height:1.8;margin:0\">" +
                "<span style=\"font-size:14px\">This button will expire in 15 minutes.</span>" +
                "</p>" +
                "</div>" +
                "</div>" +

                "<div align=\"left\" style=\"padding-top:10px;padding-right:10px;padding-bottom:10px;padding-left:0px\">" +
                "<a class=\"m_-55359595955564775login-button m_-55359595955564775bg\" href=\""+link+"\" style=\"line-height:1.8;display:block;text-align:center;text-decoration:none!important;font-size:14px;color:#ffffff!important;text-transform:uppercase;background-color:#f31d5d;border-radius:10px;font-weight:bold;letter-spacing:2px;padding-top:15px;padding-bottom:15px;padding-right:25px;padding-left:25px;margin-top:10px\">" +
                "<span style=\"font-size:14px;text-transform:uppercase\">Log In to creators</span>" +
                "</a>" +
                "</div>" +

                "<div style=\"font-family:'Helvetica Neue',Helvetica,Arial,sans-serif;line-height:1.8;padding-top:15px;padding-right:0px;padding-bottom:15px;padding-left:0px\">" +
                "<div style=\"font-size:12px;display:block;line-height:1.8;font-family:'Helvetica Neue',Helvetica,Arial,sans-serif;\">" +
                "<p style=\"font-size:14px;line-height:1.8;margin:0;color:#ffffff!important\">" +
                "Confirming this request will securely log you in using" +
                "</p>" +
                "<p style=\"font-size:14px;line-height:1.8;margin:0;font-weight:700;color:#E42575!important\">" +
                ""+maskedEmail1+""+
                "</p>" +
                "</div>"+
                "</div>" +
                "<div style=\"font-family:'Helvetica Neue',Helvetica,Arial,sans-serif;line-height:1.8;padding-top:15px;padding-right:0px;padding-bottom:0px;padding-left:0px\">" +
                "<div style=\"font-size:12px;line-height:1.8;font-family:'Helvetica Neue',Helvetica,Arial,sans-serif\">" +
                "<p style=\"font-size:14px;line-height:1.8;margin:0\">" +
                "<span style=\"font-size:14px;color:#ffffff!important\">Thanks.</span>" +
                "</p>" +
                "</div>" +
                "</div>" +
                "</div>" +
                "</div>" +
                "</div>" +
                "</div>" +
                "</div>" +
                "</div>" +
                "</div>" +
                "</div>" +
                "</td>" +
                "</tr>" +
                "</tbody>";
    }


}
