DROP PROCEDURE IF EXISTS insertFilm;
DELIMITER //
CREATE PROCEDURE insertFilm(IN fName varchar(100),IN fYear smallint(4),
							IN imdbID varchar(7), IN imdbRating float(3,1),
							OUT filmID int (10))
BEGIN
	START TRANSACTION;
    
    INSERT INTO Films(film_name, film_year, imdb_id, imdb_rating)
	VALUES (fName, fYear, imdbID, imdbRating);
	
	IF ((SELECT count(*) FROM Films WHERE imdb_id = imdbID) > 1) THEN
		SELECT 'rollback' AS 'result';
		ROLLBACK;
	ELSE
		SELECT LAST_INSERT_ID() INTO filmID;
		SELECT 'commit' AS 'result';
		COMMIT;
	END IF;
END //
DELIMITER ;

DROP PROCEDURE IF EXISTS insertActor;
DELIMITER //
CREATE PROCEDURE insertActor(IN aFirstNames varchar(100),
							 IN aLastName varchar(100),
							 IN imdbID varchar(7),
							 OUT actorID int (10))
BEGIN
	START TRANSACTION;
    
    INSERT INTO Actors(actor_firstNames, actor_lastName, imdb_id)
	VALUES (aFirstNames, aLastName, imdbID);
	
	IF ((SELECT count(*) FROM Actors WHERE imdb_id = imdbID) > 1) THEN
		SELECT 'rollback' AS 'result';
		ROLLBACK;
	ELSE
		SELECT LAST_INSERT_ID() INTO actorID;
		SELECT 'commit' AS 'result';
		COMMIT;
	END IF;
END //
DELIMITER ;


DROP PROCEDURE IF EXISTS insertDirector;
DELIMITER //
CREATE PROCEDURE insertDirector(IN dFirstNames varchar(100),
							 IN dLastName varchar(100),
							 IN imdbID varchar(7),
							 OUT directorID int (10))
BEGIN
	START TRANSACTION;
    
    INSERT INTO Directors(director_firstNames, director_lastName, imdb_id)
	VALUES (dFirstNames, dLastName, imdbID);
	
	IF ((SELECT count(*) FROM Directors WHERE imdb_id = imdbID) > 1) THEN
		SELECT 'rollback' AS 'result';
		ROLLBACK;
	ELSE
		SELECT LAST_INSERT_ID() INTO directorID;
		SELECT 'commit' AS 'result';
		COMMIT;
	END IF;
END //
DELIMITER ;


DROP PROCEDURE IF EXISTS insertDataset;
DELIMITER //
CREATE PROCEDURE insertDataset(IN fName varchar(100),IN fYear smallint(4),
					  IN fImdbID varchar(7), IN imdbRating float(3,1),
					  IN aFirstNames varchar(100), IN aLastName varchar(100), IN aImdbID varchar(7),
					  IN dFirstNames varchar(100), IN dLastName varchar(100), IN dImdbID varchar(7))
BEGIN
	START TRANSACTION;
	
	CALL insertFilm(fName, fYear, fImdbID, imdbRating, @filmID);

	CALL insertActor(aFirstNames, aLastName, aImdbID, @actorID);

	CALL insertDirector(dFirstNames, dLastName, dImdbID, @directorID);

	IF ((SELECT count(*) FROM Lookup_Film_Actors WHERE film_id = @filmID) > 0) AND
	   ((SELECT count(*) FROM Lookup_Film_Directors WHERE film_id = @filmID) > 0) THEN

	   	SELECT 'rollback' AS 'result';
		ROLLBACK;
	ELSE
		INSERT INTO Lookup_Film_Actors VALUES (@filmID, @actorID);
		INSERT INTO Lookup_Film_Directors VALUES (@filmID, @directorID);

		SELECT 'commit' AS 'result';
	   	COMMIT;
	END IF;
	
END //
DELIMITER ;
