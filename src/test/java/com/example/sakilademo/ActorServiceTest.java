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




}
