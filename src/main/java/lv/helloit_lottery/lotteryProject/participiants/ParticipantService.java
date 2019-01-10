package lv.helloit_lottery.lotteryProject.participiants;

import lv.helloit_lottery.lotteryProject.lotteries.DAO.LotteryDAO;
import lv.helloit_lottery.lotteryProject.lotteries.Lottery;
import lv.helloit_lottery.lotteryProject.participiants.DAO.ParticipantDAO;
import lv.helloit_lottery.lotteryProject.participiants.Response.ParticipantResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import java.util.Random;

@Service
public class ParticipantService {

    public final ParticipantDAO participantDAO;
    public final LotteryDAO lotteryDAO;

    @Autowired
    public  ParticipantService(ParticipantDAO participantDAO, LotteryDAO lotteryDAO){
        this.participantDAO = participantDAO;
        this.lotteryDAO = lotteryDAO;
    }

    public ParticipantResponse assignAndRegister(Participant participant){
        Optional<Lottery> wrappedLottery = lotteryDAO.getById(participant.getLotteryId());

        participant.setLottery(wrappedLottery.get());
        participant.setUniqueCode(generateParticipanCode(participant.getEmail(), participant.getLottery().getStartDate()));
        wrappedLottery.get().setRegisteredParticipants(wrappedLottery.get().getRegisteredParticipants()+1);

        participantDAO.register(participant);
        lotteryDAO.updateLottery(wrappedLottery.get());

        return new ParticipantResponse("OK");

    }

    public String generateParticipanCode(String email, Long date){

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("ddMMYY");
        String participanCode = simpleDateFormat.format(date);
        participanCode += email.length() < 10 ? "0" + email.length() : email.length();

        for(int i = 0; i <=7; i++){
            participanCode +=  new Random().nextInt(10);
        }

        return participanCode;

    }


}
