package lv.helloit_lottery.lotteryProject.lotteries;

import lv.helloit_lottery.lotteryProject.Response;
import lv.helloit_lottery.lotteryProject.lotteries.DAO.LotteryDAO;
import lv.helloit_lottery.lotteryProject.participants.DAO.ParticipantDAO;
import lv.helloit_lottery.lotteryProject.participants.Participant;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LotteryServiceTest {

    @Autowired
    private LotteryService victim;

    @Autowired
    private LotteryDAO lotteryDAO;


    @Test
    public void shouldCreateNewLottery() {
        Lottery lot = new Lottery();
        lot.setTitle("title");
        lot.setLimit(30L);
        BindingResult result = mock(BindingResult.class);

        victim.createLottery(lot, result);

        Collection<Lottery> lotteries = victim.lotteries();

        Lottery savedLottery = lotteries.stream().findFirst().get();

        assertEquals(savedLottery.getTitle(), "title");
    }

    @Test
    public void shouldNotCreateNewLotteryIfTitleIsRegistered() {

        Lottery lot1 = new Lottery();
        lot1.setTitle("title");
        lot1.setLimit(30L);
        BindingResult result = mock(BindingResult.class);

        Response status = (Response) victim.createLottery(lot1, result);
        assertEquals(status.getStatus(), "Fail");

    }

    @Test
    public void shouldNotChooseWinnerIfStatusIsNotOpen() {
        Lottery lot = new Lottery();
        lot.setTitle("title");
        lot.setLimit(30L);

        BindingResult result = mock(BindingResult.class);

        victim.createLottery(lot, result);

        Long lotId = 0L;
        Response res = null;
        for (Lottery lottery : victim.lotteries()) {
            if (lottery.getTitle().equals("title")) {
                lotId = lottery.getId();
                lotteryDAO.updateLottery(lottery);
                res = (Response) victim.chooseWinner(lotId);
            }
        }
        assertEquals(res.getStatus(), "Fail");
        assertEquals(lotteryDAO.getById(lotId).get().getLotteryStatus(), Status.OPEN);
    }

    @Test
    public void shouldNotChooseWinnerIfNoRegisteredParticipant() {
        Lottery lot = new Lottery();
        lot.setTitle("title1");
        lot.setLimit(30L);

        BindingResult result = mock(BindingResult.class);

        victim.createLottery(lot, result);

        Long lotId = 0L;
        Response res = null;
        for (Lottery lottery : victim.lotteries()) {
            if (lottery.getTitle().equals("title1")) {
                lotId = lottery.getId();
                lottery.setLotteryStatus(Status.CLOSED);
                lotteryDAO.updateLottery(lottery);
                res = (Response) victim.chooseWinner(lotId);
            }
        }
        assertEquals(res.getStatus(), "Fail");

        assertEquals(lotteryDAO.getById(lotId).get().getLotteryStatus(), Status.CLOSED);
    }

    @Test
    public void shouldShowStatistic() {
        Lottery lot = new Lottery();
        lot.setTitle("title11");
        lot.setLimit(30L);

        BindingResult result = mock(BindingResult.class);

        victim.createLottery(lot, result);

        Long lotId = 0L;
        Collection<LotteryBasic> lb = new ArrayList<>();

        for (Lottery lottery : victim.lotteries()) {
            if (lottery.getTitle().equals("title11")) {
                lotId = lottery.getId();
                lottery.setLotteryStatus(Status.WINNER_SELECTED);
                lotteryDAO.updateLottery(lottery);
                lb = victim.getStatistic();
            }
        }
        assertEquals(lb.size(), 1);

    }

    @Test
    public void shouldStopLotteryIfStatusIsOpen() {
        Lottery lot = new Lottery();
        lot.setTitle("title11");
        lot.setLimit(30L);

        BindingResult result = mock(BindingResult.class);

        victim.createLottery(lot, result);

        Long lotId = 0L;

        Response r = null;

        for (Lottery lottery : victim.lotteries()) {
            if (lottery.getTitle().equals("title11")) {
                lotId = lottery.getId();
                r = (Response) victim.stopLotteryRegistration(lotId);

            }
        }
        assertEquals(r.getStatus(), "OK");
        assertEquals(lotteryDAO.getById(lotId).get().getLotteryStatus(), Status.CLOSED);

    }


}
