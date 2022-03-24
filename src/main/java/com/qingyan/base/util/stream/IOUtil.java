package com.qingyan.base.util.stream;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Base64Utils;

/**
 * IOUtil
 *
 * @author xuzhou
 * @version v1.0.0
 * @date 2021/11/11 10:40
 */
public class IOUtil {

    private static final Logger log = LoggerFactory.getLogger(IOUtil.class);
    /**
     * byte数组长度
     */
    private static final int BYTE_LENGTH = 1024;

    private IOUtil() {

    }

    /**
     * 输入流转为字节数组
     *
     * @param inputStream {@link InputStream}
     * @return byte[]
     * @throws IOException 异常
     */
    public static byte[] toByteArray(InputStream inputStream) throws IOException {
        byte[] result = null;
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            byte[] bytes = new byte[BYTE_LENGTH];
            int len;
            while ((len = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, len);
            }
            result = outputStream.toByteArray();
        } catch (IOException e) {
            log.error("输入流转化为base64失败");
        } finally {
            inputStream.close();
        }
        return result;
    }

    /**
     * 输入流转为字节数组字符串
     *
     * @param inputStream {@link InputStream}
     * @return String
     * @throws IOException 异常
     */
    public static String getBase64FromInputStream(InputStream inputStream) throws IOException {
        byte[] bytes = toByteArray(inputStream);
        return Base64.getEncoder().encodeToString(bytes);
    }


    /**
     * base64转inputStream
     *
     * @param base64string 字节数组字符串
     * @return {@link InputStream}
     */
    public static InputStream baseToInputStream(String base64string) {
        ByteArrayInputStream stream = null;
        try {
            byte[] bytes = Base64Utils.decodeFromString(base64string);
            stream = new ByteArrayInputStream(bytes);
        } catch (Exception e) {
            log.error("base64转化为输入流失败");
        }
        return stream;
    }
}
