package lv.helloit_lottery.lotteryProject.lotteries;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lv.helloit_lottery.lotteryProject.participants.Participant;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "LOTTERY")
public class Lottery extends LotteryBasic {


    @NotNull(message = "Participant count can't be empty")
    @Column(name = "participant_limit")
    private Long limit;


    @Column(name = "winner_code")
    private String winnerCode;

    @Column(name = "winner_email")
    private String winnerEmail;

    @Enumerated(EnumType.STRING)
    private Status lotteryStatus;


    @OneToMany(fetch = FetchType.EAGER, mappedBy = "lottery")
    @JsonIgnore
    private List<Participant> participants;


    public void setLotteryStatus(Status lotteryStatus) {
        this.lotteryStatus = lotteryStatus;
    }

    public Status getLotteryStatus() {
        return lotteryStatus;
    }


    public Long getLimit() {
        return limit;
    }

    public void setLimit(Long limit) {
        this.limit = limit;
    }

    public List<Participant> getParticipants() {
        return participants;
    }

    public void setParticipants(List<Participant> participants) {
        this.participants = participants;
    }

    public String getWinnerCode() {
        return winnerCode;
    }

    public void setWinnerCode(String winnerCode) {
        this.winnerCode = winnerCode;
    }

    public String getWinnerEmail() {
        return winnerEmail;
    }

    public void setWinnerEmail(String winnerEmail) {
        this.winnerEmail = winnerEmail;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Lottery lottery = (Lottery) o;
        return Objects.equals(limit, lottery.limit) &&
                Objects.equals(winnerCode, lottery.winnerCode) &&
                Objects.equals(winnerEmail, lottery.winnerEmail) &&
                lotteryStatus == lottery.lotteryStatus &&
                Objects.equals(participants, lottery.participants);
    }

    @Override
    public String toString() {
        return "Lottery{" +
                "limit=" + limit +
                ", winnerCode='" + winnerCode + '\'' +
                ", winnerEmail='" + winnerEmail + '\'' +
                ", lotteryStatus=" + lotteryStatus +
                ", participants=" + participants +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), limit, winnerCode, winnerEmail, lotteryStatus, participants);
    }
}
