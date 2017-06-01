/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xml;

/**
 *
 * @author david
 */
import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

public class xmlcuenta {

    public String crearXML() {
        String xml;

        Element cuenta = new Element("cuenta");
        Document doc = new Document(cuenta);

        Element datos_cuenta = new Element("dato_cuenta");
        datos_cuenta.addContent(new Element("nombre").setText("yong"));
        datos_cuenta.addContent(new Element("numerocuenta").setText("123456"));
        doc.getRootElement().addContent(datos_cuenta);
        Element infocuenta = new Element("cuenta");
        infocuenta.addContent(new Element("tipoCuenta").setText("Debito"));
        infocuenta.addContent(new Element("Dinero").setText("5000"));

        doc.getRootElement().addContent(infocuenta);

         Element datos_cuenta2 = new Element("datos_cuenta");
        datos_cuenta2.addContent(new Element("nombreTickets").setText("Metallica"));
        datos_cuenta2.addContent(new Element("cuentaDeposito").setText("800"));

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
