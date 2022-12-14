package cn.altaria.base.util.mail;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author xuzhou
 * @since 2022/12/14
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MailAcount {

    private String username;
    private String password;

    private String senderNickName;

    private Boolean isSsl = false;

    private String host;

    private String port;


}
