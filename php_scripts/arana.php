<?php

    ini_set('display_errors',1);
    ini_set('display_startups_errors', 1);
    error_reporting(E_ALL);


    require_once('settingsDB.php');


    function cutDesired($strstart, $strfinish, $text){
        $str_ini = $strstart;
        $str_fin = $strfinish;
        
        $pos_ini = strpos($text, $str_ini);
        $str_cut = substr($text, ($pos_ini+strlen($str_ini)));
        $pos_fin = strpos($str_cut, $str_fin);
        
        $finalstr = substr($str_cut, 0, $pos_fin);
        return $finalstr;
    }
    
    function getCategoriesArray($str_ini, $str_end, $text){
            $result = array();
            $numApariciones = substr_count($text, $str_ini);
            for($i = 0; $i < $numApariciones; $i++){
                $posIni = strpos($text, $str_ini);
                $strCutted = substr($text, ($posIni + strlen($str_ini)));
                $posEnd = strpos($strCutted, $str_end);
                $str = substr($strCutted, 0, $posEnd);
                $result[] = $str;
                //Recortamos la busqueda para el siguiente
                if($i != $numApariciones - 1){
                    $text = $strCutted;
                }
            }
            return $result;
    }
    
    function eliminar_tildes($cadena){


    //Ahora reemplazamos las letras
    $cadena = str_replace(
        array('á', 'à', 'ä', 'â', 'ª', 'Á', 'À', 'Â', 'Ä'),
        array('a', 'a', 'a', 'a', 'a', 'A', 'A', 'A', 'A'),
        $cadena
    );

    $cadena = str_replace(
        array('é', 'è', 'ë', 'ê', 'É', 'È', 'Ê', 'Ë'),
        array('e', 'e', 'e', 'e', 'E', 'E', 'E', 'E'),
        $cadena );

    $cadena = str_replace(
        array('í', 'ì', 'ï', 'î', 'Í', 'Ì', 'Ï', 'Î'),
        array('i', 'i', 'i', 'i', 'I', 'I', 'I', 'I'),
        $cadena );

    $cadena = str_replace(
        array('ó', 'ò', 'ö', 'ô', 'Ó', 'Ò', 'Ö', 'Ô'),
        array('o', 'o', 'o', 'o', 'O', 'O', 'O', 'O'),
        $cadena );

    $cadena = str_replace(
        array('ú', 'ù', 'ü', 'û', 'Ú', 'Ù', 'Û', 'Ü'),
        array('u', 'u', 'u', 'u', 'U', 'U', 'U', 'U'),
        $cadena );

    $cadena = str_replace(
        array('ñ', 'Ñ', 'ç', 'Ç'),
        array('n', 'N', 'c', 'C'),
        $cadena
    );

    return $cadena;
}


    function getJson($array){
        
        $json = "{";
        for ($i = 0; $i < count($array); $i++) {
            if($i!= count($array)-1){
             $json.="\"$i\":". "\"$array[$i]\",";
            } else {
               $json.="\"$i\":". "\"$array[$i]\""; 
            }
        }
        
        $json.="}";
        
        return $json;
    }
    
    $archivo = file_get_contents("https://www.milanuncios.com/tienda/autos-dominguez-52455.htm");
        if(!$archivo){
           echo("Error: file_get_contents no ha podido acceder a la URL");
           exit(1);
        }

        $div_pag = "<div class=\"adlist-paginator-pagelink\"";
        $num_pags = substr_count($archivo, $div_pag);
       
        $c = 1;
        while($c <= $num_pags + 1){
            echo "<br><h1>Estoy en la pagina ".$c."</h1><br>";
            $archivo = file_get_contents("https://www.milanuncios.com/tienda/autos-dominguez-52455.htm?pagina=".$c);
            if(!$archivo){
                echo("Error: file_get_contents no ha podido acceder a la URL");
                exit(1);
            }
            
            $num_anun = substr_count($archivo, "<div class=\"aditem ProfesionalCardTestABClass\">");

            $div_ini = "<div class=\"aditem ProfesionalCardTestABClass\">";
            
            $cont = 0;
            
            while($cont < $num_anun){
                $str_ini=strpos($archivo, $div_ini) + strlen($div_ini);;
                $archivo = substr($archivo, $str_ini);
                
                $url_ini = "<a href=\"";
                $url_fin = "\" target=\"_blank\" class=\"aditem-detail-title\"";
                $url = cutDesired($url_ini, $url_fin, $archivo);
                getCar("https://www.milanuncios.com".$url , $dbc);
                $cont++;
            }
            
            $c++;
    }   
    
    function getCar($url, $dbc){
        $archivo = file_get_contents($url);
        if(!$archivo){
           echo("Error: file_get_contents no ha podido acceder a la URL");
           exit(1);
        }
    
        $str_ini = "Milanuncios - ";
        $str_fin = "</title>";
        
        $pos_ini = strpos($archivo, $str_ini)+strlen($str_ini);
        $pos_fin = strpos($archivo, $str_fin);
        $length = $pos_fin - $pos_ini;
        $title = substr($archivo, $pos_ini, $length);
        echo "<br><br>".$title."<br>\n";
        
        
        $str_ini = "<p class=\"ma-AdDetail-description\">";
        $str_fin = "</p>";
        $temp_str = cutDesired($str_ini, $str_fin, $archivo);
        $description = strtolower(eliminar_tildes($temp_str));
        echo $description."<br>\n";
        
        $str_ini = "<span class=\"ma-AdPrice-value ma-AdPrice-value--default ma-AdPrice-value--heading--l\">";
        $str_fin = "</span>";
        $price = cutDesired($str_ini, $str_fin, $archivo);
        $first_price = str_replace("€", "",$price);
        $second_price = str_replace(".","",$first_price);
        $final_price = (int) $second_price;
        
        echo $final_price."<br>";
        
        $str_ini = "<div class=\"ma-ContentAdDetail-attributes\"><ul class=\"ma-AdTagList\">";
        $str_fin = "</ul></div>";
        
        $tags = cutDesired($str_ini, $str_fin, $archivo);
        $catIni = "<span class=\"ma-AdTag ma-AdTag-small\"><span class=\"ma-AdTag-label\" title=\"";
        $catEnd = "\">";
        $categories = getCategoriesArray($catIni, $catEnd, $tags);
        $temp_cat = getJson($categories);
        $json_cat = eliminar_tildes($temp_cat);
        echo $json_cat;
        
        $str_ini = "<span class=\"ma-AdDetail-metadata-reference ma-AdDetail-metadataTag\">";
        $str_fin = "</span>";
        
        $ref = cutDesired($str_ini, $str_fin, $archivo);
        $cutted_ref = substr($ref, 5, strlen($ref));
        $int_ref = (int) $cutted_ref;
        echo $int_ref."<br>";
        
        
        
        $str_ini = "https://img.milanuncios.com/fg/";
        $str_fin = ".jpg";
        
        $temp_img = cutDesired($str_ini, $str_fin, $archivo);
        
        $img = $str_ini . $temp_img . $str_fin;
        echo $img;
        
        $str_ini = "<p class=\"ma-SharedSliderCounter\">";
        $str_fin = "</p>";
        
        $first_cut = cutDesired($str_ini, $str_fin, $archivo) . "</p>";
        
        $num_img = cutDesired("1 / ", "</p>", $first_cut);
        
        echo "<br>".$num_img;
        

        $arr_img = array();
        for ($i = 0; $i < $num_img; $i++) {
             $arr_img[$i] = "https://img.milanuncios.com/fg/" . substr($temp_img,0,17)."_".($i + 1).".jpg";
        }
        
        $img_json = getJson($arr_img);
        echo $img_json;
        
        $sql = "INSERT INTO coches VALUES (CAST(".$cutted_ref." AS INT), '".$title."', '".$description."', '".$json_cat."', '"
        .$url."', '".$img_json."', ".$final_price.")";
        
        $dbc->runQuery($sql);
    }
    


?>