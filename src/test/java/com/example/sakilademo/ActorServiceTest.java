package com.example.sakilademo;

import com.example.sakilademo.input.ActorInput;
import com.example.sakilademo.model.Actor;
import com.example.sakilademo.repository.ActorRepository;
import com.example.sakilademo.response.ActorResponse;
import com.example.sakilademo.service.ActorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ActorServiceTest {

//    @Mock
//    private ActorRepository actorRepository;
//    @InjectMocks
//    private ActorService actorService;
//
////    @BeforeEach
////    void setUp() {
////
////    }
//
//    @Test
//    void listActors_Should_Return_List_Of_Actor_Responses() {
//        Actor actor1 = new Actor();
//        actor1.setFirstName("John");
//        actor1.setLastName("Doe");
//
//        Actor actor2 = new Actor();
//        actor2.setFirstName("Jane");
//        actor2.setLastName("Doe");
//
//        when(actorRepository.findAll()).thenReturn(List.of(actor1, actor2));
//
//        List<ActorResponse> actorResponses = actorService.listActors();
//
//        assertNotNull(actorResponses);
//        assertEquals(2, actorResponses.size());
//        assertEquals("John", actorResponses.get(0).getFirstName());
//        assertEquals("Jane", actorResponses.get(1).getFirstName());
//    }
//
//    @Test
//    void replaceActor_Should_Return_Empty_Optional_When_Actor_Does_Not_Exist() {
//
//        short actorId = 1;
//        ActorInput actorInput = new ActorInput();
//        actorInput.setFirstName("John");
//        actorInput.setLastName("Doe");
//
//        when(actorRepository.findById(actorId)).thenReturn(Optional.empty());
//
//        Optional<ActorResponse> actorResponse = actorService.replaceActor(actorInput, actorId);
//
//        assertFalse(actorResponse.isPresent());
//    }
//
//    @Test
//    void findActor_Should_Return_Actor_Response_When_Actor_Exists() {
//
//        short actorId = 1;
//        Actor actor = new Actor();
//        actor.setId(actorId);
//        actor.setFirstName("John");
//        actor.setLastName("Doe");
//
//        when(actorRepository.findById(actorId)).thenReturn(Optional.of(actor));
//
//        Optional<ActorResponse> actorResponse = actorService.findActor(actorId);
//
//        assertTrue(actorResponse.isPresent());
//        assertEquals("John", actorResponse.get().getFirstName());
//    }
}
