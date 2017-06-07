var bytesClave; /*Clave de sesion*/
var eventoSeleccionado = 'C'; /*Evento actual seleccionado*/

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
    eventoSeleccionado = tipo;
    consultarEventos(tipo);
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
                        solicitarClavePublica();
                    }
                }
            });
            return false;
        }
});

function consultarEventos(evento){
    var obj = new Object();
    obj.accion = "eventos";
    obj.tipo = evento;
    var datos = JSON.stringify(obj);
    var cifrado32 = encrypt(datos, bytesClave);
    var texto = sjcl.codec.base64.fromBits(cifrado32);
    $.ajax({
        'type': 'POST',
        'url': 'Ticket',
        'data': {'datos': texto},
        success: function(resp){
            var textoJson = decrypt(sjcl.codec.base64.toBits(resp), bytesClave);
            var listaEventos = $.parseJSON(textoJson);
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
            $("#loading").fadeOut(500);
            $('#menu-principal').addClass('fixed-top');
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
    //$("#loading").fadeIn(500);
    $.ajax({
        'type': 'POST',
        'url': 'Handshake',
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
        'url': 'Handshake',
        'data': {'accion': 'resultadoDH', 'resultado': fn["number->string"](y)},
        success: function(resp){
            var yServer = 0;
            yServer = resp;
            var clave = fastModularExponentiation(yServer, xb, p);
            var hash = md5(fn["number->string"](clave));
            bytesClave = parseHexString(hash);
            consultarEventos('C');
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

function encrypt(cadena, bytesClave){
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

function decrypt(ciphertext, bytesClave){
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
    return sjcl.codec.utf8String.fromBits(decrypted);
}

function parseHexString(str) { 
    var result = [];
    while (str.length >= 8) { 
        result.push(parseInt(str.substring(0, 8), 16));

        str = str.substring(8, str.length);
    }

    return result;
}

function pruebaRSA(){
    var privateKey = "-----BEGIN RSA PRIVATE KEY-----" +
"MIICXQIBAAKBgQDlOJu6TyygqxfWT7eLtGDwajtNFOb9I5XRb6khyfD1Yt3YiCgQ" +
"WMNW649887VGJiGr/L5i2osbl8C9+WJTeucF+S76xFxdU6jE0NQ+Z+zEdhUTooNR" +
"aY5nZiu5PgDB0ED/ZKBUSLKL7eibMxZtMlUDHjm4gwQco1KRMDSmXSMkDwIDAQAB" +
"AoGAfY9LpnuWK5Bs50UVep5c93SJdUi82u7yMx4iHFMc/Z2hfenfYEzu+57fI4fv" +
"xTQ//5DbzRR/XKb8ulNv6+CHyPF31xk7YOBfkGI8qjLoq06V+FyBfDSwL8KbLyeH" +
"m7KUZnLNQbk8yGLzB3iYKkRHlmUanQGaNMIJziWOkN+N9dECQQD0ONYRNZeuM8zd" +
"8XJTSdcIX4a3gy3GGCJxOzv16XHxD03GW6UNLmfPwenKu+cdrQeaqEixrCejXdAF" +
"z/7+BSMpAkEA8EaSOeP5Xr3ZrbiKzi6TGMwHMvC7HdJxaBJbVRfApFrE0/mPwmP5" +
"rN7QwjrMY+0+AbXcm8mRQyQ1+IGEembsdwJBAN6az8Rv7QnD/YBvi52POIlRSSIM" +
"V7SwWvSK4WSMnGb1ZBbhgdg57DXaspcwHsFV7hByQ5BvMtIduHcT14ECfcECQATe" +
"aTgjFnqE/lQ22Rk0eGaYO80cc643BXVGafNfd9fcvwBMnk0iGX0XRsOozVt5Azil" +
"psLBYuApa66NcVHJpCECQQDTjI2AQhFc1yRnCU/YgDnSpJVm1nASoRUnU8Jfm3Oz" +
"uku7JUXcVpt08DFSceCEX9unCuMcT72rAQlLpdZir876" +
"-----END RSA PRIVATE KEY-----";
    var publicKey = "-----BEGIN PUBLIC KEY-----" +
"MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDlOJu6TyygqxfWT7eLtGDwajtN" +
"FOb9I5XRb6khyfD1Yt3YiCgQWMNW649887VGJiGr/L5i2osbl8C9+WJTeucF+S76" +
"xFxdU6jE0NQ+Z+zEdhUTooNRaY5nZiu5PgDB0ED/ZKBUSLKL7eibMxZtMlUDHjm4" +
"gwQco1KRMDSmXSMkDwIDAQAB" +
"-----END PUBLIC KEY-----";
    // Encrypt with the public key...
    var encrypt = new JSEncrypt();
    encrypt.setPublicKey(publicKey);
    var encrypted = encrypt.encrypt("Hola mundo");
    // Decrypt with the private key...
    var decrypt = new JSEncrypt();
    decrypt.setPrivateKey(privateKey);
    var uncrypted = decrypt.decrypt(encrypted);

    // Now a simple check to see if the round-trip worked.
    if (uncrypted == "Hola mundo") {
    }
    else {
    }
    $.ajax({
        type: "POST",
        url: "http://localhost:8080/BankESCOMaester/ConsultarClavePublica",
        data: {'accion': 'publicKey'},
        //if received a response from the server
        success: function(resp) {
            var publicKey = "-----BEGIN PUBLIC KEY-----" + resp + "-----END PUBLIC KEY-----";
            var encrypt = new JSEncrypt();
            encrypt.setPublicKey(publicKey);
            var encrypted = encrypt.encrypt("Hola mundo12341234124143");
            $.ajax({
                type: "POST",
                url: "http://localhost:8080/BankESCOMaester/ConsultarClavePublica",
                data: {'accion': 'descifrar', 'datos': encrypted},
                //if received a response from the server
                success: function(resp) {
                    alert(resp);
                }
            });
            // Decrypt with the private key...
            //var decrypt = new JSEncrypt();
            //decrypt.setPrivateKey(privateKey);
            //var uncrypted = decrypt.decrypt(encrypted);
        }
    });
}

function solicitarClavePublica(){
    $.ajax({
        type: "POST",
        url: "http://localhost:8080/BankESCOMaester/ConsultarClavePublica",
        data: {'accion': 'publicKey'},
        //if received a response from the server
        success: function(resp) {
            var publicKey = "-----BEGIN PUBLIC KEY-----" + resp + "-----END PUBLIC KEY-----";
            comprarBoleto(publicKey);
        }
    });
}

function comprarBoleto(publicKey){
    var cifrador = new JSEncrypt();
    cifrador.setPublicKey(publicKey);
    var clave = sjcl.random.randomWords(4); // 128 bits
    var objEvento   = new Object();
    var objTarjeta  = new Object();
    var objPeticion = new Object();
    var obj         = new Object();
    objEvento.evento   = $('#modal-comprar').attr('data-evento');
    objEvento.boletos  = '1';

    objTarjeta.tarjeta = $('#numero-tarjeta').val();
    objTarjeta.titular = $('#titular-tarjeta').val();
    objTarjeta.mes     = $('#vigencia-mes').val();
    objTarjeta.anio    = $('#vigencia-anio').val();
    objTarjeta.cvv     = $('#cvv-tarjeta').val();

    var datos = JSON.stringify(objTarjeta);
    var jsonCifrado  = encrypt(datos, clave); //Cifra con AES
    var textoTarjeta = sjcl.codec.base64.fromBits(jsonCifrado);
    var textoClave = sjcl.codec.base64.fromBits(clave);
    var textoClaveRSA = cifrador.encrypt(textoClave);

    obj.clave   = textoClaveRSA;
    obj.evento  = objEvento;
    obj.tarjeta = textoTarjeta;

    objPeticion.accion = "comprar";
    objPeticion.datos = obj;
    var datos = JSON.stringify(objPeticion);
    var jsonCifrado  = encrypt(datos, bytesClave);
    var texto = sjcl.codec.base64.fromBits(jsonCifrado);
    
    $.ajax({
        'type': 'POST',
        'url': 'Ticket',
        'data': {'datos': texto},
        success: function( resp ){
            var status = $.parseJSON( resp );
            
            if(status[0].RESP != "OK") {
                Lobibox.notify("error",{
                    'title': "Error en la transacci&oacute;n",
                    'msg': "La tarjeta proporcionada es inv&aacute;lida o no tiene fondos.",
                    'position': "bottom right",
                    'delay': 6000,
                    'width': 400,
                    'iconSource': "fontAwesome"
                });
            } else {
                consultarEventos(eventoSeleccionado);
                $('#modal-comprar').modal("hide");
                $("#formulario-pago")[0].reset();
                Lobibox.notify("success", {
                    'title': "Boleto comprado",
                    'msg': "Se realizo el pago del boleto correctamente. Presione este mensaje para ver su boleto.",
                    'position': "bottom right",
                    'delay': false,
                    'width': 400,
                    'iconSource': "fontAwesome",
                    'closeOnClick': true,
                    onClick: function(){
                        window.open("conf/pdf/" + status[0].pdf + ".pdf",'_blank');
                    }
                });
            }
        }
    });
}

handShake();
//pruebaRSA();