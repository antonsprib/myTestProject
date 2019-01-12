package lv.helloit_lottery.lotteryProject.lotteries;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lv.helloit_lottery.lotteryProject.participiants.Participant;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "LOTTERY")
public class Lottery {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Long id;

    @NotBlank(message = "Title can't be emptry")
    @Size(max=50, message = "You entered too long title name")
    @Column(name = "title")
    private String title;

    @NotNull(message = "Participant count can't be empty")
    @Column(name = "participant_limit")

    private Long limit;

    @Column(name = "start_date")
    private Long startDate;

    @Column(name = "end_date")
    private Long endDate;

    @Column(name = "registered_participants")
    private Integer registeredParticipants;

    @Column(name = "winner_code")

    private String winnerCode;

    @Column(name = "winner_email")

    private String winnerEmail;

    @Enumerated(EnumType.STRING)

    private Status lotteryStatus;
    @JsonIgnore
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "lottery")

    private List<Participant> participants;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setLotteryStatus(Status lotteryStatus) {
        this.lotteryStatus = lotteryStatus;
    }

    public Status getLotteryStatus() {
        return lotteryStatus;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getLimit() {
        return limit;
    }

    public void setLimit(Long limit) {
        this.limit = limit;
    }

    public Long getStartDate() {
        return startDate;
    }

    public void setStartDate(Long startDate) {
        this.startDate = startDate;
    }

    public Long getEndDate() {
        return endDate;
    }

    public void setEndDate(Long endDate) {
        this.endDate = endDate;
    }

    public Integer getRegisteredParticipants() {
        return registeredParticipants;
    }

    public void setRegisteredParticipants(Integer registeredParticipants) {
        this.registeredParticipants = registeredParticipants;
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
    public String toString() {
        return "Lottery{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", limit=" + limit +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", registeredParticipants=" + registeredParticipants +
                ", winnerCode='" + winnerCode + '\'' +
                ", winnerEmail='" + winnerEmail + '\'' +
                ", lotteryStatus=" + lotteryStatus +
                ", participants=" + participants +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Lottery lottery = (Lottery) o;
        return Objects.equals(id, lottery.id) &&
                Objects.equals(title, lottery.title) &&
                Objects.equals(limit, lottery.limit) &&
                Objects.equals(startDate, lottery.startDate) &&
                Objects.equals(endDate, lottery.endDate) &&
                Objects.equals(registeredParticipants, lottery.registeredParticipants) &&
                Objects.equals(winnerCode, lottery.winnerCode) &&
                Objects.equals(winnerEmail, lottery.winnerEmail) &&
                lotteryStatus == lottery.lotteryStatus &&
                Objects.equals(participants, lottery.participants);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, limit, startDate, endDate, registeredParticipants, winnerCode, winnerEmail, lotteryStatus, participants);
    }
}
