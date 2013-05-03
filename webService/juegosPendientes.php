<?php
//todos los includes
include 'baseDatos.php';

//todos los datos que viene en el header (body)
$id=$_POST["id"];

//tomare de la base de datos los archivos que voy a seleccionar 
$query="SELECT u.nombre FROM juegosPendientes j,usuarios u WHERE j.uId=?";

$mysqliPendiente=new mysqli($host, $user, $pass, $db_name);//un nuevo objeto de mysqli llamado mysqliPendiente
$statement=$mysqliPendiente->prepare($query);//una variable statemente que  contiene el query sanitisado solo para pasarle los params 

$statement->bind_param("i",$id);//le paso los parametros por referencia no por valor 

begin();
$statement->execute();//se ejecuta 
$result=$statement->bind_result($nombre);

?>