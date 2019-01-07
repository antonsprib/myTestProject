package lv.helloit_lottery.lotteryProject.lotteries.DAO;

import lv.helloit_lottery.lotteryProject.lotteries.Lottery;
import lv.helloit_lottery.lotteryProject.lotteries.LotteryResponse;


import java.util.Collection;

public interface LotteryDAO {

    LotteryResponse createLottery(Lottery lottery);

    Collection<Lottery> getAll();

    boolean titleIsRegistered(String title);

}
