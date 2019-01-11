package lv.helloit_lottery.lotteryProject;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response {

    private String status;
    private Long id;
    private String uniqueCode;
    private String reason;

    public Response(String status, Long id) {
        this.status = status;
        this.id = id;
    }

    public Response(String status) {
        this.status = status;
    }

    public Response() {
    }

    public Response(String status, String reason) {
        this.status = status;
        this.reason = reason;
    }

    public Response(String status, Long id, String uniqueCode) {
        this.status = status;
        this.id = id;
        this.uniqueCode = uniqueCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUniqueCode() {
        return uniqueCode;
    }

    public void setUniqueCode(String uniqueCode) {
        this.uniqueCode = uniqueCode;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
