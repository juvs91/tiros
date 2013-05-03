<?php
/*
este archivo tiene la conexion a la base de datos , para llamarse solo se tiene que hace include 'config.php'
*/

class baseDatos
{
	private $host;//nombre del host
	private $user;//nombre del usuario
	private  $pass;//password del usuario
	private $db_name;//nombre de la base de datos
	//hacer la conexion con la base de datos
	/* verificar conexión */
	/**
	* 
	*/
	function __construct()
	{
		$this->host="mysql5.000webhost.com";
		$this->user="a3153621_cc";
		$this->pass="cookies1";
		$this->db_name="a3153621_shootGo";
		
	}
	public function getHost()
	{
		return $this->host;
	}
	public function getUser()
	{
		return $this->user;
	}
	public function getpass()
	{
		return $this->pass;
	}
	public function getDB()
	{
		return $this->db_name;
	}
	
	public function begin(){
		@mysql_query("BEGIN");
	}
	public function commit(){
		@mysql_query("COMMIT");
	}
	public function rollback(){
		@mysql_query("ROLLBACK");
	}
}



?>