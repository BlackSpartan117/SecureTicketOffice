$.validate({
    'form': "#registro",
    'lang': "es",
    onSuccess: function () {
        Lobibox.confirm({
            'msg': "¿Est&aacute;s seguro que los datos son correctos? Este evento aparecer&aacute; en la p&aacute;gina principal",
            'title': "Confirmaci&oacute;n",
            buttons: {
                yes: {
                    'class': 'btn btn-success',
                    'text': "Si",
                    'closeOnClick': true
                },
                cancel: {
                    'class': 'btn btn-danger',
                    'text': 'Cancelar',
                    'closeOnClick': true
                }
            }/*,
            callback: function (lobibox, type) {
                if( type === 'yes' ) {
                    var nombre = $("#name").val();
                    var tipo = $("#tipo").val();
                    var precio = $("#precio").val();
                    var asientos = $("#asientos").val();
                    var fecha = $("#fecha").val();
                    var foto = $("#").val();
                    
                    alert(jsonString);
                }
            }*/
        });
        
        return false;
    }
});

$(document).ready(function() {
    var cookie = getCookie("registro");
    
    if( cookie !== "" ) {
        alert("Registro exitoso!");
        document.cookie = "registro=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/TicketESCOM/;";
    }
});

function getCookie(cname) {
    var name = cname + "=";
    var decodedCookie = decodeURIComponent(document.cookie);
    var ca = decodedCookie.split(';');
    
    for(var i = 0; i <ca.length; i++) {
        var c = ca[i];
        while (c.charAt(0) == ' ') {
            c = c.substring(1);
        }
        if (c.indexOf(name) == 0) {
            return c.substring(name.length, c.length);
        }
    }
    return "";
}

$("#logout").click( function() { 
    document.cookie = "JSESSIONID=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/TicketESCOM/;";
    $.ajax({
        'type':"POST",
        'url': "Login",
        data: { "logout":"logout" },
        sucess: function( resp ) {
            location.href = "index.html";
        }
    });
});

