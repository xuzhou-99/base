package cn.altaria.base.util.mail;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;

import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * MailUtil
 * 邮件工具类
 *
 * @author xuzhou
 * @version v1.0.0
 * @create 2021/5/27 10:46
 */
public class MailUtil {

    private static final Logger log = LoggerFactory.getLogger(MailUtil.class);

    private final MimeMessage message;
    private final Session session;
    private final String smtpHost;
    private final String senderUserName;
    private final String senderPassword;
    private Transport transport;
    private int smtpPort = 587;


    public MailUtil(Properties properties, boolean debug) {
        this.smtpHost = properties.getProperty("mail.smtp.host");
        this.smtpPort = Integer.parseInt(properties.getProperty("mail.smtp.port"));
        this.senderUserName = properties.getProperty("mail.smtp.username");
        this.senderPassword = properties.getProperty("mail.smtp.password");

        Authenticator authenticator = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {

                return new PasswordAuthentication(senderUserName, senderPassword);
            }
        };

        session = Session.getInstance(properties, authenticator);
        session.setDebug(debug);
        message = new MimeMessage(session);
    }

    public MailUtil() {
        Properties properties = getProperty();
        this.smtpHost = properties.getProperty("mail.smtp.host");
        this.smtpPort = Integer.parseInt(properties.getProperty("mail.smtp.port"));
        this.senderUserName = properties.getProperty("mail.sender.username");
        this.senderPassword = properties.getProperty("mail.smtp.password");

        session = Session.getInstance(properties);
        message = new MimeMessage(session);
    }

    public MailUtil(String username, String password) {
        Properties properties = getProperty();
        this.smtpHost = properties.getProperty("mail.smtp.host");
        this.smtpPort = Integer.parseInt(properties.getProperty("mail.smtp.port"));
        this.senderUserName = username;
        this.senderPassword = password;

        session = Session.getInstance(properties);
        message = new MimeMessage(session);
    }

    /**
     * Send E-mail
     *
     * @param subject        主题
     * @param mailBody       邮件内容
     * @param senderNickName 发件人NickName
     * @param receiverUser   收件人地址
     * @param ccReceiveUser  抄送地址
     * @param bccReceiveUser 密送地址
     * @param isHtmlFormat   Html格式
     */
    public void sendEmail(String subject, String mailBody, String senderNickName, String receiverUser,
                          String ccReceiveUser, String bccReceiveUser, Boolean isHtmlFormat) {

        try {
            // 发件人
            InternetAddress from = null;
            if (ObjectUtils.isEmpty(senderNickName)) {
                from = new InternetAddress(senderUserName);
            } else {
                from = new InternetAddress(MimeUtility.encodeWord(senderNickName) + " <" + senderUserName + ">");
            }
            message.setFrom(from);

            // 收件人
            InternetAddress to = new InternetAddress(receiverUser);
            message.setRecipient(Message.RecipientType.TO, to);

            // 抄送人
            if (!ObjectUtils.isEmpty(ccReceiveUser)) {
                InternetAddress cc = new InternetAddress(ccReceiveUser);
                message.setRecipient(Message.RecipientType.CC, cc);
            }

            // 密送人
            if (!ObjectUtils.isEmpty(bccReceiveUser)) {
                InternetAddress bcc = new InternetAddress(bccReceiveUser);
                message.setRecipient(Message.RecipientType.BCC, bcc);
            }

            // 邮件主题
            message.setSubject(subject);

            String content = mailBody.toString();
            if (isHtmlFormat) {
                message.setContent(content, "text/html;charset=UTF-8");
            } else {
                message.setContent(content, "text/plain;charset=UTF-8");
            }
            message.saveChanges();

            transport = session.getTransport("smtp");
            transport.connect(smtpHost, smtpPort, senderUserName, senderPassword);
            transport.sendMessage(message, message.getAllRecipients());

            log.debug("{} 向 {} 发送邮件成功！", senderUserName, receiverUser);

        } catch (UnsupportedEncodingException | MessagingException e) {
            e.printStackTrace();
            log.error("SendEMail失败！", e);
        } finally {
            if (transport != null) {
                try {
                    transport.close();
                } catch (MessagingException e) {
                    e.printStackTrace();
                    log.error("SendEmail->transport关闭失败！", e);
                }
            }
        }
    }


    private Properties getProperty() {
        Properties properties = new Properties();
        properties.put("mail.smtp.host", smtpHost);
        properties.put("mail.smtp.port", smtpPort);
        properties.put("mail.smtp.username", senderUserName);
        properties.put("mail.smtp.password", senderPassword);
        return properties;
    }


}
