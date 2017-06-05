package xml;

/**
 *
 * @author david
 */
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

public class xmlcuenta {

    public String crearXML() {
        String xml;

        Element cuenta = new Element("cuentas");
        Document doc = new Document(cuenta);

        
        Element datos_cuenta2 = new Element("datos_cuenta");
        Element nombre2 =new Element("nombre").setText("Tickets ESCOM");
        datos_cuenta2.addContent(nombre2);
        datos_cuenta2.addContent(new Element("noTarjeta").setText("1234567890123455"));
        datos_cuenta2.addContent(new Element("cvv").setText("666"));
        datos_cuenta2.addContent(new Element("vigencia").setText("2018-12"));
        datos_cuenta2.addContent(new Element("precio").setText("00.0"));
        doc.getRootElement().addContent(datos_cuenta2);

        XMLOutputter xmlOutput = new XMLOutputter();

        // display nice nice
        xmlOutput.setFormat(Format.getPrettyFormat());
        xml = xmlOutput.outputString(doc);

        System.out.println();
        System.out.println("File Saved! " + xml);

        return xml;
    }
    
    public String crearXML( String tarjetaCliente, String tarjetaTicket, String monto, String clave ) {
        String xml;

        Element cuenta = new Element("cuentas");
        Document doc = new Document(cuenta);

        Element datos_cuenta = new Element("datos_cuenta");
        
        Element nombre = new Element("cliente").setText( tarjetaCliente );
        datos_cuenta.addContent(nombre);
        datos_cuenta.addContent(new Element("noTarjeta").setText( tarjetaTicket ) );
        datos_cuenta.addContent(new Element("monto").setText( monto ) );
        datos_cuenta.addContent(new Element("clave").setText( clave ) );
        
        doc.getRootElement().addContent( datos_cuenta );
        
        XMLOutputter xmlOutput = new XMLOutputter();

        // display nice nice
        xmlOutput.setFormat(Format.getPrettyFormat());
        xml = xmlOutput.outputString( doc );

        //System.out.println();
        //System.out.println("File Saved! " + xml);

        return xml;
    }
}
