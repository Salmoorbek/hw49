package com.example.hw49.controller;

import com.example.hw49.model.Candidate;
import com.example.hw49.service.CandidateService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class HomeWork49Controller {
    private final Candidate anon = new Candidate("./src/main/resources/static/images/anon.jpg");
    private CandidateService candidateService = new CandidateService();
    @RequestMapping(value = "/")
    public String homeHandler(Model model){
        model.addAttribute("candidates", candidateService.getCandidates());
        return "candidates";
    }

    @GetMapping(value = "/thankyou")
    public String thankyou(@RequestParam(name = "candidateId")String name, Model model){
        try {
            Candidate candidate = candidateService.getCurrentCandidate(name);
            candidateService.getVote(candidate);
            model.addAttribute("candidate", candidate);
            model.addAttribute("percent", candidateService.getPrecent(candidate));
        }catch (Exception e){
            model.addAttribute("candidate", anon);
            model.addAttribute("percent", 0);
        }
        return "thankyou";
    }

    @RequestMapping(value = "/votes")
    public String votes(Model model){
        model.addAttribute("candidates", candidateService.sortLost(candidateService));
        model.addAttribute("votes", candidateService.getAllVotes());
        return "votes";
    }
}
