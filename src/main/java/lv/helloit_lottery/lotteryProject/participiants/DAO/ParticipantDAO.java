package lv.helloit_lottery.lotteryProject.participiants.DAO;

import lv.helloit_lottery.lotteryProject.lotteries.Lottery;
import lv.helloit_lottery.lotteryProject.participiants.Participant;
import lv.helloit_lottery.lotteryProject.participiants.Response.ParticipantResponse;

import java.util.Collection;
import java.util.Optional;

public interface ParticipantDAO {

    ParticipantResponse register(Participant participant);

    boolean codeIsRegistered(String code);


}
