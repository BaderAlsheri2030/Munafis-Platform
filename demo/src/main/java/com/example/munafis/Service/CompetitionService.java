package com.example.munafis.Service;
import com.example.munafis.API.ApiException;
import com.example.munafis.Model.Competition;
import com.example.munafis.Model.Rfp;
import com.example.munafis.Model.User;
import com.example.munafis.Repository.AuthRepository;
import com.example.munafis.Repository.CompetitionRepository;
import com.example.munafis.Repository.RfpRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
@Service
@RequiredArgsConstructor
public class CompetitionService {

    private final CompetitionRepository competitionRepository;
    private final RfpRepository rfpRepository;
    private final AuthRepository authRepository;
    public List<Competition> getCompetitions(){
        return competitionRepository.findAll();
    }

    public void addCompetition(Integer id){
        User user = authRepository.findUserById(id);
        if (user == null){
            throw new ApiException("invalid");
        }
        if (!competitionRepository.findAll().isEmpty()){
            throw new ApiException("You cannot add another competition");
        }
        Competition competition1 = new Competition(null,null);
        competitionRepository.save(competition1);
    }

    public void updateCompetition(Integer auth,Integer id){
        User user = authRepository.findUserById(auth);
        if (user == null){
            throw new ApiException("invalid input");
        }
        Competition competition1 = competitionRepository.findCompetitionById(id);
        Set<Rfp> rfps = new HashSet<>();
        if (competition1 == null){
            throw new ApiException("invalid");
        }
        for (Rfp rfp:rfpRepository.findAll()){
            if (!rfp.isComplete()){
                rfps.add(rfp);
            }
        }
        competition1.setRfps(rfps);
        competitionRepository.save(competition1);
    }

    public void deleteCompetition(Integer id){
        Competition competition = competitionRepository.findCompetitionById(id);
        if (competition == null){
            throw new ApiException("invalid id");
        }
        for (Rfp rfp:competition.getRfps()){
            if (!rfp.isComplete()){
                throw new ApiException("cannot delete since there's an incomplete proposals in the competition");
            }
        }
        competitionRepository.delete(competition);
    }

}
