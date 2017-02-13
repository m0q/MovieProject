--currently being used
DELIMITER //
CREATE PROCEDURE getAllDetails()
BEGIN  
  SELECT fs.imdb_id AS 'Film ID'
        ,fs.film_name AS 'Film Name'
        ,fs.imdb_rating AS 'IMDB Rating'
        ,d.imdb_id AS 'Director ID'
        ,CONCAT(d.director_firstNames, ' ', d.director_lastName) AS 'Director'
        ,a.imdb_id AS 'Actor ID'
        ,CONCAT(a.actor_firstNames, ' ', a.actor_lastName) AS 'Actor'
        ,fs.film_year AS 'Year'
  FROM Films fs
  
  JOIN lookup_film_actors lfa 
    ON lfa.film_id = fs.film_id
  JOIN actors a 
    ON a.actor_id = lfa.actor_id
  
  JOIN lookup_film_directors lfd
    ON lfd.film_id = fs.film_id
  JOIN directors d
    ON d.director_id = lfd.director_id;
END//
DELIMITER ;

--alternate
DROP PROCEDURE IF EXISTS getAllDetails; 
DELIMITER //
CREATE PROCEDURE getAllDetails ()
BEGIN  
  SELECT fs.imdb_id AS 'Film ID'
      ,fs.film_name AS 'Film Name'
      ,fs.imdb_rating AS 'IMDB Rating'
      ,d.imdb_id AS 'Director ID'
      ,CONCAT(d.director_firstNames, ' ', d.director_lastName) AS 'Director'
      ,a.imdb_id AS 'Actor ID'
      ,CONCAT(a.actor_firstNames, ' ', a.actor_lastName) AS 'Actor'
      ,fs.film_year AS 'Year'
FROM Films fs, Directors d, Actors a, lookup_film_directors lfd, lookup_film_actors lfa
WHERE lfa.film_id = fs.film_id AND a.actor_id = lfa.actor_id AND
      lfd.film_id = fs.film_id AND d.director_id = lfd.director_id;
END //
DELIMITER ;
