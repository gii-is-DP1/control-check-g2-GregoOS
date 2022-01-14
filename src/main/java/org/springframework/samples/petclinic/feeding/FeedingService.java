package org.springframework.samples.petclinic.feeding;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.pet.Pet;
import org.springframework.samples.petclinic.pet.PetType;
import org.springframework.stereotype.Service;

@Service
public class FeedingService {

    @Autowired
    private FeedingRepository feedingRepository;

    public List<Feeding> getAll() {
        List<Feeding> f = feedingRepository.findAll();
        return f;
    }

    public List<FeedingType> getAllFeedingTypes() {
        return null;
    }

    public FeedingType getFeedingType(String typeName) {
        FeedingType ft = feedingRepository.findFeedingType(typeName);
        return ft;
    }

    public Feeding save(Feeding p) throws UnfeasibleFeedingException {
        Pet pet = p.getPet();
        PetType pt = p.getFeedingType().getPetType();
        if (pet.getType() != pt) {
            throw new UnfeasibleFeedingException();
        } else {
            feedingRepository.save(p);
        }
        return p;
    }

}
