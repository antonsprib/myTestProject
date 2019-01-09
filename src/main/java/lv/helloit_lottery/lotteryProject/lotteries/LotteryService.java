package lv.helloit_lottery.lotteryProject.lotteries;

import lv.helloit_lottery.lotteryProject.lotteries.DAO.LotteryDAO;
import lv.helloit_lottery.lotteryProject.lotteries.Response.LotteryResponse;
import lv.helloit_lottery.lotteryProject.lotteries.Response.LotteryWrongResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.Collection;
import java.util.Date;

@Service
public class LotteryService {

    private final LotteryDAO lotteryDAO;

    @Autowired
    public LotteryService(LotteryDAO lotteryDAO) {
        this.lotteryDAO = lotteryDAO;
    }

    public LotteryResponse createLottery(Lottery lottery, BindingResult bindingResult){
        String titleMessageError;
        String limitMessageError;

        if (bindingResult.hasErrors()) {
            int titleErrorCount = bindingResult.getFieldErrorCount("title");
            int limitErrorCount = bindingResult.getFieldErrorCount("limit");

            titleMessageError = titleErrorCount != 0 ? bindingResult.getFieldError("title").getDefaultMessage()  + "; \n": "";
            limitMessageError = limitErrorCount != 0 ? bindingResult.getFieldError("limit").getDefaultMessage() : "";

            return new LotteryWrongResponse("Fail", titleMessageError  + limitMessageError);
        }

        if (!titleIsRegistered(lottery.getTitle())) {
            return new LotteryWrongResponse("Fail", "Title with the same name is registered \n");
        }

        lottery.setStartDate(new Date().getTime());
        lottery.setLotteryStatus(Status.OPEN);
        return  lotteryDAO.createLottery(lottery);
    }

    public Collection<Lottery> lotteries(){
        return lotteryDAO.getAll();
    }

    public boolean titleIsRegistered(String title){
        return lotteryDAO.titleIsRegistered(title);
    }
}
