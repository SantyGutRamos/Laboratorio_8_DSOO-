import java.time.LocalDateTime;

public abstract class Transaccion {
    private static int contador = 1;
    private String idTransaccion;
    private LocalDateTime fecha;
    protected double monto;
    private Cuenta cuenta;
    private UsuarioEmpleado empleado;

    public Transaccion(Cuenta cuenta, double monto) {
        this.idTransaccion = "TRX-" + contador++;
        this.fecha = LocalDateTime.now();
        this.monto = monto;
        this.cuenta = cuenta;
    }

    public Cuenta getCuenta() { return cuenta; }
    public double getMonto() { return monto; }
    public void setEmpleado(UsuarioEmpleado empleado) { this.empleado = empleado; }
    public UsuarioEmpleado getEmpleado() { return empleado; }

    public abstract void procesar();

    @Override
    public String toString() {
        String emp = (empleado == null) ? "N/A" : empleado.getNombre() + " " + empleado.getApellido();
        return "[" + fecha + "] " + getClass().getSimpleName() + " ID:" + idTransaccion +
                " Monto:" + monto + " Empleado:" + emp;
    }
}
