DROP PROCEDURE IF EXISTS associateActor; 
DELIMITER //
CREATE PROCEDURE associateActor (IN filmImdb_id varchar(7), actorImdb_id varchar(7))
BEGIN  
	START TRANSACTION;
		SET @filmID = (SELECT film_id FROM Films WHERE imdb_id = filmImdb_id);
		SET @actorID = (SELECT actor_id FROM Actors WHERE imdb_id = actorImdb_id);

		DELETE FROM Lookup_Film_Actors WHERE film_id = @filmID;

		INSERT INTO Lookup_Film_Actors(film_id, actor_id) VALUES (@filmID, @actorID);
		COMMIT;
END //
DELIMITER ;

DROP PROCEDURE IF EXISTS associateDirector; 
DELIMITER //
CREATE PROCEDURE associateDirector (IN filmImdb_id varchar(7), directorImdb_id varchar(7))
BEGIN  
	START TRANSACTION;
		SET @filmID = (SELECT film_id FROM Films WHERE imdb_id = filmImdb_id);
		SET @directorID = (SELECT director_id FROM Directors WHERE imdb_id = directorImdb_id);

		DELETE FROM Lookup_Film_Directors WHERE film_id = @filmID;

		INSERT INTO Lookup_Film_Directors(film_id, director_id) VALUES (@filmID, @directorID);
		COMMIT;
END //

DROP PROCEDURE IF EXISTS insertOrUpdateActor;
DELIMITER //
CREATE PROCEDURE insertOrUpdateActor(IN aFirstNames varchar(100),IN aLastName varchar(100),
									 IN imdbID varchar(7))
BEGIN
	START TRANSACTION;
    
    SET @doesActorExist = ((SELECT count(*) FROM Actors WHERE imdb_id = imdbID) > 0);
    
    IF @doesActorExist IS TRUE THEN 
		SET @actorID = (SELECT actor_id FROM Actors WHERE imdb_id = imdbID);
		
		UPDATE Actors 
		SET actor_firstNames = aFirstNames, actor_lastName = aLastName
		WHERE imdb_id = imdbID;
        
        SELECT 'An actor with that ID already exists, their details were updated';
		  
		COMMIT;
	ELSEIF @doesActorExist IS FALSE THEN
		INSERT INTO Actors(actor_firstNames, actor_lastName, imdb_id)
		VALUES (aFirstNames, aLastName, imdbID);
        
		SELECT 'A new actor has successfully been added to the database';
        COMMIT;
	ELSE
		SELECT '(rollback) ERROR: Something went wrong';
        ROLLBACK;
	END IF;
END //
DELIMITER ;


DROP PROCEDURE IF EXISTS insertOrUpdateDirector;
DELIMITER //
CREATE PROCEDURE insertOrUpdateDirector(IN dFirstNames varchar(100),IN dLastName varchar(100),
										IN imdbID varchar(7))
BEGIN
	START TRANSACTION;
    
    SET @doesDirectorExist = ((SELECT count(*) FROM Directors WHERE imdb_id = imdbID) > 0);
    
    IF @doesDirectorExist IS TRUE THEN 
		SET @directorID = (SELECT director_id FROM Directors WHERE imdb_id = imdbID);
      
		UPDATE Directors 
		SET director_firstNames = dFirstNames, director_lastName = dLastName
		WHERE imdb_id = imdbID;

		SELECT 'A director with that ID already exists, their details were updated';
		  
		COMMIT;
	ELSEIF @doesDirectorExist IS FALSE THEN
		INSERT INTO Directors(director_firstNames, director_lastName, imdb_id)
		VALUES (dFirstNames, dLastName, imdbID);
        
        SELECT 'A new director has successfully been added to the database';
        COMMIT;
	ELSE
		SELECT '(rollback) ERROR: Something went wrong';
        ROLLBACK;
	END IF;
END //
DELIMITER ;


