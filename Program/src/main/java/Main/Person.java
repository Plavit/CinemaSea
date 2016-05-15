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
import java.util.Arrays;
import java.util.Objects;

/**
 *
 * @author Löffler David, Szeles Marek
 */
public class Person{
    
    private final int Id;
    private String Name;
    private String LastName;
    private String FullName;
    private String Description;
    private int Year;
    private ArrayList<Movie> moviesActed = new ArrayList<Movie>(0);
    private ArrayList<Movie> moviesDirected = new ArrayList<Movie>(0);
    private ArrayList<Movie> moviesScreenwritten = new ArrayList<Movie>(0);
    

    public Person(int Id) {
        this.Id = Id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String LastName) {
        this.LastName = LastName;
    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String FullName) {
        this.FullName = FullName;
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

    public Movie[] getMoviesActed() {
        Movie[] movie = new Movie[moviesActed.size()];
        for(int i=0;i<moviesActed.size();i++){
            movie[i]=moviesActed.get(i);
        }
        return movie;
    }
    
    public void addMovieActed(Movie movie) {
        //check if Movie to be added isn't included yet
        if(!this.moviesActed.contains(movie)){
            //if not, add movie
            this.moviesActed.add(movie);
        }
    }
    
    public void setMoviesActed(Movie[] movies) {
        for (Movie movie : movies) {
            this.moviesActed.add(movie);
        }
        

    }
    
    public void removeMovieActed(Movie movie){
        this.moviesActed.remove(movie);
    }

    public Movie[] getMoviesDirected() {
        Movie[] movie = new Movie[moviesDirected.size()];
        for(int i=0;i<moviesDirected.size();i++){
            movie[i]=moviesDirected.get(i);
        }
        return movie;
    }
    
    public void addMovieDirected(Movie movie) {
        //check if Movie to be added isn't included yet
        if(!this.moviesDirected.contains(movie)){
            //if not, add movie
            this.moviesDirected.add(movie);
        }
    }
    
    public void removeMovieDirected(Movie movie){
        this.moviesDirected.remove(movie);
    }
    
    public Movie[] getMoviesScreenwritten() {
        Movie[] movie = new Movie[moviesScreenwritten.size()];
        for(int i=0;i<moviesScreenwritten.size();i++){
            movie[i]=moviesScreenwritten.get(i);
        }
        return movie;
    }
    
    public void addMovieScreenwritten(Movie movie) {
        //check if Movie to be added isn't included yet
        if(!this.moviesScreenwritten.contains(movie)){
            //if not, add movie
            this.moviesScreenwritten.add(movie);
        }
    }
    
    public void removeMovieScreenwritten(Movie movie){
        this.moviesScreenwritten.remove(movie);
    }
    
    public int getId() {
        return Id;
    }
    
    public Person copy(){
        Person newPR = new Person(this.Id);
        newPR.setFullName(this.FullName);
        newPR.setName(this.Name);
        newPR.setLastName(this.LastName);
        newPR.setYear(this.Year);
        newPR.setDescription(this.Description);
        return newPR;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 53 * hash + this.Id;
        hash = 53 * hash + Objects.hashCode(this.FullName);
        hash = 53 * hash + this.Year;
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
        final Person other = (Person) obj;
        if (this.Id != other.Id) {
            return false;
        }
        if (!Objects.equals(this.FullName, other.FullName)) {
            return false;
        }
        if (this.Year != other.Year) {
            return false;
        }
        return true;
    }
    
    
    
}
