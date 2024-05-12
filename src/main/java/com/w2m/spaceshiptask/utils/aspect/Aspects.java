package com.w2m.spaceshiptask.utils.aspect;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class Aspects {

    @Before("execution(* com.w2m.spaceshiptask.spaceship.service.SpaceshipService.*(Long, ..)) && args(id, ..))")
    public void logNegativeId(Long id) {
        if (id <= 0) {
            System.err.println("WARNING: Attempt to find spaceship with an invalid id: " + id);

        }
    }

    @After("execution(* com.w2m.spaceshiptask.spaceship.service.SpaceshipService.showAllSpaceshipsRequest())")
    public void RequestMessageSent() {

        System.out.println("Producer Request sent to show Spaceship images");
    }

}
