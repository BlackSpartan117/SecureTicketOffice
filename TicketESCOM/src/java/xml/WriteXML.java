package xml;


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

        Element company = new Element("company");
        Document doc = new Document(company);

        Element staff = new Element("staff");
        staff.setAttribute(new Attribute("id", "1"));
        staff.addContent(new Element("firstname").setText("yong"));
        staff.addContent(new Element("lastname").setText("mook kim"));
        staff.addContent(new Element("nickname").setText("mkyong"));
        staff.addContent(new Element("salary").setText("199999"));

        doc.getRootElement().addContent(staff);

        Element staff2 = new Element("staff");
        staff2.setAttribute(new Attribute("id", "2"));
        staff2.addContent(new Element("firstname").setText("low"));
        staff2.addContent(new Element("lastname").setText("yin fong"));
        staff2.addContent(new Element("nickname").setText("fong fong"));
        staff2.addContent(new Element("salary").setText("188888"));

        doc.getRootElement().addContent(staff2);

        XMLOutputter xmlOutput = new XMLOutputter();

        // display nice nice
        xmlOutput.setFormat(Format.getPrettyFormat());
        xml = xmlOutput.outputString(doc);

        //System.out.println();
        //System.out.println("File Saved! " + xml);

        return xml;
    }
}
