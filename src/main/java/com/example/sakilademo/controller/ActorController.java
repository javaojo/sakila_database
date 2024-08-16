package com.example.sakilademo.controller;

import com.example.sakilademo.response.ActorResponse;
import com.example.sakilademo.service.ActorService;
import com.example.sakilademo.utility.ValidationGroup;
import com.example.sakilademo.input.ActorInput;
import com.example.sakilademo.model.Actor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/actors")
@CrossOrigin(origins = "*")
public class ActorController {

    @Autowired
    private ActorService actorService;

    public ActorController(ActorService actorService) {
        this.actorService = actorService;
    }

    // Read Function Service Implemented
    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    @CrossOrigin(origins = "*")
    public ResponseEntity<List<ActorResponse>> readAllActors() {
        List<Actor> actors = actorService.listActors();
        List<ActorResponse> actorResponses = actors.stream()
                .map(ActorResponse::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(actorResponses);
    }

    // Read Function Service Implemented
    @GetMapping("/{id}")
    @CrossOrigin(origins = "*")
    public ResponseEntity<ActorResponse> findActor(@PathVariable short id) {
        Optional<Actor> optionalActor = actorService.findActor(id);

        if (optionalActor.isPresent()) {
            ActorResponse actorResponse = new ActorResponse(optionalActor.get());
            return ResponseEntity.ok(actorResponse);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(null);
        }
    }

    // Create Function Service Implemented
    @PostMapping("")
    @CrossOrigin(origins = "*")
    public ResponseEntity<ActorResponse> createActor(@Validated(ValidationGroup.Create.class) @RequestBody ActorInput data) {
        Actor actor = actorService.createActor(data);
        ActorResponse actorResponse = new ActorResponse(actor);
        return ResponseEntity.status(HttpStatus.CREATED).body(actorResponse);
    }

    // Put Function Service Implemented
    @PutMapping("/{id}")
    @CrossOrigin(origins = "*")
    public ResponseEntity<ActorResponse> updateActor(@Validated(ValidationGroup.Create.class) @RequestBody ActorInput newActor, @PathVariable short id) {
        Actor updatedActor = actorService.updateActor(newActor, id);

        if (updatedActor != null) {
            ActorResponse actorResponse = new ActorResponse(updatedActor);
            return ResponseEntity.ok(actorResponse);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(null);
        }
    }

    // Delete Function Service Implemented
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @CrossOrigin(origins = "*")
    public ResponseEntity<String> deleteActor(@Validated(ValidationGroup.Create.class) @PathVariable short id) {
        boolean isDeleted = actorService.deleteActor(id);

        if (isDeleted) {
            return ResponseEntity.ok("ACTOR HAS BEEN TERMINATED PER YOUR DEMAND!");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No matching actor to delete exists.");
        }
    }
}
