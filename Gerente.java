// Gerente.java
public class Gerente extends UsuarioEmpleado {

    public Gerente(String id, String correo, String contraseña, String estado,
                   String nombre, String apellido, String direccion, String telefono) {
        super(id, correo, contraseña, estado, nombre, apellido, direccion, telefono);
    }

    @Override
    public void mostrarPermisos() {
        System.out.println("Gerente: Todo lo del cajero + crear cuentas, editar datos de clientes, listar clientes y cuentas.");
    }
}
