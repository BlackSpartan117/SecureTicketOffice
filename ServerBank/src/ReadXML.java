/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author isaac_stark
 */
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import org.jdom2.Document;         // |
import org.jdom2.Element;          // |\ Librerías
import org.jdom2.JDOMException;    // |/ JDOM
import org.jdom2.input.SAXBuilder; // |

public class ReadXML {

    public void cargarXml( String xml ) throws IOException {
        //Se crea un SAXBuilder para poder parsear el archivo
        SAXBuilder builder = new SAXBuilder();
        InputStream streamXML = new ByteArrayInputStream( xml.getBytes(StandardCharsets.UTF_8 ) );
        
        try {
            //Se crea el documento a traves del archivo
            Document document = (Document) builder.build( streamXML );

            //Se obtiene la raiz 'tables'
            Element rootNode = document.getRootElement();

            //Se obtiene la lista de hijos de la raiz 'tables'
            List list = rootNode.getChildren();

            System.out.println("LeerXML.cargarXml() " + list.size() + "  " + document.getRootElement().getName() );
            for( int i = 0; i < list.size(); i++ ) {
                Element element = (Element) list.get(i);
                System.out.println("Element: " + element );
            }
            
            //Se recorre la lista de hijos de 'tables'
            /*for (int i = 0; i < list.size(); i++) {
                //Se obtiene el elemento 'tabla'
                Element tabla = (Element) list.get(i);

                //Se obtiene el atributo 'nombre' que esta en el tag 'tabla'
                String nombreTabla = tabla.getAttributeValue("nombre");

                System.out.println("Tabla: " + nombreTabla);

                //Se obtiene la lista de hijos del tag 'tabla'
                List lista_campos = tabla.getChildren();

                System.out.println("\tNombre\t\tTipo\t\tValor");

                //Se recorre la lista de campos
                for (int j = 0; j < lista_campos.size(); j++) {
                    //Se obtiene el elemento 'campo'
                    Element campo = (Element) lista_campos.get(j);

                    //Se obtienen los valores que estan entre los tags '<campo></campo>'
                    //Se obtiene el valor que esta entre los tags '<nombre></nombre>'
                    String nombre = campo.getChildTextTrim("nombre");

                    //Se obtiene el valor que esta entre los tags '<tipo></tipo>'
                    String tipo = campo.getChildTextTrim("tipo");

                    //Se obtiene el valor que esta entre los tags '<valor></valor>'
                    String valor = campo.getChildTextTrim("valor");

                    System.out.println("\t" + nombre + "\t\t" + tipo + "\t\t" + valor);
                }
            }*/
        } catch (IOException io) {
            System.out.println(io.getMessage());
        } catch (JDOMException jdomex) {
            System.out.println(jdomex.getMessage());
        } finally {
            streamXML.close();
        }
    }
}
