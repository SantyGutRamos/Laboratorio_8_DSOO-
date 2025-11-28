// Administrador.java
public class Administrador extends Usuario {
    public Administrador(String id, String correo, String contraseña, String estado,
                         String nombre, String apellido, String direccion, String telefono) {
        super(id, correo, contraseña, estado, nombre, apellido, direccion, telefono);
    }

    @Override
    public void mostrarPermisos() {
        System.out.println("Administrador: todos los permisos del sistema (crear/eliminar/modificar usuarios y cuentas).");
    }
}
