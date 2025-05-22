package com.tourman.app.services.impls;

import com.tourman.app.domains.entities.Destination;
import com.tourman.app.exceptions.ResourceNotFoundException;
import com.tourman.app.repositories.DestinationRepository;
import com.tourman.app.services.DestinationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DestinationServiceImpl implements DestinationService {
    @Autowired
    private DestinationRepository destinationRepository;

    @Override
    public List<Destination> getDestinations() {
        return destinationRepository.findAll();
    }

    @Override
    public Destination getDestination(long id) {
        return destinationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Destination not found with id " + id));
    }


    @Override
    public Destination addDestination(Destination destination) {
        return destinationRepository.save(destination);
    }

    @Override
    public Destination updateDestination(long id, Destination newDestinationData) {
        Destination existing = destinationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Destination not found with id " + id));

        // Update fields
        existing.setDesDescription(newDestinationData.getDesDescription());
        existing.setDesLocation(newDestinationData.getDesLocation());
        existing.setDesTitle(newDestinationData.getDesTitle());
        existing.setImagePath(newDestinationData.getImagePath());

        return destinationRepository.save(existing);
    }

    @Override
    public void deleteDestination(long id) {
        if (!destinationRepository.existsById(id)) {
            throw new ResourceNotFoundException("Destination not found with id " + id);
        }
        destinationRepository.deleteById(id);
    }

}
