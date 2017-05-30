//setTimeout( iniciarPagina, 500 );
window.onload = function() {
    iniciarPagina();
};

function iniciarPagina() {
    var opcion = "iniciaPagina";
    
    $.ajax({
        type: 'POST',
        url: 'Ticket',
        /*contentType : false,
        processData : false,*/
        data: { iniciar: opcion },
        sucess: function ( resp ) {
            alert( resp );
            var obj = JSON.parse( resp );
            //var obj = JSON.stringify( resp );
            
            alert( obj );
            var divContent, divThum, divCaption, divRow;
            var divName, divPrice, divRating;
            
            for( var i in obj ) {
                divContent = $("<div>", {"class": "col-sm-4 col-lg-4 col-md-4", "id": obj[i].Id } );
                    divThum = $("<div>", {"class": "thumbnail"} );
                        divThum.append( $("<img>", {"src": obj[i].Foto, "alt": obj[i].Nombre } ) );
                        divThum.append("<br><br>");
                        
                        divCaption = $("<div>", {"class": "caption" } );
                            divRow = $("<div>", {"class": "row" } );
                                divName = $("<div>", {"class": "col-sm-7" } );
                                    divName.append("<h4><a href='#'>" + obj[i].Nombre + "</a></h4>");
                                    divRow.append( divName );
                                    
                                divPrice = $("<div>", {"class": "col-sm-5" } );
                                    divPrice.append("<h4>" + obj[i].Precio + "</h4>");
                                    divRow.append( divPrice );
                                    
                            divCaption.append( divRow );
                            divCaption.append("<p>Peque&ntilde;a descripci&oacute;n del evento.</p>");
                        
                        divRating = $("<div>", {"class": "ratings" } );
                        divRating.append("<p class='pull-right'>12 reviews</p>");
                        divRating.append( "<p>"+ "<i class='fa fa-star' aria-hidden='true'></i>"
                                                + "<i class='fa fa-star' aria-hidden='true'></i>"
                                                + "<i class='fa fa-star' aria-hidden='true'></i>"
                                                + "<i class='fa fa-star' aria-hidden='true'></i>" 
                                                + "<i class='fa fa-star-o' aria-hidden='true'></i> </p>" );
                                        
                    divThum.append( divCaption );
                    divThum.append( divRating );
                    divThum.append("<center><button type='button' class='btn btn-primary' data-toggle='modal' data-target='#exampleModalLong'>Comprar</button></center>");
                divContent.append( divThum );    
                
                $("#eventos").append( divContent );
            }
        }, 
        error: function() {
            alert("ERROR PERRO");
        }
    });
}
