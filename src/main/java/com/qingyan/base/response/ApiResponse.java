package com.qingyan.base.response;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import static com.qingyan.base.response.ResponseStatus.FAIL;
import static com.qingyan.base.response.ResponseStatus.SUCCESS;


/**
 * ApiResponse
 *
 * @author xuzhou
 * @version v1.0.0
 * @date 2021/5/11 13:53
 */
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse implements Serializable {

    private static final long serialVersionUID = -1412236073582963299L;

    private boolean success;

    private int code;

    private String message;


    /**
     * 通用返回结果封装
     *
     * @param success 是否成功
     * @param code    响应码
     * @param message 消息
     * @return {@link ApiResponse}   封装结果
     */
    public static ApiResponse of(final boolean success, final int code, final String message) {
        return new ApiResponse(success, code, message);
    }

    /**
     * 通用返回结果封装-操作成功
     *
     * @return {@link ApiResponse}
     */
    public static ApiResponse ofSuccess() {
        return of(SUCCESS.getSuccess(), SUCCESS.getCode(), SUCCESS.getMessage());
    }

    /**
     * 通用返回结果封装-操作成功
     *
     * @param message 消息
     * @return {@link ApiResponse}
     */
    public static ApiResponse ofSuccess(String message) {
        return of(SUCCESS.getSuccess(), SUCCESS.getCode(), message);
    }

    /**
     * 通用返回结果封装-操作失败
     *
     * @return {@link ApiResponse}
     */
    public static ApiResponse ofFail() {
        return of(FAIL.getSuccess(), FAIL.getCode(), FAIL.getMessage());
    }

    /**
     * 通用返回结果封装-操作失败
     *
     * @param message 消息
     * @return {@link ApiResponse}
     */
    public static ApiResponse ofFail(String message) {
        return of(FAIL.getSuccess(), FAIL.getCode(), message);
    }

    /**
     * 通用返回结果封装-操作失败
     *
     * @param message 消息
     * @param code    操作码
     * @return {@link ApiResponse}
     */
    public static ApiResponse ofFail(final Integer code, final String message) {
        return of(FAIL.getSuccess(), code, message);
    }

    /**
     * 通用返回结果封装-操作状态
     *
     * @param status {@link ResponseStatus}
     * @return {@link ApiResponse}
     */
    public static ApiResponse ofStatus(final ResponseStatus status) {
        return of(status.getSuccess(), status.getCode(), status.getMessage());
    }

}
