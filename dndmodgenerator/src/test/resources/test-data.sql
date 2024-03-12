START TRANSACTION;

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
VALUES ('Rhys', 'Wood Elf', 12),
('Sylve', 'Tiefling', 10),
('Neme', 'Satyr', 5),
('Chicken', 'Tiefling', 3),
('Rinn', 'Drow Elf', 12);

INSERT INTO classes (class_name)
VALUES ('paladin'), ('ranger'), ('warlock'), ('sorcerer'),
	('wizard'), ('druid'), ('fighter'), ('barbarian'), ('cleric'),
	('monk'), ('bard'), ('rogue'), ('artificer');

INSERT INTO character_class (character_id, class_id)
VALUES (1, (SELECT id FROM classes WHERE class_name = 'paladin')),
(2, (SELECT id FROM classes WHERE class_name = 'sorcerer')),
(3, (SELECT id FROM classes WHERE class_name = 'sorcerer')),
(3, (SELECT id FROM classes WHERE class_name = 'druid'));

COMMIT;