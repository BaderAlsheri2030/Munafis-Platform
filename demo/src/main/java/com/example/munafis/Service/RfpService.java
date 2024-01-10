package com.example.munafis.Service;

import com.example.munafis.API.ApiException;
import com.example.munafis.DTO.RfpDTO;
import com.example.munafis.Model.*;
import com.example.munafis.Repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class RfpService {
    private final RfpRepository rfpRepository;
    private final CompetitionRepository competitionRepository;
    private final CompanyRepository companyRepository;
    private final OffersRepository offersRepository;
    private final AuthRepository authRepository;

    //admin
    public List<Rfp> getAll(){
        return rfpRepository.findAll();
    }
    public Set<Rfp> viewMyProposals(Integer user_id){
        User user = authRepository.findUserById(user_id);
        Company company = companyRepository.findCompanyByUser(user);

        return company.getRfps();
    }


    //permit all
    public Set<Rfp> getAllProposals(){
        Set<Rfp> proposals  =new HashSet<>();
        for (Rfp rfp : rfpRepository.findAll()){
            if (!rfp.isComplete()){
                proposals.add(rfp);
            }
        }
        if (proposals.isEmpty()){
            throw new ApiException("There is no proposals");
        }
        for (Rfp rfp:proposals){
            rfp.setOffers(null);
        }
        return proposals;
    }

    public void addRfp(RfpDTO rfpDTO,Integer comp_id,Integer user_id){
        User user = authRepository.findUserById(user_id);
        Company company = companyRepository.findCompanyByUser(user);
        Competition competition = competitionRepository.findCompetitionById(comp_id);

        if (competition == null){
            throw new ApiException("there's no competition");
        }
        if (company == null){
            throw new ApiException("Invalid company");
        }else if (!company.getUser().getId().equals(user.getId())){
            throw new ApiException("you have no access");
        }
        Rfp rfp = new Rfp(null,rfpDTO.getDescription(),rfpDTO.getReference_number(),rfpDTO.getCompetition_type(),rfpDTO.getDead_line(),rfpDTO.getLocation(),rfpDTO.getStartDate(),rfpDTO.getContract_length(),rfpDTO.getService_details(),rfpDTO.getTitle(),rfpDTO.isComplete(),rfpDTO.getName(),rfpDTO.getTime_left(),company,competition,null);
        rfpRepository.save(rfp);
        companyRepository.save(company);
        competitionRepository.save(competition);
    }


    public void updateRfp(Integer rfp_id,RfpDTO rfpDTO,Integer user_id){
        User user = authRepository.findUserById(user_id);
        Company company = companyRepository.findCompanyById(user.getCompany().getId());

        Rfp rfp = rfpRepository.findRfpById(rfp_id);
        if (company == null){
            throw new ApiException("Invalid company");
        }
        if (rfp == null){
            throw new ApiException("Invalid proposal");
        }else if (!rfp.getCompany().getUser().getId().equals(user_id)){
            throw new ApiException("You have no access");
        }

                rfp.setDescription(rfpDTO.getDescription());
                rfp.setCompetitionType(rfpDTO.getCompetition_type());
                rfp.setContractLength(rfpDTO.getContract_length());
                rfp.setDeadLine(rfpDTO.getDead_line());
                rfp.setServiceDetails(rfpDTO.getService_details());
                rfp.setTimeLeft(rfpDTO.getTime_left());
                rfp.setTitle(rfpDTO.getTitle());

        rfpRepository.save(rfp);
    }

    public void deleteRfp(Integer rfp_id,Integer user_id){
        User user = authRepository.findUserById(user_id);
        Company company = companyRepository.findCompanyById(user.getCompany().getId());
        Rfp rfp = rfpRepository.findRfpById(rfp_id);
        if (rfp == null) {
            throw new ApiException("invalid, proposal was not found");
        }
            if (!company.getUser().getId().equals(user_id)){
              throw new ApiException("invalid access");
            }
        if (!rfp.getCompany().getId().equals(company.getId())){
            throw new ApiException("invalid access");
        }
        rfpRepository.delete(rfp);
    }

    //accept one offer and reject all offers by default
    public void acceptOffer(Integer rfp_id,Integer offer_id,Integer user_id){
        boolean mark = false;
        User user = authRepository.findUserById(user_id);
        Company company = companyRepository.findCompanyById(user.getCompany().getId());
        Rfp rfp = rfpRepository.findRfpById(rfp_id);
        Offer offer = offersRepository.findOfferById(offer_id);
        if (!offer.getRfp().getCompany().getUser().getId().equals(user_id)){
            throw new ApiException("you have no access for this offer");
        }
        offer.setStatus("accepted");
        offersRepository.save(offer);
        rfp.setComplete(true);

            for (Rfp rfp1:company.getRfps()){
                if (rfp1.getId().equals(rfp.getId())){
                    for (Offer offer1:rfp1.getOffers()) {
                        if (offer1.getStatus().equals("pending")){
                            offer1.setStatus("rejected");
                            offersRepository.save(offer1);
                        }
                    }
                }
            }

        rfpRepository.save(rfp);

    }
    public void rejectOffer(Integer rfp_id,Integer offer_id,Integer user_id){
        User user = authRepository.findUserById(user_id);
        Company company = companyRepository.findCompanyById(user.getCompany().getId());
        Rfp rfp = rfpRepository.findRfpById(rfp_id);
        Offer offer = offersRepository.findOfferById(offer_id);
        if (!offer.getRfp().getCompany().getUser().getId().equals(user_id)){
            throw new ApiException("you have no access for this offer");
        }
        for (Rfp rfp1:company.getRfps()){
            if (rfp1.getId().equals(rfp.getId())){
                for (Offer offer1:rfp1.getOffers()){
                    if (offer.getId().equals(offer1.getId())){
                        offer1.setStatus("rejected");
                        offersRepository.save(offer1);
                        break;
                    }

                }
            }
        }
        rfpRepository.save(rfp);
    }

    //add company authentication for security
    //company view rfp received offers
    public List<Offer> viewReceivedOffers(Integer rfp_id,Integer user_id){
        User user = authRepository.findUserById(user_id);
        Company company = companyRepository.findCompanyById(user.getCompany().getId());
        Rfp rfp = rfpRepository.findRfpById(rfp_id);
        if (rfp == null){
            throw new ApiException("proposal not found");
        }
        if (!rfp.getCompany().getUser().getId().equals(user_id)){
            throw new ApiException("You have no access for this proposal");
        }if (!company.getUser().getId().equals(user.getId())){
            throw new ApiException("You have no access for this company");

        }
        List<Offer> myOffers = offersRepository.findAllByRfpId(rfp.getId());
        if (myOffers == null){
            throw new ApiException("You have no offers for this proposal");
        }
        return myOffers;
    }

    //company view
    public List<Offer> viewOffersByPrice(Integer rfp_id,double min,double max,Integer user_id){
        User user = authRepository.findUserById(user_id);
        Company company = companyRepository.findCompanyById(user.getCompany().getId());
        Rfp rfp = rfpRepository.findRfpById(rfp_id);
        if (rfp == null){
            throw new ApiException("Invalid proposal");
        }
        if (!rfp.getCompany().getUser().getId().equals(user_id)){
        throw new ApiException("You have no access for this proposal");
    }   if (!company.getUser().getId().equals(user.getId())){
        throw new ApiException("You have no access for this company");

    }
        List<Offer> offers = offersRepository.findOffersByRfpIdAndPriceBetween(rfp.getId(),min,max);
        return offers;
    }

    //provider view
    // must hide offers from providers
    public Set<Rfp> findRfpsBydeadlineBefore(LocalDate date){

        Set<Rfp> rfps = rfpRepository.findAllByDeadLineBefore(date);
        for (Rfp rfp:rfps){
            if (rfp.isComplete()){
                rfps.remove(rfp);
            }
        }
        if (rfps.isEmpty()){
            throw new ApiException("There is no proposals before this date");
        }
        for (Rfp rfp : rfps){
            rfp.setOffers(null);
        }
        return rfps;
    }

    //providerView
    // must hide offers from providers
    public Set<Rfp> findProposalsByLocation(String location){
        Set<Rfp> rfps = rfpRepository.findAllByLocationEqualsIgnoreCase(location);
        if (rfps.isEmpty()){
            throw new ApiException("There is no proposals in this city");
        }
        for (Rfp rfp : rfps){
            rfp.setOffers(null);
        }
        return rfps;
    }

    public Set<Rfp> findProposalsByCompanyName(String name){
        Set<Rfp> rfps = rfpRepository.findAllByName(name);
        if (rfps.isEmpty()){
            throw new ApiException("There is no proposals in this city");
        }
        for (Rfp rfp : rfps){
            rfp.setOffers(null);
        }
        return rfps;
    }




}