package com.example.sakilademo.service;

import com.example.sakilademo.input.ActorInput;
import com.example.sakilademo.model.Actor;
import com.example.sakilademo.repository.ActorRepository;
import com.example.sakilademo.response.ActorResponse;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ActorService {

    @Autowired
    private ActorRepository actorRepository;

    public List<ActorResponse> listActors() {
        return actorRepository.findAll().stream().map(ActorResponse::new).toList();
    }

    public Optional<ActorResponse> findActor(short id) {

        Optional<Actor> actorToFind = actorRepository.findById(id);

        if (actorToFind.isPresent()) {
            // convert actor to ActorResponse
            ActorResponse actorResponse = new ActorResponse(actorToFind.get());
            return Optional.of(actorResponse);
        }
        // if the actor is not found return an empty Optional
        return Optional.empty();
    }

    public Actor createActor(ActorInput data) {
        final var newActor = new Actor();
        newActor.setFirstName(data.getFirstName());
        newActor.setLastName(data.getLastName());
        return actorRepository.save(newActor);
    }

    public Optional<ActorResponse> replaceActor(ActorInput newActor, short id){

        Optional<Actor> actorToUpdate = actorRepository.findById(id);

        if (actorToUpdate.isPresent()) {
            Actor existingActor = actorToUpdate.get();
            existingActor.setFirstName(newActor.getFirstName());
            existingActor.setLastName(newActor.getLastName());

            Actor updatedActor = actorRepository.save(existingActor);
            ActorResponse actorResponse = new ActorResponse(updatedActor);

            return Optional.of(actorResponse);
        } else {
            return Optional.empty();
        }
    }

    public boolean deleteActor(short id) {
        Optional<Actor> actorToDelete = actorRepository.findById(id);

        if (actorToDelete.isPresent()) {
            actorRepository.delete(actorToDelete.get());
            return true; // delete successfully
        } else {
            return false; // actor not found
        }
    }
}
