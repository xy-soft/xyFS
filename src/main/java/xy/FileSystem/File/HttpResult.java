package xy.FileSystem.File;

import java.io.Serializable;

public class HttpResult implements Serializable{

	private static final long serialVersionUID = 5976014738363051675L;
	private Integer statusCode;
    private String content;
 
    public HttpResult(Integer statusCode, String content) {
        this.statusCode = statusCode;
        this.content = content;
    }
    public Integer getStatusCode() {
        return statusCode;
    }
 
    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }
 
    public String getContent() {
        return content;
    }
 
    public void setContent(String content) {
        this.content = content;
    }
 
    @Override
    public String toString() {
        return "HttpResult{" +
                "statusCode=" + statusCode +
                ", content='" + content + '\'' +
                '}';
    }
}