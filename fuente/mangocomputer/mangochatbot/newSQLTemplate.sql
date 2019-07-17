/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  mm
 * Created: 16/10/2018
 */

INSERT INTO `dtAlias`(`idDt`, `alias`) VALUES (2,"marapan");
INSERT INTO `dtAlias`(`idDt`, `alias`) VALUES (2,"panaderia parque aragua");
INSERT INTO `dtAlias`(`idDt`, `alias`) VALUES (2,"parque aragua panaderia");

INSERT INTO `bolsaPalabras`(`palabra`, `palabraMostrar`, `semantica`, `idContexto`, `tipo`, `frecuencia`) 
VALUES ("panaderia","panadería","panaderia",2,1,0);
INSERT INTO `bolsaPalabras`(`palabra`, `palabraMostrar`, `semantica`, `idContexto`, `tipo`, `frecuencia`) 
VALUES ("pasteleria","pastelería","pasteleria",2,1,0);

INSERT INTO `bolsaPalabras`(`palabra`, `palabraMostrar`, `semantica`, `idContexto`, `tipo`, `frecuencia`) 
VALUES ("pan","pan","pan",2,2,0);

INSERT INTO `bolsaPalabras`(`palabra`, `palabraMostrar`, `semantica`, `idContexto`, `tipo`, `frecuencia`) 
VALUES ("cafe","café","cafe",2,2,0);

INSERT INTO `bolsaPalabras`(`palabra`, `palabraMostrar`, `semantica`, `idContexto`, `tipo`, `frecuencia`) 
VALUES ("malta","malta","malta",2,2,0);


INSERT INTO `dtBolsaPalabras`(`idDt`, `idBolsaPalabra`) VALUES (2,59);
INSERT INTO `dtBolsaPalabras`(`idDt`, `idBolsaPalabra`) VALUES (2,60);
INSERT INTO `dtBolsaPalabras`(`idDt`, `idBolsaPalabra`) VALUES (2,59);
INSERT INTO `dtBolsaPalabras`(`idDt`, `idBolsaPalabra`) VALUES (2,56);
INSERT INTO `dtBolsaPalabras`(`idDt`, `idBolsaPalabra`) VALUES (2,57);
INSERT INTO `dtBolsaPalabras`(`idDt`, `idBolsaPalabra`) VALUES (2,58);
INSERT INTO `dtBolsaPalabras`(`idDt`, `idBolsaPalabra`) VALUES (2,61);




INSERT INTO `semantica_sino`(`palabra`, `semantica`) VALUES ("si","si");
INSERT INTO `semantica_sino`(`palabra`, `semantica`) VALUES ("positivo","si");
INSERT INTO `semantica_sino`(`palabra`, `semantica`) VALUES ("exacto","si");
INSERT INTO `semantica_sino`(`palabra`, `semantica`) VALUES ("yes","si");
INSERT INTO `semantica_sino`(`palabra`, `semantica`) VALUES ("ok","si");
INSERT INTO `semantica_sino`(`palabra`, `semantica`) VALUES ("aja","si");
INSERT INTO `semantica_sino`(`palabra`, `semantica`) VALUES ("perfecto","si");
INSERT INTO `semantica_sino`(`palabra`, `semantica`) VALUES ("dale","si");
INSERT INTO `semantica_sino`(`palabra`, `semantica`) VALUES ("si","si");
INSERT INTO `semantica_sino`(`palabra`, `semantica`) VALUES ("bien","si");
INSERT INTO `semantica_sino`(`palabra`, `semantica`) VALUES ("claro","si");
INSERT INTO `semantica_sino`(`palabra`, `semantica`) VALUES ("afirmativo","si");
INSERT INTO `semantica_sino`(`palabra`, `semantica`) VALUES ("supuesto","si");
INSERT INTO `semantica_sino`(`palabra`, `semantica`) VALUES ("creo","si");
INSERT INTO `semantica_sino`(`palabra`, `semantica`) VALUES ("siempre","si");
INSERT INTO `semantica_sino`(`palabra`, `semantica`) VALUES ("obvio","si");
INSERT INTO `semantica_sino`(`palabra`, `semantica`) VALUES ("correcto","si");

INSERT INTO `semantica_sino`(`palabra`, `semantica`) VALUES ("no","no");
INSERT INTO `semantica_sino`(`palabra`, `semantica`) VALUES ("correcto","no");
INSERT INTO `semantica_sino`(`palabra`, `semantica`) VALUES ("negativo","no");
INSERT INTO `semantica_sino`(`palabra`, `semantica`) VALUES ("impreciso","no");
INSERT INTO `semantica_sino`(`palabra`, `semantica`) VALUES ("not","no");
INSERT INTO `semantica_sino`(`palabra`, `semantica`) VALUES ("nunca","no");
INSERT INTO `semantica_sino`(`palabra`, `semantica`) VALUES ("jamas","no");
INSERT INTO `semantica_sino`(`palabra`, `semantica`) VALUES ("correcto","no");


INSERT INTO `articulos`(`articulo`) VALUES ("este");
INSERT INTO `articulos`(`articulo`) VALUES ("esta");
INSERT INTO `articulos`(`articulo`) VALUES ("estas");
INSERT INTO `articulos`(`articulo`) VALUES ("esto");
INSERT INTO `articulos`(`articulo`) VALUES ("estos");
INSERT INTO `articulos`(`articulo`) VALUES ("unos");
INSERT INTO `articulos`(`articulo`) VALUES ("unas");
INSERT INTO `articulos`(`articulo`) VALUES ("pero");
INSERT INTO `articulos`(`articulo`) VALUES ("la");
INSERT INTO `articulos`(`articulo`) VALUES ("las");
INSERT INTO `articulos`(`articulo`) VALUES ("lo");
INSERT INTO `articulos`(`articulo`) VALUES ("los");
INSERT INTO `articulos`(`articulo`) VALUES ("un");
INSERT INTO `articulos`(`articulo`) VALUES ("una");
INSERT INTO `articulos`(`articulo`) VALUES ("uno");
INSERT INTO `articulos`(`articulo`) VALUES ("es");
INSERT INTO `articulos`(`articulo`) VALUES ("en");
INSERT INTO `articulos`(`articulo`) VALUES ("a");
INSERT INTO `articulos`(`articulo`) VALUES ("de");
INSERT INTO `articulos`(`articulo`) VALUES ("del");
INSERT INTO `articulos`(`articulo`) VALUES ("le");
INSERT INTO `articulos`(`articulo`) VALUES ("que");
INSERT INTO `articulos`(`articulo`) VALUES ("para");
INSERT INTO `articulos`(`articulo`) VALUES ("o");
INSERT INTO `articulos`(`articulo`) VALUES ("u");
INSERT INTO `articulos`(`articulo`) VALUES ("y");
INSERT INTO `articulos`(`articulo`) VALUES ("e");
INSERT INTO `articulos`(`articulo`) VALUES ("i");
INSERT INTO `articulos`(`articulo`) VALUES ("me");
INSERT INTO `articulos`(`articulo`) VALUES ("tengo");
INSERT INTO `articulos`(`articulo`) VALUES ("quiero");
INSERT INTO `articulos`(`articulo`) VALUES ("quiere");
INSERT INTO `articulos`(`articulo`) VALUES ("queremos");
INSERT INTO `articulos`(`articulo`) VALUES ("quieren");
INSERT INTO `articulos`(`articulo`) VALUES ("querer");
INSERT INTO `articulos`(`articulo`) VALUES ("tu");
INSERT INTO `articulos`(`articulo`) VALUES ("yo");
INSERT INTO `articulos`(`articulo`) VALUES ("el");
INSERT INTO `articulos`(`articulo`) VALUES ("ella");
INSERT INTO `articulos`(`articulo`) VALUES ("nosotros");
INSERT INTO `articulos`(`articulo`) VALUES ("vosotros");
INSERT INTO `articulos`(`articulo`) VALUES ("ellos");
INSERT INTO `articulos`(`articulo`) VALUES ("crees");
INSERT INTO `articulos`(`articulo`) VALUES ("puedes");
INSERT INTO `articulos`(`articulo`) VALUES ("podrias");
INSERT INTO `articulos`(`articulo`) VALUES ("por");
INSERT INTO `articulos`(`articulo`) VALUES ("has");
INSERT INTO `articulos`(`articulo`) VALUES ("han");


INSERT INTO `fabricaFrases`(`codFrase`, `frase`) VALUES (1, "edad tienes");
INSERT INTO `fabricaFrases`(`codFrase`, `frase`) VALUES (1, "edad tiene");
INSERT INTO `fabricaFrases`(`codFrase`, `frase`) VALUES (1, "cuantos años tienes");
INSERT INTO `fabricaFrases`(`codFrase`, `frase`) VALUES (1, "cuanto años tiene");
INSERT INTO `fabricaFrases`(`codFrase`, `frase`) VALUES (1, "cuanto año tienes");
INSERT INTO `fabricaFrases`(`codFrase`, `frase`) VALUES (1, "cuanto año tiene");
INSERT INTO `fabricaFrases`(`codFrase`, `frase`) VALUES (1, "edad");
INSERT INTO `fabricaFrases`(`codFrase`, `frase`) VALUES (1, "tienes edad");
INSERT INTO `fabricaFrases`(`codFrase`, `frase`) VALUES (1, "nacimiento");
INSERT INTO `fabricaFrases`(`codFrase`, `frase`) VALUES (1, "año naciste");
INSERT INTO `fabricaFrases`(`codFrase`, `frase`) VALUES (1, "año nacistes");
INSERT INTO `fabricaFrases`(`codFrase`, `frase`) VALUES (1, "fecha nacimiento");
INSERT INTO `fabricaFrases`(`codFrase`, `frase`) VALUES (1, "nacimiento fecha");
INSERT INTO `fabricaFrases`(`codFrase`, `frase`) VALUES (1, "tiempo vida");
INSERT INTO `fabricaFrases`(`codFrase`, `frase`) VALUES (1, "tiempo vivido");

INSERT INTO `fabricaFrases`(`codFrase`, `frase`) VALUES (2, "hola");
INSERT INTO `fabricaFrases`(`codFrase`, `frase`) VALUES (2, "hola que tal");
INSERT INTO `fabricaFrases`(`codFrase`, `frase`) VALUES (2, "hola como estas");
INSERT INTO `fabricaFrases`(`codFrase`, `frase`) VALUES (2, "hola que mas");
INSERT INTO `fabricaFrases`(`codFrase`, `frase`) VALUES (2, "como estas");
INSERT INTO `fabricaFrases`(`codFrase`, `frase`) VALUES (2, "hola");
INSERT INTO `fabricaFrases`(`codFrase`, `frase`) VALUES (2, "saludos");
INSERT INTO `fabricaFrases`(`codFrase`, `frase`) VALUES (2, "holas");
INSERT INTO `fabricaFrases`(`codFrase`, `frase`) VALUES (2, "epa");
INSERT INTO `fabricaFrases`(`codFrase`, `frase`) VALUES (2, "uju");
INSERT INTO `fabricaFrases`(`codFrase`, `frase`) VALUES (2, "hey");
INSERT INTO `fabricaFrases`(`codFrase`, `frase`) VALUES (2, "alo");
INSERT INTO `fabricaFrases`(`codFrase`, `frase`) VALUES (2, "que mas");
INSERT INTO `fabricaFrases`(`codFrase`, `frase`) VALUES (2, "alguien por alli");
INSERT INTO `fabricaFrases`(`codFrase`, `frase`) VALUES (2, "que mas");
INSERT INTO `fabricaFrases`(`codFrase`, `frase`) VALUES (2, "que hubo");
INSERT INTO `fabricaFrases`(`codFrase`, `frase`) VALUES (2, "hello");
INSERT INTO `fabricaFrases`(`codFrase`, `frase`) VALUES (2, "what up");
INSERT INTO `fabricaFrases`(`codFrase`, `frase`) VALUES (2, "como estas");
INSERT INTO `fabricaFrases`(`codFrase`, `frase`) VALUES (2, "todo bien");
INSERT INTO `fabricaFrases`(`codFrase`, `frase`) VALUES (2, "como te sientes");
INSERT INTO `fabricaFrases`(`codFrase`, `frase`) VALUES (2, "en que piensas");
INSERT INTO `fabricaFrases`(`codFrase`, `frase`) VALUES (2, "que piensas");
INSERT INTO `fabricaFrases`(`codFrase`, `frase`) VALUES (2, "que hay de nuevo");
INSERT INTO `fabricaFrases`(`codFrase`, `frase`) VALUES (2, "feliz dia");
INSERT INTO `fabricaFrases`(`codFrase`, `frase`) VALUES (2, "feliz mañana");
INSERT INTO `fabricaFrases`(`codFrase`, `frase`) VALUES (2, "feliz tarde");
INSERT INTO `fabricaFrases`(`codFrase`, `frase`) VALUES (2, "feliz noche");
INSERT INTO `fabricaFrases`(`codFrase`, `frase`) VALUES (2, "que me cuentas");
INSERT INTO `fabricaFrases`(`codFrase`, `frase`) VALUES (2, "hablame");
INSERT INTO `fabricaFrases`(`codFrase`, `frase`) VALUES (2, "cuentamelo todo");
INSERT INTO `fabricaFrases`(`codFrase`, `frase`) VALUES (2, "dime todo");
INSERT INTO `fabricaFrases`(`codFrase`, `frase`) VALUES (2, "epa viejo");
INSERT INTO `fabricaFrases`(`codFrase`, `frase`) VALUES (2, "viejo");
INSERT INTO `fabricaFrases`(`codFrase`, `frase`) VALUES (2, "tio");
INSERT INTO `fabricaFrases`(`codFrase`, `frase`) VALUES (2, "despierta");

INSERT INTO `fabricaFrases`(`codFrase`, `frase`) VALUES (3, "quien te fabrico");
INSERT INTO `fabricaFrases`(`codFrase`, `frase`) VALUES (3, "quien es tu papa");
INSERT INTO `fabricaFrases`(`codFrase`, `frase`) VALUES (3, "padres");
INSERT INTO `fabricaFrases`(`codFrase`, `frase`) VALUES (3, "madres");
INSERT INTO `fabricaFrases`(`codFrase`, `frase`) VALUES (3, "quienes te crearon");
INSERT INTO `fabricaFrases`(`codFrase`, `frase`) VALUES (3, "quienes son tus creadores");
INSERT INTO `fabricaFrases`(`codFrase`, `frase`) VALUES (3, "quienes te fabricaron");
INSERT INTO `fabricaFrases`(`codFrase`, `frase`) VALUES (3, "quien te dio la vida");
INSERT INTO `fabricaFrases`(`codFrase`, `frase`) VALUES (3, "estas vivo");
INSERT INTO `fabricaFrases`(`codFrase`, `frase`) VALUES (3, "eres hombre o mujer");
INSERT INTO `fabricaFrases`(`codFrase`, `frase`) VALUES (3, "de que sexo eres");
INSERT INTO `fabricaFrases`(`codFrase`, `frase`) VALUES (3, "tienes sexo");
INSERT INTO `fabricaFrases`(`codFrase`, `frase`) VALUES (3, "eres mujer");
INSERT INTO `fabricaFrases`(`codFrase`, `frase`) VALUES (3, "eres femenina");
INSERT INTO `fabricaFrases`(`codFrase`, `frase`) VALUES (3, "eres femenino");
INSERT INTO `fabricaFrases`(`codFrase`, `frase`) VALUES (3, "eres hombre");
INSERT INTO `fabricaFrases`(`codFrase`, `frase`) VALUES (3, "eres masculino");
INSERT INTO `fabricaFrases`(`codFrase`, `frase`) VALUES (3, "eres una maquina");
INSERT INTO `fabricaFrases`(`codFrase`, `frase`) VALUES (3, "eres un robot");
INSERT INTO `fabricaFrases`(`codFrase`, `frase`) VALUES (3, "eres un chatbot");
INSERT INTO `fabricaFrases`(`codFrase`, `frase`) VALUES (3, "eres un chat bot");
INSERT INTO `fabricaFrases`(`codFrase`, `frase`) VALUES (3, "eres humano");
INSERT INTO `fabricaFrases`(`codFrase`, `frase`) VALUES (3, "eres una inteligencia virtual");
INSERT INTO `fabricaFrases`(`codFrase`, `frase`) VALUES (3, "tienes inteligencia");
INSERT INTO `fabricaFrases`(`codFrase`, `frase`) VALUES (3, "que tan iteligente eres");
INSERT INTO `fabricaFrases`(`codFrase`, `frase`) VALUES (3, "hablame de ti");
INSERT INTO `fabricaFrases`(`codFrase`, `frase`) VALUES (3, "cuentame sobre ti");
INSERT INTO `fabricaFrases`(`codFrase`, `frase`) VALUES (3, "quiero saber sobre ti");
INSERT INTO `fabricaFrases`(`codFrase`, `frase`) VALUES (3, "como estas hecho");
INSERT INTO `fabricaFrases`(`codFrase`, `frase`) VALUES (3, "eres un software");
INSERT INTO `fabricaFrases`(`codFrase`, `frase`) VALUES (3, "eres un programa");
INSERT INTO `fabricaFrases`(`codFrase`, `frase`) VALUES (3, "eres un programa de computadora");
INSERT INTO `fabricaFrases`(`codFrase`, `frase`) VALUES (3, "vives en internet");
INSERT INTO `fabricaFrases`(`codFrase`, `frase`) VALUES (3, "vives en un servidor");
INSERT INTO `fabricaFrases`(`codFrase`, `frase`) VALUES (3, "que comes");
INSERT INTO `fabricaFrases`(`codFrase`, `frase`) VALUES (3, "que tipo de comida comes");
INSERT INTO `fabricaFrases`(`codFrase`, `frase`) VALUES (3, "de que estas hecho");
INSERT INTO `fabricaFrases`(`codFrase`, `frase`) VALUES (3, "de donde eres");
INSERT INTO `fabricaFrases`(`codFrase`, `frase`) VALUES (3, "donde vives");
INSERT INTO `fabricaFrases`(`codFrase`, `frase`) VALUES (3, "donde comes");
INSERT INTO `fabricaFrases`(`codFrase`, `frase`) VALUES (3, "donde duermes");


INSERT INTO `respuestaFrases`(`codFrase`, `respuesta`) VALUES (1,"Me crearon en el año 2018");
INSERT INTO `respuestaFrases`(`codFrase`, `respuesta`) VALUES (2,"Holaaa, todo bien por aqui");
INSERT INTO `respuestaFrases`(`codFrase`, `respuesta`) VALUES (3,"Soy un software, me alimento de big data y vivo en una computadora");

INSERT INTO `bolsaPalabras`(`palabra`, `palabraMostrar`, `semantica`, `idContexto`, `tipo`) VALUES ("hotel","hotel","hotel",1,1);
INSERT INTO `bolsaPalabras`(`palabra`, `palabraMostrar`, `semantica`, `idContexto`, `tipo`) VALUES ("hoteles","hoteles","hotel",1,1);
INSERT INTO `bolsaPalabras`(`palabra`, `palabraMostrar`, `semantica`, `idContexto`, `tipo`) VALUES ("motel","motel","hotel",1,1);
INSERT INTO `bolsaPalabras`(`palabra`, `palabraMostrar`, `semantica`, `idContexto`, `tipo`) VALUES ("moteles","motel","hotel",1,1);
INSERT INTO `bolsaPalabras`(`palabra`, `palabraMostrar`, `semantica`, `idContexto`, `tipo`) VALUES ("hostal","hostal","hotel",1,1);
INSERT INTO `bolsaPalabras`(`palabra`, `palabraMostrar`, `semantica`, `idContexto`, `tipo`) VALUES ("hostales","hostales","hotel",1,1);
INSERT INTO `bolsaPalabras`(`palabra`, `palabraMostrar`, `semantica`, `idContexto`, `tipo`) VALUES ("parador","parador","hotel",1,1);
INSERT INTO `bolsaPalabras`(`palabra`, `palabraMostrar`, `semantica`, `idContexto`, `tipo`) VALUES ("paradores","paradores","hotel",1,1);
INSERT INTO `bolsaPalabras`(`palabra`, `palabraMostrar`, `semantica`, `idContexto`, `tipo`) VALUES ("hosteria","hosteria","hotel",1,1);
INSERT INTO `bolsaPalabras`(`palabra`, `palabraMostrar`, `semantica`, `idContexto`, `tipo`) VALUES ("hosterias","hosterias","hotel",1,1);
INSERT INTO `bolsaPalabras`(`palabra`, `palabraMostrar`, `semantica`, `idContexto`, `tipo`) VALUES ("albergue","albergue","hotel",1,1);
INSERT INTO `bolsaPalabras`(`palabra`, `palabraMostrar`, `semantica`, `idContexto`, `tipo`) VALUES ("albergues","albergues","hotel",1,1);
INSERT INTO `bolsaPalabras`(`palabra`, `palabraMostrar`, `semantica`, `idContexto`, `tipo`) VALUES ("casa","casa","hotel",1,1);
INSERT INTO `bolsaPalabras`(`palabra`, `palabraMostrar`, `semantica`, `idContexto`, `tipo`) VALUES ("casas","casas","hotel",1,1);
INSERT INTO `bolsaPalabras`(`palabra`, `palabraMostrar`, `semantica`, `idContexto`, `tipo`) VALUES ("apartamento","apartamento","hotel",1,1);
INSERT INTO `bolsaPalabras`(`palabra`, `palabraMostrar`, `semantica`, `idContexto`, `tipo`) VALUES ("apartamentos","apartamentos","hotel",1,1);
INSERT INTO `bolsaPalabras`(`palabra`, `palabraMostrar`, `semantica`, `idContexto`, `tipo`) VALUES ("cuarto","cuarto","hotel",1,1);
INSERT INTO `bolsaPalabras`(`palabra`, `palabraMostrar`, `semantica`, `idContexto`, `tipo`) VALUES ("cuartos","cuartos","hotel",1,1);
INSERT INTO `bolsaPalabras`(`palabra`, `palabraMostrar`, `semantica`, `idContexto`, `tipo`) VALUES ("pieza","pieza","hotel",1,1);
INSERT INTO `bolsaPalabras`(`palabra`, `palabraMostrar`, `semantica`, `idContexto`, `tipo`) VALUES ("piezas","piezas","hotel",1,1);
INSERT INTO `bolsaPalabras`(`palabra`, `palabraMostrar`, `semantica`, `idContexto`, `tipo`) VALUES ("hospedaje","hospedaje","hotel",1,1);
INSERT INTO `bolsaPalabras`(`palabra`, `palabraMostrar`, `semantica`, `idContexto`, `tipo`) VALUES ("hospedajes","hospedajes","hotel",1,1);
INSERT INTO `bolsaPalabras`(`palabra`, `palabraMostrar`, `semantica`, `idContexto`, `tipo`) VALUES ("posada","posada","hotel",1,1);
INSERT INTO `bolsaPalabras`(`palabra`, `palabraMostrar`, `semantica`, `idContexto`, `tipo`) VALUES ("posadas","posadas","hotel",1,1);
INSERT INTO `bolsaPalabras`(`palabra`, `palabraMostrar`, `semantica`, `idContexto`, `tipo`) VALUES ("fonda","fonda","hotel",1,1);
INSERT INTO `bolsaPalabras`(`palabra`, `palabraMostrar`, `semantica`, `idContexto`, `tipo`) VALUES ("fondas","fondas","hotel",1,1);
INSERT INTO `bolsaPalabras`(`palabra`, `palabraMostrar`, `semantica`, `idContexto`, `tipo`) VALUES ("finca","finca","hotel",1,1);
INSERT INTO `bolsaPalabras`(`palabra`, `palabraMostrar`, `semantica`, `idContexto`, `tipo`) VALUES ("fincas","fincas","hotel",1,1);
INSERT INTO `bolsaPalabras`(`palabra`, `palabraMostrar`, `semantica`, `idContexto`, `tipo`) VALUES ("hacienda","hacienda","hotel",1,1);
INSERT INTO `bolsaPalabras`(`palabra`, `palabraMostrar`, `semantica`, `idContexto`, `tipo`) VALUES ("haciendas","haciendas","hotel",1,1);
INSERT INTO `bolsaPalabras`(`palabra`, `palabraMostrar`, `semantica`, `idContexto`, `tipo`) VALUES ("parcela","parcela","hotel",1,1);
INSERT INTO `bolsaPalabras`(`palabra`, `palabraMostrar`, `semantica`, `idContexto`, `tipo`) VALUES ("parcelas","parcelas","hotel",1,1);
INSERT INTO `bolsaPalabras`(`palabra`, `palabraMostrar`, `semantica`, `idContexto`, `tipo`) VALUES ("hato","hato","hotel",1,1);
INSERT INTO `bolsaPalabras`(`palabra`, `palabraMostrar`, `semantica`, `idContexto`, `tipo`) VALUES ("hatos","hatos","hotel",1,1);
INSERT INTO `bolsaPalabras`(`palabra`, `palabraMostrar`, `semantica`, `idContexto`, `tipo`) VALUES ("pension","pension","hotel",1,1);
INSERT INTO `bolsaPalabras`(`palabra`, `palabraMostrar`, `semantica`, `idContexto`, `tipo`) VALUES ("pensiones","pensiones","hotel",1,1);
INSERT INTO `bolsaPalabras`(`palabra`, `palabraMostrar`, `semantica`, `idContexto`, `tipo`) VALUES ("alojamiento","alojamiento","hotel",1,1);
INSERT INTO `bolsaPalabras`(`palabra`, `palabraMostrar`, `semantica`, `idContexto`, `tipo`) VALUES ("alojamientos","alojamientos","hotel",1,1);
INSERT INTO `bolsaPalabras`(`palabra`, `palabraMostrar`, `semantica`, `idContexto`, `tipo`) VALUES ("suite","suite","hotel",1,1);
INSERT INTO `bolsaPalabras`(`palabra`, `palabraMostrar`, `semantica`, `idContexto`, `tipo`) VALUES ("suites","suites","hotel",1,1);
INSERT INTO `bolsaPalabras`(`palabra`, `palabraMostrar`, `semantica`, `idContexto`, `tipo`) VALUES ("habitacion","habitacion","hotel",1,1);
INSERT INTO `bolsaPalabras`(`palabra`, `palabraMostrar`, `semantica`, `idContexto`, `tipo`) VALUES ("habitaciones","habitaciones","hotel",1,1);
INSERT INTO `bolsaPalabras`(`palabra`, `palabraMostrar`, `semantica`, `idContexto`, `tipo`) VALUES ("hospederia","hospederia","hotel",1,1);
INSERT INTO `bolsaPalabras`(`palabra`, `palabraMostrar`, `semantica`, `idContexto`, `tipo`) VALUES ("hospederias","hospederias","hotel",1,1);
INSERT INTO `bolsaPalabras`(`palabra`, `palabraMostrar`, `semantica`, `idContexto`, `tipo`) VALUES ("parcela","parcela","hotel",1,1);
INSERT INTO `bolsaPalabras`(`palabra`, `palabraMostrar`, `semantica`, `idContexto`, `tipo`) VALUES ("parcela","parcela","hotel",1,1);
INSERT INTO `bolsaPalabras`(`palabra`, `palabraMostrar`, `semantica`, `idContexto`, `tipo`) VALUES ("parcela","parcela","hotel",1,1);
INSERT INTO `bolsaPalabras`(`palabra`, `palabraMostrar`, `semantica`, `idContexto`, `tipo`) VALUES ("parcela","parcela","hotel",1,1);
INSERT INTO `bolsaPalabras`(`palabra`, `palabraMostrar`, `semantica`, `idContexto`, `tipo`) VALUES ("parcela","parcela","hotel",1,1);
INSERT INTO `bolsaPalabras`(`palabra`, `palabraMostrar`, `semantica`, `idContexto`, `tipo`) VALUES ("parcela","parcela","hotel",1,1);


INSERT INTO `contexto`(`contexto`) VALUES ("hospedaje");
INSERT INTO `contexto`(`contexto`) VALUES ("alimentacion");
INSERT INTO `contexto`(`contexto`) VALUES ("transporte");
INSERT INTO `contexto`(`contexto`) VALUES ("licor");
INSERT INTO `contexto`(`contexto`) VALUES ("compras");
INSERT INTO `contexto`(`contexto`) VALUES ("ecoturismo");
INSERT INTO `contexto`(`contexto`) VALUES ("playa");
INSERT INTO `contexto`(`contexto`) VALUES ("lago");
INSERT INTO `contexto`(`contexto`) VALUES ("rio");
INSERT INTO `contexto`(`contexto`) VALUES ("monatana");
INSERT INTO `contexto`(`contexto`) VALUES ("llano");
INSERT INTO `contexto`(`contexto`) VALUES ("historia");
INSERT INTO `contexto`(`contexto`) VALUES ("folklore");
INSERT INTO `contexto`(`contexto`) VALUES ("cine");
INSERT INTO `contexto`(`contexto`) VALUES ("teatro");
INSERT INTO `contexto`(`contexto`) VALUES ("baile");
INSERT INTO `contexto`(`contexto`) VALUES ("parquenatural");
INSERT INTO `contexto`(`contexto`) VALUES ("parquemecanico");
INSERT INTO `contexto`(`contexto`) VALUES ("extremo");
INSERT INTO `contexto`(`contexto`) VALUES ("paseos");

select bolsaPalabras.palabra, bolsaPalabras.palabraMostrar, bolsaPalabras.semantica, bolsaPalabras.tipo, contexto.contexto FROM bolsaPalabras, contexto 
WHERE bolsaPalabras.idContexto = contexto.idContexto 


tabla dt
id
latitud
longitud
codUbigeo-estado
codUbigeo-municipio
codUbigeo-parroquia
status (0 lugar cerrado, 1 lugar abierto, abierto-full 2)
nombresLugar
idContexto
palabras-lugar
palabras-producto
palabras-caracteristica
url
flag-formas-pago (1 punto, 2 transferencia, 4 efectivo, 8 credito)


INSERT INTO `dt`(`latitud`, `longitud`, `nombres`) VALUES (10.2539264, -67.5905535, "Marapan")
SELECT * FROM `dt` WHERE `latitud`=10.2539264
https://www.google.com/maps/@10.2539264,-67.5905535,15

lat-actual=10.2491163
lon-actual=-67.5859457
distancia-radio=1500

select *, (
    6371 * acos(cos(radians(10.2491163)) * cos(radians(latitud)) * cos(radians(longitud) - radians(-67.5859457)) + sin(radians(10.2491163)) * sin(radians(latitud)))) as distancia 
from dt 
where idContexto=2 AND nombres like '%marapan%' AND (`palabras-lugar` LIKE '%pan%' OR `palabras-lugar` LIKE '%arroz%')  AND
    latitud between (10.2491163-0.5) and (10.2491163+0.5) and 
    longitud between (-67.5859457-0.5) and (-67.5859457+0.5)  
having distancia < (2000/1000);


SELECT dt.*, (
    6371 * acos(cos(radians(10.2491163)) * cos(radians(latitud)) * cos(radians(longitud) - radians(-67.5859457)) + sin(radians(10.2491163)) * sin(radians(latitud)))) as distancia 
FROM dt INNER JOIN dtBolsaPalabras ON (dt.idDt = dtBolsaPalabras.idDt)
WHERE 
(idContexto=2) 
AND 
(dt.flagCaracteristicas & (1<<0))
AND
(dt.flagCaracteristicas & (1<<2))
AND
(dtBolsaPalabras.idBolsaPalabra=56) 
AND
    latitud BETWEEN (10.2491163-0.5) AND (10.2491163+0.5) AND 
    longitud BETWEEN (-67.5859457-0.5) AND (-67.5859457+0.5)  
GROUP BY dt.idDt 
HAVING distancia < (2000/1000);