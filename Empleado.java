public class Empleado extends Persona {
    private String idEmpleado;
    private String cargo;

    public Empleado(String idEmpleado, String dni, String nombre, String apellido,
                    String direccion, String telefono, String cargo) {
        super(dni, nombre, apellido, direccion, telefono);
        this.idEmpleado = idEmpleado;
        this.cargo = cargo;
    }

    public String getIdEmpleado() { return idEmpleado; }
    public String getCargo() { return cargo; }
    public void setCargo(String cargo) { this.cargo = cargo; }

    public void procesarTransaccion(Transaccion t) {
        t.setEmpleado(this);
        t.procesar();
        System.out.println("Transacción procesada por " + getNombre() + " " + getApellido());
    }
    @Override
    public void mostrarDatos() {
        System.out.println("Empleado ID: " + idEmpleado + " - Cargo: " + cargo);
        super.mostrarDatos();
    }
}
