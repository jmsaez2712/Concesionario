<?php 

class DBConnection {
    private $host = DB_HOST;
    private $user = DB_USER;
    private $pass = DB_PASS;
    private $dbname = DB_NAME;
    
    private $dbc;
    
    private $error = '';
    
    public function __construct(){
        
        $dsn = 'mysql:host='.$this->host.';dbname='.$this->dbname;
        
        $options = array(
            PDO::ATTR_PERSISTENT => true,
            PDO::ATTR_ERRMODE =>PDO::ERRMODE_EXCEPTION
            );
        
        try{
            $this->dbc = new PDO($dsn, $this->user, $this->pass, $options);
        }
        
        catch (PDOException $e){
            $this->error = $e->getMessage();
        }
        
        return $this->error;
    }
    
    public function __destruct(){
        $this->dbc = NULL;
    }
    
    private function getPDOConnection(){
        if($this->dbc == NULL){
            $dsn ="".
                $this->_config['driver'].
                ":host=".$this->_config['host'].
                ";dbname=".$this->_config['dbname'];
        $options = array(
                PDO::ATTR_PERSISTENT => true,
                PDO::ATTR_ERRMODE => PDO::ERRMODE_EXCEPTION
                );
                
        //PARA HACER REFERENCIA A LOS METODOS DE UNA CLASE ESTATICA O VARIABLES, SE USA EL ::
        
        
        try{
            $this->dbc = new PDO($dsn, $this->_config['username'], $this->_config['password'], $options);
        }catch ( PDOException $e){
            echo __LINE__.$e->getMessage();
        }
            
        }
    }
    
    //METODO QUE EJECUTA INSTRUCCIONES SQL QUE DEVUELVE UN NUMERO DE TUPLAS
    /* Function runQuery()
    * @param $sql String. Contiene la instruccion sql que se pasa al metodo
    * @return $filas 
    
    */
    public function runQuery($sql){
        try{
            $filas = $this->dbc->exec($sql);
            return $filas;
        }catch(PDOException $e){
            echo __LINE__.$e->getMessage();
        }
    }
    
    //METODO DE EJECUCION DE LAS INSTRUCCIONES QUE DEVUELVEN UN RESULTSET
    public function getQuery($sql){
        try{
            $filas = $this->dbc->query($sql);
            $filas->setFetchMode(PDO::FETCH_ASSOC);     //El metodo fetchMode permite seleccionar como va a ser el modo
                                                        //De salida del codidgo. Esta forma se obtiene a través de la clase estática PDO
                                                        //En este caso FETCH_ASSOC nos devuelve el resultset como un array asociativo.
            return $filas;
        }catch (PDOException $e){
            echo __LINE__.$e->getMessage();
        }
    }
    
    public function getCon(){
        return $this->dbc;
    }
    
    public function __toString(){
        return $this->error;
    }
    
}

?>