create table juegos(
id int NOT NULL,
idPortero int NOT NULL,
idTirador int NOT NULL,
estado int NOT NULL,
PRIMARY KEY (id),
FOREIGN KEY (idPortero) REFERENCES usuarios(id) on delete cascade on update cascade,
FOREIGN KEY (idTirador) REFERENCES usuarios(id) on delete cascade on update cascade
)engine innoDB

