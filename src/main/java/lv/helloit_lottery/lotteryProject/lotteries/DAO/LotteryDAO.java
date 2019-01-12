package lv.helloit_lottery.lotteryProject.lotteries.DAO;

import lv.helloit_lottery.lotteryProject.lotteries.Lottery;
import lv.helloit_lottery.lotteryProject.Response;
import lv.helloit_lottery.lotteryProject.lotteries.LotteryBasic;


import java.util.Collection;
import java.util.Optional;

public interface LotteryDAO {

    Response createLottery(Lottery lottery);

    Collection<Lottery> getAll();

    boolean titleIsRegistered(String title);

    Optional<Lottery> getById(Long id);

    void updateLottery(Lottery lottery);

}
