import java.util.*;

public class Cliente extends Persona {
    private String idCliente;
    private String correo;
    private String estado;
    private List<Titularidad> titularidades = new ArrayList<>();

    public Cliente(String idCliente, String dni, String nombre, String apellido,
                   String direccion, String telefono, String correo, String estado) {
        super(dni, nombre, apellido, direccion, telefono);
        this.idCliente = idCliente;
        this.correo = correo;
        this.estado = estado;
    }

    public String getIdCliente() { return idCliente; }
    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public void agregarTitularidad(Titularidad t) {
        if (!titularidades.contains(t)) titularidades.add(t);
    }

    public List<Cuenta> consultarCuentas() {
        List<Cuenta> cuentas = new ArrayList<>();
        for (Titularidad t : titularidades) {
            cuentas.add(t.getCuenta());
        }
        return cuentas;
    }

    @Override
    public void mostrarDatos() {
        System.out.println("Cliente ID: " + idCliente);
        super.mostrarDatos();
        System.out.println("Correo: " + correo + ", Estado: " + estado);
    }
}
