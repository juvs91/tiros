<?php
/*
este archivo tiene la conexion a la base de datos , para llamarse solo se tiene que hace include 'config.php'
*/
$host="localhost";//nombre del host
$user="sgoris@gmail.com";//nombre del usuario
$pass="MamaJuve#1";//password del usuario
$db_name="shoot_goal";//nombre de la base de datos
//hacer la conexion con la base de datos
/* verificar conexión */
if (mysqli_connect_errno()) {
    printf("Error de conexión: %s\n", mysqli_connect_error());
    exit();
}
function begin(){
	@mysql_query("BEGIN");
}
function commit(){
	@mysql_query("COMMIT");
}
function rollback(){
	@mysql_query("ROLLBACK");
}

?>