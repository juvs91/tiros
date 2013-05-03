<?php

	include 'baseDatos.php';
	
	$baseDatos=new baseDatos();
	//en este apartado recojo todos los parametros 
	$nombre=$_POST["usuario"];
	$pass=$_POST["password"];
	//$nombre="juvs";
	//$pass="juvs";
	//los objetos de mysqli
	$usuario=new mysqli($baseDatos->getHost(),$baseDatos->getUser(),$baseDatos->getPass(), $baseDatos->getDB());
	
	//los querys a ejectuar
	$loginQuery="SELECT id,nombre,puntaje,mail FROM usuarios u WHERE u.mail=? AND u.contrasenia=?";
	
	//los statemenst de los querys
	$stmt=$usuario->prepare($loginQuery);
	$stmt->bind_param("ss",$nombre,$pass);
	$baseDatos->begin();
	$stmt->execute();//se ejecuta 
	$result=$stmt->bind_result($id,$nombre,$score,$mail);
	
	if (!$result) {
		$baseDatos->rollback();
		exit;		
	} else {
		$consulta=array();
		while($stmt->fetch()){
			$temporal=array(
				"id"=>$id,
				"nombre"=>$nombre,
				"puntaje"=>$score,
				"mail"=>$mail
			);
			array_push($consulta,$temporal);
		}	
	}
	echo (json_encode($consulta));

?>