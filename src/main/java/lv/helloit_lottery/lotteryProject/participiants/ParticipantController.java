package lv.helloit_lottery.lotteryProject.participiants;


import lv.helloit_lottery.lotteryProject.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class ParticipantController {

    private final ParticipantService participantService;

    @Autowired
    public ParticipantController(ParticipantService participantService) {
        this.participantService = participantService;
    }

    @PostMapping(value = "/register")
    public Response register(@Valid @RequestBody Participant participant, BindingResult bindingResult) {

        return participantService.assignAndRegister(participant, bindingResult);

    }
}
