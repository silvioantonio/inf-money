CREATE TABLE lancamento (
	codigo SERIAL,
	descricao VARCHAR(50) NOT NULL,
	data_vencimento DATE NOT NULL,
	data_pagamento DATE,
	valor numeric(10,2) NOT NULL,
	observacao VARCHAR(100),
	tipo VARCHAR(20) NOT NULL,
	codigo_categoria bigint NOT NULL,
	codigo_pessoa bigint NOT NULL,
	PRIMARY KEY (codigo),
	FOREIGN KEY (codigo_categoria) REFERENCES categoria (codigo),
	FOREIGN KEY (codigo_pessoa) REFERENCES pessoa (id)
);

INSERT INTO lancamento (descricao, data_vencimento, data_pagamento, valor, observacao, tipo, codigo_categoria, codigo_pessoa) VALUES ('Salario mensal', '2017-06-10', null, 6500.00, 'Distribuiçao de lucros', 'RECEITA', 1, 1);
INSERT INTO lancamento (descricao, data_vencimento, data_pagamento, valor, observacao, tipo, codigo_categoria, codigo_pessoa) VALUES ('Bahamas', '2017-06-10', '2017-02-10', 100.32, null, 'DESPESA', 3, 1);

