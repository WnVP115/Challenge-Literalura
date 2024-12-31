package com.example.literalura.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Books {
    @JsonProperty("guttendex_id")
    private int gutendexId;  // ID único del libro en Gutendex

    @JsonProperty("title")
    private String title;  // Título del libro

    @JsonProperty("translators")
    private String translators;  // Traductores (si aplica)

    @JsonProperty("subjects")
    private String subjects;  // Temas

    @JsonProperty("bookshelves")
    private String bookshelves;  // Estantes o categorías en las que está clasificado

    @JsonProperty("languages")
    private String languages;  // Idiomas en los que está disponible el libro

    @JsonProperty("copyright")
    private boolean copyright;  // Indica si el libro está protegido por derechos de autor

    public int getGutendexId() {
        return gutendexId;
    }

    public void setGutendexId(int gutendexId) {
        this.gutendexId = gutendexId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTranslators() {
        return translators;
    }

    public void setTranslators(String translators) {
        this.translators = translators;
    }

    public String getSubjects() {
        return subjects;
    }

    public void setSubjects(String subjects) {
        this.subjects = subjects;
    }

    public String getBookshelves() {
        return bookshelves;
    }

    public void setBookshelves(String bookshelves) {
        this.bookshelves = bookshelves;
    }

    public String getLanguages() {
        return languages;
    }

    public void setLanguages(String languages) {
        this.languages = languages;
    }

    public boolean isCopyright() {
        return copyright;
    }

    public void setCopyright(boolean copyright) {
        this.copyright = copyright;
    }
}
