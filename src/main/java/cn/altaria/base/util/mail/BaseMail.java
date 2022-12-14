package cn.altaria.base.util.mail;

import java.io.File;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;


/**
 * @author xuzhou
 * @since 2022/12/6
 */
public class BaseMail {

    private MailAcount mailAcount;

    private String[] tos;
    private String[] ccs;
    private String[] bccs;
    private String[] reply;
    protected String mailSubject;
    protected String mailContent;
    private boolean isHtml;
    private final Multipart multipart;
    private boolean useGlobalSession;
    private PrintStream debugOutput;


    public BaseMail() {
        // 整封邮件的MIME消息体
        // MimeMultipart bodyMultipart = new MimeMultipart("related");
        multipart = new MimeMultipart();
        mailAcount = MailUtil.getGlobalMailAccount();
    }


    public BaseMail(MailAcount mailAcount) {
        this.mailAcount = mailAcount;
        multipart = new MimeMultipart();
    }


    public String getMailSubject() {
        return mailSubject;
    }

    public BaseMail setMailSubject(String mailSubject) {
        this.mailSubject = mailSubject;
        return this;
    }

    public String getMailContent() {
        return mailContent;
    }

    public BaseMail setMailContent(String mailContent) {
        this.mailContent = mailContent;
        return this;
    }

    public String[] getTos() {
        return tos;
    }

    public BaseMail setTos(String... tos) {
        this.tos = tos;
        return this;
    }

    public String[] getCcs() {
        return ccs;
    }

    public BaseMail setCcs(String... ccs) {
        this.ccs = ccs;
        return this;
    }

    public String[] getBccs() {
        return bccs;
    }

    public BaseMail setBccs(String... bccs) {
        this.bccs = bccs;
        return this;
    }

    public String[] getReply() {
        return reply;
    }

    public BaseMail setReply(String[] reply) {
        this.reply = reply;
        return this;
    }

    public boolean isHtml() {
        return isHtml;
    }

    public BaseMail setHtml(boolean html) {
        isHtml = html;
        return this;
    }

    public Multipart getMultipart() {
        return multipart;
    }

    public MailAcount getMailAcount() {
        return mailAcount;
    }

    public void setMailAcount(MailAcount mailAcount) {
        this.mailAcount = mailAcount;
    }

    public boolean isUseGlobalSession() {
        return useGlobalSession;
    }

    public void setUseGlobalSession(boolean useGlobalSession) {
        this.useGlobalSession = useGlobalSession;
    }

    public PrintStream getDebugOutput() {
        return debugOutput;
    }

    public void setDebugOutput(PrintStream debugOutput) {
        this.debugOutput = debugOutput;
    }


    public MimeMessage build() {
        try {
            return buildMessage();
        } catch (MessagingException | UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    public String send() {
        try {
            return doSend();
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    private String doSend() throws MessagingException {
        try {
            MimeMessage mimeMessage = this.buildMessage();
            Transport.send(mimeMessage);

            return mimeMessage.getMessageID();
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    public BaseMail addFiles(File... files) {
        if (files == null || files.length < 1) {
            return this;
        }
        try {
            // 附件
            for (File file : files) {
                // 附件
                MimeBodyPart bodyPart = new MimeBodyPart();
                // 数据源
                FileDataSource fileDataSource = new FileDataSource(file);
                // 数据处理器
                DataHandler dataHandler = new DataHandler(fileDataSource);
                // 设置附件数据
                bodyPart.setDataHandler(dataHandler);
                // 设置附件文件名
                String nameEncoded = fileDataSource.getName();
                nameEncoded = MimeUtility.decodeText(nameEncoded);
                bodyPart.setFileName(nameEncoded);
                if (fileDataSource.getContentType() != null && fileDataSource.getContentType().startsWith("image/")) {
                    bodyPart.setContentID(nameEncoded);
                }
                // 将附件放入MIME消息体中
                multipart.addBodyPart(bodyPart);
            }
            return this;

        } catch (MessagingException | UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * 创建邮件消息
     *
     * @return 邮件
     * @throws MessagingException           创建邮件异常
     * @throws UnsupportedEncodingException 发送人名称编码异常
     */
    private MimeMessage buildMessage() throws MessagingException, UnsupportedEncodingException {
        Session session = MailUtil.getSession(mailAcount);
        MimeMessage message = new MimeMessage(session);
        // 发件人
        InternetAddress fromAddress;
        if (null == mailAcount.getSenderNickName()) {
            fromAddress = new InternetAddress(mailAcount.getUsername());
        } else {
            fromAddress = new InternetAddress(MimeUtility.encodeWord(mailAcount.getSenderNickName()) + " <" + mailAcount.getUsername() + ">");
        }
        message.setFrom(fromAddress);

        // 收件人
        Address[] toAddresses = new Address[tos.length];
        for (int i = 0; i < tos.length; i++) {
            toAddresses[i] = new InternetAddress(tos[i]);
        }
        message.setRecipients(Message.RecipientType.TO, toAddresses);

        // 抄送人
        if (null != ccs && ccs.length > 0) {
            Address[] ccAddresses = new Address[ccs.length];
            for (int i = 0; i < ccs.length; i++) {
                ccAddresses[i] = new InternetAddress(ccs[i]);
            }
            message.setRecipients(Message.RecipientType.CC, ccAddresses);
        }

        // 密送人
        if (null != bccs && bccs.length > 0) {
            Address[] bccAddresses = new Address[bccs.length];
            for (int i = 0; i < bccs.length; i++) {
                bccAddresses[i] = new InternetAddress(bccs[i]);
            }
            message.setRecipients(Message.RecipientType.BCC, bccAddresses);
        }

        // 邮件主题
        message.setSubject(mailSubject);

        // ===================== 邮件内容 ===================//
        // 正文内容
        MimeBodyPart content = new MimeBodyPart();
        if (Boolean.TRUE.equals(isHtml)) {
            content.setContent(mailContent, "text/html;charset=UTF-8");
        } else {
            content.setContent(mailContent, "text/plain;charset=UTF-8");
        }
        // 说明html中的img标签的src，引用的是此图片
        // content.setHeader("Content-Location", "https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=2703288617,3613848102&fm=23&gp=0.jpg");
        // html代码
        // content.setContent("时间就是一个沙漏。<img src='https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1495797374&di=537ebbbc19d917e7c425475e867069ca&imgtype=jpg&er=1&src=http%3A%2F%2Fimgsrc.baidu.com%2Fforum%2Fpic%2Fitem%2Fcf594910b912c8fc2c8ed308fc039245d48821cc.jpg'>", "text/html;charset=utf-8");
        multipart.addBodyPart(content);

        // 设置消息体
        message.setContent(multipart);
        return message;
    }
}
