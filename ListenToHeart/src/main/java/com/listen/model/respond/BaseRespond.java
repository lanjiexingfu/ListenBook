package com.listen.model.respond;

/**
 * Created by Chiely on 15/6/18.
 */
public class BaseRespond {
    private String result;
    private String code;

    private String errMsg;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }
}
