package lv.helloit_lottery.lotteryProject.participiants.DAO;

import lv.helloit_lottery.lotteryProject.lotteries.Lottery;
import lv.helloit_lottery.lotteryProject.participiants.Participant;
import lv.helloit_lottery.lotteryProject.participiants.Response.ParticipantResponse;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public class ParticipantDAOImplementation implements ParticipantDAO{

    private final SessionFactory sessionFactory;

    @Autowired
    public ParticipantDAOImplementation(SessionFactory sessionFactory) {

        this.sessionFactory = sessionFactory;
    }

    @Override
    public ParticipantResponse register(Participant participant) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.save(participant);

        transaction.commit();
        session.close();

        return new ParticipantResponse("OK");
    }


}
