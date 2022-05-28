/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function actualizar(){
 
    document.getElementById("planName").innerHTML  = document.getElementById("form:name").value;
    document.getElementById("planDescription").innerHTML  = document.getElementById("form:description").value;
    document.getElementById("planUbicacion").innerHTML  = document.getElementById("form:city").value;
    document.getElementById("planTipo").innerHTML  = document.getElementById("form:type").value;
    document.getElementById("planEdad").innerHTML  = document.getElementById("form:age").value;
    document.getElementById("planCompania").innerHTML  = document.getElementById("form:companion").value;
    document.getElementById("planPrecio").innerHTML  = document.getElementById("form:price").value;
    
}

function showPlan(){
    if(document.getElementById("planName").innerHTML !== ""){
            document.getElementById("planSho").style.display = "block";

    }else{
        document.getElementById("planSho").style.display = "none";
    }
}

function mostrar(id){
    if(id === 'favourites'){
        document.getElementById("favourites").style.display = "block";
        document.getElementById("plans").style.display = "none";
    }else{
        document.getElementById("favourites").style.display = "none";
        document.getElementById("plans").style.display = "block";    }
}