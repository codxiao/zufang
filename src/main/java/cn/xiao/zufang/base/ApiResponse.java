package cn.xiao.zufang.base;

/**
 * API结构封装
 */
public class ApiResponse {
    private int code;//状态码
    private String message;//描述信息
    private Object data;//结果集
    private boolean more;//还有没有信息

    public ApiResponse(int code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public ApiResponse() {
        this.code=STATUS.SUCCESS.getCode();
        this.message=STATUS.SUCCESS.getStandardMessage();
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

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public boolean isMore() {
        return more;
    }

    public void setMore(boolean more) {
        this.more = more;
    }
    public static ApiResponse ofMessage(int code,String message){
        return new ApiResponse(code,message,null);
    }
    public static ApiResponse ofSuccess(Object data){
        return new ApiResponse(STATUS.SUCCESS.getCode(),STATUS.SUCCESS.getStandardMessage(),data);
    }
    public static ApiResponse ofStatus(STATUS status){
        return new ApiResponse(status.getCode(),status.getStandardMessage(),null);
    }

    public enum STATUS{
        SUCCESS(200,"OK"),
        BAD_REQUEST(400,"BAD REQUEST"),
        INTERNAL_ERROR(500,"unknown internal error"),
        NOT_VALID_PARAM(40003,"NOT VALID PARAMS"),
        NOT_SUPPORTED_OPERATION(40006,"not supported opertion"),
        NOT_LOGIN(50000,"NOT LOGIN");

        private int code;
        private String standardMessage;//存储code代表的信息

        STATUS(int code, String standardMessage) {
            this.code = code;
            this.standardMessage = standardMessage;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public int getCode() {
            return code;
        }

        public String getStandardMessage() {
            return standardMessage;
        }

        public void setStandardMessage(String standardMessage) {
            this.standardMessage = standardMessage;
        }
    }
}
