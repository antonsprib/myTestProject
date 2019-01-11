package lv.helloit_lottery.lotteryProject.participiants;

import lv.helloit_lottery.lotteryProject.lotteries.Lottery;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
@Table(name = "PARTICIPANTS")
public class Participant {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Long id;

    @Email(message = "Please enter valid email")
    @NotBlank(message = "Field email can't be empty")
    @Column(name = "email")
    private String email;

    @NotNull(message = "Field age can't be empty")
    @Min(value = 21, message = "Age can't be less than 21")
    @Column(name = "age")
    private Byte age;

    @Column(name = "unique_code")
    private String uniqueCode;

    @NotNull(message = "Field lotteryId can't be empty")
    @Column(name= "lotteryId")
    private Long lotteryId;

    public String getUniqueCode() {
        return uniqueCode;
    }

    public void setUniqueCode(String uniqueCode) {
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

    public Byte getAge() {
        return age;
    }

    public void setAge(Byte age) {
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
