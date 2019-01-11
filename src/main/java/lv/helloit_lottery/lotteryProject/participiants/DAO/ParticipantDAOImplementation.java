package lv.helloit_lottery.lotteryProject.participiants.DAO;

import lv.helloit_lottery.lotteryProject.lotteries.Lottery;
import lv.helloit_lottery.lotteryProject.participiants.Participant;
import lv.helloit_lottery.lotteryProject.participiants.Response.ParticipantResponse;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Collection;
import java.util.List;
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

    @Override
    public boolean codeIsRegistered(String code) {
        Session session = sessionFactory.openSession();

        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Participant> query = builder.createQuery(Participant.class);
        Root<Participant> root = query.from(Participant.class);
        query.where(builder.equal(root.get("uniqueCode"), code));
        query.select(root);

        List<Participant> identicalUnits= session.createQuery(query).getResultList();
        if(identicalUnits.size() != 0){
            session.close();
            return false;
        }
        session.close();
        return true;
    }


}
