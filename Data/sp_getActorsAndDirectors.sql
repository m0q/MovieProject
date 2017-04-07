DROP PROCEDURE IF EXISTS getActors; 
DELIMITER //
CREATE PROCEDURE getActors ()
BEGIN  
  SELECT imdb_id AS 'Actor ID', CONCAT(actor_firstNames, ' ', actor_lastName) AS 'Actor Name'
  FROM Actors;
END //
DELIMITER ;

DROP PROCEDURE IF EXISTS getDirectors; 
DELIMITER //
CREATE PROCEDURE getDirectors ()
BEGIN  
  SELECT imdb_id AS 'Director ID', CONCAT(director_firstNames, ' ', director_lastName) AS 'Director Name'
  FROM Directors;
END //
DELIMITER ;

DROP PROCEDURE IF EXISTS getFilms; 
DELIMITER //
CREATE PROCEDURE getFilms ()
BEGIN  
  SELECT imdb_id AS 'Film ID', film_name AS 'Film Name'
  FROM Films;
END //
DELIMITER ;