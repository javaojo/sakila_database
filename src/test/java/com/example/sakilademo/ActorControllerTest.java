package com.example.sakilademo;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ActorControllerTest {



    @Test
    public void test() {

        final var expect=4;

        final var actual= 2+2;

        Assertions.assertEquals(expect, actual, "2+2 shoulbe be 4");



    }
}
