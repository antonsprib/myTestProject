package lv.helloit_lottery.lotteryProject.lotteries;

import lv.helloit_lottery.lotteryProject.lotteries.Response.LotteryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;

@RestController
public class LotteryController {
    private final LotteryService lotteryService;

    @Autowired
    public LotteryController(LotteryService lotteryService) {
        this.lotteryService = lotteryService;
    }

    @PostMapping(value = "/start-registration")
    public LotteryResponse createLottery(@Valid @RequestBody Lottery lottery, BindingResult bindingResult){
        return lotteryService.createLottery(lottery, bindingResult);
    }

    @GetMapping(value = "/lotteries")
    public Collection<Lottery> lotteries(){
        return lotteryService.lotteries();
    }

    @PostMapping(value = "/stop-registration", produces = MediaType.APPLICATION_JSON_VALUE)
    public LotteryResponse stopLotteryRegistration(@RequestBody Lottery lottery){

        Long lotteryId = lottery.getId();
        return lotteryService.stopLotteryRegistration(lotteryId);
    }

    @PostMapping(value = "/choose-winner", produces = MediaType.APPLICATION_JSON_VALUE)
    public LotteryResponse chooseWinner(@RequestBody Lottery lottery){
        Long lotteryId = lottery.getId();
        return lotteryService.chooseWinner(lotteryId);
    }

    @GetMapping(value = "/stats")
    public  Collection<Lottery> getStatistic(){
        return lotteryService.getStatistic();
    }

}
