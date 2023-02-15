START TRANSACTION;

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

CREATE SCHEMA IF NOT EXISTS `semifnafusa` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `semifnafusa` ;

-- -----------------------------------------------------
-- Table `semifnafusa`.`categorias`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `semifnafusa`.`categorias` (
  `id_categoria` BIGINT NOT NULL AUTO_INCREMENT,
  `descricao` VARCHAR(50) NULL DEFAULT NULL,
  `nivel` INT NULL DEFAULT NULL,
  `ordem` VARCHAR(255) NULL DEFAULT NULL,
  `tipo` VARCHAR(255) NOT NULL,
  `id_categoriapai` BIGINT NULL DEFAULT NULL,
  PRIMARY KEY (`id_categoria`),
  INDEX `FKa7ldrr3vs8qke5ppxke9gbatu` (`id_categoriapai` ASC) VISIBLE,
  CONSTRAINT `FKa7ldrr3vs8qke5ppxke9gbatu`
    FOREIGN KEY (`id_categoriapai`)
    REFERENCES `semifnafusa`.`categorias` (`id_categoria`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `semifnafusa`.`cofres`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `semifnafusa`.`cofres` (
  `id_cofre` BIGINT NOT NULL AUTO_INCREMENT,
  `descricao` VARCHAR(30) NULL DEFAULT NULL,
  `saldo_atual` DECIMAL(19,2) NULL DEFAULT NULL,
  `saldo_inicial` DECIMAL(19,2) NULL DEFAULT NULL,
  `sigla` VARCHAR(2) NULL DEFAULT NULL,
  PRIMARY KEY (`id_cofre`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `semifnafusa`.`fornecedores`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `semifnafusa`.`fornecedores` (
  `id_fornecedor` BIGINT NOT NULL AUTO_INCREMENT,
  `bairro` VARCHAR(30) NULL DEFAULT NULL,
  `celular` VARCHAR(15) NULL DEFAULT NULL,
  `cnpj` VARCHAR(255) NULL DEFAULT NULL,
  `complemento_end` VARCHAR(30) NULL DEFAULT NULL,
  `email` VARCHAR(100) NULL DEFAULT NULL,
  `nome` VARCHAR(50) NULL DEFAULT NULL,
  `nome_empresarial` VARCHAR(100) NULL DEFAULT NULL,
  `nome_responsavel` VARCHAR(100) NULL DEFAULT NULL,
  `numero_end` VARCHAR(6) NULL DEFAULT NULL,
  `rua` VARCHAR(100) NULL DEFAULT NULL,
  `telefone` VARCHAR(15) NULL DEFAULT NULL,
  PRIMARY KEY (`id_fornecedor`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `semifnafusa`.`hibernate_sequence`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `semifnafusa`.`hibernate_sequence` (
  `next_val` BIGINT NULL DEFAULT NULL)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `semifnafusa`.`subcategorias`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `semifnafusa`.`subcategorias` (
  `id_subcategoria` BIGINT NOT NULL AUTO_INCREMENT,
  `descricao` VARCHAR(50) NULL DEFAULT NULL,
  `id_categoria` BIGINT NOT NULL,
  PRIMARY KEY (`id_subcategoria`),
  INDEX `FKsbisvbwa0a367eixgq6mol3x1` (`id_categoria` ASC) VISIBLE,
  CONSTRAINT `FKsbisvbwa0a367eixgq6mol3x1`
    FOREIGN KEY (`id_categoria`)
    REFERENCES `semifnafusa`.`categorias` (`id_categoria`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `semifnafusa`.`periodos`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `semifnafusa`.`periodos` (
  `id_periodo` BIGINT NOT NULL AUTO_INCREMENT,
  `encerrado` BIT(1) NULL DEFAULT NULL,
  `fim` DATE NOT NULL,
  `inicio` DATE NOT NULL,
  PRIMARY KEY (`id_periodo`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `semifnafusa`.`movimentos`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `semifnafusa`.`movimentos` (
  `id_movimento` BIGINT NOT NULL AUTO_INCREMENT,
  `data` DATE NOT NULL,
  `descricao` VARCHAR(255) NULL DEFAULT NULL,
  `documento` VARCHAR(20) NULL DEFAULT NULL,
  `reducao` BIT(1) NULL DEFAULT NULL,
  `valor` DECIMAL(19,2) NOT NULL,
  `id_cofre` BIGINT NOT NULL,
  `id_periodo` BIGINT NULL DEFAULT NULL,
  `id_subcategoria` BIGINT NOT NULL,
  PRIMARY KEY (`id_movimento`),
  INDEX `FKs28i5w7tb3c3cmbxt64ixjvxm` (`id_cofre` ASC) VISIBLE,
  INDEX `FKoa18anvtyphpss1fy0f3nj0y1` (`id_periodo` ASC) VISIBLE,
  INDEX `FK6edbt2okr9xybfa83w3hqwfo0` (`id_subcategoria` ASC) VISIBLE,
  CONSTRAINT `FK6edbt2okr9xybfa83w3hqwfo0`
    FOREIGN KEY (`id_subcategoria`)
    REFERENCES `semifnafusa`.`subcategorias` (`id_subcategoria`),
  CONSTRAINT `FKoa18anvtyphpss1fy0f3nj0y1`
    FOREIGN KEY (`id_periodo`)
    REFERENCES `semifnafusa`.`periodos` (`id_periodo`),
  CONSTRAINT `FKs28i5w7tb3c3cmbxt64ixjvxm`
    FOREIGN KEY (`id_cofre`)
    REFERENCES `semifnafusa`.`cofres` (`id_cofre`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `semifnafusa`.`usuario`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `semifnafusa`.`usuario` (
  `id` BIGINT NOT NULL,
  `email` VARCHAR(255) NULL DEFAULT NULL,
  `login` VARCHAR(255) NULL DEFAULT NULL,
  `nomecompleto` VARCHAR(255) NULL DEFAULT NULL,
  `permissao` VARCHAR(255) NULL DEFAULT NULL,
  `senha` VARCHAR(255) NULL DEFAULT NULL,
  `telefone` VARCHAR(255) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `semifnafusa`.`pendencias`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `semifnafusa`.`pendencias` (
  `id_pendencia` BIGINT NOT NULL AUTO_INCREMENT,
  `data_emissao` DATE NOT NULL,
  `data_recebimento` DATE NULL DEFAULT NULL,
  `data_vencimento` DATE NULL DEFAULT NULL,
  `desconto` DECIMAL(19,2) NULL DEFAULT NULL,
  `juros` DECIMAL(19,2) NULL DEFAULT NULL,
  `motivo_baixa` VARCHAR(255) NULL DEFAULT NULL,
  `motivo_emissao` VARCHAR(255) NOT NULL,
  `numero` VARCHAR(10) NULL DEFAULT NULL,
  `parcela` VARCHAR(3) NULL DEFAULT NULL,
  `percentual_juros_mes` FLOAT NULL DEFAULT NULL,
  `situacao` VARCHAR(255) NULL DEFAULT NULL,
  `total` DECIMAL(19,2) NOT NULL,
  `valor` DECIMAL(19,2) NOT NULL,
  `id` BIGINT NOT NULL,
  PRIMARY KEY (`id_pendencia`),
  INDEX `FK2awypmp7nhvsg4jx93xowks6` (`id` ASC) VISIBLE,
  CONSTRAINT `FK2awypmp7nhvsg4jx93xowks6`
    FOREIGN KEY (`id`)
    REFERENCES `semifnafusa`.`usuario` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 2
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `semifnafusa`.`role`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `semifnafusa`.`role` (
  `nome_role` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`nome_role`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `semifnafusa`.`socios`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `semifnafusa`.`socios` (
  `id_socio` BIGINT NOT NULL AUTO_INCREMENT,
  `bairro` VARCHAR(30) NULL DEFAULT NULL,
  `celular` VARCHAR(15) NULL DEFAULT NULL,
  `complemento_end` VARCHAR(30) NULL DEFAULT NULL,
  `cpf` VARCHAR(255) NULL DEFAULT NULL,
  `email` VARCHAR(100) NULL DEFAULT NULL,
  `nascimento` DATE NULL DEFAULT NULL,
  `nome` VARCHAR(50) NULL DEFAULT NULL,
  `nome_mae` VARCHAR(45) NULL DEFAULT NULL,
  `numero_end` VARCHAR(6) NULL DEFAULT NULL,
  `password` VARCHAR(100) NULL DEFAULT NULL,
  `rua` VARCHAR(100) NULL DEFAULT NULL,
  `sobrenome` VARCHAR(100) NULL DEFAULT NULL,
  `telefone` VARCHAR(15) NULL DEFAULT NULL,
  `username` VARCHAR(50) NULL DEFAULT NULL,
  PRIMARY KEY (`id_socio`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `semifnafusa`.`usuario_roles`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `semifnafusa`.`usuario_roles` (
  `usuario_id` BIGINT NOT NULL,
  `role_id` VARCHAR(255) NOT NULL,
  INDEX `FK3t77lxrs76nthhni13ctc7dlj` (`role_id` ASC) VISIBLE,
  INDEX `FKqblnumndby0ftm4c7sg6uso6p` (`usuario_id` ASC) VISIBLE,
  CONSTRAINT `FK3t77lxrs76nthhni13ctc7dlj`
    FOREIGN KEY (`role_id`)
    REFERENCES `semifnafusa`.`role` (`nome_role`),
  CONSTRAINT `FKqblnumndby0ftm4c7sg6uso6p`
    FOREIGN KEY (`usuario_id`)
    REFERENCES `semifnafusa`.`usuario` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

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

COMMIT;
