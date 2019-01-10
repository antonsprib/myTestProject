package lv.helloit_lottery.lotteryProject.lotteries.Response;

import lv.helloit_lottery.lotteryProject.lotteries.Response.LotteryResponse;

public class LotterySuccessResponse extends LotteryResponse {
    private Long id;
    private String uniqueCode;

    public LotterySuccessResponse(String status, Long id) {
        super(status);
        this.id = id;
    }

    public LotterySuccessResponse() {
    }

    public LotterySuccessResponse(String status, String uniqueCode) {
        super(status);
        this.uniqueCode = uniqueCode;
    }

    public String getuniqueCode() {
        return uniqueCode;
    }

    public void setuniqueCode(String uniqueCode) {
        uniqueCode = uniqueCode;
    }

    public LotterySuccessResponse(String status) {
        super(status);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
