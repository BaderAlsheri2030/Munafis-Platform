package com.example.munafis.Controller;

import com.example.munafis.Model.Competition;
import com.example.munafis.Model.User;
import com.example.munafis.Service.CompetitionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/comp")
@RequiredArgsConstructor
public class CompetitionController {
    private final CompetitionService competitionService;


    @GetMapping("/get")
    public ResponseEntity getCompetitions(){
        return ResponseEntity.status(200).body(competitionService.getCompetitions());
    }

    @PostMapping("/add")
    public ResponseEntity addCompetition(@AuthenticationPrincipal User user){
        competitionService.addCompetition(user.getId());
        return ResponseEntity.status(200).body("competition created");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateCompetition(@PathVariable Integer id,@AuthenticationPrincipal User user){
        competitionService.updateCompetition(user.getId(),id);
        return ResponseEntity.status(200).body("competition updated");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteCompetition(@PathVariable Integer id){
        competitionService.deleteCompetition(id);
        return ResponseEntity.status(200).body("competition deleted");
    }


}

