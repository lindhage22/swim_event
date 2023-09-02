DROP TABLE IF EXISTS swimmer_event;
DROP TABLE IF EXISTS event;
DROP TABLE IF EXISTS swimmer;
DROP TABLE IF EXISTS location;

CREATE TABLE location(
 location_id int NOT NULL AUTO_INCREMENT,
 pool_name varchar(256) NOT NULL,
 street_address varchar (128) NOT NULL,
 city varchar (60),
 state varchar (20),
 zip varchar(20),
 phone varchar(30),
 PRIMARY KEY(location_id)
  
);

CREATE TABLE swimmer(
 swimmer_id int NOT NULL AUTO_INCREMENT,
 location_id int NULL,
 name varchar (60) NOT NULL,
 age int,
 school varchar (300),
 PRIMARY KEY (swimmer_id),
 FOREIGN KEY (location_id) REFERENCES location (location_id)ON DELETE CASCADE
 );
 
CREATE TABLE event (
 event_id int NOT NULL AUTO_INCREMENT,
 name varchar (128),
 PRIMARY KEY (event_id)
 );
 
 CREATE TABLE swimmer_event (
  swimmer_id int NOT NULL,
  event_id int NOT NULL,
  FOREIGN KEY (swimmer_id) REFERENCES swimmer (swimmer_id) ON DELETE CASCADE, 
  FOREIGN KEY (event_id) REFERENCES event (event_id) ON DELETE CASCADE
  );