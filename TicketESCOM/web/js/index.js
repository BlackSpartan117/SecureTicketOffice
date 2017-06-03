var bytesClave; /*Clave de sesion*/

$('.boton-comprar').click(function(){
    $('#modal-comprar').attr('data-evento', $(this).attr('data-evento'));
    $('#modal-comprar').modal({
        keyboard: true
    });
});

$('.list-group button').click(function(){
    $('.list-group button').each(function(){
        $(this).removeAttr("disabled");
        $(this).removeAttr("style");
    });
    var tipo = $(this).attr('data-evento');
    $(this).attr('style', "background-color: #428bca;color: white;");
    $(this).attr("disabled", "disabled");
    //consultarEventos(tipo);
});
$(".list-group button[data-evento='C']").attr('style', "background-color: #428bca;color: white;");
$(".list-group button[data-evento='C']").attr("disabled", "disabled");

$.validate({
        'form': "#formulario-pago",
        'lang': "es",
        onSuccess: function(){
            Lobibox.confirm({
                'msg': "¿Est&aacute;s seguro que deseas comprar boletos para este evento?",
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
                },
                callback: function(lobibox, type){
                    if(type === 'yes') {
                        var obj = new Object();
                        obj.evento = $('#modal-comprar').attr('data-evento');
                        obj.boletos = '1';
                        obj.tarjeta  = $('#numero-tarjeta').val();
                        obj.titular = $('#titular-tarjeta').val();
                        obj.mes = $('#vigencia-mes').val();
                        obj.anio = $('#vigencia-anio').val();
                        obj.cvv = $('#cvv-tarjeta').val();
                        var jsonString= JSON.stringify(obj);
                        alert(jsonString);
                        /*
                        $.ajax({
                            'type': 'POST',
                            'url': 'ControladorComprarBoletos',
                            'data': datos,
                            success: function(resp){
                                if(resp == 0) {
                                    Lobibox.notify("error",{
                                        'title': "Error en la transacci&oacute;n",
                                        'msg': "La tarjeta proporcionada es inv&aacute;lida o no tiene fondos.",
                                        'position': "bottom right",
                                        'delay': 6000,
                                        'width': 400,
                                        'iconSource': "fontAwesome"
                                    });
                                } else {
                                    $('#modal-comprar').modal("hide");
                                    $("#formulario-pago")[0].reset();
                                    Lobibox.notify("success", {
                                        'title': "Boleto comprado",
                                        'msg': "Se realizo el pago del boleto correctamente.",
                                        'position': "bottom right",
                                        'delay': 5000,
                                        'width': 400,
                                        'iconSource': "fontAwesome"
                                    });
                                }
                            }
                        });*/
                    }
                }
            });
            return false;
        }
});

function consultarEventos(evento){
    $.ajax({
        'type': 'POST',
        'url': 'Ticket',
        'data': {'iniciar': 'iniciaPagina', 'tipo': evento},
        success: function(resp){
            var listaEventos = $.parseJSON(resp);
            var htmlEventos = '';
            for(var i = 0; i < listaEventos.length; i++){
                htmlEventos += "<div class='col-sm-4 col-lg-4 col-md-4'>" +
                                    "<div class='thumbnail'>" +
                                        "<img src='" + listaEventos[i].imagen + "' height ='' width='' alt=''>" +
                                        "<br><br>" +
                                        "<div class='caption'>" +
                                            "<div class='row'>" +
                                                "<div class='col-sm-7'>" +
                                                    "<h4><a href='#'>" + listaEventos[i].titulo + "</a></h4>" +
                                                "</div>" +
                                                "<div class='col-sm-5'>" +
                                                    "<h4>$" + listaEventos[i].precio + "</h4>" +
                                                "</div>" +
                                            "</div>" +
                                            "<p> Fecha: " + listaEventos[i].descripcion + "</p>" +
                                            "<p> Lugar: " + listaEventos[i].lugar + "</p>" +
                                        "</div>" +
                                        "<div class='ratings'>" +
                                            "<p class='pull-right'>12 reviews</p>" +
                                            "<p>";
                                            /*for( var j = 0; j < 5; j++ ) {
                                                if( j <  listaEventos[i].estrellas ) {
                                                    htmlEventos += "<i class='fa fa-star' aria-hidden='true'></i>";
                                                } else{
                                                    htmlEventos += "<i class='fa fa-star-o' aria-hidden='true'></i>";
                                                }
                                            }*/
                                            var n = 1 + Math.floor(Math.random() * 100) % 5;
                                            for( var j = 0; j < n; j++ ){
                                                htmlEventos += "<i class='fa fa-star' aria-hidden='true'></i>";
                                            }
                                            for( var j = 0; j < 5-n; j++ ){
                                                htmlEventos += "<i class='fa fa-star-o' aria-hidden='true'></i>";
                                            }
                htmlEventos +=              "</p>" +
                                        "</div>";
                if(listaEventos[i].lugares > 0){
                    htmlEventos +=      "<center><button type='button' class='btn btn-primary boton-comprar' data-toggle='modal' data-evento='" + listaEventos[i].id + "'>Comprar</button></center>";
                }else{
                    htmlEventos +=      "<center><button type='button' class='btn btn-primary boton-comprar' data-toggle='modal' data-evento='" + listaEventos[i].id + "' disabled>Agotado</button></center>";
                }
                
                    htmlEventos +=  "</div>" +
                                "</div>";
            }
            $("#lista-eventos").html(htmlEventos);
            $('.boton-comprar').click(function(){
                $('#modal-comprar').attr('data-evento', $(this).attr('data-evento'));
                $('#modal-comprar').modal({
                    keyboard: true
                });
            });
        }
    });
}

function handShake(){
    $.ajax({
        'type': 'POST',
        'url': 'Ticket',
        'data': {'accion': 'parametros'},
        success: function(resp){
            //alert(resp);
            var i1 = resp.indexOf("primo");
            var i2 = resp.indexOf("generador");
            var i3 = resp.indexOf("longitud");
            var primoStr = resp.substring(i1 + 7, i2 - 2);
            var genStr = resp.substring(i2 + 11, i3 - 2);
            var sn = SchemeNumber;
            var fn = sn.fn;
            var ns = fn["number->string"];
            
            var primo = fn["string->number"](primoStr);
            var generador = fn["string->number"](genStr);
            var random = fn["string->number"]("0");
            
            if(sjcl.random.isReady(10)) {
                do{
                    var resultado = sjcl.random.randomWords(1, 10)[0];
                }while(resultado < 2);
                random = fn["+"](resultado, 0);
            }
            var y = fastModularExponentiation(generador, random, primo);
            enviarResultadoDH(y, random, primo);
        }
    });
}

function enviarResultadoDH(y, xb, p){
    var sn = SchemeNumber;
    var fn = sn.fn;
    var ns = fn["number->string"];
    $.ajax({
        'type': 'POST',
        'url': 'Ticket',
        'data': {'accion': 'resultadoDH', 'resultado': fn["number->string"](y)},
        success: function(resp){
            var yServer = 0;
            yServer = resp;
            var clave = fastModularExponentiation(yServer, xb, p);
            //alert("Clave: " + fn["number->string"](clave));
            var hash = md5(fn["number->string"](clave));
            alert("Clave hash: " + hash);
            bytesClave = parseHexString(hash);
            enviarCifrado();
        }
    });
}

function enviarCifrado(){
    var cifrado32 = encrypt("Mensaje secreto enviado por aes");
    var texto = sjcl.codec.base64.fromBits(cifrado32);
    $.ajax({
        'type': 'POST',
        'url': 'Ticket',
        'data': {'accion' : 'descifrar', 'datos' : texto},
        success: function(resp){
            alert(resp);
            decrypt(sjcl.codec.base64.toBits(resp));
        }
    });
}

function encrypt(cadena){
    var p = sjcl.json.defaults; 
    var iv  = new Uint8Array(16);
    for (var i = 0; i < iv.length; ++i) {
        iv[i] = 0;
    }
    p.iv = sjcl.codec.bytes.toBits(iv);
    p.salt = [];
    p.mode = "cbc";
    var aes = new sjcl.cipher.aes(bytesClave);
    sjcl.beware["CBC mode is dangerous because it doesn't protect message integrity."]();
    var encrypted = sjcl.mode[p.mode].encrypt(aes, sjcl.codec.utf8String.toBits(cadena), p.iv);
    var cipherText = sjcl.bitArray.concat(p.iv, encrypted);
    return cipherText;
}

function decrypt(ciphertext){
    var p = sjcl.json.defaults; 
    var p = sjcl.json.defaults; 
    var len = sjcl.bitArray.bitLength(ciphertext);
    var iv = sjcl.bitArray.bitSlice(ciphertext, 0, 128);
    p.iv = iv;
    p.salt = [];
    p.mode = "cbc";
    var aes = new sjcl.cipher.aes(bytesClave);
    sjcl.beware["CBC mode is dangerous because it doesn't protect message integrity."]();
    var decrypted = sjcl.mode[p.mode].decrypt(aes, sjcl.bitArray.bitSlice(ciphertext, 128, len), p.iv);
    alert(sjcl.codec.utf8String.fromBits(decrypted));
}

function parseHexString(str) { 
    var result = [];
    while (str.length >= 8) { 
        result.push(parseInt(str.substring(0, 8), 16));

        str = str.substring(8, str.length);
    }

    return result;
}

handShake();
//consultarEventos('C');