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

}

