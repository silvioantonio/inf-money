CREATE TABLE categoria (
	codigo SERIAL,
	nome character varying(50) NOT NULL,
	PRIMARY KEY (codigo)
);

INSERT INTO categoria (nome) values ('Lazer');
INSERT INTO categoria (nome) values ('Alimentacao');
INSERT INTO categoria (nome) values ('Farmacia');
INSERT INTO categoria (nome) values ('Supermercado');
INSERT INTO categoria (nome) values ('Outros');


