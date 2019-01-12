package lv.helloit_lottery.lotteryProject.lotteries;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Objects;

@Entity
@Table(name = "LOTTERY")
public class LotteryBasic {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Long id;

    @NotBlank(message = "Title can't be emptry")
    @Size(max=50, message = "You entered too long title name")
    @Column(name = "title")
    private String title;

    @Column(name = "start_date")
    private String startDate;

    @Column(name = "end_date")
    private String endDate;

    @Column(name = "registered_participants")
    private Integer registeredParticipants;

    public LotteryBasic(Long id, @NotBlank(message = "Title can't be emptry") @Size(max = 50, message = "You entered too long title name") String title, String startDate, String endDate, Integer registeredParticipants) {
        this.id = id;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.registeredParticipants = registeredParticipants;
    }

    public LotteryBasic() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Integer getRegisteredParticipants() {
        return registeredParticipants;
    }

    public void setRegisteredParticipants(Integer registeredParticipants) {
        this.registeredParticipants = registeredParticipants;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LotteryBasic that = (LotteryBasic) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(title, that.title) &&
                Objects.equals(startDate, that.startDate) &&
                Objects.equals(endDate, that.endDate) &&
                Objects.equals(registeredParticipants, that.registeredParticipants);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, startDate, endDate, registeredParticipants);
    }

    @Override
    public String toString() {
        return "LotteryBasic{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", registeredParticipants=" + registeredParticipants +
                '}';
    }
}
