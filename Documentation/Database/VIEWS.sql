
CREATE VIEW movieactors AS SELECT movie.id_movie, actor.name, actor.surname, actor.year, actor.description 
FROM movie, actor, plays 
WHERE movie.id_movie = plays.id_movie AND plays.id_actor = actor.id_actor;

CREATE VIEW moviedirectors AS SELECT movie.id_movie, director.name, director.surname, director.year, director.description
FROM movie, director, shoots
WHERE movie.id_movie = shoots.id_movie AND shoots.id_director = director.id_director;

CREATE VIEW moviescenarist AS SELECT movie.id_movie, scenarist.name, scenarist.surname, scenarist.year, scenarist.description
FROM movie, scenarist, screenplay
WHERE movie.id_movie = screenplay.id_movie AND screenplay.id_scenarist = scenarist.id_scenarist;

CREATE VIEW movierating AS SELECT movie.id_movie, AVG(rating.stars)
FROM movie, rating, rating_related
WHERE movie.id_movie = rating_related.id_movie AND rating.id_rate = rating_related.id_rate
GROUP BY movie.id_movie;

CREATE VIEW usermovies AS SELECT users.id_user, movie.id_movie
FROM users, movie, rating, rating_related
WHERE users.id_user = rating.id_user AND rating.id_rate = rating_related.id_rate AND rating_related.id_movie = movie.id_movie;
