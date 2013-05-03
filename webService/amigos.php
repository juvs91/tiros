<?php
	include 'baseDatos.php';
	
	$baseDatos=new baseDatos();
	//en este apartado recojo todos los parametros 
	$id=$_POST["id"];
	//$id=1;
	//los objetos de mysqli
	$amigos=new mysqli($baseDatos->getHost(),$baseDatos->getUser(),$baseDatos->getPass(), $baseDatos->getDB());
	
	//los querys a ejectuar
	$amigosQuery="SELECT u.nombre,u.puntaje,u.id,u.mail FROM usuarios u,amigos a WHERE u.id=a.idAmigo and a.idPersona=?";
	
	//los statemenst de los querys
	$stmt=$amigos->prepare($amigosQuery);
	$stmt->bind_param("i",$id);
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