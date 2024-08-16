package com.example.sakilademo.model;

import lombok.Getter;

@Getter
public enum Rating {
    G("G"),
    PG("PG"),
    PG_13("PG-13"),
    R("R"),
    NC_17("NC-17");

    private final String displayName;

    // CONSTRUCTS enums with a string value and assigns to displayName
    Rating(String displayName) {
        // this holds the string value of the enum.
        this.displayName = displayName;
    }

//    public static Rating fromDisplayName(String displayName) {
//
//        // loop over all the values of the Rating enum
//
//        for (Rating rating : Rating.values()) {
//            // check if the current rating's displayName matches the input displayName
//            if (rating.displayName.equals(displayName)) {
//
//                // if a match is found it returns the matching Rating enum value
//                return rating;
//            }
//        }
//        throw new IllegalArgumentException("No enum value for display name: " + displayName);
//    }
}

