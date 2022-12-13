package cn.altaria.base.util.mail;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * EmailConfig
 *
 * @author xuzhou
 * @version v1.0.0
 * @since 2022/6/4 17:29
 */
@Component
@ConfigurationProperties(prefix = "spring.mail")
public class EmailConfig {

    private static String host;
    private static String username;
    private static String password;
    private static String port;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        EmailConfig.host = host;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        EmailConfig.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        EmailConfig.password = password;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        EmailConfig.port = port;
    }
}
