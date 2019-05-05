package com.us.demo.base;


/**
 *返回单个对象
 */
public class ServiceResult<T> {

    private boolean success;

    private String message;

    //返回的信息
    private T result;

    public ServiceResult(boolean success)
    {
        this.success = success;
    }

    public ServiceResult(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public ServiceResult(boolean success, String message, T result) {
        this.success = success;
        this.message = message;
        this.result = result;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public static <T> ServiceResult<T> success() {
        return new ServiceResult<>(true);
    }

    public static <T> ServiceResult<T> of(T result) {
        ServiceResult<T> serviceResult = new ServiceResult<>(true);
        serviceResult.setResult(result);
        return serviceResult;
    }

    public static <T> ServiceResult<T> notFind() {
        return new ServiceResult<>(false, Message.NOT_FOUND.getMessage(), null);
    }

    public static <T> ServiceResult<T> notSuccess() {
        return new ServiceResult<>(false, Message.NOT_SUCCESS.getMessage(), null);
    }

    public enum Message {
        NOT_FOUND("NOT FOUND RESOURCE !"),
        //未登录
        NOT_LOGIN("NOT LOGIN!"),
        NOT_SUCCESS("not success");

        private String message;

        public String getMessage() {
            return message;
        }

        Message(String message) {
            this.message = message;
        }
    }

}
