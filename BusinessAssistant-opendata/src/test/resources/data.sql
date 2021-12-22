
insert  into clientes(RAZON_SOCIAL,CIF,DIRECCION,TELEFONO,EMAIL) values
('GONZALEZ BYASS','99999','AVDA. LIBERTAD 25 35850 SEVILLA','999','me@localhost'),
('OSBORNE','8956324Q','TORO NEGRO 1','986-968574','me@localhost'),
('BODEGAS VEGA SICILIA','9658745W','CASTELLANA 125 - MADRID','91-3526565','me@localhost'),
('BODEGAS PENALBA','123456678','AVDA. DIAGONAL 32 - BARCELONA','93-6546565','me@localhost'),
('RESTAURANTES LA VACA ARGENTINA','85475896W','RECOLETOS 95','91-98547548','me@localhost'),
('RITZ MADRID','65654645R','CASTELLANA 25','9154689877','me@localhost'),
('RESTAURANTES GITANA','4454744J','DIAGONAL 25','93.2565877','me@localhost'),
('BODEGAS JIMENEZ DE LA ROSA','521454R','AV. MAYOR 52','965.542121','me@localhost'),
('TRANSPORTES EL CARRO','959875874Q','Caminito de Castilla 25','00000','me@localhost'),
('BAR PEPE','9999','Caminito de Castilla 25','85858','me@localhost'),
('LAS 3 TABERNAS','999585987','Via della Vita 34','888888','me@localhost');

insert  into roles(ROL) values
('Mozo de almacen'),
('Jefe de almacen'),
('Responsable de plataforma'),
('Responsable de compras'),
('Responsable depto informatica'),
('Gerente');

insert  into tipos_proveedores(TIPO_PROVEEDOR) values
('Interno'),
('Externo');

insert  into tipos_vinos(CATEGORIA) values
('Blanco'),
('Tinto'),
('Rosado'),
('Generoso'),
('Licoroso'),
('Dulce'),
('Mistela'),
('Espumoso'),
('Natural'),
('Gasificado'),
('De aguja'),
('Enverado'),
('Chacoli'),
('Champagne'),
('Whiskey'),
('Ginebra'),
('Cognac'),
('Vermouth'),
('Rioja'),
('Ribera del Duero');

insert  into empleados(NOMBRE,APELLIDOS,NIF,EMAIL,ID_ROL,DIRECCION) values
('Emilio','Botin Fernandez','65465498y','gerencia@drinks.com',6,'Avda. Roma 1'),
('Guilllermo','Puertas González','12345678r','informatica@drinks.com',5,'Palomares 24'),
('Jose Maria','Hernandez Ruiz','5421478T','compras@drinks.com',4,'Recoletos 32'),
('Antonio','Gomez Plataformero','54879654T','plataforma@drinks.com',3,'Castellana 125'),
('Alberto','Fernandez Ramirez','878787e','almacen1@drinks.com',2,'Sol 3'),
('Luis','Carpio Gomez','8547854E','almacen2@drinks.com',2,'Rioja 34'),
('Manuel','Ruperez Fernandez','548745s','almacen3@drinks.com',2,'Alcarria 25'),
('Felipe','Moran Ros','852852w','mozo1@drinks.com',1,'Codo 25'),
('David','Lopez Cardenosa','1234568y','mozo2@drinks.com',1,'Alemania 232'),
('Juan Jose','Perea Lopez','9654784r','mozo3@drinks.com',1,'San Jose del Romero 3'),
('Pablo','Puertas Combo','545421U','mozo4@drinks.com',1,'Orense 2'),
('German','Rol Iglesias','X526214s','mozo5@drinks.com',1,'Avda. Villanueva 25'),
('Jose Manuel','Fernades Gomes','54587R','mozo6@drinks.com',1,'Callejon estrecho 5');


insert  into productos(DENOMINACION,PVP,RATIO,P_NETO,ID_VINO,EXISTENCIAS,ID_TIPO_PROVEEDOR,DENOMINACION_ORIGEN) values
('MARQUES DE RISCAL',0,3,0,2,541,1,''),
('BALLANTINES',9999,6,888,8,5000,2,NULL),
('ABSOLUT VODKA',9999,8,1300,18,3500,2,NULL),
('MARTINI ROSSO',9999,4,4000,18,3000,2,NULL),
('DIAMANTE',0,5,0,1,600,1,NULL),
('SENORIO DE LOS LLANOS',0,3,0,2,12500,1,NULL),
('VINA ALBALI',0,5,1700,2,2500,1,''),
('FAUSTINO II',9999,6,8000,14,300,2,''),
('DUQUE DE ALBA',9999,3,9000,17,500,2,''),
('FAUSTINO V',9999,2,1000,2,8900,2,''),
('NAPOLEON',9999,2,1100,17,9950,2,NULL),
('BALLANTINES',9999,6,888,15,9500,2,NULL),
('ABSOLUT VODKA',9999,8,1300,16,4100,2,NULL),
('LOS MOLINOS',0,4,0,2,5412,1,NULL),
('LAMBRUSCO',0,3,7777,1,8500,1,NULL),
('CUATRO ROBLES',0,3,0,2,25,1,NULL),
('CHIVAS REGAL 20 ANOS',9999,5,1700,15,540,2,NULL),
('FAUSTINO II',0,6,8888,2,6500,1,NULL),
('CASTROVIEJO',0,5,6666,2,885,1,NULL),
('SANGRE DE TORO',0,2,444,2,1520,1,NULL);


