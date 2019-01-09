package lv.helloit_lottery.lotteryProject.participiants;


import lv.helloit_lottery.lotteryProject.participiants.Response.ParticipantResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ParticipantController {

    private final ParticipantService participantService;

    @Autowired
    public ParticipantController(ParticipantService participantService) {
        this.participantService = participantService;
    }

    @PostMapping(value = "/register")
    public ParticipantResponse register(@RequestBody Participant participant){

        return participantService.assignAndRegister(participant);

    }
}
