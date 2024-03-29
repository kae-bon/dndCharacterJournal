DROP TABLE IF EXISTS character_class, classes, abilityscores, characters;

CREATE TABLE classes (
	id SERIAL NOT NULL PRIMARY KEY,
	class_name VARCHAR(50) NOT NULL);

CREATE TABLE characters (
	id SERIAL NOT NULL PRIMARY KEY,
	name VARCHAR(50) NOT NULL,
	race VARCHAR(50) NOT NULL,
	level INTEGER NOT NULL,

	CONSTRAINT ck_level_constraint CHECK (level <= 20 AND level > 0));

CREATE TABLE abilityscores (
	id SERIAL NOT NULL PRIMARY KEY,
	character_id INTEGER NOT NULL,
	ability VARCHAR(20) NOT NULL,
	score INTEGER NOT NULL,

	CONSTRAINT FK_characters_char_id FOREIGN KEY (character_id) REFERENCES characters(id));
	
CREATE TABLE character_class (
	character_id integer NOT NULL,
	class_id integer NOT NULL,
	
	CONSTRAINT FK_character_id FOREIGN KEY (character_id) REFERENCES characters(id),
	CONSTRAINT FK_class_id FOREIGN KEY (class_id) REFERENCES classes(id));
	
INSERT INTO characters (name, race, level)
VALUES ('Rhys', 'Wood Elf', 12);

INSERT INTO classes (class_name) 
VALUES ('Paladin'), ('Ranger'), ('Warlock'), ('Sorcerer'),
	('Wizard'), ('Druid'), ('Fighter'), ('Barbarian'), ('Cleric'),
	('Monk'), ('Bard'), ('Rogue'), ('Artificer');

SELECT * FROM classes;
SELECT * FROM characters;



