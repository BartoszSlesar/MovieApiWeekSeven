package com.bard.movielistweekseven.utils;


import com.bard.movielistweekseven.services.UserService;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class SentEmailAfterPost {

    private final JavaMailSender emailSender;

    private final UserService userService;

    public SentEmailAfterPost(JavaMailSender emailSender, UserService userService) {
        this.emailSender = emailSender;
        this.userService = userService;
    }


    @After(value = "@annotation(SentConfirmationEmail) && args(token,..)", argNames = "token")
    private void afterMoviePost(String token) throws Throwable {
        if (!this.userService.checkIfPresent(token)) {
            String email = this.userService.getEmail(token);
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("noreply@bard.com");
            message.setTo(email);
            message.setSubject("Your Movie was Added");
            message.setText("Congratulations, your movie was added correctly");
            this.emailSender.send(message);
        }


    }
}
