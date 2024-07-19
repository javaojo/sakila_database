package com.example.sakilademo.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "language")
public class Language {

    @Id
    @Column(name = "language_id")
    private short languageId;

    @Setter
    @Getter
    @Column(name = "name")
    private String name;


    @JsonCreator
    public Language(@JsonProperty("id") short languageId, @JsonProperty("name") String name) {
        this.languageId = languageId;
        this.name = name;
    }

    // Default constructor
    public Language() {
    }

    // Getter for languageId
    public Short getId() {
        return languageId;
    }

    @Override
    public String toString() {
        return "Language{" +
                "id=" + languageId +
                ", name='" + name + '\'' +
                '}';
    }

}