package lv.helloit_lottery.lotteryProject.lotteries;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Service
public class LotteryService {

    private final LotteryDAO lotteryDAO;

    @Autowired
    public LotteryService(LotteryDAO lotteryDAO) {

        this.lotteryDAO = lotteryDAO;
    }

    public Long createLottery(Lottery lottery){
        lottery.setStartDate(new Date().getTime());
        lottery.setLotteryStatus(Status.OPEN);
        return  lotteryDAO.createLottery(lottery);
    }

    public Collection<Lottery> lotteries(){
        return lotteryDAO.getAll();
    }
}
