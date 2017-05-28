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
        'url': 'ControladorConsultarEventos',
        'data': {'tipo': evento},
        success: function(resp){
            var listaEventos = $.parseJSON(resp);
            var htmlEventos = '';
            for(var i = 0; i < listaEventos.length; i++){
                htmlEventos += "<div class='col-sm-4 col-lg-4 col-md-4'>" +
                                    "<div class='thumbnail'>" +
                                        "<img src='" + listaEventos[i].imagen + "' alt=''>" +
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
                                            "<p>" + listaEventos[i].descripcion + "</p>" +
                                        "</div>" +
                                        "<div class='ratings'>" +
                                            "<p class='pull-right'>12 reviews</p>" +
                                            "<p>";
                                            for(var j = 0; j < 5; j++){
                                                if(j <  listaEventos[i].estrellas){
                                                    htmlEventos += "<i class='fa fa-star' aria-hidden='true'></i>";
                                                }else{
                                                    htmlEventos += "<i class='fa fa-star-o' aria-hidden='true'></i>";
                                                }
                                            }
                htmlEventos +=              "</p>" +
                                        "</div>";
                if(listaEventos[i].lugares > 0){
                    htmlEventos +=      "<center><button type='button' class='btn btn-primary boton-comprar' data-toggle='modal' data-evento='" + listaEventos[i].id + "'>Comprar</button></center>" +
                }else{
                    htmlEventos +=      "<center><button type='button' class='btn btn-primary boton-comprar' data-toggle='modal' data-evento='" + listaEventos[i].id + "' disabled>Agotado</button></center>" +
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
