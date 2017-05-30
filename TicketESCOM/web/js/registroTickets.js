var form = { "name" : 0, "precio" : 0, "asientos" : 0, "fecha" : 0, "lugar" : 0, "desc" : 0  };

$("#submit").click( function() {
    for( var i in form ) {
        if( form[i] == 0 )
            return false;
    }
    
    return true;
});

$("#name").focus( function () {
    $( this ).attr("class", "form-control");
});

$("#name").blur( function() {
    var name = $("#name").val();
    var expReg = /[^A-Za-z0-9\s!?]+/g;
    
    validarTexto( this, expReg, name );
});

$("#precio").blur( function() {
    var precio = $("#precio").val();
    var expReg = /[0-9]{1,6}\.[0-9]{2}/;
    
    cambiarBorde( this, expReg, precio );
});

$("#precio").focus( function () {
    $( this ).attr("class", "form-control");
});

$("#fecha").blur( function() {
    var precio = $( this ).val();
    var expReg = /^\d{4}-\d{2}-\d{2}$/;
    
    cambiarBorde( this, expReg, precio );
});

$("#fecha").focus( function () {
    $( this ).attr("class", "form-control");
});

$("#lugar").focus( function () {
    $( this ).attr("class", "form-control");
});

$("#lugar").blur( function() {
    var name = $( this ).val();
    var expReg = /[^A-Za-z0-9\s-]+/g;
    
    validarTexto( this, expReg, name );
});

$("#desc").focus( function () {
    $( this ).attr("class", "form-control");
});

$("#desc").blur( function() {
    var name = $( this ).val();
    var expReg = /[^A-Za-z0-9\s()!?¡¿]+/;
    
    validarTexto( this, expReg, name );
});

function cambiarBorde( obj, expReg, validar ) {
    if( expReg.test( validar ) ) {
        $( obj ).attr("class", "form-control borde");
        form[obj.attr("id")] = 1;
    } else {
        $( obj ).attr("class", "form-control bordeError");
        form[obj.attr("id")] = 0;
    }
}

function validarTexto( obj, expReg, validar ) {
    if( expReg.test( validar ) ) {
        $( obj ).attr("class", "form-control bordeError");
        form[obj.attr("id")] = 0;
    } else {
        $( obj ).attr("class", "form-control borde");
        form[obj.attr("id")] = 1;
    }
}