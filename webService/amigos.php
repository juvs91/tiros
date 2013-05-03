<?php
	include 'baseDatos.php';
	
	$baseDatos=new baseDatos();
	//en este apartado recojo todos los parametros 
	$id=$_POST["id"];
	//$id=1;
	//los objetos de mysqli
	$amigos=new mysqli($baseDatos->getHost(),$baseDatos->getUser(),$baseDatos->getPass(), $baseDatos->getDB());
	
	//los querys a ejectuar
	$amigosQuery="SELECT u.nombre,u.puntaje,u.mail,u.id,j.estado,j.idJugador1,j.idJugador2 FROM usuarios u,juegos j WHERE (u.id=j.idJugador2 and j.idJugador1=? ) OR (u.id=j.idJugador1 and j.idJugador2=? ) ";
	
	//los statemenst de los querys
	$stmt=$amigos->prepare($amigosQuery);
	$stmt->bind_param("ii",$id,$id);
	$baseDatos->begin();
	$stmt->execute();//se ejecuta 
	$result=$stmt->bind_result($nombre,$score,$mail,$id,$estado,$idJugador1,$idJugador2);
	
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
				"mail"=>$mail,
				"estado"=>$estado,
				"idJugador1"=>$idJugador1,
				"idJugador2"=>$idJugador2
			);
			array_push($consulta,$temporal);
		}	
	}
	echo (json_encode($consulta));
	
?>