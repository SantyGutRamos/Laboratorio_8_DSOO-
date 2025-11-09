import java.time.*;

public class Titularidad {
    private Cliente cliente;
    private Cuenta cuenta;
    private LocalDate fechaInicio;
    private String tipoTitular;

    public Titularidad(Cliente cliente, Cuenta cuenta, LocalDate fechaInicio, String tipoTitular) {
        this.cliente = cliente;
        this.cuenta = cuenta;
        this.fechaInicio = fechaInicio;
        this.tipoTitular = tipoTitular;
    }

    public Cliente getCliente() { return cliente; }
    public Cuenta getCuenta() { return cuenta; }
    @Override
    public String toString() {
        return "Titularidad{" +
                "cliente=" + cliente.getNombre() + " " + cliente.getApellido() +
                ", cuenta=" + cuenta.getNumeroCuenta() +
                ", fechaInicio=" + fechaInicio +
                ", tipoTitular='" + tipoTitular + '\'' +
                '}';
    }
}
