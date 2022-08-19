# aou-microservices-movies
Model :
 - A movie has : release year, original title, french title
 - An artist has : first and last name, birth date
 - an artist can act on many movies
 - an artist can direct many movies

We have the tables :
Movie
Artist
Acting (artist_id, movie_id)
Directing (artist_id, movie_id)
