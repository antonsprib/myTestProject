package lv.helloit_lottery.lotteryProject.participants;

import lv.helloit_lottery.lotteryProject.Response;
import lv.helloit_lottery.lotteryProject.lotteries.DAO.LotteryDAO;
import lv.helloit_lottery.lotteryProject.lotteries.Lottery;
import lv.helloit_lottery.lotteryProject.lotteries.Status;
import lv.helloit_lottery.lotteryProject.participants.DAO.ParticipantDAO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.Optional;

@Service
public class ParticipantService {

    Logger logger = LoggerFactory.getLogger(ParticipantService.class);

    public final ParticipantDAO participantDAO;
    public final LotteryDAO lotteryDAO;

    @Autowired
    public ParticipantService(ParticipantDAO participantDAO, LotteryDAO lotteryDAO) {
        this.participantDAO = participantDAO;
        this.lotteryDAO = lotteryDAO;
    }

    public Response assignAndRegister(Participant participant, BindingResult bindingResult) {
        Optional<Lottery> wrappedLottery = lotteryDAO.getById(participant.getLotteryId());

        if (wrappedLottery.isPresent()) {


            if (!wrappedLottery.get().getLotteryStatus().equals(Status.OPEN)) {
                logger.warn("Lottery doesnt have status OPEN.");
                return new Response("Fail", "Registration for this lottery is closed. Please choose lottery with status open");
            }

            if (wrappedLottery.get().getRegisteredParticipants() >= wrappedLottery.get().getLimit()) {
                logger.warn("Lottery with id max participant count ir registered. Lottery is full");
                return new Response("Fail", "Sorry, choosen by you lottery is full");
            }

            String emailMessageError;
            String ageMessageError;
            String lotteryIdMessageError;
            String codeMessageError;

            if (bindingResult.hasErrors()) {
                int emailErrorCount = bindingResult.getFieldErrorCount("email");
                int ageErrorCount = bindingResult.getFieldErrorCount("age");
                int lotteryIdErrorCount = bindingResult.getFieldErrorCount("lotteryId");
                int codeErrorCount = bindingResult.getFieldErrorCount("uniqueCode");

                emailMessageError = emailErrorCount != 0 ? bindingResult.getFieldError("email").getDefaultMessage() + "; \n" : "";
                ageMessageError = ageErrorCount != 0 ? bindingResult.getFieldError("age").getDefaultMessage()+ "; \n" : "";
                lotteryIdMessageError = lotteryIdErrorCount != 0 ? bindingResult.getFieldError("lotteryId").getDefaultMessage()+ "; \n" : "";
                codeMessageError = codeErrorCount != 0 ? bindingResult.getFieldError("uniqueCode").getDefaultMessage()+ "; \n" : "";
                logger.warn("Incorrect input data:"+ emailMessageError + ageMessageError + lotteryIdMessageError + codeMessageError);
                return new Response("Fail", emailMessageError + ageMessageError + lotteryIdMessageError + codeMessageError);
            }

            if(!participant.getUniqueCode().matches("\\d+")){
                logger.warn("Entered code: contains not only numbers");
                return new Response("Fail", "Code must contain only digits");
            }

            if(!isValidFirst8Digits(participant.getUniqueCode(), wrappedLottery.get().getStartDate(), participant.getEmail())){
                logger.warn("Entered code first 8 digits is not correct");
                return new Response("Fail", "First 8 digits of your code is not valid");
            }

            for(Participant participant1 : wrappedLottery.get().getParticipants()){
                if(participant1.getUniqueCode().equals(participant.getUniqueCode())){
                    logger.warn("Entered code is registered in DB");
                    return new Response("Fail", "Entered code is registered");
                }
            }


            participant.setLottery(wrappedLottery.get());
            wrappedLottery.get().setRegisteredParticipants(wrappedLottery.get().getRegisteredParticipants() + 1);
            logger.info("Change participant count + 1");

            participantDAO.register(participant);
            logger.info("Update participant");

            lotteryDAO.updateLottery(wrappedLottery.get());
            logger.info("Update lottery");

            return new Response("OK");
        }
        logger.warn("Lottery with id does not exist");
        return new Response("Fail", "Lottery does not exist");

    }

    public boolean isValidFirst8Digits(String code, String date, String email) {

        String withoutPoints = date.replace( ".", "" );
        String correctString = withoutPoints.substring(0,4) + withoutPoints.substring(6,8);
        correctString += email.length() < 10 ? "0" + email.length() : email.length();

        String participant8Digits = code.substring(0,8);
        if(correctString.equals(participant8Digits)){
            return true;
        }
        return false;
    }
}
