package lv.helloit_lottery.lotteryProject.lotteries;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "LOTTERY")
public class Lottery {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Long id;

    @NotNull (message = "Title can't be emptry")
    @Size(min=1, max=100, message = "You entered too long title name")
    @Column(name = "title")
    private String title;

    @NotNull(message = "Participiant count can't be empty")
    @Column(name = "participiants_limit")
    private Long limit;

    @Column(name = "start_date")
    private Long startDate;

    @Column(name = "end_date")
    private Date endDate;

    @Enumerated(EnumType.STRING)
    private Status lotteryStatus;

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

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }


    @Override
    public String toString() {
        return "Lottery{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", limit=" + limit +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", lotteryStatus='" + lotteryStatus + '\'' +
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
                Objects.equals(lotteryStatus, lottery.lotteryStatus);

    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, limit, startDate, endDate, lotteryStatus);
    }
}
