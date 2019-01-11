package lv.helloit_lottery.lotteryProject.participiants.DAO;

import lv.helloit_lottery.lotteryProject.Response;
import lv.helloit_lottery.lotteryProject.lotteries.Lottery;
import lv.helloit_lottery.lotteryProject.participiants.Participant;


import java.util.Collection;
import java.util.Optional;

public interface ParticipantDAO {

    Response register(Participant participant);

    boolean codeIsRegistered(String code);

    Collection<Participant> getAll();

}
