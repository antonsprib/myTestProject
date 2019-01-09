package lv.helloit_lottery.lotteryProject.participiants.Response;

public class ParticipantWrongResponse extends ParticipantResponse{

    private String reason;

    public ParticipantWrongResponse(String status, String reason) {
        super(status);
        this.reason = reason;
    }

    public ParticipantWrongResponse() {

    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
