Feature: ActorController

  Scenario: An actor is read by ID
    Given an actor exists with id 42
    When a GET request is made to for an actors with id 42
    Then an ActorDetailsOutput is returned


  Scenario: An actor is created
    Given a new actor with first name "Jane" and last name "Doe"
    When a POST request is made to create the actor
    Then the actor is created successfully


  Scenario: An actor is not found by ID
    Given no actor exists with ID 43
    When a GET request is made to for an actors with id 43
    Then a NotFound status is returned



  Scenario: An actor is updated
    Given an actor exists with ID 42
    When a PUT request is made to update the actor with first name "John" and last name "Smith"
    Then the actor is updated successfully


  Scenario: An actor is deleted
    Given an actor exists with ID 42
    When a DELETE request is made to delete the actor
    Then the actor is deleted successfully



  Scenario: Actor could not be found to delete
    Given no actor exists with ID 43
    When a DELETE request is made to delete a non-existent actor
    Then a No matching actor to delete exists is returned


#  Scenario: An actor is created
#    Given a valid ActorInput request body
#    When a POST request is made to actors collection
#    Then an ActorDetailsOutput is returned
#    And the status code is 204
#
#  Scenario: An invalid actor is read by ID
#    Given no actor exists with ID 43
#    When a GET request is made to for an actors with id 43
#    Then a ResponseStatusException is thrown
#    And the status code 404


