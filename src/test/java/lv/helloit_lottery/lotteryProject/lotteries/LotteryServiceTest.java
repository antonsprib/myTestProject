package lv.helloit_lottery.lotteryProject.lotteries;

import lv.helloit_lottery.lotteryProject.Response;
import lv.helloit_lottery.lotteryProject.lotteries.DAO.LotteryDAO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.validation.BindingResult;

import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LotteryServiceTest {

    @Autowired
    private LotteryService victim;



    @Test
    public void shouldCreateNewLottery(){
        Lottery lot = new Lottery();
        lot.setTitle("title");
        lot.setLimit(30L);
        BindingResult result = mock(BindingResult.class);

        victim.createLottery(lot, result);

        Collection<Lottery> lotteries = victim.lotteries();

        assertEquals(lotteries.size(), 1);

        Lottery savedLottery = lotteries.stream().findFirst().get();

        assertEquals(savedLottery.getTitle(), "title");
    }

    @Test
    public void shouldNotCreateNewLotteryIfTitleIsRegistered(){
        Lottery lot = new Lottery();
        lot.setTitle("title");
        lot.setLimit(30L);

        Lottery lot1 = new Lottery();
        lot1.setTitle("title");
        lot1.setLimit(30L);
        BindingResult result = mock(BindingResult.class);

        Response status = (Response) victim.createLottery(lot1, result);
        assertEquals(status.getStatus(), "Fail");

        Collection<Lottery> lotteries = victim.lotteries();
        assertEquals(lotteries.size(), 1);
    }

    @Test
    public void shouldNotCreateNewLotteryIfTitleIsEmpty(){
        Lottery lot = new Lottery();
        lot.setTitle("");
        lot.setLimit(30L);

        BindingResult result = mock(BindingResult.class);

        Response status = (Response) victim.createLottery(lot, result);
        assertEquals(status.getStatus(), "Fail");

//        Collection<Lottery> lotteries = victim.lotteries();
//        assertEquals(lotteries.size(), 0);
    }

}
