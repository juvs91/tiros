<?php
include 'baseDatos.php';

$baseDatos=new baseDatos();
//en este apartado recojo todos los parametros 

$jugador1=$_POST["jugador1"];
$jugador2=$_POSt["jugador2"];
$status=$_POST["status"];
$posTiro=$_POST["posTiro"];
$aceptado=$_POST["aceptado"];
$posParo=$_POST["posParo"];

if($aceptado){
	$aceptado=1;
}




//los objetos de mysqli
$tiro=new mysqli($baseDatos->getHost(),$baseDatos->getUser(),$baseDatos->getPass(), $baseDatos->getDB());

//los querys a ejectuar
$tiroQuery="INSERT INTO juegos VALUES(NULL,?,?,?,?,?,CURRENT_TIMESTAMP)";

//creo un statement para prepara el query
$statement=$tiro->prepare($tiroQuery);
$statement->bind_param("iiiii",$jugador1,$jugador2,$status,$posTiro,$posParo);
$baseDatos->begin();
$result=$statement->execute();
if (!$result) {
	$baseDatos->rollback();
	exit;
} else {
	$idInsertado=$statement->insert_id;//con esto obtengo el id del query
	$baseDatos->commit();
}
echo json_encode($idInsertado);

?>