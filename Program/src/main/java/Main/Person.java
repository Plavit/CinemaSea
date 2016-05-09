
package Main;

import java.util.ArrayList;

/**
 *
 * @author Löffler David, Szeles Marek
 */
public class Person {
    
    private String Name;
    private String LastName;
    private String FullName;
    private String Description;
    private int Year;
    private ArrayList<Movie> moviesActed = new ArrayList<Movie>(0);
    private ArrayList<Movie> moviesDirected = new ArrayList<Movie>(0);
    private ArrayList<Movie> moviesScreenwritten = new ArrayList<Movie>(0);
    
    public Person() {
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
    
    public void removeMovieActed(Movie movie){
        this.moviesActed.remove(movie);
    }
    
}
