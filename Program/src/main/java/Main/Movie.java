/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

/**
 *
 * @author Löffler David, Szeles Marek
 */
public class Movie {
    
    private final int Id;
    private String NameCZ;
    private String NameEN;
    private String CoverImage;
    private Person[] Actors;
    private Person[] Scenarists;
    private Person[] Directors;
    private double Rating;
    private String[] Genres;
    private String[] Tags;
    private String Description;
    private int Year;

    public Movie(int id) {
        this.Id = id;
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

    public double getRating() {
        return Rating;
    }

    public void setRating(double Rating) {
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

    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }

    public int getYear() {
        return Year;
    }

    public void setYear(int Year) {
        this.Year = Year;
    }
    
    public int getId(){
        return Id;
    }

    @Override
    public String toString() {
        
        String show = Id + " | " + NameCZ + " / " + NameEN + " - " + Year + " | RT: " + Rating;
        
        show += "\nGenres: ";
        for(int i = 0; i < Genres.length; i++){
            String str = Genres[i];
            if(Genres.length > 1 && Genres.length != i+1){
                show += str + ", ";
            }else if(Genres.length == i+1){
                show += str;
            }
            else{
                show += str;
            }
        }               
        
        show += " | Tags: ";
        for(int i = 0; i < Tags.length; i++){
            String str = Tags[i];
            if(Tags.length > 1 && Tags.length != i+1){
                show += str + ", ";
            }else if(Tags.length == i+1){
                show += str;
            }
            else{
                show += str;
            }
        }
        
        show += "\nDirectors: ";
        for(int i = 0; i < Directors.length; i++){
            Person p = Directors[i];
            if(Directors.length > 1 && Directors.length != i+1){
                show += p.getFullName() + ", ";
            }else if(Directors.length == i+1){
                show += p.getFullName();
            }
            else{
                show += p.getFullName();
            }
        }
        
        show += "\nScenarists: ";
        for(int i = 0; i < Scenarists.length; i++){
            Person p = Scenarists[i];
            if(Scenarists.length > 1 && Scenarists.length != i+1){
                show += p.getFullName() + ", ";
            }else if(Scenarists.length == i+1){
                show += p.getFullName();
            }
            else{
                show += p.getFullName();
            }
        }
        
        show += "\nActors: ";
        for(int i = 0; i < Actors.length; i++){
            Person p = Actors[i];
            if(Actors.length > 1 && Actors.length != i+1){
                show += p.getFullName() + ", ";
            }else if(Actors.length == i+1){
                show += p.getFullName();
            }
            else{
                show += p.getFullName();
            }
        }
        show += "\nDescription: " + Description;
        
        return show;
    }
}
