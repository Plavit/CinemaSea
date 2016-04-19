/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

/**
 *
 * @author leffl_000
 */
public class Movie {
    
    private String NameCZ;
    private String NameEN;
    private String CoverImage;
    private Person[] Actors;
    private Person[] Scenarists;
    private Person[] Directors;
    private int Rating;
    private String[] Genres;
    private String[] Tags;

    public Movie() {
    }

    public String getNameCZ() {
        return NameCZ;
    }

    public void setNameCZ(String NameCZ) {
        this.NameCZ = NameCZ;
    }

    public String getNameEN() {
        return NameEN;
    }

    public void setNameEN(String NameEN) {
        this.NameEN = NameEN;
    }

    public String getCoverImage() {
        return CoverImage;
    }

    public void setCoverImage(String CoverImage) {
        this.CoverImage = CoverImage;
    }

    public Person[] getActors() {
        return Actors;
    }

    public void setActors(Person[] Actors) {
        this.Actors = Actors;
    }

    public Person[] getScenarists() {
        return Scenarists;
    }

    public void setScenarists(Person[] Scenarists) {
        this.Scenarists = Scenarists;
    }

    public Person[] getDirectors() {
        return Directors;
    }

    public void setDirectors(Person[] Directors) {
        this.Directors = Directors;
    }

    public int getRating() {
        return Rating;
    }

    public void setRating(int Rating) {
        this.Rating = Rating;
    }

    public String[] getGenres() {
        return Genres;
    }

    public void setGenres(String[] Genres) {
        this.Genres = Genres;
    }

    public String[] getTags() {
        return Tags;
    }

    public void setTags(String[] Tags) {
        this.Tags = Tags;
    }
    
    

    
    
    
}
