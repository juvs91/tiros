<?php

$tiro=$_POST["tiro"];
echo $tiro;
/*//todos los includes
include 'GlobalInfo.php';

//es el codigo de tiro 0 si es fuera 1 primer cuadrante , 2 segundo cuadrante 3 tercer cuadrante 
$tiro=$_POST["tiro"];
$tirador=$_POST["tirador"];
$portero=$_POST["portero"];

//almacenare en la base de datos la persona que iso el tiro , para quien y el codigo 
$queryGuardar="INSERT INTO juegosPendientes VALUES(NULL,?,?,?)";

$mysqliTiro=new mysqli($host, $user, $pass, $db_name);//un nuevo objeto de mysqli llamado mysqliTiro

$statement =$mysqliTiro->prepare($queryGuardar);//en este statement guardo el query preparado solo para pasarle los parametros que bienen siendo los datos a guardar a la base de datos , sirve para sanitisar la consulta  a la base de datos 
$statement->bind_param("iss",$tiro,$tirador,$portero);

begin();//inicio una transaccion
$result=$statement->execute();//ejecuto el query y lo gusrdo en un resultado 
if (!$result) {//verifico si se creo 
	rollback();//rollbackeo la consulta
	echo "problemas checalo chango"; 
	exit;//salgo ya que ocurrio un error ya sea de conexion o de consulta o de parametros etc.
} else {
	$idInsertado=$statement->insert_id;//con esto obtengo el id del query por si lo llego a nesesitar despues 
	commit();//comiteo la transaccion , todo salio bien creo 
	echo "consulta exitosa";
}


*/

?>