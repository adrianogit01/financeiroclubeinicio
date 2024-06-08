use semifnafusa;

CREATE TABLE IF NOT EXISTS `semifnafusa`.`role` (
  `nome_role` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`nome_role`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;
