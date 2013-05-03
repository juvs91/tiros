<?php
	include 'baseDatos.php';
	
	$baseDatos=new baseDatos();
	//en este apartado recojo todos los parametros 
	$mail=$_POST["mail"];
	//los objetos de mysqli
	$amigos=new mysqli($baseDatos->getHost(),$baseDatos->getUser(),$baseDatos->getPass(), $baseDatos->getDB());
	
	//los querys a ejectuar
	$amigosQuery="SELECT u.nombre,u.puntaje,u.id,u.mail FROM usuarios u WHERE u.mail=?";
	//los statemenst de los querys
	$stmt=$amigos->prepare($amigosQuery);
	$stmt->bind_param("s",$mail);
	$baseDatos->begin();
	$stmt->execute();//se ejecuta 
	$result=$stmt->bind_result($nombre,$score,$id,$mail);
	
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