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

        Element datos_cuenta = new Element("datos_cuenta");
        Element nombre =new Element("nombre").setText("usuario");
        datos_cuenta.addContent(nombre);
        datos_cuenta.addContent(new Element("noTarjeta").setText("123456"));
        datos_cuenta.addContent(new Element("cvv").setText("123"));
        datos_cuenta.addContent(new Element("vigencia").setText("2017-08"));
        datos_cuenta.addContent(new Element("precio").setText("1234.89"));
        
        doc.getRootElement().addContent(datos_cuenta);
        
        Element datos_cuenta2 = new Element("datos_cuenta");
        Element nombre2 =new Element("nombre").setText("Tickets");
        datos_cuenta2.addContent(nombre2);
        datos_cuenta2.addContent(new Element("noTarjeta").setText("987654"));
        datos_cuenta2.addContent(new Element("cvv").setText("456"));
        datos_cuenta2.addContent(new Element("vigencia").setText("2020-10"));
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
    
    public String crearXML(String Tarjetauser, String precio,String cvv,String Titular,String Vencimiento) {
        String xml;

        Element cuenta = new Element("cuentas");
        Document doc = new Document(cuenta);

        Element datos_cuenta = new Element("datos_cuenta");
        Element nombre =new Element("nombre").setText(Titular);
        datos_cuenta.addContent(nombre);
        datos_cuenta.addContent(new Element("noTarjeta").setText(Tarjetauser));
        datos_cuenta.addContent(new Element("cvv").setText(cvv));
        datos_cuenta.addContent(new Element("vigencia").setText(Vencimiento));
        datos_cuenta.addContent(new Element("precio").setText(precio));
        
        doc.getRootElement().addContent(datos_cuenta);
        
        Element datos_cuenta2 = new Element("datos_cuenta");
        Element nombre2 =new Element("nombre").setText("Tickets");
        datos_cuenta2.addContent(nombre2);
        datos_cuenta2.addContent(new Element("noTarjeta").setText("987654"));
        datos_cuenta2.addContent(new Element("cvv").setText("456"));
        datos_cuenta2.addContent(new Element("vigencia").setText("10-2020"));
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
}
