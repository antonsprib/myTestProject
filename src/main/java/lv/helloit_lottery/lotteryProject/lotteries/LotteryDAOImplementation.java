package lv.helloit_lottery.lotteryProject.lotteries;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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
}
