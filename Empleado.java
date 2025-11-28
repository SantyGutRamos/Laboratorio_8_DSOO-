public class Empleado extends Persona {
    private String idEmpleado;
    private String cargo;
    private String correo;  // ahora lo generamos automáticamente

    public Empleado(String idEmpleado, String dni, String nombre, String apellido,
                    String direccion, String telefono, String cargo) {
        super(dni, nombre, apellido, direccion, telefono);
        this.idEmpleado = idEmpleado;
        this.cargo = cargo;
        this.correo = generarCorreoEmpresa(nombre, apellido, idEmpleado);
    }

    private String generarCorreoEmpresa(String nombre, String apellido, String id) {
        String base = nombre.toLowerCase() + "." + apellido.toLowerCase();
        base = base.replace("á", "a").replace("é", "e").replace("í", "i")
                .replace("ó", "o").replace("ú", "u");
        return base + id.toLowerCase() + "@bancoempresa.com";
    }

    public String getIdEmpleado() { return idEmpleado; }
    public String getCargo() { return cargo; }
    public String getCorreo() { return correo; }

    public void procesarTransaccion(Transaccion t) {
        t.setEmpleado(this);
        t.procesar();
        System.out.println("Transacción procesada por " + getNombre() + " " + getApellido());
    }

    @Override
    public void mostrarDatos() {
        System.out.println("Empleado ID: " + idEmpleado + " - Cargo: " + cargo);
        super.mostrarDatos();
        System.out.println("Correo empresarial: " + correo);
    }
}