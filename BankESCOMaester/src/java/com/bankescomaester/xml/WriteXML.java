package com.bankescomaester.xml;

/**
 *
 * @author isaac_stark
 */
import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

public class WriteXML {

    public String crearXML() {
        String xml;

        Element company = new Element("BankESCOMaester");
        Document doc = new Document( company );

        Element cliente = new Element("cliente");
        cliente.setAttribute( new Attribute("id", "1") );
        cliente.addContent( new Element("nombre").setText("George") );
        cliente.addContent( new Element("apellido").setText("Lucas") );
        cliente.addContent( new Element("cuenta").setText("09827209882") );
        cliente.addContent( new Element("saldo").setText("19999") );

        doc.getRootElement().addContent( cliente );

        Element cliente2 = new Element("cliente");
        cliente2.setAttribute( new Attribute("id", "2") );
        cliente2.addContent( new Element("nombre").setText("Kurt") );
        cliente2.addContent( new Element("apellido").setText("Cobain") );
        cliente2.addContent( new Element("cuenta").setText("09272982") );
        cliente2.addContent( new Element("saldo").setText("18888") );

        doc.getRootElement().addContent( cliente2 );

        XMLOutputter xmlOutput = new XMLOutputter();

        // display nice nice
        xmlOutput.setFormat(Format.getPrettyFormat());
        xml = xmlOutput.outputString( doc );

        System.out.println();
        //System.out.println("File Saved! " + xml);

        return xml;
    }
}
