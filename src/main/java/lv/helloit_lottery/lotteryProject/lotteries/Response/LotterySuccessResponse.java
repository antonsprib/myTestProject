package lv.helloit_lottery.lotteryProject.lotteries.Response;

import lv.helloit_lottery.lotteryProject.lotteries.Response.LotteryResponse;

public class LotterySuccessResponse extends LotteryResponse {
    private Long id;

    public LotterySuccessResponse(String status, Long id) {
        super(status);
        this.id = id;
    }

    public LotterySuccessResponse() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
