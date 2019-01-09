package lv.helloit_lottery.lotteryProject.lotteries.DAO;

import lv.helloit_lottery.lotteryProject.lotteries.Lottery;
import lv.helloit_lottery.lotteryProject.lotteries.Response.LotteryResponse;
import lv.helloit_lottery.lotteryProject.lotteries.Response.LotterySuccessResponse;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

@Repository
public class LotteryDAOImplementation implements LotteryDAO {
    private final SessionFactory sessionFactory;

    @Autowired
    public LotteryDAOImplementation(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public LotteryResponse createLottery(Lottery lottery) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        Long id = (Long) session.save(lottery);

        transaction.commit();
        session.close();

        return new LotterySuccessResponse("OK",id);
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

    @Override
    public boolean titleIsRegistered(String title) {
        Session session = sessionFactory.openSession();

        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Lottery> query = builder.createQuery(Lottery.class);
        Root<Lottery> root = query.from(Lottery.class);
        query.where(builder.equal(root.get("title"), title));
        query.select(root);

        List<Lottery>  identicalUnits= session.createQuery(query).getResultList();
        if(identicalUnits.size() != 0){
            session.close();
            return false;
        }
        session.close();
        return true;
    }

    @Override
    public Optional<Lottery> getById(Long id) {
        Session session = sessionFactory.openSession();

        Lottery lottery = session.get(Lottery.class, id);

        session.close();
        return Optional.ofNullable(lottery);
    }
}
