package com.example.sakilademo.response;


import com.example.sakilademo.model.Actor;
import lombok.Getter;

@Getter
public class PartialActorResponse {

    private final short id;
    private final String firstName;
    private final String lastName;


    public PartialActorResponse(Actor actor) {
        this.id = actor.getId();
        this.firstName = actor.getFirstName();
        this.lastName = actor.getLastName();
    }
}
