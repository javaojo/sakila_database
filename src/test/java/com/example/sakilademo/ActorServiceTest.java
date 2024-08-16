package com.example.sakilademo;

import com.example.sakilademo.input.ActorInput;
import com.example.sakilademo.model.Actor;
import com.example.sakilademo.repository.ActorRepository;

import com.example.sakilademo.service.ActorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ActorServiceTest {

    @InjectMocks
    private ActorService actorService;

    @Mock
    private ActorRepository actorRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testListActors() {

        List<Actor> actors = new ArrayList<>();
        actors.add(new Actor());
        when(actorRepository.findAll()).thenReturn(actors);

        List<Actor> result = actorService.listActors();

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void testFindActor() {

        short actorId = 1;
        Actor actor = new Actor();
        when(actorRepository.findById(actorId)).thenReturn(Optional.of(actor));

        Optional<Actor> result = actorService.findActor(actorId);

        assertTrue(result.isPresent());
        assertEquals(actor, result.get());
    }

    @Test
    void testCreateActor() {

        ActorInput actorInput = new ActorInput();
        actorInput.setFirstName("John");
        actorInput.setLastName("Doe");
        Actor newActor = new Actor();
        newActor.setFirstName("John");
        newActor.setLastName("Doe");

        when(actorRepository.save(any(Actor.class))).thenReturn(newActor);

        Actor result = actorService.createActor(actorInput);

        assertNotNull(result);
        assertEquals("John", result.getFirstName());
        assertEquals("Doe", result.getLastName());
    }

    @Test
    void testUpdateActor() {

        ActorInput actorInput = new ActorInput();

        actorInput.setFirstName("Jane");
        actorInput.setLastName("Doe");

        Actor existingActor = new Actor();
        short actorId = 1;
        existingActor.setId(actorId);
        existingActor.setFirstName("John");
        existingActor.setLastName("Doe");

        when(actorRepository.findById(actorId)).thenReturn(Optional.of(existingActor));
        when(actorRepository.save(any(Actor.class))).thenReturn(existingActor);

        Actor result = actorService.updateActor(actorInput, actorId);

        assertNotNull(result);
        assertEquals("Jane", result.getFirstName());
        assertEquals("Doe", result.getLastName());
    }

    @Test
    void testDeleteActor() {

        short actorId = 1;
        Actor actor = new Actor();
        when(actorRepository.findById(actorId)).thenReturn(Optional.of(actor));

        boolean result = actorService.deleteActor(actorId);

        assertTrue(result);
    }

    @Test
    void testDeleteActorNotFound() {

        short actorId = 1;
        when(actorRepository.findById(actorId)).thenReturn(Optional.empty());

        boolean result = actorService.deleteActor(actorId);

        assertFalse(result);
        verify(actorRepository, times(0)).delete(any(Actor.class));
    }

    @Test
    void updateActorWhenNamesAreTooShort() {

        Actor existingActor = new Actor();
        short actorId = 1;
        existingActor.setId(actorId);
        existingActor.setFirstName("John");
        existingActor.setLastName("Doe");

        ActorInput actorInput = new ActorInput();
        actorInput.setFirstName("A");
        actorInput.setLastName("B");

        when(actorRepository.findById(actorId)).thenReturn(Optional.of(existingActor));

        assertThrows(IllegalArgumentException.class, () -> actorService.updateActor(actorInput, actorId));

        verify(actorRepository, never()).save(any(Actor.class));
    }

}
