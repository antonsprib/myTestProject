package lv.helloit_lottery.lotteryProject.participiants;

import lv.helloit_lottery.lotteryProject.lotteries.DAO.LotteryDAO;
import lv.helloit_lottery.lotteryProject.lotteries.Lottery;
import lv.helloit_lottery.lotteryProject.lotteries.Response.LotteryWrongResponse;
import lv.helloit_lottery.lotteryProject.lotteries.Status;
import lv.helloit_lottery.lotteryProject.participiants.DAO.ParticipantDAO;
import lv.helloit_lottery.lotteryProject.participiants.Response.ParticipantResponse;
import lv.helloit_lottery.lotteryProject.participiants.Response.ParticipantWrongResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import java.util.Random;

@Service
public class ParticipantService {

    public final ParticipantDAO participantDAO;
    public final LotteryDAO lotteryDAO;

    @Autowired
    public ParticipantService(ParticipantDAO participantDAO, LotteryDAO lotteryDAO) {
        this.participantDAO = participantDAO;
        this.lotteryDAO = lotteryDAO;
    }

    public ParticipantResponse assignAndRegister(Participant participant, BindingResult bindingResult) {
        Optional<Lottery> wrappedLottery = lotteryDAO.getById(participant.getLotteryId());

        if (wrappedLottery.isPresent()) {

            String emailMessageError;
            String ageMessageError;
            String lotteryIdMessageError;

            if (bindingResult.hasErrors()) {
                int emailErrorCount = bindingResult.getFieldErrorCount("email");
                int ageErrorCount = bindingResult.getFieldErrorCount("age");
                int lotteryIdErrorCount = bindingResult.getFieldErrorCount("lotteryId");

                emailMessageError = emailErrorCount != 0 ? bindingResult.getFieldError("email").getDefaultMessage() + "; \n" : "";
                ageMessageError = ageErrorCount != 0 ? bindingResult.getFieldError("age").getDefaultMessage()+ "; \n" : "";
                lotteryIdMessageError = lotteryIdErrorCount != 0 ? bindingResult.getFieldError("lotteryId").getDefaultMessage()+ "; \n" : "";

                return new ParticipantWrongResponse("Fail", emailMessageError + ageMessageError + lotteryIdMessageError);
            }

            if (!wrappedLottery.get().getLotteryStatus().equals(Status.OPEN)) {
                return new ParticipantWrongResponse("Fail", "Registration for this lottery is closed. Please choose lottery with status open");
            }

            if (wrappedLottery.get().getRegisteredParticipants() >= wrappedLottery.get().getLimit()) {
                return new ParticipantWrongResponse("Fail", "Sorry, choosen by you lottery is full");
            }

//            String uniqueCode = generateParticipanCode(participant.getEmail(), participant.getLottery().getStartDate());

//            while (participantDAO.codeIsRegistered(uniqueCode)) {
//                uniqueCode = generateParticipanCode(participant.getEmail(), participant.getLottery().getStartDate());
//            }

            participant.setLottery(wrappedLottery.get());
            participant.setUniqueCode(generateParticipanCode(participant.getEmail(), participant.getLottery().getStartDate()));
            wrappedLottery.get().setRegisteredParticipants(wrappedLottery.get().getRegisteredParticipants() + 1);

            participantDAO.register(participant);
            lotteryDAO.updateLottery(wrappedLottery.get());

            return new ParticipantResponse("OK");
        }

        return new ParticipantWrongResponse("Fail", "Lottery does not exist");

    }

    public String generateParticipanCode(String email, Long date) {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("ddMMYY");
        String participanCode = simpleDateFormat.format(date);
        participanCode += email.length() < 10 ? "0" + email.length() : email.length();

        for (int i = 0; i <= 7; i++) {
            participanCode += new Random().nextInt(10);
        }
        return participanCode;
    }


}
