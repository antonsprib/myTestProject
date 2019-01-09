package lv.helloit_lottery.lotteryProject.participiants;

import lv.helloit_lottery.lotteryProject.lotteries.Lottery;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "PARTICIPANTS")
public class Participant {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Long id;

    @Column(name = "email")
    private String email;

    @Column(name = "age")
    private String age;

    @Column(name = "unique_code")
    private Long uniqueCode;

    @Column(name= "lotteryId")
    private Long lotteryId;

    public Long getUniqueCode() {
        return uniqueCode;
    }

    public void setUniqueCode(Long uniqueCode) {
        this.uniqueCode = uniqueCode;
    }

    public Long getLotteryId() {
        return lotteryId;
    }

    public void setLotteryId(Long lotteryId) {
        this.lotteryId = lotteryId;
    }

    @ManyToOne
    @JoinColumn(name = "assigned_lottery_id")
    private Lottery lottery;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public Lottery getLottery() {
        return lottery;
    }

    public void setLottery(Lottery lottery) {
        this.lottery = lottery;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Participant that = (Participant) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(email, that.email) &&
                Objects.equals(age, that.age) &&
                Objects.equals(uniqueCode, that.uniqueCode) &&
                Objects.equals(lotteryId, that.lotteryId) &&
                Objects.equals(lottery, that.lottery);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, age, uniqueCode, lotteryId, lottery);
    }

    @Override
    public String toString() {
        return "Participant{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", age='" + age + '\'' +
                ", uniqueCode=" + uniqueCode +
                ", lotteryId=" + lotteryId +
                ", lottery=" + lottery +
                '}';
    }
}
