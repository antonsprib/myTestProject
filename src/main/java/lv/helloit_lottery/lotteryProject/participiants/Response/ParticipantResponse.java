package lv.helloit_lottery.lotteryProject.participiants.Response;

public class ParticipantResponse {
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ParticipantResponse(String status) {
        this.status = status;
    }

    public ParticipantResponse() {
    }
}
