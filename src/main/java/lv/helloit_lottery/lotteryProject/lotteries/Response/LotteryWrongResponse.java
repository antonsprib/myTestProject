package lv.helloit_lottery.lotteryProject.lotteries.Response;

import lv.helloit_lottery.lotteryProject.lotteries.Response.LotteryResponse;

public class LotteryWrongResponse extends LotteryResponse {
    private String reason;

    public LotteryWrongResponse() {
    }

    public LotteryWrongResponse(String status, String reason) {
        super(status);
        this.reason = reason;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
