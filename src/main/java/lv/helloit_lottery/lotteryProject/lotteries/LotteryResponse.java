package lv.helloit_lottery.lotteryProject.lotteries;

public class LotteryResponse {
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LotteryResponse(String status) {
        this.status = status;
    }

    public LotteryResponse() {
    }
}
