public class Cajero extends UsuarioEmpleado {

    public Cajero(String id, String correo, String contraseña, String estado,
                  String nombre, String apellido, String direccion, String telefono) {
        super(id, correo, contraseña, estado, nombre, apellido, direccion, telefono);
    }

    @Override
    public void mostrarPermisos() {
        System.out.println("Cajero: Depositar, Retirar, Consultar saldo y movimientos. No puede crear empleados ni eliminar registros.");
    }
}
