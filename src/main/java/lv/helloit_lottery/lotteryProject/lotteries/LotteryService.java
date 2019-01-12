package lv.helloit_lottery.lotteryProject.lotteries;

import lv.helloit_lottery.lotteryProject.Response;
import lv.helloit_lottery.lotteryProject.lotteries.DAO.LotteryDAO;

import lv.helloit_lottery.lotteryProject.participiants.DAO.ParticipantDAO;
import lv.helloit_lottery.lotteryProject.participiants.Participant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class LotteryService {

    private final LotteryDAO lotteryDAO;
    private final ParticipantDAO participanDao;

    @Autowired
    public LotteryService(LotteryDAO lotteryDAO, ParticipantDAO participantDAO) {
        this.lotteryDAO = lotteryDAO;
        this.participanDao = participantDAO;
    }

    public Response createLottery(Lottery lottery, BindingResult bindingResult) {
        String titleMessageError;
        String limitMessageError;

        if (bindingResult.hasErrors()) {
            int titleErrorCount = bindingResult.getFieldErrorCount("title");
            int limitErrorCount = bindingResult.getFieldErrorCount("limit");

            titleMessageError = titleErrorCount != 0 ? bindingResult.getFieldError("title").getDefaultMessage() + "; \n" : "";
            limitMessageError = limitErrorCount != 0 ? bindingResult.getFieldError("limit").getDefaultMessage() : "";

            return new Response("Fail", titleMessageError + limitMessageError);
        }

        if (!titleIsRegistered(lottery.getTitle())) {
            return new Response("Fail", "Title with the same name is registered \n");
        }

        if(lottery.getLimit() <= 0){
            return new Response("Fail", "Participan count can be less or equals 0 \n");
        }

        String pattern = "dd.MM.yyyy HH:mm";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String startDate = simpleDateFormat.format(new Date());

        lottery.setStartDate(startDate);
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

    public Response stopLotteryRegistration(Long lotteryId) {
        Optional<Lottery> wrappedLottery = lotteryDAO.getById(lotteryId);
        if (wrappedLottery.isPresent()) {
            if(wrappedLottery.get().getLotteryStatus() != Status.OPEN){
                return new Response("Fail", "You can't stop lottery. Lottery is stopped or winner is selected");
            }
            wrappedLottery.get().setLotteryStatus(Status.CLOSED);


            lotteryDAO.updateLottery(wrappedLottery.get());
            return new Response("OK");
        }
        return new Response("Fail", "Lottery does not exist");

    }

    public Response chooseWinner(Long lotteryId) {
        Optional<Lottery> wrappedLottery = lotteryDAO.getById(lotteryId);
        if (wrappedLottery.isPresent()) {

            if(wrappedLottery.get().getLotteryStatus() != Status.CLOSED){
                return new Response("Fail", "You can't choose winner if lottery is not stopped");
            }
            if(wrappedLottery.get().getRegisteredParticipants() == 0){
                return new Response("Fail", "Can't choose winer if participan count = 0");
            }

            int winnerIndex = new Random().nextInt(wrappedLottery.get().getRegisteredParticipants());
            String winnerCode = wrappedLottery.get().getParticipants().get(winnerIndex).getUniqueCode();
            String winnerEmail = wrappedLottery.get().getParticipants().get(winnerIndex).getEmail();

            String pattern = "dd.MM.yyyy HH:mm";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
            String endDate = simpleDateFormat.format(new Date());
            wrappedLottery.get().setEndDate(endDate);

            wrappedLottery.get().setWinnerCode(winnerCode);
            wrappedLottery.get().setWinnerEmail(winnerEmail);
            wrappedLottery.get().setEndDate(endDate);
            wrappedLottery.get().setLotteryStatus(Status.WINNER_SELECTED);

            lotteryDAO.updateLottery(wrappedLottery.get());
            return new Response("OK", null, wrappedLottery.get().getWinnerCode());
        }
        return new Response("Fail", "Lottery does not exist");
    }

    public Collection<LotteryBasic> getStatistic() {
        Collection<Lottery> lotteries = lotteryDAO.getAll();
        Collection<LotteryBasic> finishedLotteries = new ArrayList<>();

        for (Lottery lottery :lotteries) {
            if(lottery.getLotteryStatus().equals(Status.WINNER_SELECTED)){
                finishedLotteries.add(new LotteryBasic(lottery.getId(), lottery.getTitle(),lottery.getStartDate(), lottery.getEndDate(), lottery.getRegisteredParticipants()));
            }
        }
        return finishedLotteries;
    }

    public Response getStatus(Long id, String email, String code){

        Optional<Lottery> wrappedLottery = lotteryDAO.getById(id);

        if(wrappedLottery.isPresent()){


            for(Participant participant : wrappedLottery.get().getParticipants()){

                if(participant.getEmail().equals(email) && participant.getUniqueCode().equals(code)){

                    if(wrappedLottery.get().getLotteryStatus() != Status.WINNER_SELECTED){
                        return new Response("PENDING");
                    }

                    if(wrappedLottery.get().getWinnerEmail().equals(email) && wrappedLottery.get().getWinnerCode().equals(code)){
                        return new Response("WIN");
                    } else {
                        return new Response("LOSE");
                    }
                }
            }

        }
        return new Response("ERROR ");
    }
}
