START TRANSACTION;

drop database if exists
testesp;

create database testesp;

use testesp;

#DROP TABLE IF EXISTS
	#movimentos,
	#cofres,pendencias,
	#subcategorias,categorias,periodos,
    #fornecedores,socios,
    #autorizacoes,persistent_logins,usuarios,
    #clubes;

DROP EVENT IF EXISTS atTotalPendenciaDiariamente;
DROP EVENT IF EXISTS delRememberMe;

CREATE TABLE clubes (
  id_clube BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  nome VARCHAR(50) NULL,
  cnpj VARCHAR(14) NULL,
  email VARCHAR(100) NULL,
  telefone VARCHAR(10) NULL,
  celular VARCHAR(11) NULL,
  rua VARCHAR(100) NULL,
  numero_end VARCHAR(6) NULL,
  complemento_end VARCHAR(30) NULL,
  bairro VARCHAR(30) NULL,
  PRIMARY KEY(id_clube)
);

CREATE TABLE usuarios (
  id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  username VARCHAR(50) UNIQUE NULL,
  password VARCHAR(100) NULL,
  ativo BOOL NULL,
  nome VARCHAR(50) NULL,
  sobrenome VARCHAR(100) NULL,
  email VARCHAR(100) NULL,
  id_clube BIGINT UNSIGNED NULL,
  PRIMARY KEY(id),
  FOREIGN KEY(id_clube)
    REFERENCES clubes(id_clube)
      ON DELETE SET NULL
      ON UPDATE CASCADE
);

CREATE TABLE autorizacoes (
  id_usuario BIGINT UNSIGNED NOT NULL,
  autorizacao VARCHAR(50) NOT NULL,
  PRIMARY KEY(id_usuario, autorizacao),
  FOREIGN KEY(id_usuario)
    REFERENCES usuarios(id)
      ON DELETE CASCADE
      ON UPDATE CASCADE
);

CREATE TABLE persistent_logins (
  series VARCHAR(64) NOT NULL,
  username VARCHAR(50) NULL,
  token VARCHAR(64) NULL,
  last_used TIMESTAMP NULL,
  PRIMARY KEY(series),
    FOREIGN KEY(username)
    REFERENCES usuarios(username)
      ON DELETE CASCADE
      ON UPDATE NO ACTION
);

CREATE TABLE socios (
  id_socio BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  nome VARCHAR(50) NULL,
  email VARCHAR(100) NULL,
  telefone VARCHAR(15) NULL,
  celular VARCHAR(15) NULL,
  rua VARCHAR(100) NULL,
  numero_end VARCHAR(6) NULL,
  complemento_end VARCHAR(30) NULL,
  bairro VARCHAR(30) NULL,
  id_clube BIGINT UNSIGNED NOT NULL,
  sobrenome VARCHAR(100) NULL,
  cpf VARCHAR(11) NULL,
  nome_mae VARCHAR(45) NULL,
  nascimento DATE NULL,
  PRIMARY KEY(id_socio),
  FOREIGN KEY(id_clube)
    REFERENCES clubes(id_clube)
      ON DELETE CASCADE
      ON UPDATE CASCADE
);

CREATE TABLE fornecedores (
  id_fornecedor BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  nome VARCHAR(50) NULL,
  email VARCHAR(100) NULL,
  telefone VARCHAR(15) NULL,
  celular VARCHAR(15) NULL,
  rua VARCHAR(100) NULL,
  numero_end VARCHAR(6) NULL,
  complemento_end VARCHAR(30) NULL,
  bairro VARCHAR(30) NULL,
  nome_empresarial VARCHAR(100) NULL,
  cnpj VARCHAR(14) NULL,
  nome_responsavel VARCHAR(100) NULL,
  id_clube BIGINT UNSIGNED NOT NULL,
  PRIMARY KEY(id_fornecedor),
  FOREIGN KEY(id_clube)
    REFERENCES clubes(id_clube)
      ON DELETE CASCADE
      ON UPDATE CASCADE
);

CREATE TABLE cofres (
  id_cofre BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  sigla VARCHAR(2) NULL,
  descricao VARCHAR(30) NULL,
  saldo_inicial DECIMAL(9,2) NULL,
  saldo_atual DECIMAL(9,2) NULL,
  id_clube BIGINT UNSIGNED NOT NULL,
  PRIMARY KEY(id_cofre),
  FOREIGN KEY(id_clube)
    REFERENCES clubes(id_clube)
      ON DELETE CASCADE
      ON UPDATE CASCADE
);

CREATE TABLE categorias (
  idCategoria BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  tipo CHAR NULL,
  descricao VARCHAR(50) NULL,
  nivel INTEGER UNSIGNED NULL,
  ordem VARCHAR(255) NULL,
  idCategoriaPai BIGINT UNSIGNED NULL,
  id_clube BIGINT UNSIGNED NOT NULL,
  PRIMARY KEY(idCategoria),
  FOREIGN KEY(id_clube)
    REFERENCES clubes(id_clube)
      ON DELETE CASCADE
      ON UPDATE CASCADE,
  FOREIGN KEY(idCategoriaPai)
    REFERENCES categorias(idCategoria)
      ON DELETE CASCADE
      ON UPDATE CASCADE
);

CREATE TABLE subcategorias (
  idSubcategoria BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  descricao VARCHAR(50) NULL,
  idCategoria BIGINT UNSIGNED NOT NULL,
  PRIMARY KEY(idSubcategoria),
  FOREIGN KEY(idCategoria)
    REFERENCES categorias(idCategoria)
      ON DELETE CASCADE
      ON UPDATE CASCADE
);

CREATE TABLE pendencias (
  id_pendencia BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  id_socio BIGINT UNSIGNED NOT NULL,
  motivo_emissao CHAR NULL,
  numero VARCHAR(10) NULL,
  parcela VARCHAR(3) NULL,
  data_emissao DATE NULL,
  data_vencimento DATE NULL,
  valor DECIMAL(9,2) NULL,
  desconto DECIMAL(9,2) NULL,
  juros DECIMAL(9,2) NULL,
  total DECIMAL(9,2) NULL,
  percentual_juros_mes FLOAT NULL,
  situacao CHAR NULL,
  data_recebimento DATE NULL,
  motivo_baixa CHAR NULL,
  id_clube BIGINT UNSIGNED NOT NULL,
  PRIMARY KEY(id_pendencia),
  FOREIGN KEY(id_clube)
    REFERENCES clubes(id_clube)
      ON DELETE CASCADE
      ON UPDATE CASCADE,
  FOREIGN KEY(id_socio)
    REFERENCES socios(id_socio)
      ON DELETE CASCADE
      ON UPDATE CASCADE
);

CREATE TABLE periodos (
  id_periodo BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  inicio DATE NULL,
  fim DATE NULL,
  encerrado BOOL NULL,
  id_clube BIGINT UNSIGNED NOT NULL,
  PRIMARY KEY(id_periodo),
  FOREIGN KEY(id_clube)
    REFERENCES clubes(id_clube)
      ON DELETE CASCADE
      ON UPDATE CASCADE
);

CREATE TABLE movimentos (
  id_movimento BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  id_periodo BIGINT UNSIGNED NOT NULL,
  idSubcategoria BIGINT UNSIGNED NOT NULL,
  data DATE NULL,
  valor DECIMAL(9,2) NULL,
  documento VARCHAR(20) NULL,
  descricao VARCHAR(255) NULL,
  reducao BOOL NULL,
  id_cofre BIGINT UNSIGNED NOT NULL,
  PRIMARY KEY(id_movimento),
  FOREIGN KEY(id_cofre)
    REFERENCES cofres(id_cofre)
      ON DELETE CASCADE
      ON UPDATE CASCADE,
      FOREIGN KEY(idSubcategoria)
    REFERENCES subcategorias(idSubcategoria)
      ON DELETE CASCADE
      ON UPDATE CASCADE,
  FOREIGN KEY(id_periodo)
    REFERENCES periodos(id_periodo)
      ON DELETE CASCADE
      ON UPDATE CASCADE
);

DELIMITER $$
CREATE TRIGGER atSaldoOnInsertMovimento
BEFORE INSERT ON movimentos
FOR EACH ROW
BEGIN
	IF NEW.reducao THEN
		UPDATE cofres
        SET saldo_atual = saldo_atual - NEW.valor
        WHERE id_cofre = NEW.id_cofre;
    ELSE
		UPDATE cofres
        SET saldo_atual = saldo_atual + NEW.valor
        WHERE id_cofre = NEW.id_cofre;
    END IF;
END;$$
DELIMITER ;

DELIMITER $$
CREATE TRIGGER atSaldoOnDeleteMovimento
BEFORE DELETE ON movimentos
FOR EACH ROW
BEGIN
	IF OLD.reducao THEN
		UPDATE cofres
        SET saldo_atual = saldo_atual + OLD.valor
        WHERE id_cofre = OLD.id_cofre;
    ELSE
		UPDATE cofres
        SET saldo_atual = saldo_atual - OLD.valor
        WHERE id_cofre = OLD.id_cofre;
    END IF;
END;$$
DELIMITER ;

DELIMITER $$
CREATE TRIGGER atSaldoOnUpdateMovimento
BEFORE UPDATE ON movimentos
FOR EACH ROW
BEGIN
	IF OLD.reducao THEN
		UPDATE cofres
        SET saldo_atual = saldo_atual + OLD.valor
        WHERE id_cofre = OLD.id_cofre;
    ELSE
		UPDATE cofres
        SET saldo_atual = saldo_atual - OLD.valor
        WHERE id_cofre = OLD.id_cofre;
    END IF;
	IF NEW.reducao THEN
		UPDATE cofres
        SET saldo_atual = saldo_atual - NEW.valor
        WHERE id_cofre = NEW.id_cofre;
    ELSE
		UPDATE cofres
        SET saldo_atual = saldo_atual + NEW.valor
        WHERE id_cofre = NEW.id_cofre;
    END IF;
END;$$
DELIMITER ;

DELIMITER $$
CREATE EVENT atTotalPendenciaDiariamente
ON SCHEDULE EVERY 1 DAY STARTS (CURRENT_DATE + INTERVAL 1 DAY + INTERVAL 1 MINUTE) DO
BEGIN
	UPDATE pendencias
    SET
		total = total - juros,
		juros = valor * (percentual_juros_mes/30/100) * DATEDIFF(CURRENT_DATE,data_vencimento),
        total = total + juros
    WHERE
		data_recebimento IS NULL
        AND data_vencimento < CURRENT_DATE;
END;$$
DELIMITER ;

DELIMITER $$
CREATE EVENT delRememberMe
ON SCHEDULE EVERY 1 DAY STARTS (CURRENT_DATE + INTERVAL 1 DAY + INTERVAL 12 HOUR) DO
BEGIN
	DELETE FROM persistent_logins
    WHERE
		(current_timestamp - last_used) > (120960 * 1000);
END;$$
DELIMITER ;

COMMIT;