package com.example.literalura.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Authors {
    @JsonProperty("name")
    private String name;  // Nombre del autor

    @JsonProperty("birth_year")
    private int birthYear;  // Año de nacimiento

    @JsonProperty("death_year")
    private int deathYear;  // Año de fallecimiento (si aplica)

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(int birthYear) {
        this.birthYear = birthYear;
    }

    public int getDeathYear() {
        return deathYear;
    }

    public void setDeathYear(int deathYear) {
        this.deathYear = deathYear;
    }
}
