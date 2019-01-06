package lv.helloit_lottery.lotteryProject.lotteries;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

@Repository
public class LotteryDAOImplementation implements LotteryDAO{
    private final SessionFactory sessionFactory;

    @Autowired
    public LotteryDAOImplementation(SessionFactory sessionFactory) {

        this.sessionFactory = sessionFactory;
    }

    @Override
    public Long createLottery(Lottery lottery) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        Long id = (Long) session.save(lottery);

        transaction.commit();
        session.close();

        return id;
    }

    @Override
    public List<Lottery> getAll() {
        Session session = sessionFactory.openSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Lottery> query = builder.createQuery(Lottery.class);
        query.select(query.from(Lottery.class));

        List<Lottery> lotteries = session.createQuery(query).getResultList();
        session.close();
        return  lotteries;
    }
}
