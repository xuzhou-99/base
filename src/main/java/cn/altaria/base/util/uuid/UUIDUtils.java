package cn.altaria.base.util.uuid;

import java.util.UUID;

/**
 * UUIDHelper
 *
 * @author xuzhou
 * @version v1.0.0
 * @date 2021/7/21 15:48
 */
public class UUIDUtils {

    /**
     * 生成随机UUID，默认为小写
     *
     * @return String
     */
    public static String getUuid() {
        return getUuid(false);
    }

    /**
     * 生成随机UUID
     *
     * @param upperCase 是否大写
     * @return String
     */
    public static String getUuid(boolean upperCase) {
        String uuid = UUID.randomUUID().toString().replace("-", "").trim();
        return upperCase ? uuid.toUpperCase() : uuid;
    }

}
