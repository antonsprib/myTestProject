package lv.helloit_lottery.lotteryProject.lotteries;

import java.util.Collection;
import java.util.List;

public interface LotteryDAO {
    Long createLottery(Lottery lottery);
    Collection<Lottery> getAll();
}
