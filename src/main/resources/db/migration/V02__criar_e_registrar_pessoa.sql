
CREATE TABLE pessoa (
	id SERIAL,
	nome varchar(50) NOT NULL,
	numero varchar(4) NOT NULL,
	complemento varchar(20),
	bairro varchar(20) NOT NULL,
	cep varchar(8) NOT NULL,
	cidade varchar(20) NOT NULL,
	estado varchar(20) NOT NULL,
	ativo boolean,
	PRIMARY KEY (id)
);

INSERT INTO pessoa (nome, numero, complemento, bairro, cep, cidade, estado, ativo) VALUES ('Teste 01', '01', 'casa 01', 'bairro teste 1', '11000000', 'cidade teste 01', 'estado teste 01', true);

