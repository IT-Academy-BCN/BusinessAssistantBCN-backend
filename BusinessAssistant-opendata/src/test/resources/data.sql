
insert  into clientes(ID_CLIENTES,RAZON_SOCIAL,CIF,DIRECCION,TELEFONO,EMAIL) values
(1,'GONZALEZ BYASS','99999','AVDA. LIBERTAD 25 35850 SEVILLA','999','me@localhost'),
(2,'OSBORNE','8956324Q','TORO NEGRO 1','986-968574','me@localhost'),
(3,'BODEGAS VEGA SICILIA','9658745W','CASTELLANA 125 - MADRID','91-3526565','me@localhost'),
(4,'BODEGAS PENALBA','123456678','AVDA. DIAGONAL 32 - BARCELONA','93-6546565','me@localhost'),
(5,'RESTAURANTES LA VACA ARGENTINA','85475896W','RECOLETOS 95','91-98547548','me@localhost'),
(6,'RITZ MADRID','65654645R','CASTELLANA 25','9154689877','me@localhost'),
(7,'RESTAURANTES GITANA','4454744J','DIAGONAL 25','93.2565877','me@localhost'),
(8,'BODEGAS JIMENEZ DE LA ROSA','521454R','AV. MAYOR 52','965.542121','me@localhost'),
(9,'TRANSPORTES EL CARRO','959875874Q','Caminito de Castilla 25','00000','me@localhost'),
(10,'BAR PEPE','9999','Caminito de Castilla 25','85858','me@localhost'),
(11,'LAS 3 TABERNAS','999585987','Via della Vita 34','888888','me@localhost');

insert  into roles(ID_ROL,ROL) values 
(1,'Mozo de almacen'),
(2,'Jefe de almacen'),
(3,'Responsable de plataforma'),
(4,'Responsable de compras'),
(5,'Responsable depto informatica'),
(6,'Gerente');

insert  into tipos_proveedores(ID_TIPO_PROVEEDOR,TIPO_PROVEEDOR) values 
(1,'Interno'),
(2,'Externo');

insert  into tipos_vinos(ID_VINO,CATEGORIA) values 
(1,'Blanco'),
(2,'Tinto'),
(3,'Rosado'),
(4,'Generoso'),
(5,'Licoroso'),
(6,'Dulce'),
(7,'Mistela'),
(8,'Espumoso'),
(9,'Natural'),
(10,'Gasificado'),
(11,'De aguja'),
(12,'Enverado'),
(13,'Chacoli'),
(14,'Champagne'),
(15,'Whiskey'),
(16,'Ginebra'),
(17,'Cognac'),
(18,'Vermouth'),
(19,'Rioja'),
(20,'Ribera del Duero');

insert  into empleados(ID_EMP,NOMBRE,APELLIDOS,NIF,EMAIL,ID_ROL,DIRECCION) values 
(1,'Emilio','Botin Fernandez','65465498y','gerencia@drinks.com',6,'Avda. Roma 1'),
(2,'Guilllermo','Puertas González','12345678r','informatica@drinks.com',5,'Palomares 24'),
(3,'Jose Maria','Hernandez Ruiz','5421478T','compras@drinks.com',4,'Recoletos 32'),
(4,'Antonio','Gomez Plataformero','54879654T','plataforma@drinks.com',3,'Castellana 125'),
(5,'Alberto','Fernandez Ramirez','878787e','almacen1@drinks.com',2,'Sol 3'),
(6,'Luis','Carpio Gomez','8547854E','almacen2@drinks.com',2,'Rioja 34'),
(7,'Manuel','Ruperez Fernandez','548745s','almacen3@drinks.com',2,'Alcarria 25'),
(8,'Felipe','Moran Ros','852852w','mozo1@drinks.com',1,'Codo 25'),
(9,'David','Lopez Cardenosa','1234568y','mozo2@drinks.com',1,'Alemania 232'),
(10,'Juan Jose','Perea Lopez','9654784r','mozo3@drinks.com',1,'San Jose del Romero 3'),
(11,'Pablo','Puertas Combo','545421U','mozo4@drinks.com',1,'Orense 2'),
(12,'German','Rol Iglesias','X526214s','mozo5@drinks.com',1,'Avda. Villanueva 25'),
(13,'Jose Manuel','Fernades Gomes','54587R','mozo6@drinks.com',1,'Callejon estrecho 5');


insert  into productos(ID_PRODUCTO,DENOMINACION,PVP,RATIO,P_NETO,ID_VINO,EXISTENCIAS,ID_TIPO_PROVEEDOR,DENOMINACION_ORIGEN) values 
(1,'MARQUES DE RISCAL',0,3,0,2,541,1,''),
(2,'BALLANTINES',9999,6,888,8,5000,2,NULL),
(3,'ABSOLUT VODKA',9999,8,1300,18,3500,2,NULL),
(4,'MARTINI ROSSO',9999,4,4000,18,3000,2,NULL),
(5,'DIAMANTE',0,5,0,1,600,1,NULL),
(6,'SENORIO DE LOS LLANOS',0,3,0,2,12500,1,NULL),
(7,'VINA ALBALI',0,5,1700,2,2500,1,''),
(8,'FAUSTINO II',9999,6,8000,14,300,2,''),
(9,'DUQUE DE ALBA',9999,3,9000,17,500,2,''),
(10,'FAUSTINO V',9999,2,1000,2,8900,2,''),
(11,'NAPOLEON',9999,2,1100,17,9950,2,NULL),
(12,'BALLANTINES',9999,6,888,15,9500,2,NULL),
(13,'ABSOLUT VODKA',9999,8,1300,16,4100,2,NULL),
(14,'LOS MOLINOS',0,4,0,2,5412,1,NULL),
(15,'LAMBRUSCO',0,3,7777,1,8500,1,NULL),
(16,'CUATRO ROBLES',0,3,0,2,25,1,NULL),
(17,'CHIVAS REGAL 20 ANOS',9999,5,1700,15,540,2,NULL),
(18,'FAUSTINO II',0,6,8888,2,6500,1,NULL),
(19,'CASTROVIEJO',0,5,6666,2,885,1,NULL),
(20,'SANGRE DE TORO',0,2,444,2,1520,1,NULL);


