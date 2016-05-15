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

import java.util.LinkedList;

/**
 *
 * @author Löffler David, Szeles Marek
 */
public class User {
    
    final private int Id;
    final private String Nickname;
    final private boolean isAdmin;
    private Movie[] Favorites;
    private Movie[] Rated;
    private LinkedList moviePaths;

    public User(int id, String Nickname, boolean isAdmin) {
        this.Id = id;
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

    public int getId() {
        return Id;
    }

    public String getNickname() {
        return Nickname;
    }

    public boolean isIsAdmin() {
        return isAdmin;
    }
    
    
    
}
