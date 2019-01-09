package lv.helloit_lottery.lotteryProject.lotteries.DAO;

import lv.helloit_lottery.lotteryProject.lotteries.Lottery;
import lv.helloit_lottery.lotteryProject.lotteries.Response.LotteryResponse;


import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface LotteryDAO {

    LotteryResponse createLottery(Lottery lottery);

    Collection<Lottery> getAll();

    boolean titleIsRegistered(String title);

    Optional<Lottery> getById(Long id);

}
