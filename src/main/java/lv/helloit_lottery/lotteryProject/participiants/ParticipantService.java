package lv.helloit_lottery.lotteryProject.participiants;

import lv.helloit_lottery.lotteryProject.lotteries.DAO.LotteryDAO;
import lv.helloit_lottery.lotteryProject.lotteries.Lottery;
import lv.helloit_lottery.lotteryProject.participiants.DAO.ParticipantDAO;
import lv.helloit_lottery.lotteryProject.participiants.Response.ParticipantResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
        participant.setUniqueCode(1234567L);

        participantDAO.register(participant);
        return new ParticipantResponse("OK");

    }


}
