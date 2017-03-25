DROP PROCEDURE IF EXISTS getAllPeople; 
DELIMITER //
CREATE PROCEDURE getAllPeople ()
BEGIN  
  SELECT imdb_id AS 'Director ID', CONCAT(director_firstNames, ' ', director_lastName) AS 'Director Name'
  FROM Directors;

  SELECT imdb_id AS 'Actor ID', CONCAT(actor_firstNames, ' ', actor_lastName) AS 'Actor Name'
  FROM Actors;
END //
DELIMITER ;
