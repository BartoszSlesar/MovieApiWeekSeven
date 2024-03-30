package com.bard.movielistweekseven.utils;

import java.util.regex.Pattern;

public class RegexValidation {

    public static boolean emailValidation(String emailAddress) {
        return Pattern.compile("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")
                .matcher(emailAddress)
                .matches();
    }


}
