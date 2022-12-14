package cn.altaria.base.util.mail;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.mail.util.MailSSLSocketFactory;

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

    private static final EmailConfig emailConfig;

    private static Properties properties = null;

    private static Authenticator authenticator;
    private static Session session;

    private static final MailAcount GlobalMailAccount;

    static {
        emailConfig = new EmailConfig();
        session = getSession();
        GlobalMailAccount = new MailAcount();
        GlobalMailAccount.setUsername(emailConfig.getUsername());
        GlobalMailAccount.setPassword(emailConfig.getPassword());
    }

    private MailUtil() {

    }

    public static MailAcount getGlobalMailAccount() {
        return GlobalMailAccount;
    }

    /**
     * Send E-mail
     *
     * @param subject        主题
     * @param mailBody       邮件内容
     * @param senderNickName 发件人NickName
     * @param receiverUser   收件人地址
     * @param isHtmlFormat   Html格式
     */
    public static void sendEmail(String subject, String mailBody, String senderNickName,
                                 String receiverUser,
                                 Boolean isHtmlFormat) {
        Session session = getSession();
        sendEmail(session, subject, mailBody, senderNickName, emailConfig.getUsername(),
                receiverUser, null, null,
                isHtmlFormat, null);
    }

    /**
     * Send E-mail
     *
     * @param subject        主题
     * @param mailBody       邮件内容
     * @param senderNickName 发件人NickName
     * @param receiverUser   收件人地址
     * @param isHtmlFormat   Html格式
     * @param files          附件列表
     */
    public static void sendEmail(String subject, String mailBody, String senderNickName,
                                 String receiverUser,
                                 Boolean isHtmlFormat, File... files) {
        Session session = getSession();
        sendEmail(session, subject, mailBody, senderNickName, emailConfig.getUsername(),
                receiverUser, null, null,
                isHtmlFormat, files);
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
     * @param files          附件列表
     */
    public static void sendEmail(String subject, String mailBody, String senderNickName,
                                 String receiverUser, String ccReceiveUser, String bccReceiveUser,
                                 Boolean isHtmlFormat, File... files) {
        Session session = getSession();
        sendEmail(session, subject, mailBody, senderNickName, emailConfig.getUsername(),
                receiverUser, ccReceiveUser, bccReceiveUser,
                isHtmlFormat, files);
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
    public static void sendEmail(String subject, String mailBody, String senderNickName, String fromUser,
                                 String receiverUser, String ccReceiveUser, String bccReceiveUser,
                                 Boolean isHtmlFormat) {
        Session session = getSession();
        sendEmail(session, subject, mailBody, senderNickName, fromUser,
                receiverUser, ccReceiveUser, bccReceiveUser,
                isHtmlFormat, null);
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
     * @param files          附件列表
     */
    public static void sendEmail(String subject, String mailBody, String senderNickName, String fromUser,
                                 String receiverUser, String ccReceiveUser, String bccReceiveUser,
                                 Boolean isHtmlFormat, File... files) {

        Session session = getSession();
        sendEmail(session, subject, mailBody, senderNickName, fromUser,
                receiverUser, ccReceiveUser, bccReceiveUser,
                isHtmlFormat, files);
    }


    /**
     * Send E-mail
     *
     * @param subject        主题
     * @param mailBody       邮件内容
     * @param senderNickName 发件人NickName
     * @param receiverUser   收件人地址
     * @param isHtmlFormat   Html格式
     */
    public static void sendEmail(Session session, String subject, String mailBody, String senderNickName, String fromUser,
                                 String receiverUser,
                                 Boolean isHtmlFormat) {
        sendEmail(session, subject, mailBody, senderNickName, fromUser,
                receiverUser, null, null,
                isHtmlFormat, null);
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
    public static void sendEmail(Session session, String subject, String mailBody, String senderNickName, String fromUser,
                                 String receiverUser, String ccReceiveUser, String bccReceiveUser,
                                 Boolean isHtmlFormat) {
        sendEmail(session, subject, mailBody, senderNickName, fromUser,
                receiverUser, ccReceiveUser, bccReceiveUser,
                isHtmlFormat, null);
    }

    /**
     * Send E-mail
     *
     * @param session        邮件session
     * @param subject        主题
     * @param mailBody       邮件内容
     * @param senderNickName 发件人NickName
     * @param receiverUser   收件人地址
     * @param ccReceiveUser  抄送地址
     * @param bccReceiveUser 密送地址
     * @param isHtmlFormat   Html格式
     * @param files          附件列表
     */
    public static void sendEmail(Session session, String subject, String mailBody, String senderNickName, String fromUser,
                                 String receiverUser, String ccReceiveUser, String bccReceiveUser,
                                 Boolean isHtmlFormat, File... files) {

        try {
            MimeMessage message = createMessage(session, subject, mailBody, senderNickName, fromUser,
                    receiverUser, ccReceiveUser, bccReceiveUser, isHtmlFormat, files);
            sendEmail(session, message);

            log.info("{} 向 {} 发送邮件成功！", fromUser, receiverUser);

        } catch (UnsupportedEncodingException | MessagingException e) {
            e.printStackTrace();
            log.error("SendEMail失败！", e);
        }
    }

    /**
     * Send E-mail
     *
     * @param session 邮件session
     * @param message 邮件
     */
    public static void sendEmail(Session session, Message message) {
        Transport transport = null;
        try {
            transport = session.getTransport();
            transport.connect();
            transport.sendMessage(message, message.getAllRecipients());

            log.info("发送邮件成功！");

        } catch (MessagingException e) {
            e.printStackTrace();
            log.error("SendEMail失败！", e);
        } finally {
            if (transport != null) {
                try {
                    transport.close();
                } catch (MessagingException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }


    /**
     * 创建邮件消息
     *
     * @param session        session
     * @param subject        邮件主题
     * @param mailBody       邮件内容
     * @param senderNickName 发送人名称
     * @param fromUser       发送人邮箱
     * @param receiverUser   接收人邮箱
     * @param ccReceiveUser  抄送人邮箱
     * @param bccReceiveUser 密送人邮箱
     * @param isHtmlFormat   是否html
     * @param files          文件列表
     * @return 邮件
     * @throws MessagingException           创建邮件异常
     * @throws UnsupportedEncodingException 发送人名称编码异常
     */
    public static MimeMessage createMessage(Session session, String subject, String mailBody, String senderNickName, String fromUser,
                                            String receiverUser, String ccReceiveUser, String bccReceiveUser,
                                            Boolean isHtmlFormat, File... files) throws MessagingException, UnsupportedEncodingException {

        MimeMessage message = new MimeMessage(session);
        // 发件人
        InternetAddress from;
        if (null == senderNickName) {
            from = new InternetAddress(fromUser);
        } else {
            from = new InternetAddress(MimeUtility.encodeWord(senderNickName) + " <" + fromUser + ">");
        }
        message.setFrom(from);

        // 收件人
        InternetAddress to = new InternetAddress(receiverUser);
        message.setRecipient(Message.RecipientType.TO, to);

        // 抄送人
        if (null != ccReceiveUser) {
            InternetAddress cc = new InternetAddress(ccReceiveUser);
            message.setRecipient(Message.RecipientType.CC, cc);
        }

        // 密送人
        if (null != bccReceiveUser) {
            InternetAddress bcc = new InternetAddress(bccReceiveUser);
            message.setRecipient(Message.RecipientType.BCC, bcc);
        }

        // 邮件主题
        message.setSubject(subject);

        if (files == null) {
            if (Boolean.TRUE.equals(isHtmlFormat)) {
                message.setContent(mailBody, "text/html;charset=UTF-8");
            } else {
                message.setContent(mailBody, "text/plain;charset=UTF-8");
            }

            message.saveChanges();

            return message;
        } else {

            // 整封邮件的MIME消息体
            MimeMultipart mimeMultipart = new MimeMultipart("mixed");
            // 设置消息体
            message.setContent(mimeMultipart);

            // 正文内容
            MimeBodyPart content = new MimeBodyPart();
            mimeMultipart.addBodyPart(content);

            //正文（图片和文字部分）
            MimeMultipart bodyMultipart = new MimeMultipart("related");
            //设置内容为正文
            content.setContent(bodyMultipart);
            //text部分
            MimeBodyPart textPart = new MimeBodyPart();
            //html代码部分
            MimeBodyPart htmlPart = new MimeBodyPart();
            //html中嵌套的图片部分
            MimeBodyPart gifPart = new MimeBodyPart();
            //正文添加图片和html代码
            bodyMultipart.addBodyPart(textPart);
            bodyMultipart.addBodyPart(htmlPart);
            bodyMultipart.addBodyPart(gifPart);

            //text部分
            textPart.setText(mailBody);

//            //说明html中的img标签的src，引用的是此图片
//            gifPart.setHeader("Content-Location", "https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=2703288617,3613848102&fm=23&gp=0.jpg");
//
//            //html代码
//            htmlPart.setContent("时间就是一个沙漏。<img src='https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1495797374&di=537ebbbc19d917e7c425475e867069ca&imgtype=jpg&er=1&src=http%3A%2F%2Fimgsrc.baidu.com%2Fforum%2Fpic%2Fitem%2Fcf594910b912c8fc2c8ed308fc039245d48821cc.jpg'>", "text/html;charset=utf-8");


            // 附件
            for (File file : files) {
                // 附件
                MimeBodyPart attach = new MimeBodyPart();
                // 将附件放入MIME消息体中
                mimeMultipart.addBodyPart(attach);
                // 数据源
                FileDataSource fileDataSource = new FileDataSource(file);
                // 数据处理器
                DataHandler dataHandler = new DataHandler(fileDataSource);
                // 设置附件数据
                attach.setDataHandler(dataHandler);
                // 设置附件文件名
                attach.setFileName(MimeUtility.decodeText(file.getName()));
            }
        }

        message.saveChanges();
        return message;
    }

    public static Session getSession() {
        if (session == null) {
            session = Session.getInstance(getProperty(), getAuthenticator());
        }
        return session;
    }

    public static Session getSession(MailAcount mailAcount) {
        return createSession(mailAcount.getUsername(), mailAcount.getPassword(), mailAcount.getHost(), mailAcount.getPort(), mailAcount.getIsSsl());
    }


    private static Properties getProperty() {
        if (properties == null) {
            properties = new Properties();
            // 表示SMTP发送邮件，必须进行身份验证
            properties.setProperty("mail.smtp.auth", "true");
            properties.setProperty("mail.smtp.host", emailConfig.getHost());
            properties.setProperty("mail.smtp.port", emailConfig.getPort());
            properties.setProperty("mail.user", emailConfig.getUsername());
            properties.setProperty("mail.password", emailConfig.getPassword());
            return properties;
        }
        return properties;
    }

    private static Authenticator getAuthenticator() {
        if (authenticator == null) {
            // 用户名、密码
            String userName = properties.getProperty("mail.user");
            String password = properties.getProperty("mail.password");
            authenticator = getAuthenticator(userName, password);
        }

        return authenticator;
    }

    protected static Authenticator getAuthenticator(String username, String password) {
        return new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        };
    }


    public static Session createSession(String username, String password, String host, String port) {
        return createSession(username, password, host, port, false);
    }

    /**
     * PS: 某些邮箱服务器要求 SMTP 连接需要使用 SSL 安全认证 (为了提高安全性, 邮箱支持SSL连接, 也可以自己开启),
     * 如果无法连接邮件服务器, 仔细查看控制台打印的 log, 如果有有类似 “连接失败, 要求 SSL 安全连接” 等错误,
     * 则开启 SSL 安全连接。
     *
     * @param username 邮箱
     * @param password 密码/授权码
     * @param host     服务器地址
     * @param port     端口
     * @param ssl      是否开启ssl
     * @return 连接session
     */
    public static Session createSession(String username, String password, String host, String port, boolean ssl) {

        Properties props = new Properties();
        // 表示SMTP发送邮件，必须进行身份验证
        props.put("mail.smtp.auth", "true");
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);
        props.put("mail.user", username);
        props.put("mail.password", password);

        // SMTP 服务器的端口 (非 SSL 连接的端口一般默认为 25, 可以不添加, 如果开启了 SSL 连接,
        //                  需要改为对应邮箱的 SMTP 服务器的端口, 具体可查看对应邮箱服务的帮助,
        //                  QQ邮箱的SMTP(SLL)端口为465或587, 其他邮箱自行去查看)

        if (ssl) {

            //使用SSL，企业邮箱必需
            //开启安全协议
            MailSSLSocketFactory sf = null;
            try {
                sf = new MailSSLSocketFactory();
                sf.setTrustAllHosts(true);
            } catch (GeneralSecurityException e1) {
                e1.printStackTrace();
            }
            props.put("mail.smtp.ssl.enable", "true");
            props.put("mail.smtp.ssl.socketFactory", sf);

        }

        return Session.getInstance(props, new Authenticator() {
            //此访求返回用户和密码的对象
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
    }


}
