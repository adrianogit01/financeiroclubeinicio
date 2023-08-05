use semifnafusa;

CREATE TABLE IF NOT EXISTS `semifnafusa`.`role` (
  `nome_role` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`nome_role`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

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
