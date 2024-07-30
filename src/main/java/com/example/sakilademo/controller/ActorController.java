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


@RestController
@RequestMapping("/actors")
@CrossOrigin(origins = "http://localhost:5173")
public class ActorController {

    @Autowired
    private ActorService actorService;

//    public ActorController(ActorService actorService) {
//        this.actorService = actorService;
//    }

    // Read Function Service Implemented
    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    @CrossOrigin(origins = "*")
    public List<ActorResponse> readAllActors(){
        return actorService.listActors();
    }

    // Read Function Service Implemented
    @GetMapping("/{id}")
    @CrossOrigin(origins = "*")
    public ResponseEntity<?> findActor (@Validated(ValidationGroup.Create.class)@PathVariable short id) {
        Optional<ActorResponse> optionalActorResponse = actorService.findActor(id);

        if (optionalActorResponse.isPresent()) {
            // return it with HTTP 200 OK status
            ActorResponse actorResponse = optionalActorResponse.get();
            return ResponseEntity.ok(actorResponse);
        } else {
            // return HTTP 404 NOT FOUND status
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Actor not found with id: " + id);
        }
    }

    // Create Function Service Implemented
    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    @CrossOrigin(origins = "*")
    public Actor createActor (@Validated(ValidationGroup.Create.class) @RequestBody ActorInput data) {
        return actorService.createActor(data);
    }

    // Put Function Service Implemented
    @PutMapping("/{id}")
    @CrossOrigin(origins = "*")
    ResponseEntity<?> replaceActor(@Validated(ValidationGroup.Create.class) @RequestBody ActorInput newActor,@Validated(ValidationGroup.Create.class) @PathVariable short id) {
        Optional<ActorResponse> updatedActorResponse = actorService.replaceActor(newActor, id);

        if (updatedActorResponse.isPresent()) {
            return ResponseEntity.ok(updatedActorResponse.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No matching actor to update exists.");
        }

    }

    // Delete Function Service Implemented
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @CrossOrigin(origins = "*")
    public ResponseEntity<?> deleteActor (@Validated(ValidationGroup.Create.class)@PathVariable short id) {
        boolean isDeleted = actorService.deleteActor(id);

        if (isDeleted) {
            return ResponseEntity.ok("ACTOR HAS BEEN TERMINATED PER YOUR DEMAND!");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No matching actor to delete exists.");
        }
    }

}
