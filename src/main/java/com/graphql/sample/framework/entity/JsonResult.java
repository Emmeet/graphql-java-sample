package com.graphql.sample.framework.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class JsonResult<T> {

    public static final int CODE_OK = 200;
    public static final int CODE_FAIL = 500;

    public int getCode() {
        return code;
    }

    private int code = CODE_OK;

    private String message;

    private T data;

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public void setMsgErr(String message) {
        this.message = message;
        code = CODE_FAIL;
    }

    public void setMsgErr(String msg, Throwable ex) {
        if (ex == null) {
            setMsgErr(msg);
            log.error(msg);
        } else if ((ex instanceof Exception)) {
            setMsgErr(msg);
            log.error(msg, ex);
        } else {
            setMsgErr(msg + "[" + ex.getClass().getName() + "]" + ex.getMessage());
            log.error(msg, ex);
        }
    }

    public static <E> JsonResult<E> fail(String msg) {
        JsonResult<E> jsonResult = new JsonResult<>();
        jsonResult.setMsgErr(msg);
        jsonResult.setCode(CODE_FAIL);
        return jsonResult;
    }

    public static <E> JsonResult<E> success(String msg) {
        return success(null, msg);
    }

    public static <E> JsonResult<E> success(E data) {
        JsonResult<E> jsonResult = new JsonResult<>();
        jsonResult.setData(data);
        return jsonResult;
    }

    public static <E> JsonResult<E> success(E data, String msg) {
        JsonResult<E> jsonResult = new JsonResult<>();
        jsonResult.setMessage(msg);
        jsonResult.setData(data);
        return jsonResult;
    }
}
