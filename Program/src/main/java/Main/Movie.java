/*
 * Copyright (C) 2016 CinemaSea
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package Main;

import java.util.ArrayList;

/**
 * Class representing a general movie object, it contains basic information 
 * about the movie corresponding to the database columns, such as Czech name, 
 * English name, year of theatrical release, etc.
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

    /**
     * Sets id of the Movie on Movie initialization
     * 
     * @param id new Movie ID
     */
    public Movie(int id) {
        this.Id = id;
    }

    /**
     * Returns Czech name of given movie
     * 
     * @return Czech name of given movie
     */
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
    
    public String personsToString(char Who){
        String str = "";
        switch (Who) {
            case 'A':
                for(int i = 0; i < Actors.length; i++){
                    if(Actors.length > 1 && Actors.length != i+1){
                        str += Actors[i].getFullName() + ", ";
                    }else{
                        str += Actors[i].getFullName();
                    }
                }
                break;
            case 'D':
                for(int i = 0; i < Directors.length; i++){
                    if(Directors.length > 1 && Directors.length != i+1){
                        str += Directors[i].getFullName() + ", ";
                    }else{
                        str += Directors[i].getFullName();
                    }
                }
                break;
            case 'S':
                for(int i = 0; i < Scenarists.length; i++){
                    if(Scenarists.length > 1 && Scenarists.length != i+1){
                        str += Scenarists[i].getFullName() + ", ";
                    }else{
                        str += Scenarists[i].getFullName();
                    }
                }
                break;
        }
        return str;
    }

    public String genresToString() {
        String genres = "";

        for (int k = 0; k < Genres.length; k++) {
            if (Genres.length > 1 && Genres.length != k + 1) {
                genres += Genres[k] + " / ";
            } else {
                genres += Genres[k];
            }
        }
        return genres;
    }
    
    public void addActor(Person pr){
        if(this.Actors != null){
        Person[] newPPL = new Person[this.Actors.length+1];
        for(int i = 0; i < newPPL.length-1; i++){
            newPPL[i] = this.Actors[i];
        }
        newPPL[newPPL.length-1] = pr;
        this.Actors = newPPL;
        }else{
            this.Actors = new Person[1];
            Actors[0] = pr;
        }
    }
    
    public void addDirector(Person pr){
        if(this.Directors != null){
        Person[] newPPL = new Person[this.Directors.length+1];
        for(int i = 0; i < newPPL.length-1; i++){
            newPPL[i] = this.Directors[i];
        }
        newPPL[newPPL.length-1] = pr;
        this.Directors = newPPL;
        }else{
            this.Directors = new Person[1];
            Directors[0] = pr;
        }
    }
    
    public void addScenarist(Person pr){
        if(this.Scenarists != null){
        Person[] newPPL = new Person[this.Scenarists.length+1];
        for(int i = 0; i < newPPL.length-1; i++){
            newPPL[i] = this.Scenarists[i];
        }
        newPPL[newPPL.length-1] = pr;
        this.Scenarists = newPPL;
        }else{
            this.Scenarists = new Person[1];
            Scenarists[0] = pr;
        }
    }
    
    public void rmActor(int id){
        ArrayList<Person> ppl = new ArrayList<>(0);
        for(int i = 0; i < this.Actors.length; i++){
            if(id != this.Actors[i].getId()){
                ppl.add(this.Actors[i]);
            }
        }
        this.Actors = new Person[ppl.size()];
        int k = 0;
        for(Person man : ppl){
            this.Actors[k] = man;
            k++;
        }
    }
    
    public void rmDirector(int id){
        ArrayList<Person> ppl = new ArrayList<>(0);
        for(int i = 0; i < this.Directors.length; i++){
            if(id != this.Directors[i].getId()){
                ppl.add(this.Directors[i]);
            }
        }
        this.Directors = new Person[ppl.size()];
        int k = 0;
        for(Person man : ppl){
            this.Directors[k] = man;
            k++;
        }
    }
    
    public void rmScenarist(int id){
        ArrayList<Person> ppl = new ArrayList<>(0);
        for(int i = 0; i < this.Scenarists.length; i++){
            if(id != this.Scenarists[i].getId()){
                ppl.add(this.Scenarists[i]);
            }
        }
        this.Scenarists = new Person[ppl.size()];
        int k = 0;
        for(Person man : ppl){
            this.Scenarists[k] = man;
            k++;
        }
    }
    
    public boolean isEmpty(char what){
        boolean decide = false;
        switch(what){
            case 'A':
                if(this.Actors == null) decide = true;
                break;
            case 'S':
                if(this.Scenarists == null) decide = true;
                break;
            case 'D':
                if(this.Directors == null) decide = true;
                break;
        }
        return decide;
    }
    
    public Movie clone(){
        Movie clon = new Movie(Id);
        clon.setActors(Actors);
        clon.setCoverImage(CoverImage);
        clon.setDirectors(Directors);
        clon.setDescription(Description);
        clon.setGenres(Genres);
        clon.setNameCZ(NameCZ);
        clon.setNameEN(NameEN);
        clon.setRating(Rating);
        clon.setScenarists(Scenarists);
        clon.setTags(Tags);
        clon.setYear(Year);
        return clon;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 71 * hash + this.Id;
        hash = 71 * hash + (int) (Double.doubleToLongBits(this.Rating) ^ (Double.doubleToLongBits(this.Rating) >>> 32));
        hash = 71 * hash + this.Year;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Movie other = (Movie) obj;
        if (this.Id != other.Id) {
            return false;
        }
        if (Double.doubleToLongBits(this.Rating) != Double.doubleToLongBits(other.Rating)) {
            return false;
        }
        if (this.Year != other.Year) {
            return false;
        }
        return true;
    }
    
    
    
}
