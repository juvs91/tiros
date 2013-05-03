<?php
	include 'baseDatos.php';
	
	$baseDatos=new baseDatos();
	//en este apartado recojo todos los parametros 
	$idJugador1=$_POST["idJugador1"];
	$idJugador2=$_POST["idJugador2"];
	//los objetos de mysqli
	$amigos=new mysqli($baseDatos->getHost(),$baseDatos->getUser(),$baseDatos->getPass(), $baseDatos->getDB());
	
	//los querys a ejectuar
	$amigosQuery="SELECT * FROM juegos j WHERE j.idJugador1=? AND j.idJugador2=? ";
	
	//los statemenst de los querys
	$stmt=$amigos->prepare($amigosQuery);
	$stmt->bind_param("ii",$idJugador1,$idJugador2);
	$baseDatos->begin();
	$stmt->execute();//se ejecuta 
	$result=$stmt->bind_result($id,$jugador1,$jugador2,$estado,$posTiro,$posParo,$tiempo);
	
	if (!$result) {
		$baseDatos->rollback();
		exit;		
	} else {
		$consulta=array();
		while($stmt->fetch()){
			$temporal=array(
				"id"=>$id,
				"jugador1"=>$jugador1,
				"jugador2"=>$jugador2,
				"estado"=>$estado,
				"posTiro"=>$porTiro,
				"posParo"=>$posParo,
				"tiempo"=>$tiempo
			);
			array_push($consulta,$temporal);
		}	
	}
	echo (json_encode($consulta));

?>