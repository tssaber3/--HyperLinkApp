package com.us.demo.base;


/*
 * API服务类接口统一返回格式
 */
public class ApiResponse {

    //状态码 200 404 等
    private int code;

    //携带消息
    private String message;

    //接口返回的数据 放在这里面
    private Object result;

    //??
    private boolean more;

    //不携带返回结果的构造方法
    public ApiResponse(int code,String message)
    {
        this.code = code;
        this.message = message;
    }

    //携带返回结果的构造方法
    public ApiResponse(int code,String message,Object result)
    {
        this.code = code;
        this.message = message;
        this.result = result;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public boolean isMore() {
        return more;
    }

    public void setMore(boolean more) {
        this.more = more;
    }

    //返回一个 状态为成功 的ApiResponse 对象 data(result)是返回的数据
    public static ApiResponse ofSuccess(Object data)
    {
        return new ApiResponse(Status.SUCCESS.getCode(),Status.SUCCESS.getMessage(),data);
    }

    //返回一个不携带返回结果的ApiResponse
    public static ApiResponse ofMessage(int code ,String message)
    {
        return new ApiResponse(code,message,null);
    }

    //通过Status 的参数返回一个ApiResponse
    public static ApiResponse ofStatus(Status status){
        return new ApiResponse(status.getCode(),status.getMessage(),null);
    }

    //通过枚举列出各种状态码
    public enum Status{
        SUCCESS(200,"OK"),
        //没有权限访问
        ACCESS_DENIED(403,"ACCESS DENIED"),
        //没找到资源
        NOT_FOUND(404,"NOT FOUND"),
        //客户端请求的语法错误  服务器无法理解
        BAD_REQUEST(400,"BAD REQUEST"),
        //服务器内部错误，无法完成请求
        INTERNAL_SERVER_ERROR(500,"INTERNAL_SERVER_ERROR"),
        //参数不是有效的（参数无效）
        NOT_VALID_PARAM(40000, "NOT_VALID_PARAM"),
        //不支持的操作
        NOT_SUPPORTED_OPTIONS(40001, "NOT_SUPPORTED_OPTIONS"),
        //没有登录
        NOT_LOGIN(40002, "NOT_LOGIN"),
        //登录错误
        LOGIN_ERR(40003, "LOGIN ERR");

        private int code;
        private String message;

        Status(int code,String message)
        {
            this.code = code;
            this.message = message;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
