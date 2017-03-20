/*DROP PROCEDURE IF EXISTS insertFilm;
DELIMITER //
CREATE PROCEDURE insertFilm(IN fName varchar(100),IN fYear smallint(4),
							IN imdbID varchar(7), IN imdbRating float(3,1),
							OUT filmID int (10))
BEGIN
	INSERT INTO Films(film_name, film_year, imdb_id, imdb_rating)
	VALUES (fName, fYear, imdbID, imdbRating);
	
	IF ((SELECT count(*) FROM Films WHERE imdb_id = imdbID) > 1) THEN
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Error: A Film with that ID already exists';
	ELSE
		SELECT LAST_INSERT_ID() INTO filmID;
	END IF;
END //
DELIMITER ;*/
DROP PROCEDURE IF EXISTS insertOrUpdateFilm;
DELIMITER //
CREATE PROCEDURE insertOrUpdateFilm(IN fName varchar(100),IN fYear smallint(4),
									IN imdbID varchar(7), IN imdbRating float(3,1),
									OUT filmID int (10))
BEGIN
	IF ((SELECT count(*) FROM Films WHERE imdb_id = imdbID) > 0) THEN
		UPDATE Films 
		SET film_name = fName, film_year = fYear, imdb_rating = imdbRating
		WHERE imdb_id = imdbID;

		SELECT (SELECT film_id FROM Films WHERE imdb_id = imdbID) INTO filmID;
	ELSE
		INSERT INTO Films(film_name, film_year, imdb_id, imdb_rating)
		VALUES (fName, fYear, imdbID, imdbRating);

		SELECT LAST_INSERT_ID() INTO filmID;
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
			ROLLBACK;
			SELECT (SELECT actor_id FROM Actors WHERE imdb_id = imdbID) INTO actorID;
			/*SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Error: An Actor with that ID already exists';*/
		ELSE
			SELECT LAST_INSERT_ID() INTO actorID;
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
			ROLLBACK;
			SELECT (SELECT director_id FROM Directors WHERE imdb_id = imdbID) INTO directorID;
			/*SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Error: A Director with that ID already exists';*/
		ELSE
			SELECT LAST_INSERT_ID() INTO directorID;
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
	DECLARE EXIT HANDLER FOR SQLEXCEPTION 
	BEGIN
		GET DIAGNOSTICS CONDITION 1
		@p1 = MESSAGE_TEXT;
		SELECT '0' AS 'resultCode', @p1 AS 'resultMessage';
		ROLLBACK;
	END;

	START TRANSACTION;
	
	CALL insertOrUpdateFilm(fName, fYear, fImdbID, imdbRating, @filmID);

	CALL insertActor(aFirstNames, aLastName, aImdbID, @actorID);

	CALL insertDirector(dFirstNames, dLastName, dImdbID, @directorID);

	IF ((SELECT count(*) FROM Lookup_Film_Actors WHERE film_id = @filmID AND actor_id = @actorID) > 0) AND
	   ((SELECT count(*) FROM Lookup_Film_Directors WHERE film_id = @filmID AND director_id = @directorID) > 0) THEN
	   	SELECT '-1' AS 'resultCode', 'Warning: data already exists' AS 'resultMessage';
		ROLLBACK;
	ELSE
		INSERT INTO Lookup_Film_Actors VALUES (@filmID, @actorID);
		INSERT INTO Lookup_Film_Directors VALUES (@filmID, @directorID);

		SELECT '1' AS 'resultCode', 'Success: changes committed' AS 'resultMessage';
	   	COMMIT;
	END IF;
	
END //
DELIMITER ;

CALL insertDataset('fName', '2007', '0000000', '3.4', 'aName', 'aLName', '0000000', 'dName', 'dLName', '0000000');

CALL insertFilm('FN', '2007', '0000000', '6.7', @result);

DELIMITER //
CREATE PROCEDURE selectAll()
BEGIN
select * from films where film_id > 30;
select * from actors where actor_id > 16;
select * from directors where director_id > 22;
END //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE removeNew()
BEGIN
delete from Lookup_Film_Directors where film_id > 30;
delete from Lookup_Film_Actors where film_id > 30;
delete from films where film_id > 30;
delete from actors where actor_id > 16;
delete from directors where director_id > 22;	
END //
DELIMITER ;




