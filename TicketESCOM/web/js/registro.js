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