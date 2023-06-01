create database bd_expenses_tracker;
use bd_expenses_tracker;

-- select fec_reg, sum(impac_reg) from bd_expenses_tracker.tb_registro group by fec_reg;
create table tb_usuario (
	id_usu int auto_increment,
    nom_usu varchar(255) not null,
    email_usu varchar(255) not null,
    pass_usu varchar(255) not null,
	presu_usu decimal not null,
    constraint PK_Usuario primary key (id_usu)
);

create table tb_categorias (
	cod_cat int auto_increment,
    nom_cat varchar(100),
	tipo_cat char check(tipo_cat='I' or tipo_cat='E'), -- I o E
    constraint PK_Categorias primary key (cod_cat)
);

create table tb_registro (
	id_reg varchar(255),
    desc_reg varchar(255) not null,
    impac_reg double not null,
    cod_cat int,
    fec_reg date not null default (curdate()),
	id_usu int,
    constraint FK_CatRegistro foreign key (cod_cat) references tb_categorias(cod_cat),
     constraint FK_UsuRegistro foreign key (id_usu) references tb_usuario(id_usu),
    constraint PK_Registro primary key (id_reg, id_usu)
    
);

insert into tb_categorias 
values 	(null, "Alimentación", 'E'),
		(null, "Cuentas y Pagos",'E'),
        (null, "Casa",'E'),
        (null, "Transporte",'E'),
        (null, "Ropa",'E'),
        (null, "Salud e Higiene",'E'),
        (null, "Diversión",'E'),
        (null, "Otros gastos",'E'),
        (null, "Salario o Nómina", 'I'),
        (null, "Honorarios", 'I'),
        (null, "Negocio Propio o Comerciante", 'I'),
        (null, "Dividendos o Participaciones", "I"),
        (null, "Pensión", 'I'),
        (null, "Rentas de Capital", 'I'),
        (null, "Ocasional", 'I');
        



insert into tb_usuario values (null, "Juan Ahorrador", "huanaco@gmail.com", "123456", 3500);
insert into tb_registro values ('2022-NOV-1', "Propina por Año Nuevo", 50, 15, "2022-11-01", 1);
insert into tb_registro values ('2022-NOV-2', "Venta de peluches", 150, 15, "2022-11-05", 1);
insert into tb_registro values ('2022-NOV-3', "Canchuelo de ayudante de electricista", 300, 15, "2022-11-07", 1);
insert into tb_registro values ('2022-NOV-4', "Canchuelo de Tutor de Excel", 75, 15, "2022-11-09", 1);
insert into tb_registro values ('2022-NOV-5', "Cobro de préstamo", 100, 14, "2022-11-10", 1);

insert into tb_registro values ('2022-NOV-6', "Salida al Mall Aventura", -60, 7, "2022-11-04", 1);
insert into tb_registro values ('2022-NOV-7', "Salida al Mall Real Plaza", -40, 7, "2022-11-05", 1);
insert into tb_registro values ('2022-NOV-8', "Pago de cuota de préstamo Banco el Amigo", -450, 2, "2022-11-06", 1);
insert into tb_registro values ('2022-NOV-9', "Cena familiar", -150, 1, "2022-11-07", 1);
insert into tb_registro values ('2022-NOV-10', "Pasajes viaje a Chile", -250, 4, "2022-11-08", 1);
insert into tb_registro values ('2022-NOV-11', "Pasajes viaje a Bolivia", -250, 4, "2022-11-08", 1);


insert into tb_usuario values (null, "Pedrito Punche", "pedrito@gmail.com", "123456", 3500);
insert into tb_registro values ('2022-NOV-1', "Canchuelo de ayudante de electricista", 300, 15, "2022-11-07", 2);
insert into tb_registro values ('2022-NOV-2', "Canchuelo de Tutor de Excel", 75, 15, "2022-11-09", 2);
insert into tb_registro values ('2022-NOV-3', "Cobro de préstamo", 100, 14, "2022-11-10", 2);

insert into tb_registro values ('2022-NOV-6', "Salida al Mall Aventura", -60, 7, "2022-11-04", 2);
insert into tb_registro values ('2022-NOV-7', "Salida al Mall Real Plaza", -40, 7, "2022-11-05", 2);
insert into tb_registro values ('2022-NOV-8', "Pago de cuota de préstamo Banco el Amigo", -450, 2, "2022-11-06", 2);
insert into tb_registro values ('2022-NOV-9', "Cena familiar", -150, 1, "2022-11-07", 2);
insert into tb_registro values ('2022-NOV-10', "Pasajes viaje a Chile", -250, 4, "2022-11-08", 2);
insert into tb_registro values ('2022-NOV-11', "Pasajes viaje a Bolivia", -250, 4, "2022-11-08", 2);

DELIMITER &&
create procedure usp_obtenerRegistrosParaExportar (IN id_usuario int)
begin
	select r.id_reg, r.desc_reg, r.impac_reg, c.nom_cat, r.fec_reg 
    from tb_registro r inner join tb_categorias c on r.cod_cat = c.cod_cat
    where r.id_usu = id_usuario order by cast(substring_index(r.id_reg, "-", -1) as unsigned) asc;
end &&
DELIMITER ;

DELIMITER &&
create procedure usp_listarRegistrosIngresos (IN id_usuario int)
begin
	select r.id_reg, r.desc_reg, r.impac_reg, r.cod_cat, c.nom_cat, r.fec_reg 
    from tb_registro r inner join tb_categorias c on r.cod_cat = c.cod_cat
    where r.id_usu = id_usuario and r.impac_reg > 0 order by r.fec_reg asc, cast(substring_index(r.id_reg, "-", -1) as unsigned) asc;
end &&
DELIMITER ;

DELIMITER &&
create procedure usp_listarRegistrosEgresos (IN id_usuario int)
begin
	select r.id_reg, r.desc_reg, r.impac_reg, r.cod_cat, c.nom_cat, r.fec_reg 
    from tb_registro r inner join tb_categorias c on r.cod_cat = c.cod_cat
    where r.id_usu = id_usuario and r.impac_reg < 0 order by r.fec_reg asc, cast(substring_index(r.id_reg, "-", -1) as unsigned) asc;
end &&
DELIMITER ;

DELIMITER &&
create procedure usp_eliminarRegistro(
	IN id_registro varchar(250),
    IN id_usuario int
)
begin
	delete from tb_registro r where r.id_reg = id_registro and r.id_usu = id_usuario;
end &&
DELIMITER ;

DELIMITER &&
create procedure usp_actualizarRegistro(
	id_registro varchar(255),
	desc_registro varchar(255),
    impac_registro double,
    cod_categoria int,
	id_usuario int
)
begin

update tb_registro set desc_reg = desc_registro, impac_reg = impac_registro, cod_cat = cod_categoria 
where id_reg = id_registro and id_usu = id_usuario;

end &&
DELIMITER ;

DELIMITER &&
create procedure usp_agregarRegistro(
	desc_reg varchar(255),
    impac_reg double,
    cod_cat int,
	id_usu int
)
begin
	set @codigo = GenerarCodRegistro(id_usu);

	insert into tb_registro values (@codigo, desc_reg, impac_reg, cod_cat, default, id_usu);

end &&
DELIMITER ;

-- Esta funcion es de uso único interno de la BD, trabaja en conjunto con usp_agregarRegistro
DELIMITER &&
CREATE FUNCTION GenerarCodRegistro(
	cod_usu int
) 
RETURNS VARCHAR(250)
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
END &&
DELIMITER ;

DELIMITER &&
create procedure usp_actualizarUsuario(
	id_usuario int ,
    nom_usuario varchar(255),
    email_usuario varchar(255),
    pass_usuario varchar(255)
)
begin
update tb_usuario set nom_usu = nom_usuario, email_usu = email_usuario, pass_usu = pass_usuario where id_usu = id_usuario;
end &&
DELIMITER ;

-- Obtiene todos los impactos (ingresos/egresos) + Fecha de un usuario

DELIMITER &&
create procedure usp_obtenerSumatoriaGeneralImpactosYFecha(
	IN id_usuario int
)
begin
select sum(impac_reg), fec_reg from bd_expenses_tracker.tb_registro where id_usu = id_usuario group by fec_reg order by fec_reg asc;
end &&
DELIMITER ;

DELIMITER &&
create procedure usp_obtenerSumatoriaIngresoImpactosYFecha(
	IN id_usuario int
)
begin
select sum(impac_reg), fec_reg from bd_expenses_tracker.tb_registro where impac_reg >= 0 and id_usu = id_usuario group by fec_reg order by fec_reg asc;
end &&
DELIMITER ;

DELIMITER &&
create procedure usp_obtenerSumatoriaEgresoImpactosYFecha(
	IN id_usuario int
)
begin
select sum(impac_reg), fec_reg from bd_expenses_tracker.tb_registro where impac_reg < 0 and id_usu = id_usuario group by fec_reg order by fec_reg asc;
end &&
DELIMITER ;



