package lv.helloit_lottery.lotteryProject.lotteries;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class LotteryService {

    private final LotteryDAO lotteryDAO;

    @Autowired
    public LotteryService(LotteryDAO lotteryDAO) {

        this.lotteryDAO = lotteryDAO;
    }

    public Long createLottery(Lottery lottery){
        lottery.setStartDate(new Date());
        lottery.setLotteryStatus(Status.OPEN);
        return  lotteryDAO.createLottery(lottery);
    }
}
