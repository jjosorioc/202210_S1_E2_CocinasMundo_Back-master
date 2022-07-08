---------INGREDIENTES
--GRUPO A
INSERT INTO INGREDIENTE_ENTITY (ID,  CATEGORIA, DESCRIPCION, HISTORIA, MARCAS_REPRESENTATIVAS, NOMBRE) VALUES (1, 'HUEVOS', 'Uno de los alimentos más presentes en la alimentación humana. Particularmente por su capacidad aglutinante, que permite usarlos para crear recetas complejas.', 'Vino de la gallina, o quizá fue de la manera contraria.', 'Kikes, Gallina feliz,
 Santa Reyes', 'Huevo');
INSERT INTO INGREDIENTE_ENTITY (ID,  CATEGORIA, DESCRIPCION, HISTORIA, MARCAS_REPRESENTATIVAS, NOMBRE) VALUES (2, 'CARNES MADURADAS', 'Ingrediente muy popular en la cultura Ibérica. Se obtiene a partir de la salazón y secado al aire de las patas traseras del cerdo.', 'Sus diferentes versiones han mantenido la tradicción de los txarcuteros de antaño. Probablemente, evolucionó de una práctica para preservar las carnes para el invierno.', 'Monte nevado, Pomona, Monticello', 'Jamón Serrano');
INSERT INTO INGREDIENTE_ENTITY (ID,  CATEGORIA, DESCRIPCION, HISTORIA, MARCAS_REPRESENTATIVAS, NOMBRE) VALUES (3, 'PATATAS', 'Tubérculo alto en carbohidratos almidonados.', 'Se dominó su cultivo en la zona del altiplano Andino, para luego ser llevada a Europa durante la colonización.', 'Granel, Carulla', 'Papa Pastusa');
INSERT INTO INGREDIENTE_ENTITY (ID,  CATEGORIA, DESCRIPCION, HISTORIA, MARCAS_REPRESENTATIVAS, NOMBRE) VALUES (4, 'ACEITES', 'Fuente de grasa vegetal, muy utilizado para conducir calor o fritar alientos.', 'Es un alimento casi exclusivo a la zona del mediterráneo, donde aún se obtiene el 97% del aceite de oliva  del  mundo.', 'La Española, Carulla, Carbonell', 'Aceite de oliva');




---------REGION

--MEDITERRÁNEA
INSERT INTO REGION_ENTITY(ID, NOMBRE) VALUES (1, 'MEDITERRÁNEA');
INSERT INTO REGION_ENTITY(ID, NOMBRE) VALUES (2, 'LATINOAMAERICA');
INSERT INTO REGION_ENTITY(ID, NOMBRE) VALUES (3, 'EUROPA');

---------UBICACIÓN
INSERT INTO UBICACION_ENTITY (ID, LATITUD_UBICACION, LONGITUD_UBICACION) VALUES (1, 40.41227993394692, -3.7096356020101346);
INSERT INTO UBICACION_ENTITY (ID, LATITUD_UBICACION, LONGITUD_UBICACION) VALUES (2, 4.7110, 74.0721);
INSERT INTO UBICACION_ENTITY (ID, LATITUD_UBICACION, LONGITUD_UBICACION) VALUES (3, 43.79255141961347, 7.528901148803425);
INSERT INTO UBICACION_ENTITY (ID, LATITUD_UBICACION, LONGITUD_UBICACION) VALUES (4, 55.688288816237296, 12.60975123727623);
INSERT INTO UBICACION_ENTITY (ID, LATITUD_UBICACION, LONGITUD_UBICACION) VALUES (5, 43.11600521892108, -2.598458373609808);
INSERT INTO UBICACION_ENTITY (ID, LATITUD_UBICACION, LONGITUD_UBICACION) VALUES (6, 41.37791500120127, 2.165446566992636);
INSERT INTO UBICACION_ENTITY (ID, LATITUD_UBICACION, LONGITUD_UBICACION) VALUES (7, 43.311727292078764, -1.926723552364599);
INSERT INTO UBICACION_ENTITY (ID, LATITUD_UBICACION, LONGITUD_UBICACION) VALUES (8, 43.28356382998645, 0.0911353166268918);
INSERT INTO UBICACION_ENTITY (ID, LATITUD_UBICACION, LONGITUD_UBICACION) VALUES (9, 23.634501, -102.552784);

---------PAIS
INSERT INTO PAIS_ENTITY (ID, NOMBRE, UBICACION_ID) VALUES (1, 'España', 1);
INSERT INTO PAIS_ENTITY (ID, NOMBRE, UBICACION_ID) VALUES (2, 'Colombia', 2);
INSERT INTO PAIS_ENTITY (ID, NOMBRE, UBICACION_ID) VALUES (3, 'Francia', 3);
INSERT INTO PAIS_ENTITY (ID, NOMBRE, UBICACION_ID) VALUES (4, 'Dinamarca', 4);



---------PAIS-REGION
INSERT INTO PAIS_ENTITY_REGIONES (PAISES_ID, REGIONES_ID) VALUES (1, 1);
INSERT INTO PAIS_ENTITY_REGIONES (PAISES_ID, REGIONES_ID) VALUES (2, 2);
INSERT INTO PAIS_ENTITY_REGIONES (PAISES_ID, REGIONES_ID) VALUES (3, 3);
INSERT INTO PAIS_ENTITY_REGIONES (PAISES_ID, REGIONES_ID) VALUES (4, 3);



---------CULTURA CULINARIA
INSERT INTO CULTURA_CULINARIA_ENTITY (ID, CALIFICACION_CULTURA, DESCRIPCION_CULTURA, IMAGEN, NOMBRE_CULTURA, PAIS_ID, REGION_ID) VALUES (1, 5, 'La cocina española está fuertemente influida a lo largo de su historia por los pueblos que conquistan su territorio, así como de los pueblos que posteriormente conquista y coloniza.', 'https://www.excelenciasgourmet.com/sites/default/files/styles/slideshow_large/public/2018-12/gastronomia-espa%C3%B1ola.jpg?itok=tkBUtFkc', 'Española', 1, 1 );
INSERT INTO CULTURA_CULINARIA_ENTITY(ID,CALIFICACION_CULTURA, DESCRIPCION_CULTURA, IMAGEN, NOMBRE_CULTURA, PAIS_ID, REGION_ID) VALUES (2, 4, 'Latinoamérica tiene una gastronomía propia nacida de su tradición y sus orígenes culturales. Su cocina no es más que el resultado de la historia de sus pueblos', 'https://eldiariony.com/wp-content/uploads/sites/2/2021/11/Comida-latinoamericana.jpg?quality=80&strip=all&w=960', 'Latinoamericana', 2, 2 );
INSERT INTO CULTURA_CULINARIA_ENTITY(ID,CALIFICACION_CULTURA, DESCRIPCION_CULTURA, IMAGEN, NOMBRE_CULTURA, PAIS_ID, REGION_ID) VALUES (3, 5, 'El Mediterráneo, lugar de origen de la exquisita comida mediterránea, morada de Zeus y otros dioses. Un paraíso que es cuna de uno de los placeres gastronómicos más apetecidos en el planeta.', 'https://as01.epimg.net/deporteyvida/imagenes/2018/05/03/portada/1525334231_410962_1525334326_noticia_normal_recorte1.jpg', 'Mediterranea', 3, 3);


---------CIUDADES
INSERT INTO  CIUDAD_ENTITY  (ID, NOMBRE, PAIS_ID, UBICACION_ID) VALUES (1, 'Madrid', 1, 1);
INSERT INTO  CIUDAD_ENTITY  (ID, NOMBRE , PAIS_ID, UBICACION_ID) VALUES (2, 'Bogotá', 2, 2);
INSERT INTO  CIUDAD_ENTITY  (ID, NOMBRE , PAIS_ID, UBICACION_ID) VALUES (3, 'Menton', 3, 3);
INSERT INTO  CIUDAD_ENTITY  (ID, NOMBRE , PAIS_ID, UBICACION_ID) VALUES (4, 'Norrebro', 4, 4);
INSERT INTO  CIUDAD_ENTITY  (ID, NOMBRE , PAIS_ID, UBICACION_ID) VALUES (5, 'Bizkaia', 1, 5);
INSERT INTO  CIUDAD_ENTITY  (ID, NOMBRE , PAIS_ID, UBICACION_ID) VALUES (6, 'Barcelona', 1, 6);
INSERT INTO  CIUDAD_ENTITY  (ID, NOMBRE , PAIS_ID, UBICACION_ID) VALUES (7, 'Gipuzkoa', 1, 7);
INSERT INTO  CIUDAD_ENTITY  (ID, NOMBRE , PAIS_ID, UBICACION_ID) VALUES (8, 'Tarbes', 3, 8);


---------ESTRELLA MICHELIN
INSERT INTO ESTRELLA_MICHELIN_ENTITY (ID, DESCRIPCION, FECHA_ENTREGADA,TIPO_ESTRELLA) VALUES (1, 'Dada a uno de los platos insignia', '2018', 'Primera');
INSERT INTO ESTRELLA_MICHELIN_ENTITY (ID, DESCRIPCION, FECHA_ENTREGADA,TIPO_ESTRELLA) VALUES (2, 'Dada por la calidad de la comida', '2019', 'Primera');
INSERT INTO ESTRELLA_MICHELIN_ENTITY (ID, DESCRIPCION, FECHA_ENTREGADA,TIPO_ESTRELLA) VALUES (3, 'Dada a uno de los platos insignia', '2017', 'Segunda');
INSERT INTO ESTRELLA_MICHELIN_ENTITY (ID, DESCRIPCION, FECHA_ENTREGADA,TIPO_ESTRELLA) VALUES (4, 'Platos inspirados por las montañas y el mar de la zona', '2019', 'Tercera');
INSERT INTO ESTRELLA_MICHELIN_ENTITY (ID, DESCRIPCION, FECHA_ENTREGADA,TIPO_ESTRELLA) VALUES (5, 'Menu variante en todo el año', '2019', 'Tercera');
INSERT INTO ESTRELLA_MICHELIN_ENTITY (ID, DESCRIPCION, FECHA_ENTREGADA,TIPO_ESTRELLA) VALUES (6, 'Excelente manejo de la parrilla', '2020', 'Segunda');
INSERT INTO ESTRELLA_MICHELIN_ENTITY (ID, DESCRIPCION, FECHA_ENTREGADA,TIPO_ESTRELLA) VALUES (7, 'Lugar unico en españa', '2006', 'Tercera');
INSERT INTO ESTRELLA_MICHELIN_ENTITY (ID, DESCRIPCION, FECHA_ENTREGADA,TIPO_ESTRELLA) VALUES (8, 'Especialista por usar ingredientes organicos', '2018', 'Primera');
INSERT INTO ESTRELLA_MICHELIN_ENTITY (ID, DESCRIPCION, FECHA_ENTREGADA,TIPO_ESTRELLA) VALUES (9, 'Sus platos juegan con las verduras de una manera especial', '2017', 'Segunda');


---------RESTAURANTES

--SERRATA
INSERT INTO RESTAURANTE_ENTITY(ID, DIRECCION_RESTAURANTE, IMAGEN, NOMBRE_RESTAURANTE, CIUDAD_ID, ESTRELLA_MICHELLIN_ID)
VALUES (1, 'AUTOPISTA NORTE - 114 44 BOGOTÁ', 'https://bestrestaurantsincolombia.com/public/images/image_list/9f19d6ae7f3f298338ba206d76329323_head_format.jpg', 'Seratta Gourmand Market', 2, 1);
--HARRY SASSON
INSERT INTO RESTAURANTE_ENTITY(ID, DIRECCION_RESTAURANTE, IMAGEN, NOMBRE_RESTAURANTE, CIUDAD_ID, ESTRELLA_MICHELLIN_ID)
VALUES (2, 'Cra. 9 #75-70', 'https://bestrestaurantsincolombia.com/public/images/image_list/8720cd028b49dbbce01523c1125a2b69_head_format.jpg', 'Harry Sasson', 2, 2);
--CRITERIÓN
INSERT INTO RESTAURANTE_ENTITY(ID, DIRECCION_RESTAURANTE, IMAGEN, NOMBRE_RESTAURANTE, CIUDAD_ID, ESTRELLA_MICHELLIN_ID)
VALUES (3, 'Cl. 69a ## 5 – 75', 'https://media-cdn.tripadvisor.com/media/photo-s/03/fe/eb/80/criterion.jpg', 'Crieterión', 2, 3);
--MIRAZUR
INSERT INTO RESTAURANTE_ENTITY(ID, DIRECCION_RESTAURANTE, IMAGEN, NOMBRE_RESTAURANTE, CIUDAD_ID, ESTRELLA_MICHELLIN_ID)
VALUES (4, '30 Av. Aristide Briand', 'https://s3.amazonaws.com/arc-wordpress-client-uploads/infobae-wp/wp-content/uploads/2019/06/25123114/Mauro-Colagreco-Restaurant-Mirazur-1920-1.jpg', 'Restaurant Mirazur', 3, 4);
--NUEVO NOMA
INSERT INTO RESTAURANTE_ENTITY(ID, DIRECCION_RESTAURANTE, IMAGEN, NOMBRE_RESTAURANTE, CIUDAD_ID, ESTRELLA_MICHELLIN_ID)
VALUES (5, '30 Av. Aristide Briand', 'https://diariodesign.com/wp-content/uploads/2018/10/nuevo-noma-big-diariodesign-942x531.jpg', 'Nuevo noma', 4, 5);
--ASADOR ETXEBARRI
INSERT INTO RESTAURANTE_ENTITY(ID, DIRECCION_RESTAURANTE, IMAGEN, NOMBRE_RESTAURANTE, CIUDAD_ID, ESTRELLA_MICHELLIN_ID)
VALUES (6, 'San Juan Plaza, 1, 48291 Axpe', 'https://encuinarte.com/wp-content/uploads/2020/03/Etxebarri_02-1024x683.jpg', 'Asador Etxebarri', 1, 6);
--SANT PAU
INSERT INTO RESTAURANTE_ENTITY(ID, DIRECCION_RESTAURANTE, IMAGEN, NOMBRE_RESTAURANTE, CIUDAD_ID, ESTRELLA_MICHELLIN_ID)
VALUES (7, 'Ronda de Sant Pau, 39', 'https://www.hola.com/imagenes/cocina/noticiaslibros/20180706126767/cierre-restaurante-sant-pau-carme-ruscalleda/0-583-63/sant-pau-cierre-t.jpg', 'Sant Pau', 1, 7);
--GERANIUM
INSERT INTO RESTAURANTE_ENTITY(ID, DIRECCION_RESTAURANTE, IMAGEN, NOMBRE_RESTAURANTE, CIUDAD_ID, ESTRELLA_MICHELLIN_ID)
VALUES (8, 'Per Henrik Lings Allé 4, 8. Sal, 2100 København', 'https://imagesvc.meredithcorp.io/v3/mm/image?q=60&c=sc&poi=face&w=2000&h=1000&url=https%3A%2F%2Fstatic.onecms.io%2Fwp-content%2Fuploads%2Fsites%2F9%2F2021%2F11%2F19%2FGeranium-Interior-FT-BLOG1121.jpg', 'Geranium', 4, 8);
--MUGARITZ
INSERT INTO RESTAURANTE_ENTITY(ID, DIRECCION_RESTAURANTE, IMAGEN, NOMBRE_RESTAURANTE, CIUDAD_ID, ESTRELLA_MICHELLIN_ID)
VALUES (9, 'Aldura Gunea Aldea, 20, 20100 Errenteria', 'https://ep00.epimg.net/elviajero/imagenes/2016/06/02/actualidad/1464863201_043441_1464864163_noticia_normal.jpg', 'Geranium', 7, 9);
--Arpège
INSERT INTO RESTAURANTE_ENTITY(ID, DIRECCION_RESTAURANTE, IMAGEN, NOMBRE_RESTAURANTE, CIUDAD_ID, ESTRELLA_MICHELLIN_ID)
VALUES (10, '22 Pl. de Verdun', 'https://www.excelenciasgourmet.com/sites/default/files/styles/slideshow_large/public/2017-12/L%E2%80%99Arp%C3%A8ge.jpg?itok=Y8PO_Hww', 'Arpège', 8, null);

--Casa Lucio
INSERT INTO RESTAURANTE_ENTITY (ID, DIRECCION_RESTAURANTE, IMAGEN, NOMBRE_RESTAURANTE, CIUDAD_ID, ESTRELLA_MICHELLIN_ID) VALUES (11, 'Calle de la Cava Baja, 35', 'https://www.azureazure.com/wp-content/uploads/2019/09/Gastronomy-Casa-Lucio-Article01.jpg', 'Casa Lucio', 1, null);

---------RESTAURANTE-CULTURAS_CULINARIAS
INSERT INTO RESTAURANTE_ENTITY_CULTURAS_CULINARIAS (RESTAURANTES_ID, CULTURAS_CULINARIAS_ID)  VALUES (11, 1);



---------PLATO
INSERT INTO PLATO_ENTITY (ID, DESCRIPCION_PLATO, NOMBRE_PLATO, PRECIO, CULTURA_CULINARIA_ID) VALUES (1, 'Huevos cocinados al mejor estilo de Lucio.', 'Huevos Rotos estilo Lucio', 9.99, 1);

INSERT INTO PLATO_ENTITY (ID, DESCRIPCION_PLATO, NOMBRE_PLATO, PRECIO, CULTURA_CULINARIA_ID) VALUES (2, 'Bocados para deleitarse: scallops con mantequilla, jerez, pepino encurtido y queso Grana Padano.', 'Scallops al fuego', 49.100, 3);
INSERT INTO PLATO_ENTITY (ID, DESCRIPCION_PLATO, NOMBRE_PLATO, PRECIO, CULTURA_CULINARIA_ID) VALUES (3, ' Crunchy Dumplings  de cerdo con hongos silvestres y confit de manzana, sumergelos en nuestra salsa tare a base de dashi y hierbas frescas con unas notas ligeras de picante.', 'Dumplings de cerdo con Hongos Silvestres', 43.200, 3);

INSERT INTO PLATO_ENTITY (ID, DESCRIPCION_PLATO, NOMBRE_PLATO, PRECIO, CULTURA_CULINARIA_ID) VALUES (4, 'El mejor asado de tira de certified angus beed', 'Asado de tira', 170.000, 2);
INSERT INTO PLATO_ENTITY (ID, DESCRIPCION_PLATO, NOMBRE_PLATO, PRECIO, CULTURA_CULINARIA_ID) VALUES (5, 'Chorizo a la parrilla con mostaza', 'Chorizo Harrys Bar', 36.400, 2);

INSERT INTO PLATO_ENTITY (ID, DESCRIPCION_PLATO, NOMBRE_PLATO, PRECIO, CULTURA_CULINARIA_ID) VALUES (6, 'Nuestro menú especial para que puebe nuestros platos estrella', 'Menú degustación', 180, 1);
INSERT INTO PLATO_ENTITY (ID, DESCRIPCION_PLATO, NOMBRE_PLATO, PRECIO, CULTURA_CULINARIA_ID) VALUES (7, 'Brandada de bacalao, pimientos de colores, olivas negras', 'Mondrian Gastronomico', 68, 1);

INSERT INTO PLATO_ENTITY (ID, DESCRIPCION_PLATO, NOMBRE_PLATO, PRECIO, CULTURA_CULINARIA_ID) VALUES (8, 'Acompañado con verduras', 'Carpaccio De Pulpo Con Vinagreta De Aceituna', 70.500, 3);
INSERT INTO PLATO_ENTITY (ID, DESCRIPCION_PLATO, NOMBRE_PLATO, PRECIO, CULTURA_CULINARIA_ID) VALUES (9, 'Deliciosa cerveza de Dinamarca', ' Lolland Falster Guld', 50, 3);



---------RESTAURANTES-PLATOS
INSERT INTO RESTAURANTE_ENTITY_PLATOS (RESTAURANTES_ID, PLATOS_ID) VALUES (11, 1);
INSERT INTO RESTAURANTE_ENTITY_PLATOS (RESTAURANTES_ID, PLATOS_ID) VALUES (3, 2);
-- RESTAURANTES DE CULTURAS CULINARIAS
INSERT INTO RESTAURANTE_ENTITY_CULTURAS_CULINARIAS(RESTAURANTES_ID ,CULTURAS_CULINARIAS_ID ) VALUES (1,2);
INSERT INTO RESTAURANTE_ENTITY_CULTURAS_CULINARIAS(RESTAURANTES_ID ,CULTURAS_CULINARIAS_ID ) VALUES (1,3);
INSERT INTO RESTAURANTE_ENTITY_CULTURAS_CULINARIAS(RESTAURANTES_ID ,CULTURAS_CULINARIAS_ID ) VALUES (2,2);
INSERT INTO RESTAURANTE_ENTITY_CULTURAS_CULINARIAS(RESTAURANTES_ID ,CULTURAS_CULINARIAS_ID ) VALUES (3,3);
INSERT INTO RESTAURANTE_ENTITY_CULTURAS_CULINARIAS(RESTAURANTES_ID ,CULTURAS_CULINARIAS_ID ) VALUES (4,3);
INSERT INTO RESTAURANTE_ENTITY_CULTURAS_CULINARIAS(RESTAURANTES_ID ,CULTURAS_CULINARIAS_ID ) VALUES (5,3);
INSERT INTO RESTAURANTE_ENTITY_CULTURAS_CULINARIAS(RESTAURANTES_ID ,CULTURAS_CULINARIAS_ID ) VALUES (6,1);
INSERT INTO RESTAURANTE_ENTITY_CULTURAS_CULINARIAS(RESTAURANTES_ID ,CULTURAS_CULINARIAS_ID ) VALUES (7,1);
INSERT INTO RESTAURANTE_ENTITY_CULTURAS_CULINARIAS(RESTAURANTES_ID ,CULTURAS_CULINARIAS_ID ) VALUES (8,3);
INSERT INTO RESTAURANTE_ENTITY_CULTURAS_CULINARIAS(RESTAURANTES_ID ,CULTURAS_CULINARIAS_ID ) VALUES (9,3);
INSERT INTO RESTAURANTE_ENTITY_CULTURAS_CULINARIAS(RESTAURANTES_ID ,CULTURAS_CULINARIAS_ID ) VALUES (10,3);



---------RECETA
INSERT INTO RECETA_ENTITY (ID, DESCRIPCION, IMAGENES, NOMBRE, PREPARACION, PLATO_ID) VALUES (1, 'Huevos cocinados al mejor estilo de Lucio. Una verdadera marca de la ciudad.', 'https://s1.eestatic.com/2020/03/30/cocinillas/recetas/aperitivos-y-entrantes/huevo-patatas-aceite_de_oliva_478712808_149359900_1706x960.jpg', 'Huevos Rotos al estilo de Lucio', 'Freímos los huevos y eso podemos hacerlo al gusto de cada uno, pero es importante que no dejemos que se cuaje la yema, pues es lo que hará de “salsa” cuando rompamos los huevos sobre las patatas.', 1);
INSERT INTO RECETA_ENTITY (ID, DESCRIPCION, IMAGENES, NOMBRE, PREPARACION, PLATO_ID) VALUES (2, 'Bocados para deleitarse: scallops con mantequilla, jerez, pepino encurtido y queso Grana Padano.', 'https://bakeitwithlove.com/wp-content/uploads/2021/03/Pan-Seared-Scallops-h1.jpg', 'Scallops al fuego', 'Combine wine and shallots in a small saucepan; bring to a boil. Cook 6 minutes or until mixture is reduced to 2 tablespoons. Strain through a sieve into a bowl; discard solids. Return mixture to pan.', 2);
INSERT INTO RECETA_ENTITY (ID, DESCRIPCION, IMAGENES, NOMBRE, PREPARACION, PLATO_ID) VALUES (3, 'Crunchy Dumplings  de cerdo con hongos silvestres y confit de manzana, sumergelos en nuestra salsa tare a base de dashi y hierbas frescas con unas notas ligeras de picante.', 'https://cdn.shopify.com/s/files/1/0191/9978/files/Dumplings_de_setas_y_cerdo_-_Claudia_Julia.jpg?v=1504172415', 'Dumplings de cerdo con Hongos Silvestres', 'Retiramos las tres primeras hojas de la col china, las lavamos bien, las secamos y retiramos la parte central dura.', 3);
INSERT INTO RECETA_ENTITY (ID, DESCRIPCION, IMAGENES, NOMBRE, PREPARACION, PLATO_ID) VALUES (4, 'El mejor asado de tira de certified angus beed', 'https://t1.rg.ltmcdn.com/es/posts/1/4/1/asado_de_tira_74141_paso_10_600.webp', 'Asado de tira', 'Para comenzar con el asado de tira, elige si usarás carbón vegetal o leña. En este caso, lo haremos con leña como dicta la tradición, pero también puedes salir de apuros y usar carbón vegetal.', 4);
INSERT INTO RECETA_ENTITY (ID, DESCRIPCION, IMAGENES, NOMBRE, PREPARACION, PLATO_ID) VALUES (5, 'Chorizo a la parrilla con mostaza', 'http://carnesproa.com/wp-content/uploads/2017/01/proa_chorizos_alvino.jpg', 'Chorizo Harrys Bar', 'Este chorizo se prepara únicamente con carne de cerdo, especialmente para Harry Sasson y con condimentos italianos, dando como resultado un sabor que no tiene nada que ver con el típico colombiano.', 5);


---------INGREDIENTES-RECETAS
INSERT INTO INGREDIENTE_ENTITY_RECETAS (INGREDIENTES_ID, RECETAS_ID) VALUES (1, 1);

---------RESTAURANTES-PLATOS
INSERT INTO RESTAURANTE_ENTITY_PLATOS (RESTAURANTES_ID, PLATOS_ID) VALUES (11, 1);
INSERT INTO RESTAURANTE_ENTITY_PLATOS (RESTAURANTES_ID, PLATOS_ID) VALUES (1, 2);
INSERT INTO RESTAURANTE_ENTITY_PLATOS (RESTAURANTES_ID, PLATOS_ID) VALUES (1, 3);
INSERT INTO RESTAURANTE_ENTITY_PLATOS (RESTAURANTES_ID, PLATOS_ID) VALUES (2, 4);
INSERT INTO RESTAURANTE_ENTITY_PLATOS (RESTAURANTES_ID, PLATOS_ID) VALUES (2, 5);
INSERT INTO RESTAURANTE_ENTITY_PLATOS (RESTAURANTES_ID, PLATOS_ID) VALUES (6, 6);
INSERT INTO RESTAURANTE_ENTITY_PLATOS (RESTAURANTES_ID, PLATOS_ID) VALUES (7, 7);
INSERT INTO RESTAURANTE_ENTITY_PLATOS (RESTAURANTES_ID, PLATOS_ID) VALUES (5, 8);
INSERT INTO RESTAURANTE_ENTITY_PLATOS (RESTAURANTES_ID, PLATOS_ID) VALUES (8, 9);


-- RESTAURANTES DE CULTURAS CULINARIAS
