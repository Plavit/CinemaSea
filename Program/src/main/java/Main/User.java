/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import java.util.LinkedList;

/**
 *
 * @author leffl_000
 */
public class User {
    
    final private String Nickname;
    final private boolean isAdmin;
    private Movie[] Favorites;
    private Movie[] Rated;
    private LinkedList moviePaths;

    public User(String Nickname, boolean isAdmin) {
        this.Nickname = Nickname;
        this.isAdmin = isAdmin;
    }

    public Movie[] getFavorites() {
        return Favorites;
    }

    public void setFavorites(Movie[] Favorites) {
        this.Favorites = Favorites;
    }

    public Movie[] getRated() {
        return Rated;
    }

    public void setRated(Movie[] Rated) {
        this.Rated = Rated;
    }

    public LinkedList getMoviePaths() {
        return moviePaths;
    }

    public void setMoviePaths(LinkedList moviePaths) {
        this.moviePaths = moviePaths;
    }
    
    
    
}
