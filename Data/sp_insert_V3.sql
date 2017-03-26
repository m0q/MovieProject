DROP PROCEDURE IF EXISTS insertOrUpdateFilm;
DELIMITER //
CREATE PROCEDURE insertOrUpdateFilm(IN fName varchar(100),IN fYear smallint(4),
									IN imdbID varchar(7), IN imdbRating float(3,1))
BEGIN
	START TRANSACTION;
	IF ((SELECT count(*) FROM Films WHERE imdb_id = imdbID) > 0) THEN
		UPDATE Films 
		SET film_name = fName, film_year = fYear, imdb_rating = imdbRating
		WHERE imdb_id = imdbID;
		SELECT 'film already exists - updated';
        COMMIT;
		-- SELECT (SELECT film_id FROM Films WHERE imdb_id = imdbID) INTO filmID;
	ELSE
		INSERT INTO Films(film_name, film_year, imdb_id, imdb_rating)
		VALUES (fName, fYear, imdbID, imdbRating);
		SELECT 'inserted new film';
        COMMIT;
		-- SELECT LAST_INSERT_ID() INTO filmID;
	END IF;
END //
DELIMITER ;

DROP PROCEDURE IF EXISTS insertOrUpdateAndLinkActor;
DELIMITER //
CREATE PROCEDURE insertOrUpdateAndLinkActor(IN aFirstNames varchar(100),IN aLastName varchar(100),
									 IN imdbID varchar(7),IN filmImdbID varchar(7))
BEGIN
	START TRANSACTION;
    
    SET @doesActorExist = ((SELECT count(*) FROM Actors WHERE imdb_id = imdbID) > 0);
	SET @doesFilmExist = ((SELECT count(*) FROM Films WHERE imdb_id = filmImdbID) > 0);
    
    IF @doesActorExist IS TRUE AND @doesFilmExist IS TRUE THEN 
      SET @actorID = (SELECT actor_id FROM Actors WHERE imdb_id = imdbID);
      SET @filmID = (SELECT film_id FROM Films WHERE imdb_id = filmImdbID);
      
      UPDATE Actors 
		SET actor_firstNames = aFirstNames, actor_lastName = aLastName
		WHERE imdb_id = imdbID;
      
      IF ((SELECT count(*) FROM Lookup_Film_Actors WHERE film_id = @filmID AND actor_id = @actorID) > 0) THEN
		SELECT 'Film and actor exist, so does the link - existing actors details were updated';
        COMMIT;
	  ELSE
		SELECT 'Film and actor exist, link doesnt so it was created - existing actors details were updated';
        INSERT INTO Lookup_Film_Actors VALUES (@filmID, @actorID);
        COMMIT;
	  END IF;
	ELSEIF @doesActorExist IS FALSE AND @doesFilmExist IS TRUE THEN
		INSERT INTO Actors(actor_firstNames, actor_lastName, imdb_id)
		VALUES (aFirstNames, aLastName, imdbID);
        
        SET @actorID = LAST_INSERT_ID();
        SET @filmID = (SELECT film_id FROM Films WHERE imdb_id = filmImdbID);
        
        INSERT INTO Lookup_Film_Actors VALUES (@filmID, @actorID);
		SELECT 'Film exists, actor and link doesnt so both were created';
        COMMIT;
	ELSE
		SELECT 'ERROR: Film with that imdb ID does not exist';
        ROLLBACK;
	END IF;
END //
DELIMITER ;


DROP PROCEDURE IF EXISTS insertOrUpdateAndLinkDirector;
DELIMITER //
CREATE PROCEDURE insertOrUpdateAndLinkDirector(IN dFirstNames varchar(100),IN dLastName varchar(100),
										IN imdbID varchar(7),IN filmImdbID varchar(7))
BEGIN
	START TRANSACTION;
    
    SET @doesDirectorExist = ((SELECT count(*) FROM Directors WHERE imdb_id = imdbID) > 0);
	SET @doesFilmExist = ((SELECT count(*) FROM Films WHERE imdb_id = filmImdbID) > 0);
    
    IF @doesDirectorExist IS TRUE AND @doesFilmExist IS TRUE THEN 
      SET @directorID = (SELECT director_id FROM Directors WHERE imdb_id = imdbID);
      SET @filmID = (SELECT film_id FROM Films WHERE imdb_id = filmImdbID);
      
      UPDATE Directors 
	  SET director_firstNames = dFirstNames, director_lastName = dLastName
	  WHERE imdb_id = imdbID;

      IF ((SELECT count(*) FROM Lookup_Film_Directors WHERE film_id = @filmID AND director_id = @directorID) > 0) THEN
		SELECT 'Film and director exist, so does the link - existing directors details were updated';
        COMMIT;
	  ELSE
		SELECT 'Film and director exist, link doesnt so it was created - existing directors details were updated';
        INSERT INTO Lookup_Film_Directors VALUES (@filmID, @directorID);
        COMMIT;
	  END IF;
	ELSEIF @doesDirectorExist IS FALSE AND @doesFilmExist IS TRUE THEN
		INSERT INTO Directors(director_firstNames, director_lastName, imdb_id)
		VALUES (dFirstNames, dLastName, imdbID);
        
        SET @directorID = LAST_INSERT_ID();
        SET @filmID = (SELECT film_id FROM Films WHERE imdb_id = filmImdbID);
        
        INSERT INTO Lookup_Film_Directors VALUES (@filmID, @directorID);
		SELECT 'Film exists, director and link doesnt so both were created';
        COMMIT;
	ELSE
		SELECT 'ERROR: Film with that imdb ID does not exist';
        ROLLBACK;
	END IF;
END //
DELIMITER ;


DROP PROCEDURE IF EXISTS DeleteAll;
DELIMITER //
CREATE PROCEDURE DeleteAll()
BEGIN
	DELETE FROM Lookup_Film_Directors;
    DELETE FROM Lookup_Film_Actors;
    DELETE FROM Films;
    DELETE FROM Directors;
    DELETE FROM Actors;
END //
DELIMITER ;


DROP PROCEDURE IF EXISTS selectAll;
DELIMITER //
CREATE PROCEDURE selectAll()
BEGIN
	SELECT * FROM Films;
	SELECT * FROM Actors;
	SELECT * FROM Directors;
	SELECT * FROM Lookup_Film_Actors;
	SELECT * FROM Lookup_Film_Directors;
END //
DELIMITER ;


