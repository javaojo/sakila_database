package com.example.sakilademo;

import com.example.sakilademo.controller.ActorController;
import com.example.sakilademo.input.ActorInput;
import com.example.sakilademo.model.Actor;
import com.example.sakilademo.response.ActorResponse;
import com.example.sakilademo.service.ActorService;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


public class ActorControllerStepDefs {

    @Mock
    //service with business logic
    ActorService mockActorService;

    @InjectMocks
    //controller with http responses
    ActorController controller;

    Exception caughtException;
    ResponseEntity<?> actualOutput;

    Actor actor1 = new Actor();
    ActorInput actorInput = new ActorInput();

    @Before
    public void setup() {

        mockActorService = mock(ActorService.class);
        controller = new ActorController(mockActorService);

        actor1.setFirstName("John");
        actor1.setLastName("Doe");
        actor1.setId((short) 42);

        actorInput.setFirstName("Jane");
        actorInput.setLastName("Doe");

    }

    //  FIND AN ACTOR BY ID

    @Given("an actor exists with id {short}")
    public void anActorExistsWithID(short actorId) {
        doReturn(Optional.of(actor1)).when(mockActorService).findActor(actorId);
    }

    @When("a GET request is made to for an actors with id {short}")
    public void aGETRequestIsMadeToActors(short actorId) {
        try {
            actualOutput = controller.findActor(actorId);
        } catch (Exception e) {
            caughtException = e;
        }
    }

    @Then("an ActorDetailsOutput is returned")
    public void anActorDetailsOutput() {
        assertNotNull(actualOutput);
        assertNull(caughtException);
        assertEquals(HttpStatus.OK, actualOutput.getStatusCode());
        assertInstanceOf(ActorResponse.class, actualOutput.getBody());

        ActorResponse response = (ActorResponse) actualOutput.getBody();
        assertEquals(actor1.getId(), response.getId());
        assertEquals(actor1.getFirstName(), response.getFirstName());
        assertEquals(actor1.getLastName(), response.getLastName());
    }


    // A NEW ACTOR IS CREATED

    @Given("a new actor with first name {string} and last name {string}")
    public void aNewActorWithFirstNameAndLastName(String firstName, String lastName) {
        actorInput.setFirstName(firstName);
        actorInput.setLastName(lastName);

    }

    @When("a POST request is made to create the actor")
    public void aPOSTRequestIsMadeToCreateActor() {

        when(mockActorService.createActor(actorInput)).thenReturn(actor1);
        actualOutput = controller.createActor(actorInput);

    }

    @Then("the actor is created successfully")
    public void theActorIsCreatedSuccessfully() {
        assertEquals(HttpStatus.CREATED, actualOutput.getStatusCode());
    }

    // ACTOR ID IS NOT FOUND

    @Given("no actor exists with ID {short}")
    public void noActorExistsWithID(short actorId) {
        doReturn(Optional.empty()).when(mockActorService).findActor(actorId);
    }

    @Then("a NotFound status is returned")
    public void aNotFoundStatusIsReturned() {
        assertEquals(HttpStatus.NOT_FOUND, actualOutput.getStatusCode());

    }

    // UPDATE EXISTING ACTOR

    @Given("an actor exists with ID {int}")
    public void anActorExistsWithID(int actorId) {
        doReturn(Optional.of(new ActorResponse(actor1))).when(mockActorService).findActor((short) actorId);
    }

    @When("a PUT request is made to update the actor with first name {string} and last name {string}")
    public void aPUTRequestIsMadeToUpdateTheActorWithFirstNameAndLastName(String firstName, String lastName) {

        ActorInput newActorInput = new ActorInput();
        newActorInput.setFirstName(firstName);
        newActorInput.setLastName(lastName);

        // mock the method to return the updated actor
        Actor updatedActor = new Actor();
        updatedActor.setId(actor1.getId());
        updatedActor.setFirstName(firstName);
        updatedActor.setLastName(lastName);

        when(mockActorService.updateActor(newActorInput, actor1.getId())).thenReturn(updatedActor);

        // perform the actual controller method call
        actualOutput = controller.updateActor(newActorInput, actor1.getId());
    }

    @Then("the actor is updated successfully")
    public void theActorIsUpdatedSuccessfully() {
        assertEquals(HttpStatus.OK, actualOutput.getStatusCode());
    }


    // DELETE EXISTING ACTOR

    @When("a DELETE request is made to delete the actor")
    public void aDELETERequestIsMadeToDeleteTheActor() {
        doReturn(true).when(mockActorService).deleteActor(actor1.getId());
        actualOutput = controller.deleteActor(actor1.getId());
    }

    @Then("the actor is deleted successfully")
    public void theActorIsDeletedSuccessfully() {
        assertEquals(HttpStatus.OK, actualOutput.getStatusCode());
        assertEquals("ACTOR HAS BEEN TERMINATED PER YOUR DEMAND!", actualOutput.getBody());
    }

    // DELETE A NON EXISTING ACTOR

    @When("a DELETE request is made to delete a non-existent actor")
    public void aDELETERequestIsMadeToDeleteANonExistentActor() {
        doReturn(false).when(mockActorService).deleteActor(actor1.getId());
        actualOutput = controller.deleteActor(actor1.getId());
    }

    @Then("a No matching actor to delete exists is returned")
    public void aNoMatchingActorToDeleteExistsIsReturned() {
        assertEquals(HttpStatus.NOT_FOUND, actualOutput.getStatusCode());
        assertEquals("No matching actor to delete exists.", actualOutput.getBody());
    }

}





