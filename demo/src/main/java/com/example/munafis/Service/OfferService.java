package com.example.munafis.Service;


import com.example.munafis.API.ApiException;
import com.example.munafis.DTO.OfferDTO;
import com.example.munafis.Model.Offer;
import com.example.munafis.Model.Provider;
import com.example.munafis.Model.Rfp;
import com.example.munafis.Model.User;
import com.example.munafis.Repository.AuthRepository;
import com.example.munafis.Repository.OffersRepository;
import com.example.munafis.Repository.ProviderRepository;
import com.example.munafis.Repository.RfpRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OfferService {
    private final OffersRepository offerRepository;
    private final RfpRepository rfpRepository;
    private final ProviderRepository providerRepository;
    private final AuthRepository authRepository;
    public List<Offer> getAllOffers(){
        return offerRepository.findAll();
    }

    //authenticate provider id
    //add offer if provider is authenticated and there's an existent rfp with valid status
    public void addOffer(OfferDTO offerDTO, Integer rfp_id,Integer user_id){
        User user = authRepository.findUserById(user_id);
        Rfp rfp = rfpRepository.findRfpById(rfp_id);
        Provider provider = providerRepository.findProviderById(user.getProvider().getId());

        for (Offer offer1: provider.getOffers()){
            if (offer1.getRfp().getId().equals(rfp.getId())){
                throw new ApiException("you can only make one offer for a project");
            }
        }
        if (rfp == null){
            throw new ApiException("Proposal doesn't exist");
        }
        if (rfp.isComplete()){
            throw new ApiException("Sorry you cannot make an offer for the proposal because it's completed");
        }else if (!provider.getUser().getId().equals(user_id)){
            throw new ApiException("you cannot add an offer for this provider");
        }
        Offer offer = new Offer(null,offerDTO.getDescription(),offerDTO.getDead_line(),offerDTO.getPrice(),"pending",offerDTO.getConditions(),rfp,provider);

        offerRepository.save(offer);
    }

    public void updateOffer(Integer offer_id,OfferDTO offerDTO,Integer user_id){
        User user = authRepository.findUserById(user_id);
        Provider provider = providerRepository.findProviderById(user.getProvider().getId());
        Offer offer = offerRepository.findOfferById(offer_id);
        if (offer == null){
            throw new ApiException("invalid id");
        }
        if (offer.getRfp().isComplete()){
            throw new ApiException("Sorry you cannot updated this offer, the proposal is completed");
        }
        else if (!offer.getProvider().getId().equals(provider.getId())){
            throw new ApiException("you cannot update the offer");
        }
        offer.setConditions(offerDTO.getConditions());
        offer.setPrice(offerDTO.getPrice());
        offer.setDeadLine(offerDTO.getDead_line());
        offer.setDescription(offerDTO.getDescription());
        offerRepository.save(offer);
    }

    public void withdrawOffer(Integer id,Integer user_id){
        User user = authRepository.findUserById(user_id);
        Provider provider = providerRepository.findProviderById(user.getProvider().getId());
        Offer offer = offerRepository.findOfferById(id);
        if (offer == null){
            throw new ApiException("invalid offer id");
        }
        if (!offer.getProvider().getId().equals(provider.getId())){
            throw new ApiException("you cannot withdraw the offer");
        }
        if (offer.getStatus().equals("accepted")){
            throw new ApiException("you can't withdraw the offer, contact the company that received your offer");
        }
        offerRepository.delete(offer);
    }

}