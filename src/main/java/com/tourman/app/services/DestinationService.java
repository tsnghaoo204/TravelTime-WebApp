package com.tourman.app.services;

import com.tourman.app.domains.entities.Destination;

import java.util.List;

public interface DestinationService {
    List<Destination> getDestinations();
    Destination getDestination(long id);
    Destination addDestination(Destination destination);
    Destination updateDestination(long id, Destination newDestinationData);
    void deleteDestination(long id);
}
