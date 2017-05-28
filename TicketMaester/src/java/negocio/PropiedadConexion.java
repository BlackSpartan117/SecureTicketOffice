package negocio;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropiedadConexion {

    private Properties prop;
    private String driverName;
    private String url;
    private String usuario;
    private String password;

    public PropiedadConexion(String ruta) {
        prop = new Properties();//https://docs.oracle.com/javase/tutorial/essential/environment/properties.html
        obtenerProperties(ruta);
    }

    private void obtenerProperties(String ruta) {
        try {
            FileInputStream in = new FileInputStream(ruta);
            prop.load(in);

            this.driverName = prop.getProperty("driverName");
            this.url = prop.getProperty("url");
            this.usuario = prop.getProperty("usuario");
            this.password = prop.getProperty("password");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
