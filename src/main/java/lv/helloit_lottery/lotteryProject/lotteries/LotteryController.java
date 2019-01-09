package lv.helloit_lottery.lotteryProject.lotteries;

import lv.helloit_lottery.lotteryProject.lotteries.Response.LotteryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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

}
