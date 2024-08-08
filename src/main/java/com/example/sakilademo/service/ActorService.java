package com.example.sakilademo.service;

import com.example.sakilademo.input.ActorInput;
import com.example.sakilademo.model.Actor;
import com.example.sakilademo.repository.ActorRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Collection;
import java.util.Optional;

@Service
@Transactional
public class ActorService {

    @Autowired
    private ActorRepository actorRepository;

    public List<Actor> listActors() {
        return actorRepository.findAll();
    }

    public Optional<Actor> findActor(short id) {
        return actorRepository.findById(id);
    }

    public Actor createActor(ActorInput data) {
        final var newActor = new Actor();
        newActor.setFirstName(data.getFirstName());
        newActor.setLastName(data.getLastName());
        return actorRepository.save(newActor);
    }

    public Actor updateActor(ActorInput newActor, short id) {
        Optional<Actor> actorToUpdate = actorRepository.findById(id);

        if (actorToUpdate.isPresent()) {
            Actor existingActor = actorToUpdate.get();
            existingActor.setFirstName(newActor.getFirstName());
            existingActor.setLastName(newActor.getLastName());
            return actorRepository.save(existingActor);
        } else {
            return null;
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
