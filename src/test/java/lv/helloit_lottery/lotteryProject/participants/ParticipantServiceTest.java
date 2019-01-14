package lv.helloit_lottery.lotteryProject.participants;

import lv.helloit_lottery.lotteryProject.Response;
import lv.helloit_lottery.lotteryProject.lotteries.DAO.LotteryDAO;
import lv.helloit_lottery.lotteryProject.lotteries.Lottery;
import lv.helloit_lottery.lotteryProject.lotteries.LotteryService;
import lv.helloit_lottery.lotteryProject.lotteries.Status;
import lv.helloit_lottery.lotteryProject.participants.DAO.ParticipantDAO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.validation.BindingResult;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ParticipantServiceTest {

    @Autowired
    private LotteryDAO lotteryDAO;

    @Autowired
    private ParticipantService participantService;

    @Autowired
    private LotteryService lotteryService;

    @Test
    public void shouldCreateParticipantIfLotteryStatusIsOpen(){
        Lottery lot = new Lottery();
        lot.setTitle("title");
        lot.setLimit(30L);

        BindingResult result = mock(BindingResult.class);

        lotteryService.createLottery(lot, result);

        Long lotId = 0L;
        Response res = null;
        for(Lottery lottery : lotteryService.lotteries()){
            if(lottery.getTitle().equals("title")){
                lotId = lottery.getId();
                lottery.setLotteryStatus(Status.OPEN);
                lotteryDAO.updateLottery(lottery);

                Participant p = new Participant();
                p.setLotteryId(lotId);
                Byte b = 23;
                p.setAge(b);
                p.setEmail("r3m1x@bk.ru");
                p.setUniqueCode("1401191100000009");
                res = (Response) participantService.assignAndRegister(p, result);
            }
        }
        assertEquals(res.getStatus(), "OK");
    }

    @Test
    public void shouldNotCreateParticipantIfLotteryStatusIsNotOpen(){
        Lottery lot = new Lottery();
        lot.setTitle("title");
        lot.setLimit(30L);

        BindingResult result = mock(BindingResult.class);

        lotteryService.createLottery(lot, result);

        Long lotId = 0L;
        Response res = null;
        for(Lottery lottery : lotteryService.lotteries()){
            if(lottery.getTitle().equals("title")){
                lotId = lottery.getId();
                lottery.setLotteryStatus(Status.CLOSED);
                lotteryDAO.updateLottery(lottery);

                Participant p = new Participant();
                p.setLotteryId(lotId);
                Byte b = 23;
                p.setAge(b);
                p.setEmail("r3m1x@bk.ru");
                p.setUniqueCode("1401191100000009");
                res = (Response) participantService.assignAndRegister(p, result);
            }
        }
        assertEquals(res.getStatus(), "Fail");

        for(Lottery lottery : lotteryService.lotteries()){
            if(lottery.getTitle().equals("title")){
                lotId = lottery.getId();
                lottery.setLotteryStatus(Status.WINNER_SELECTED);
                lotteryDAO.updateLottery(lottery);

                Participant p = new Participant();
                p.setLotteryId(lotId);
                Byte b = 23;
                p.setAge(b);
                p.setEmail("r3m1x@bk.ru");
                p.setUniqueCode("1401191100000009");
                res = (Response) participantService.assignAndRegister(p, result);
            }
        }
        assertEquals(res.getStatus(), "Fail");
    }



}
