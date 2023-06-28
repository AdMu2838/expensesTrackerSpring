CREATE DATABASE  IF NOT EXISTS `bd_expenses_tracker` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `bd_expenses_tracker`;
-- MySQL dump 10.13  Distrib 8.0.28, for Win64 (x86_64)
--
-- Host: localhost    Database: bd_expenses_tracker
-- ------------------------------------------------------
-- Server version	8.0.28

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `tb_categorias`
--

DROP TABLE IF EXISTS `tb_categorias`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_categorias` (
  `cod_cat` int NOT NULL AUTO_INCREMENT,
  `nom_cat` varchar(100) DEFAULT NULL,
  `tipo_cat` char(1) DEFAULT NULL,
  PRIMARY KEY (`cod_cat`),
  CONSTRAINT `tb_categorias_chk_1` CHECK (((`tipo_cat` = _utf8mb4'I') or (`tipo_cat` = _utf8mb4'E')))
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_categorias`
--

LOCK TABLES `tb_categorias` WRITE;
/*!40000 ALTER TABLE `tb_categorias` DISABLE KEYS */;
INSERT INTO `tb_categorias` VALUES (1,'Alimentación','E'),(2,'Cuentas y Pagos','E'),(3,'Casa','E'),(4,'Transporte','E'),(5,'Ropa','E'),(6,'Salud e Higiene','E'),(7,'Diversión','E'),(8,'Otros gastos','E'),(9,'Salario o Nómina','I'),(10,'Honorarios','I'),(11,'Negocio Propio o Comerciante','I'),(12,'Dividendos o Participaciones','I'),(13,'Pensión','I'),(14,'Rentas de Capital','I'),(15,'Ocasional','I');
/*!40000 ALTER TABLE `tb_categorias` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_registro`
--

DROP TABLE IF EXISTS `tb_registro`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_registro` (
  `id_reg` varchar(255) NOT NULL,
  `desc_reg` varchar(255) NOT NULL,
  `impac_reg` double NOT NULL,
  `cod_cat` int DEFAULT NULL,
  `fec_reg` date NOT NULL DEFAULT (curdate()),
  `id_usu` int NOT NULL,
  PRIMARY KEY (`id_reg`,`id_usu`),
  KEY `FK_CatRegistro` (`cod_cat`),
  KEY `FK_UsuRegistro` (`id_usu`),
  CONSTRAINT `FK_CatRegistro` FOREIGN KEY (`cod_cat`) REFERENCES `tb_categorias` (`cod_cat`),
  CONSTRAINT `FK_UsuRegistro` FOREIGN KEY (`id_usu`) REFERENCES `tb_usuario` (`id_usu`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_registro`
--

LOCK TABLES `tb_registro` WRITE;
/*!40000 ALTER TABLE `tb_registro` DISABLE KEYS */;
INSERT INTO `tb_registro` VALUES ('2023-JUN-1','Propina por Año Nuevo',50,15,'2023-06-01',1),('2023-JUN-10','Pasajes viaje a Bolivia',-250,4,'2023-06-08',1),('2023-JUN-11','Cobro de préstamo',100,14,'2023-06-10',1),('2023-JUN-2','Salida al Mall Aventura',-60,7,'2023-06-04',1),('2023-JUN-3','Venta de peluches',150,15,'2023-06-05',1),('2023-JUN-4','Salida al Mall Real Plaza',-40,7,'2023-06-05',1),('2023-JUN-5','Canchuelo de ayudante de electricista',300,15,'2023-06-07',1),('2023-JUN-6','Canchuelo de Tutor de Excel',75,15,'2023-06-09',1),('2023-JUN-7','Pago de cuota de préstamo Banco el Amigo',-450,2,'2023-06-06',1),('2023-JUN-8','Cena familiar',-150,1,'2023-06-07',1),('2023-JUN-9','Pasajes viaje a Chile',-250,4,'2023-06-08',1);
/*!40000 ALTER TABLE `tb_registro` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_usuario`
--

DROP TABLE IF EXISTS `tb_usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_usuario` (
  `id_usu` int NOT NULL AUTO_INCREMENT,
  `nom_usu` varchar(255) NOT NULL,
  `email_usu` varchar(255) NOT NULL,
  `pass_usu` varchar(255) NOT NULL,
  `presu_usu` decimal(10,0) NOT NULL,
  PRIMARY KEY (`id_usu`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_usuario`
--

LOCK TABLES `tb_usuario` WRITE;
/*!40000 ALTER TABLE `tb_usuario` DISABLE KEYS */;
INSERT INTO `tb_usuario` VALUES (1,'Juanes','huanaco@gmail.com','123456',3500),(2,'Pedrito Punche','pedrito@gmail.com','123456',3500),(3,'Pepe','pepe@gmail.com','123',4500),(4,'pepito','pepito@gmail.com','123',3500),(5,'Bot23','bot@bot.com','botbot',1500);
/*!40000 ALTER TABLE `tb_usuario` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'bd_expenses_tracker'
--
/*!50003 DROP FUNCTION IF EXISTS `GenerarCodRegistro` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` FUNCTION `GenerarCodRegistro`(
	cod_usu int
) RETURNS varchar(250) CHARSET utf8mb4
    DETERMINISTIC
BEGIN
    -- Todos los codigos estan generados por 3 partes AÑO , MES(3 Letras) y NUMERO; separados por "-"
    -- reflejando NUMERO la ocurrencia del mes, es decir, el PRIMER registro del año 2022 del mes de Noviembre
    -- sería "2022-NOV-1", el SEGUNDO registro sería "2022-NOV-2" y sucesivamente

	-- Generamos el el año y mes ej. 2022-NOV-
    set @temp = UPPER(DATE_FORMAT(CURDATE(), "%Y-%b-"));
    
	-- Buscamos mayor numero (parte final del codigo) generado por este usuario en el presente mes
	select max(cast(substring_index(r.id_reg, "-", -1) as unsigned)) into @num 
    from tb_registro r 
    where r.id_usu = cod_usu and r.id_reg like concat(@temp, "%");
    
    -- Revisamos si existen ocurrencias previas del mes actual, sino le asignamos 0
    set @num = cast(ifnull(@num, 0) as unsigned);
    
    -- Adicionamos 1 al numero
    set @temp = concat(@temp, @num+1);
	return @temp;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `usp_actualizarRegistro` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `usp_actualizarRegistro`(
	id_registro varchar(255),
	desc_registro varchar(255),
    impac_registro double,
    cod_categoria int,
	id_usuario int
)
begin

update tb_registro set desc_reg = desc_registro, impac_reg = impac_registro, cod_cat = cod_categoria 
where id_reg = id_registro and id_usu = id_usuario;

end ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `usp_actualizarUsuario` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `usp_actualizarUsuario`(
	id_usuario int ,
    nom_usuario varchar(255),
    email_usuario varchar(255),
    pass_usuario varchar(255)
)
begin
update tb_usuario set nom_usu = nom_usuario, email_usu = email_usuario, pass_usu = pass_usuario where id_usu = id_usuario;
end ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `usp_agregarRegistro` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `usp_agregarRegistro`(
	desc_reg varchar(255),
    impac_reg double,
    cod_cat int,
	id_usu int
)
begin
	set @codigo = GenerarCodRegistro(id_usu);

	insert into tb_registro values (@codigo, desc_reg, impac_reg, cod_cat, default, id_usu);

end ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `usp_eliminarRegistro` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `usp_eliminarRegistro`(
	IN id_registro varchar(250),
    IN id_usuario int
)
begin
	delete from tb_registro r where r.id_reg = id_registro and r.id_usu = id_usuario;
end ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `usp_listarRegistrosEgresos` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `usp_listarRegistrosEgresos`(IN id_usuario int)
begin
	select r.id_reg, r.desc_reg, r.impac_reg, r.cod_cat, c.nom_cat, r.fec_reg, r.id_usu
    from tb_registro r inner join tb_categorias c on r.cod_cat = c.cod_cat
    where r.id_usu = id_usuario and r.impac_reg < 0 order by r.fec_reg asc, cast(substring_index(r.id_reg, "-", -1) as unsigned) asc;
end ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `usp_listarRegistrosIngresos` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `usp_listarRegistrosIngresos`(IN id_usuario int)
begin
	select r.id_reg, r.desc_reg, r.impac_reg, r.cod_cat, c.nom_cat, r.fec_reg, r.id_usu
    from tb_registro r inner join tb_categorias c on r.cod_cat = c.cod_cat
    where r.id_usu = id_usuario and r.impac_reg > 0 order by r.fec_reg asc, cast(substring_index(r.id_reg, "-", -1) as unsigned) asc;
end ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `usp_obtenerRegistrosParaExportar` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `usp_obtenerRegistrosParaExportar`(IN id_usuario int)
begin
	select r.id_reg, r.desc_reg, r.impac_reg, c.nom_cat, r.fec_reg, c.cod_cat, id_usu
    from tb_registro r inner join tb_categorias c on r.cod_cat = c.cod_cat
    where r.id_usu = id_usuario order by cast(substring_index(r.id_reg, "-", -1) as unsigned) asc;
end ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `usp_obtenerSumatoriaEgresoImpactosYFecha` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `usp_obtenerSumatoriaEgresoImpactosYFecha`(
	IN id_usuario int
)
begin
select sum(impac_reg), fec_reg from bd_expenses_tracker.tb_registro where impac_reg < 0 and id_usu = id_usuario group by fec_reg order by fec_reg asc;
end ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `usp_obtenerSumatoriaGeneralImpactosYFecha` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `usp_obtenerSumatoriaGeneralImpactosYFecha`(
	IN id_usuario int
)
begin
select sum(impac_reg) as 'impac_reg', fec_reg from bd_expenses_tracker.tb_registro where id_usu = id_usuario group by fec_reg order by fec_reg asc;
end ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `usp_obtenerSumatoriaIngresoImpactosYFecha` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `usp_obtenerSumatoriaIngresoImpactosYFecha`(
	IN id_usuario int
)
begin
select sum(impac_reg), fec_reg from bd_expenses_tracker.tb_registro where impac_reg >= 0 and id_usu = id_usuario group by fec_reg order by fec_reg asc;
end ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;


