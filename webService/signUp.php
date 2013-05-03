<?php
	include 'baseDatos.php';
	
	$baseDatos=new baseDatos();
	//en este apartado recojo todos los parametros 
	$nombre=$_POST["usuario"];
	$pass=$_POST["password"];
	$mail=$_POST["mail"];
	//$nombre="juv";
	//$pass="juv";
	//$mail="juv";
	//los objetos de mysqli
	$usuario=new mysqli($baseDatos->getHost(),$baseDatos->getUser(),$baseDatos->getPass(), $baseDatos->getDB());
	
	//los querys a ejectuar
	$signUp="INSERT INTO usuarios VALUES(?,?,0,NULL,?)";
	
	//los statemenst de los querys
	$stmt=$usuario->prepare($signUp);
	$stmt->bind_param("sss",$nombre,$pass,$mail);
	$baseDatos->begin();
	$result=$stmt->execute();//se ejecuta 
	
	if (!$result) {
		$baseDatos->rollback();
		exit;		
	} else {
		$idInsertado=$stmt->insert_id;//con esto obtengo el id del query
		$baseDatos->commit();
		
	}
	
	$consulta=array();
	
	$temporal=array(
		"nombre"=>$nombre,
		"puntaje"=>0,
		"mail"=>$mail,
		"id"=>$idInsertado
	);	
	array_push($consulta,$temporal);
	
	echo(json_encode($consulta));
	


?>