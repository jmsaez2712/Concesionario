<?php

    ini_set('display_errors',1);
    ini_set('display_startups_errors', 1);
    error_reporting(E_ALL);

    define("DB_HOST", '{database_ip}');
    define('DB_NAME', '{database_name}');
    define('DB_USER', '{user}');
    define('DB_PASS', '{password}');
        
    require_once('dbConnection.php');
    
    $dbc = new dbConnection();
    $con = $dbc->getCon();
?>