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

    @Override
    public String buildEmailVerify(String email, String link, boolean isValidEmail) {

        return "<div style=\"margin:0;background-color:#000000;padding:0\">\n" +
                "<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"background-color:#000000\">\n" +
                "  <tbody>\n" +
                "  <tr>\n" +
                "    <td>\n" +
                "      <table align=\"center\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"background-color:#000000\">\n" +
                "        <tbody>\n" +
                "        \n" +
                "        <tr>\n" +
                "          <td>\n" +
                "            <table class=\"m_2990887443810164962row-content m_2990887443810164962stack\" align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"color:#000000;width:650px\" width=\"650\">\n" +
                "              <tbody>\n" +
                "              <tr>\n" +
                "                <td class=\"m_2990887443810164962column\" width=\"100%\" style=\"font-weight:400;text-align:left;vertical-align:top;padding-top:0px;padding-bottom:0px;border-top:0px;border-right:0px;border-bottom:0px;border-left:0px\">\n" +
                "                  <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\">\n" +
                "                    <tbody><tr>\n" +
                "                      <td style=\"width:100%;padding-right:0px;padding-left:0px\">\n" +
                "                      <div align=\"center\" style=\"line-height:10px\"><img src=\"https://ci3.googleusercontent.com/meips/ADKq_NaVo5p-Vs5-Cbza-aI3JB7oJImL7sevixczv2qmtFDcd1J3gLXIP_gUzEUp3kV3E6jmuU8J47lsEqjLzz_Xq1Qgv857z2r5VPEvauHaVpJ6=s0-d-e1-ft#https://ord.cdn.magiceden.dev/static_resources/ME+logo.png\" style=\"display:block;height:auto;border:0;width:100px;margin:50px;max-width:100%\" width=\"130\" alt=\"Magic Eden logo\" title=\"Magic Eden logo\" class=\"CToWUd\" data-bit=\"iit\"></div>\n" +
                "                      </td>\n" +
                "                    </tr>\n" +
                "                  </tbody></table>\n" +
                "                </td>\n" +
                "              </tr>\n" +
                "              </tbody>\n" +
                "            </table>\n" +
                "          </td>\n" +
                "        </tr>\n" +
                "        </tbody>\n" +
                "      </table>\n" +
                "      <table align=\"center\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"background-color:#000000\">\n" +
                "        <tbody>\n" +
                "        <tr>\n" +
                "          <td>\n" +
                "            <table class=\"m_2990887443810164962row-content m_2990887443810164962stack\" align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"background-color:#000000;color:#000000;width:650px\" width=\"650\">\n" +
                "              <tbody>\n" +
                "              <tr>\n" +
                "                <td class=\"m_2990887443810164962column\" width=\"100%\" style=\"font-weight:400;text-align:left;vertical-align:top;padding-top:20px;padding-bottom:0px;border-top:0px;border-right:0px;border-bottom:0px;border-left:0px\">\n" +
                "                  <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\">\n" +
                "                    <tbody><tr>\n" +
                "                      <td style=\"padding-bottom:15px;text-align:center;width:100%\">\n" +
                "                        <h1 style=\"margin:0;color:#ffffff;direction:ltr;font-family:'Cabin',Arial,'Helvetica Neue',Helvetica,sans-serif;font-size:28px;font-weight:normal;letter-spacing:normal;line-height:120%;text-align:center;margin-top:0;margin-bottom:0\"><strong>Gm! Please verify your email address</strong></h1>\n" +
                "                      </td>\n" +
                "                    </tr>\n" +
                "                  </tbody></table>\n" +
                "                  <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\">\n" +
                "                    <tbody><tr>\n" +
                "                      <td style=\"padding-bottom:15px;text-align:center;width:100%\">\n" +
                "                        <h1 style=\"margin:0;color:#b8b7b7;direction:ltr;font-family:'Cabin',Arial,'Helvetica Neue',Helvetica,sans-serif;font-size:17px;font-weight:normal;letter-spacing:normal;line-height:120%;text-align:center;margin-top:0;margin-bottom:0\">Clicking this button will verify your email address:</h1>\n" +
                "                      </td>\n" +
                "                    </tr>\n" +
                "                  </tbody></table>\n" +
                "                  <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\">\n" +
                "                    <tbody><tr>\n" +
                "                      <td style=\"padding-bottom:40px;padding-left:10px;padding-right:10px;padding-top:15px;text-align:center\">\n" +
                "                        <div align=\"center\">\n" +
                "                          <a href=\""+link+"\" style=\"text-decoration:none;display:inline-block;color:#ffffff;background-color:#e42575;border-radius:0px;width:auto;border-top:1px solid #e42575;border-right:1px solid #e42575;border-bottom:1px solid #e42575;border-left:1px solid #e42575;padding-top:10px;padding-bottom:10px;font-family:'Cabin',Arial,'Helvetica Neue',Helvetica,sans-serif;text-align:center;word-break:keep-all\" target=\"_blank\">\n" +
                "                            <span style=\"padding-left:40px;padding-right:40px;font-size:16px;display:inline-block;letter-spacing:normal\">\n" +
                "                              <span style=\"font-size:16px;line-height:2;word-break:break-word\">\n" +
                "                                <span style=\"font-size:16px;line-height:32px\">\n" +
                "                                  Verify my email\n" +
                "                                </span>\n" +
                "                              </span>\n" +
                "                            </span>\n" +
                "                          </a>\n" +
                "                        </div>\n" +
                "                      </td>\n" +
                "                    </tr>\n" +
                "                  </tbody></table>\n" +
                "                </td>\n" +
                "              </tr>\n" +
                "              </tbody>\n" +
                "            </table>\n" +
                "          </td>\n" +
                "        </tr>\n" +
                "        </tbody>\n" +
                "      </table>\n" +
                "      <table align=\"center\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"background-color:#000000\">\n" +
                "        <tbody>\n" +
                "        <tr>\n" +
                "          <td>\n" +
                "            <table class=\"m_2990887443810164962row-content m_2990887443810164962stack\" align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"color:#000000;width:650px\" width=\"650\">\n" +
                "              <tbody>\n" +
                "              <tr>\n" +
                "                <td class=\"m_2990887443810164962column\" width=\"100%\" style=\"font-weight:400;text-align:left;vertical-align:top;padding-top:20px;padding-bottom:10px;border-top:0px;border-right:0px;border-bottom:0px;border-left:0px\">\n" +
                "                  <table width=\"100%\" border=\"0\" cellpadding=\"5\" cellspacing=\"0\" role=\"presentation\">\n" +
                "                    <tbody><tr>\n" +
                "                      <td>\n" +
                "                        <div align=\"center\">\n" +
                "                          <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" width=\"100%\">\n" +
                "                            <tbody><tr>\n" +
                "                              <td style=\"font-size:1px;line-height:1px;border-top:0px solid #bbbbbb\"><span>\u200a</span></td>\n" +
                "                            </tr>\n" +
                "                          </tbody></table>\n" +
                "                        </div>\n" +
                "                      </td>\n" +
                "                    </tr>\n" +
                "                  </tbody></table>\n" +
                "                  <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"word-break:break-word\">\n" +
                "                    <tbody><tr>\n" +
                "                      <td>\n" +
                "                        <div style=\"font-family:sans-serif\">\n" +
                "                          <div style=\"font-size:12px;color:#393d47;line-height:1.2;font-family:Arial,Helvetica Neue,Helvetica,sans-serif\">\n" +
                "                            <p style=\"margin:0;text-align:center\"><span style=\"color:#8a3b8f\"><span style=\"font-size:11px\">If you no longer wish to receive emails from Garden Eden, you can unsubscribe <a href=\"https://unsubscribe.os-list.com/unsubscribe?token=eyJhbGciOiJFUzI1NiJ9.eyJpYXQiOjE3MDY3MDAxMDAsInVuc3Vic2NyaWJlX3Rva2VuIjoiYkdWa2RXTmhibWgyZFRNd01EWkFaMjFoYVd3dVkyOXRPelV4T0dRPSIsImFwcF9pZCI6IjZiNDg3ZGI2LTk4YjMtNDU3Mi1iOWM0LTYxMmNkZjE0NDk5NCIsIm5vdGlmaWNhdGlvbl9pZCI6ImJkMTk5MmEwLTFmN2EtNGE4ZC1hMzE0LTZkOTU0MzBhNzRhOCJ9.yG0Q1I0472PxC0BtyNauYqqfCwxAOhD4NvSmZ02_iHVWLDLkZKI1eqZQVCQioY0pldUpSs6cF3okWsBU3HwCzA\" target=\"_blank\" data-saferedirecturl=\"https://www.google.com/url?q=https://unsubscribe.os-list.com/unsubscribe?token%3DeyJhbGciOiJFUzI1NiJ9.eyJpYXQiOjE3MDY3MDAxMDAsInVuc3Vic2NyaWJlX3Rva2VuIjoiYkdWa2RXTmhibWgyZFRNd01EWkFaMjFoYVd3dVkyOXRPelV4T0dRPSIsImFwcF9pZCI6IjZiNDg3ZGI2LTk4YjMtNDU3Mi1iOWM0LTYxMmNkZjE0NDk5NCIsIm5vdGlmaWNhdGlvbl9pZCI6ImJkMTk5MmEwLTFmN2EtNGE4ZC1hMzE0LTZkOTU0MzBhNzRhOCJ9.yG0Q1I0472PxC0BtyNauYqqfCwxAOhD4NvSmZ02_iHVWLDLkZKI1eqZQVCQioY0pldUpSs6cF3okWsBU3HwCzA&amp;source=gmail&amp;ust=1708710975121000&amp;usg=AOvVaw0dkZIj3Pb4aAA5Zwo97X8j\"><u>here</u></a>.</span></span></p>\n" +
                "                            <p style=\"margin:0;text-align:center\">&nbsp;</p>\n" +
                "                            <p style=\"margin:0;text-align:center\"><span style=\"font-size:11px\"><span style=\"color:#8a3b8f\">©2024 Garden Eden</span></span></p>\n" +
                "                            <p style=\"margin:0\">&nbsp;</p>\n" +
                "                          </div>\n" +
                "                        </div>\n" +
                "                      </td>\n" +
                "                    </tr>\n" +
                "                  </tbody></table>\n" +
                "                </td>\n" +
                "              </tr>\n" +
                "              </tbody>\n" +
                "            </table>\n" +
                "          </td>\n" +
                "        </tr>\n" +
                "        </tbody>\n" +
                "      </table>\n" +
                "    </td>\n" +
                "  </tr>\n" +
                "  </tbody>\n" +
                "</table>\n" +
                "</div>";
    }


}
