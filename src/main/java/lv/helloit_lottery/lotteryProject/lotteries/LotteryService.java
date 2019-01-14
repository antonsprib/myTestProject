package lv.helloit_lottery.lotteryProject.lotteries;

import lv.helloit_lottery.lotteryProject.Response;
import lv.helloit_lottery.lotteryProject.lotteries.DAO.LotteryDAO;

import lv.helloit_lottery.lotteryProject.participants.DAO.ParticipantDAO;
import lv.helloit_lottery.lotteryProject.participants.Participant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class LotteryService {

    Logger logger = LoggerFactory.getLogger(LotteryService.class);

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

            logger.error("Incorrect input data: " + titleMessageError + limitMessageError);
            return new Response("Fail", titleMessageError + limitMessageError);
        }

        if (!titleIsRegistered(lottery.getTitle())) {
            logger.error("Incorrect input data: Title with the same name is registered ");
            return new Response("Fail", "Title with the same name is registered \n");
        }

        if (lottery.getLimit() <= 0) {
            logger.error("Incorrect input data: Participan count can be less or equals 0 ");
            return new Response("Fail", "Participan count can be less or equals 0 \n");
        }


        String pattern = "dd.MM.yyyy HH:mm";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String startDate = simpleDateFormat.format(new Date());
        lottery.setStartDate(startDate);
        logger.info("Set lottery start date");
        lottery.setLotteryStatus(Status.OPEN);
        logger.info("Set lottery status");
        lottery.setRegisteredParticipants(0);
        logger.info("Set registered participants count to 0");
        logger.info("Lottery successfully created");
        return lotteryDAO.createLottery(lottery);
    }

    public Collection<Lottery> lotteries() {

        Collection<Lottery> lotteries = lotteryDAO.getAll();
        lotteries.forEach(e -> e.setRegisteredParticipants(e.getParticipants().size()));

        logger.info("Lotteries collection successfully returned");
        return lotteries;
    }

    public boolean titleIsRegistered(String title) {
        return lotteryDAO.titleIsRegistered(title);
    }

    public Response stopLotteryRegistration(Long lotteryId) {
        Optional<Lottery> wrappedLottery = lotteryDAO.getById(lotteryId);
        if (wrappedLottery.isPresent()) {
            if (wrappedLottery.get().getLotteryStatus() != Status.OPEN) {

                logger.error("Lottery with id" + lotteryId + "is stopped or winner is selected");
                return new Response("Fail", "You can't stop lottery. Lottery is stopped or winner is selected");
            }
            wrappedLottery.get().setLotteryStatus(Status.CLOSED);
            logger.info("Set lottery status to CLOSED");

            lotteryDAO.updateLottery(wrappedLottery.get());
            logger.info("Lottery with id " + lotteryId + " status successfully changed to CLOSED");
            return new Response("OK");
        }

        logger.info("Lottery with id " + lotteryId + " does not exist");
        return new Response("Fail", "Lottery does not exist");

    }

    public Response chooseWinner(Long lotteryId) {
        Optional<Lottery> wrappedLottery = lotteryDAO.getById(lotteryId);
        if (wrappedLottery.isPresent()) {

            if (wrappedLottery.get().getLotteryStatus() != Status.CLOSED) {
                logger.error("Lottery " + lotteryId + "status is not equals CLOSED");
                return new Response("Fail", "You can't choose winner if lottery is not stopped");
            }
            if (wrappedLottery.get().getRegisteredParticipants() == 0) {
                logger.error("Lottery with id: " + lotteryId + "don't have registered participants");
                return new Response("Fail", "Can't choose winer if participan count = 0");
            }
            logger.info("Start choosing winner from all participans");
            int winnerIndex = new Random().nextInt(wrappedLottery.get().getRegisteredParticipants());
            String winnerCode = wrappedLottery.get().getParticipants().get(winnerIndex).getUniqueCode();
            String winnerEmail = wrappedLottery.get().getParticipants().get(winnerIndex).getEmail();

            String pattern = "dd.MM.yyyy HH:mm";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
            String endDate = simpleDateFormat.format(new Date());
            wrappedLottery.get().setEndDate(endDate);
            logger.info("Set lotery end date");
            wrappedLottery.get().setWinnerCode(winnerCode);
            logger.info("Set lottery winner code");
            wrappedLottery.get().setWinnerEmail(winnerEmail);
            wrappedLottery.get().setEndDate(endDate);
            wrappedLottery.get().setLotteryStatus(Status.WINNER_SELECTED);

            lotteryDAO.updateLottery(wrappedLottery.get());
            logger.info("Lottery : " + lotteryId + " status successfully changed to WINNER_SELECTED and winner information saved to DB");
            return new Response("OK", null, wrappedLottery.get().getWinnerCode());
        }
        logger.warn("Lottery with id: " + lotteryId + " does not exist");
        return new Response("Fail", "Lottery does not exist");
    }

    public Collection<LotteryBasic> getStatistic() {
        Collection<Lottery> lotteries = lotteryDAO.getAll();
        Collection<LotteryBasic> finishedLotteries = new ArrayList<>();

        for (Lottery lottery : lotteries) {
            logger.info("getting all lotteries with status WINNER_SELECTED");
            if (lottery.getLotteryStatus().equals(Status.WINNER_SELECTED)) {
                finishedLotteries.add(new LotteryBasic(lottery.getId(), lottery.getTitle(), lottery.getStartDate(), lottery.getEndDate(), lottery.getRegisteredParticipants()));
            }
        }
        logger.info("returning all lotteries with status WINNER_SELECTED");
        return finishedLotteries;
    }

    public Response getStatus(Long id, String email, String code) {

        Optional<Lottery> wrappedLottery = lotteryDAO.getById(id);
        if (wrappedLottery.isPresent()) {

            for (Participant participant : wrappedLottery.get().getParticipants()) {
                logger.info("Start checking if participant with code:" + code + "and email:" + email + "is registered in lottery with id: " + id);
                if (participant.getEmail().equals(email) && participant.getUniqueCode().equals(code)) {
                    if (wrappedLottery.get().getLotteryStatus() != Status.WINNER_SELECTED) {
                        logger.warn("Lottery with id: "+ id + "is open or closed, but winner is not selected");
                        return new Response("PENDING");
                    }
                    if (wrappedLottery.get().getWinnerEmail().equals(email) && wrappedLottery.get().getWinnerCode().equals(code)) {
                        logger.info("Return response with status WIN");
                        return new Response("WIN");
                    } else {
                        logger.info("Return response with status WIN");
                        return new Response("LOSE");
                    }
                }
            }
        }
        logger.warn("Lottery with id: "+ id + "does not exist");
        return new Response("ERROR ");
    }
}
