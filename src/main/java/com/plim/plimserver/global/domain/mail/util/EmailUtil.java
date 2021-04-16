package com.plim.plimserver.global.domain.mail.util;

import com.plim.plimserver.global.domain.mail.domain.EmailSubject;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@RequiredArgsConstructor
@Component
public class EmailUtil {

    private final JavaMailSender javaMailSender;

    public void sendEmail(String toAddress, EmailSubject subject, String body) {

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        try {
            helper.setTo(toAddress);
            helper.setSubject(subject.getSubject());
            helper.setText(body, true);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        javaMailSender.send(message);
    }

    public String getEmailAuthMessage(String email, String authCode) {
        return "<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n" +
                "        <tbody>\n" +
                "            <tr>\n" +
                "                <td align=\"center\">\n" +
                "                    <div style=\"max-width:520px;margin:0 auto\">\n" +
                "                        <div\n" +
                "                            style=\"vertical-align:top;text-align:left;font-family:-apple-system,BlinkMacSystemFont,Segoe UI,Roboto,Oxygen,Ubuntu,Fira Sans,Droid Sans,Helvetica Neue,sans-serif;font-size:14px;font-weight:400;color:#091e42;line-height:20px\">\n" +
                "                            <div style=\"padding-top:0px;padding-bottom:0px;vertical-align:top;text-align:center\"><a\n" +
                "                                    href=\"https://www.google.com\" target=\"_blank\"\n" +
                "                                    data-saferedirecturl=\"https://www.google.com/\"><img\n" +
                "                                        src=\"https://lh3.googleusercontent.com/5fxhI2ySjesWT3sYMQgDrBVjaR39v7Fy2tnHCar_TiJDtBmedQWSMzMR2iEUmnXhrd0sL86htpnP851MYd4sVrwgQHf0Hhc2nWhPC_DcTUnNLlvCB_3d4ZPe97gGgBXGyKY584RZ44Pm6sRSJ_S5lNDCMTmGUT0oyx7YGvr3mjl3r8mBrJRpI4yxqUwgs1aheY4yX1EWPiuFvYhJG7-ZGsphpOEM64-QI5CetD8A_0uf2NIsNoYHEmW7b9_UUQWcYrdu2MPJLDOolB5v7gyQboWxm8_UrKZnMqM925TiqSMRSwiTwtKi1XKJCLQ8KRoXOhivn4tPZ4lN3jGblp9CM-64qDE-lMQCBl9qDGyXb7RRBgaHYgJ4jvB6CkLIA5Or-axlmlAU05k0AAvcJapghA-B2it-JYTwZSvH_J3-igoRsDDY2TaTuNsk_mZJX6yez2KpSCrS6vhgfBAZl-2EVFfQZ0pYo9F95p_8l86YYDJz6j11N0fpHzzijTAhGp_XohA8HwEhTrz2iT5u79-rQqIlAW9r85ZhcVFeWgoexpWtz3I1ekSZq55E8PfkuDarE6CY72BwYQ_3UFkRcNVbTgQhcgJTyCQ1TUj_cI8k4RksoJw7pNqvyTnZ9IpIU7LENqR3GW0ykm_PbaTlcBU24NUfwVGrctuRqQrdXeNO7cilj9YWecAlvU2sTRe1OB-AnkPmw9KJSXmedBctYNYih2E=w1280-h720-no?authuser=4\"\n" +
                "                                        height=\"150\" alt=\"KATI logo\" style=\"border:0\" class=\"CToWUd\"></a></div>\n" +
                "                            <hr style=\"margin-top:0px;margin-bottom:24px;border:0;border-bottom:1px solid #c1c7d0\">\n" +
                "                            <h2\n" +
                "                                style=\"margin-bottom:0;font-family:-apple-system,BlinkMacSystemFont,Segoe UI,Roboto,Oxygen,Ubuntu,Fira Sans,Droid Sans,Helvetica Neue,sans-serif;font-size:20px;font-weight:500;color:#172b4d;line-height:24px;margin-top:28px\">\n" +
                "                                <span>KATI</span> 회원가입 인증 메일</h2>\n" +
                "                            <p\n" +
                "                                style=\"font-family:-apple-system,BlinkMacSystemFont,Segoe UI,Roboto,Oxygen,Ubuntu,Fira Sans,Droid Sans,Helvetica Neue,sans-serif;font-size:14px;font-weight:400;color:#091e42;line-height:20px;margin-top:12px\">\n" +
                "                                안녕하세요.</p>\n" +
                "                            <p\n" +
                "                                style=\"font-family:-apple-system,BlinkMacSystemFont,Segoe UI,Roboto,Oxygen,Ubuntu,Fira Sans,Droid Sans,Helvetica Neue,sans-serif;font-size:14px;font-weight:400;color:#091e42;line-height:20px;margin-top:12px\">\n" +
                "                                회원가입을 완료하시려면 아래의 <b>이메일 인증</b>을 클릭하세요.</p>\n" +
                "                            <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n" +
                "                                <tbody>\n" +
                "                                    <tr>\n" +
                "                                        <td align=\"center\">\n" +
                "                                            <a style=\\\"text-decoration: none; font-size: 20px; color: #011627\\\"\n" +
                "                                                href='http://13.124.55.59:8080/api/v1/email-auth?email=" + email + "&authCode=" + authCode + "'>이메일 인증</a>\n" +
                "                                        </td>\n" +
                "                                    </tr>\n" +
                "                                </tbody>\n" +
                "                            </table>\n" +
                "                            <hr style=\"margin-top:24px;margin-bottom:24px;border:0;border-bottom:1px solid #c1c7d0\">\n" +
                "                            <div style=\"color:#707070;font-size:13px;line-height:19px;text-align:center;margin-top:10px\">\n" +
                "                                <table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" bgcolor=\"#ffffff\"\n" +
                "                                    align=\"center\">\n" +
                "                                    <tbody>\n" +
                "                                        <tr>\n" +
                "                                            <td valign=\"top\" align=\"center\"\n" +
                "                                                style=\"padding-top:10px;line-height:18px;text-align:center;font-weight:none;font-size:12px;color:#505f79\">\n" +
                "                                                <span>이 메시지는 KATI에서 전송되었습니다.</span><br></td>\n" +
                "                                        </tr>\n" +
                "                                        <tr valign=\"top\">\n" +
                "                                            <td align=\"center\" style=\"padding-top:15px;padding-bottom:30px\"><a\n" +
                "                                                    href=\"https://www.kati.com\" target=\"_blank\"\n" +
                "                                                    data-saferedirecturl=\"https://www.google.com\"><img\n" +
                "                                                        src=\"https://lh3.googleusercontent.com/-sBUEAHO8y3qsz5djKEl5408Pftmx4jY2yhnUr1vEjXti6IGlUxXkMDJzHUCNEPHFPfllkwCBDXcPDJn5a-Grbwf7d6sUOkq2zIoYLyl7rsT45LrcmHT5xGk4CJ52jomy40_wNOmZrvAT5uHIcTIDSSdNfCFpuf-OSYFNXXyg35hrz-Y4yErvw0Ws6CXxqFE6zcKGpyuq6L0FjC7TNlg_FXUo4P09j5L1kCykmvrda2Ny7IE_hkRmtIYWcIraSTdzt5BfCql9Yqo1kugHknCYa_7Z4zoLmdfC7dsGrq1rcSaPCt4nWfSLc9AdLlSzTwo9V5Ac8dTyKbqaB6auDR2enJLwyMwuV-Ehi-_9diw6V75hGA5kDBxEFWQtd8Cv3UuPXgzH9ZLavwhg9QRGo3Tk2rpVdQxsE1wsPjrYMF2Y5wwwFO0zNLIJ4AaEi0Kdm7p7qyhKPQm9CtopDLwctDlOLT9AXwsDU3QrQE74HMKsyUhhHDzc2LnEv7lxs4-d27saFou1FcYblrM_ytATtmXrJPMkEOxiYXKalGX8vv1QSs7NDRHNV6z4awPvHBpLV_m4yR92WwMjAzmTE6BB8-0UYXc8u4q2EmWJsjecFHkS9uQ6aV1rCa_AIw-PrYeazUUYlpaNseX4yraq_arZEn5PqKHHKb2QF3TB3cgkvvOz44VxvWoq3LqzqkmDC30l8OUfibrWoHMMJEz7jmfDUkxqXc=s1182-no?authuser=4\"\n" +
                "                                                        width=\"40\" border=\"0\" alt=\"Atlassian\"\n" +
                "                                                        style=\"display:block;color:#4c9ac9\" class=\"CToWUd\"></a></td>\n" +
                "                                        </tr>\n" +
                "                                    </tbody>\n" +
                "                                </table>\n" +
                "                            </div>\n" +
                "                        </div>\n" +
                "                    </div>\n" +
                "                </td>\n" +
                "            </tr>\n" +
                "        </tbody>\n" +
                "    </table>";
    }

}
