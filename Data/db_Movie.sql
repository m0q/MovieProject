CREATE SCHEMA `db_Movie`;

CREATE TABLE `db_Movie`.`Films`(
	`film_id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
	`film_name` nvarchar(100) NOT NULL,
	`film_year` SMALLINT(4) UNSIGNED NOT NULL,
	`imdb_id` varchar(7) NOT NULL,
	PRIMARY KEY(`film_id`)
);

CREATE TABLE `db_Movie`.`Directors`(
	`director_id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
	`director_firstNames` nvarchar(100),
	`director_lastName` nvarchar(100) NOT NULL,
	`imdb_id` varchar(7) NOT NULL,
	PRIMARY KEY(`director_id`)
);

CREATE TABLE `db_Movie`.`Actors`(
	`actor_id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
	`actor_firstNames` nvarchar(100),
	`actor_lastName` nvarchar(100) NOT NULL,
	`imdb_id` varchar(7) NOT NULL,
	PRIMARY KEY(`actor_id`)
);

CREATE TABLE `db_Movie`.`Lookup_Film_Actors`(   
	`film_id` INT UNSIGNED NOT NULL,
	`actor_id` INT UNSIGNED NOT NULL,
	FOREIGN KEY(`film_id`)
	  REFERENCES `db_Movie`.`Films` (`film_id`), 
	FOREIGN KEY(`actor_id`) 
	 REFERENCES `db_Movie`.`Actors` (`actor_id`)
);

CREATE TABLE `db_Movie`.`Lookup_Film_Directors`(  
	`film_id` INT UNSIGNED NOT NULL,
	`director_id` INT UNSIGNED NOT NULL,
	FOREIGN KEY(`film_id`)
	  REFERENCES `db_Movie`.`Films` (`film_id`), 
	FOREIGN KEY(`director_id`) 
	 REFERENCES `db_Movie`.`Directors` (`director_id`)
);

#the line below makes a change to table 'film' by adding a column 'imdb_rating'
ALTER TABLE `db_Movie`.`Films` ADD `imdb_rating` FLOAT(3,1) UNSIGNED NOT NULL;

/******copy in everything below AFTER you execute eveything above*****/

#set the db we're looking at the the one created above
USE db_Movie;

#since the film_id is auto increment we dont need to explictly add a value for it
INSERT into Films (film_name, film_year, imdb_id, imdb_rating) 
VALUES ("Spider-Man", 2002, "0145487", 7.3),
	   ("Batman v Superman: Dawn of Justice", 2016, "2975590", 7.0),
	   ("Daredevil", 2003, "3322312", 8.8);

INSERT into Actors (actor_firstNames, actor_lastName,imdb_id) 
VALUES ("Tobey", "Maguire", "0001497"),
       ("Henry", "Cavill", "0147147"),
	   ("Ben", "Affleck", "0000255");

INSERT into Directors (director_firstNames, director_lastName,imdb_id) 
VALUES ("Sam", "Raimi", "0000600"),
	   ("Zack", "Snyder", "0811583"),
	   ("Mark Steven", "Johnson", "0425756");

#mapping actors to movies
INSERT into Lookup_Film_Actors 
VALUES (1,1), (2,2), (2,3), (3,3);

INSERT into Lookup_Film_Directors
VALUES (1,1), (2,2), (3,3);

#test query - returns all movies that 'Ben' stars in
SELECT f.film_name, f.film_year, f.imdb_id, f.imdb_rating 
FROM Films f 
INNER JOIN Lookup_Film_Actors e ON e.film_id = f.film_id
INNER JOIN Actors a ON a.actor_id = e.actor_id 
WHERE a.actor_firstNames = "Ben";

#test query - returns all movies (including film_id) that 'Mark Steven' stars in
SELECT f.* 
FROM Films f 
INNER JOIN Lookup_Film_Directors e ON e.film_id = f.film_id
INNER JOIN Directors d ON d.director_id = e.director_id 
WHERE d.director_firstNames = "Mark Steven";

#test query - How many movies in our DB have an IMDB rating above 7.0?
SELECT COUNT(*) AS 'Film Count' 
FROM Films f
WHERE f.imdb_rating > 7.0;

#stored procedure
#returns 3 result sets - unarchived film, actors, and directors 
#that have an ID matching the passed parameter 'fid'

#DELIMITER is used to change where MySQL looks for the end of a statement
#so instead of ending at ; the DELIMITER says this stored procedure doesnt end til you see //
DROP PROCEDURE IF EXISTS getFilmDetails; 
DELIMITER //
CREATE PROCEDURE getFilmDetails (IN fid INT)
BEGIN
  SELECT fs.film_id, fs.film_name, fs.film_year, fs.imdb_id, fs.imdb_rating
  FROM Films fs
  WHERE fs.film_id = fid 
  	AND fs.is_archived = FALSE;
 
  SELECT ac.actor_id, ac.actor_firstNames, ac.actor_lastName, ac.imdb_id
  FROM Actors ac
  JOIN Lookup_Film_Actors lfa
            ON ac.actor_id = lfa.actor_id
  WHERE lfa.film_id = fid;
 
  SELECT di.director_id, di.director_firstNames, di.director_lastName, di.imdb_id
  FROM Directors di
  JOIN Lookup_Film_Directors lfd
            ON di.director_id = lfd.director_id
  WHERE lfd.film_id = fid;
END //
DELIMITER ;

#add a film is_archived boolean
ALTER TABLE Films ADD `is_archived` BOOLEAN NOT NULL DEFAULT FALSE;

#archive film with film_id = 2
UPDATE Films fs SET fs.is_archived = TRUE WHERE fs.film_id = 2;

#test: check if stored procedure works 
#should only return 2 result sets since film_id 2 is archived
CALL getFilmDetails(2); 


