package com.bard.movielistweekseven.utils;


import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class SentEmailAfterPost {


    @After("@annotation(SentConfirmationEmail)")
    private void afterHello() throws Throwable {

    }
}
