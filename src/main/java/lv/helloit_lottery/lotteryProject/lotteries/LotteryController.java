package lv.helloit_lottery.lotteryProject.lotteries;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
public class LotteryController {
    private final LotteryService lotteryService;

    @Autowired
    public LotteryController(LotteryService lotteryService) {
        this.lotteryService = lotteryService;
    }

    @PostMapping(value = "/start-registration")
    public void createLottery(@RequestBody Lottery lottery){
        lotteryService.createLottery(lottery);
    }

    @GetMapping(value = "/lotteries")
    public Collection<Lottery> lotteries(){
        return lotteryService.lotteries();
    }

}
