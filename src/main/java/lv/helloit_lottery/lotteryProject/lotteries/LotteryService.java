package lv.helloit_lottery.lotteryProject.lotteries;

import lv.helloit_lottery.lotteryProject.lotteries.DAO.LotteryDAO;
import lv.helloit_lottery.lotteryProject.lotteries.Response.LotteryResponse;
import lv.helloit_lottery.lotteryProject.lotteries.Response.LotterySuccessResponse;
import lv.helloit_lottery.lotteryProject.lotteries.Response.LotteryWrongResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.*;

@Service
public class LotteryService {

    private final LotteryDAO lotteryDAO;

    @Autowired
    public LotteryService(LotteryDAO lotteryDAO) {
        this.lotteryDAO = lotteryDAO;
    }

    public LotteryResponse createLottery(Lottery lottery, BindingResult bindingResult) {
        String titleMessageError;
        String limitMessageError;

        if (bindingResult.hasErrors()) {
            int titleErrorCount = bindingResult.getFieldErrorCount("title");
            int limitErrorCount = bindingResult.getFieldErrorCount("limit");

            titleMessageError = titleErrorCount != 0 ? bindingResult.getFieldError("title").getDefaultMessage() + "; \n" : "";
            limitMessageError = limitErrorCount != 0 ? bindingResult.getFieldError("limit").getDefaultMessage() : "";

            return new LotteryWrongResponse("Fail", titleMessageError + limitMessageError);
        }

        if (!titleIsRegistered(lottery.getTitle())) {
            return new LotteryWrongResponse("Fail", "Title with the same name is registered \n");
        }

        lottery.setStartDate(new Date().getTime());
        lottery.setLotteryStatus(Status.OPEN);
        lottery.setRegisteredParticipants(0);
        return lotteryDAO.createLottery(lottery);
    }

    public Collection<Lottery> lotteries() {

        Collection<Lottery> lotteries = lotteryDAO.getAll();
        lotteries.forEach(e -> e.setRegisteredParticipants(e.getParticipants().size()));

        return lotteries;
    }

    public boolean titleIsRegistered(String title) {
        return lotteryDAO.titleIsRegistered(title);
    }

    public LotteryResponse stopLotteryRegistration(Long lotteryId) {
        Optional<Lottery> wrappedLottery = lotteryDAO.getById(lotteryId);
        if (wrappedLottery.isPresent()) {

            wrappedLottery.get().setLotteryStatus(Status.CLOSED);


            lotteryDAO.updateLottery(wrappedLottery.get());
            return new LotterySuccessResponse("OK");
        }
        return new LotteryWrongResponse("FAIL", "FAIL");

    }

    public LotteryResponse chooseWinner(Long lotteryId) {
        Optional<Lottery> wrappedLottery = lotteryDAO.getById(lotteryId);
        if (wrappedLottery.isPresent()) {

            if(wrappedLottery.get().getLotteryStatus() != Status.WINNER_SELECTED){
                return new LotteryWrongResponse("Fail", "You can't choose winner while lottery is not stopped");
            }
            if(wrappedLottery.get().getRegisteredParticipants() == 0){
                return new LotteryWrongResponse("Fail", "Can't choose wiiner if participan count = 0");
            }

            int winnerIndex = new Random().nextInt(wrappedLottery.get().getRegisteredParticipants());


            Long endDate = new Date().getTime();
            wrappedLottery.get().setEndDate(endDate);

            wrappedLottery.get().setWinnerIndex(winnerIndex);
            wrappedLottery.get().setEndDate(endDate);
            wrappedLottery.get().setLotteryStatus(Status.WINNER_SELECTED);

            lotteryDAO.updateLottery(wrappedLottery.get());
            return new LotterySuccessResponse("OK", wrappedLottery.get().getParticipants().get(winnerIndex).getUniqueCode());
        }
        return new LotteryWrongResponse("FAil", "Lottery does not exist");
    }

    public Collection<Lottery> getStatistic() {
        Collection<Lottery> lotteries = lotteryDAO.getAll();
        List<Lottery> finishedLotteries = new ArrayList<>();

        for (Lottery lottery :lotteries) {
            if(lottery.getLotteryStatus().equals(Status.WINNER_SELECTED)){
                finishedLotteries.add(lottery);
            }
        }
        return finishedLotteries;
    }
}
