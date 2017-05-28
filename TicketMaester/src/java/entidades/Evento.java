package entidades;


import java.awt.Image;
import java.util.Date;

/**
 *
 * @author isaac_stark
 */
public class Evento {
    private int id;
    private String nombre;
    private String tipo;
    private double precio;
    private int asientos;
    private Date fecha;
    private byte[] foto;
    private String lugar;
    private String desc;
    
    public Evento() {
        
    }

    public Evento(int id, String nombre, String tipo, double precio, int asientos, Date fecha, byte[] foto, String lugar) {
        this.id = id;
        this.nombre = nombre;
        this.tipo = tipo;
        this.precio = precio;
        this.asientos = asientos;
        this.fecha = fecha;
        this.foto = foto;
        this.lugar = lugar;
    }

    public Evento( String nombre, String tipo, double precio, int asientos, Date fecha, byte[] foto, String lugar, String desc ) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.precio = precio;
        this.asientos = asientos;
        this.fecha = fecha;
        this.foto = foto;
        this.lugar = lugar;
        this.desc = desc;
    }
    
    public Evento(int id, String nombre, String tipo, double precio, int asientos, Date fecha) {
        this.id = id;
        this.nombre = nombre;
        this.tipo = tipo;
        this.precio = precio;
        this.asientos = asientos;
        this.fecha = fecha;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getAsientos() {
        return asientos;
    }

    public void setAsientos(int asientos) {
        this.asientos = asientos;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
    
}
