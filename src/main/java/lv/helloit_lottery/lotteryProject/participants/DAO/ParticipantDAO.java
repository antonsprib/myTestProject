package lv.helloit_lottery.lotteryProject.participants.DAO;

import lv.helloit_lottery.lotteryProject.Response;
import lv.helloit_lottery.lotteryProject.participants.Participant;


import java.util.Collection;

public interface ParticipantDAO {

    Response register(Participant participant);

    boolean codeIsRegistered(String code);

    Collection<Participant> getAll();

}
