 create table people (
 	id INTEGER IDENTITY PRIMARY KEY,
 	first_name VARCHAR(50),
 	last_name VARCHAR(50),
 	car VARCHAR(50),
 	city VARCHAR(50),
 	country VARCHAR(50),
         timestamp TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
 );
 CREATE TRIGGER updateTimestamp
     AFTER UPDATE OF first_name, last_name, car, city, country ON people
     REFERENCING OLD AS OLDROW
     FOR EACH ROW
         UPDATE people 
             SET TIMESTAMP = CURRENT_TIMESTAMP
             WHERE ID = OLDROW.ID;
