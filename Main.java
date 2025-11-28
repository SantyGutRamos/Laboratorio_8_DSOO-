import java.time.LocalDate;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Banco banco = new Banco();
        GestorUsuarios gestor = new GestorUsuarios(banco);
        Scanner sc = new Scanner(System.in);

        Usuario admin = new Administrador(
                "ADM0000001",
                "admin@banco.com",
                "admin123",
                "activo",
                "Admin",
                "Principal",
                "Oficina Central",
                "999999999"
        );

        UsuarioEmpleado gerente = new Gerente(
                "EMP0000001",
                "gerente@banco.com",
                "gerente123",
                "activo",
                "María",
                "García",
                "Sucursal Norte",
                "998887766"
        );

        UsuarioEmpleado cajero = new Cajero(
                "EMP0000002",
                "cajero@banco.com",
                "cajero123",
                "activo",
                "Carlos",
                "López",
                "Sucursal Sur",
                "997776655"
        );

        UsuarioCliente clienteNormal = new ClienteNormal(
                "CLI0000001",
                "cliente@banco.com",
                "cliente123",
                "activo",
                "Juan",
                "Pérez",
                "Av. Principal 123",
                "999111222"
        );

        UsuarioCliente clienteVIP = new ClienteVIP(
                "CLI0000002",
                "vip@banco.com",
                "vip123",
                "activo",
                "Ana",
                "Vega",
                "Calle Falsa 456",
                "999333444"
        );

        gestor.registrarUsuario(admin);
        gestor.registrarUsuario(gerente);
        gestor.registrarUsuario(cajero);
        gestor.registrarUsuario(clienteNormal);
        gestor.registrarUsuario(clienteVIP);

        boolean salir = false;
while (!salir) {
    System.out.println("\n=== SISTEMA BANCARIO - LOGIN ===");
    System.out.println("1. Iniciar sesión");
    System.out.println("0. Salir");
    System.out.print("Seleccione: ");
    int opcion = Validador.leerEntero(sc, "Opción inválida. Intente de nuevo: ");

    switch (opcion) {
        case 1 -> {
            System.out.print("Correo: ");
            String correo = sc.nextLine().trim();

            // Validación de correo
            if (!correo.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
                System.out.println("El correo no tiene un formato válido.");
                continue; 
            }

            System.out.print("Contraseña: ");
            String pass = sc.nextLine().trim();

            Usuario u = gestor.login(correo, pass);
            if (u == null) {
                System.out.println("Login fallido.");
                continue; // vuelve al menú sin cerrar el programa
            }

            System.out.println("Bienvenido: " + u.getNombre() + " " + u.getApellido() + " (" + u.getClass().getSimpleName() + ")");

            if (u instanceof UsuarioCliente) menuCliente((UsuarioCliente) u, banco, sc);
            else if (u instanceof Cajero) menuCajero((Cajero) u, banco, sc);
            else if (u instanceof Gerente) menuGerente((Gerente) u, banco, sc);
            else if (u instanceof UsuarioEmpleado) menuEmpleadoGenerico((UsuarioEmpleado) u, banco, sc);
            else if (u instanceof Administrador) menuAdmin((Administrador) u, banco, gestor, sc);
            else System.out.println("Tipo de usuario no soportado en menús.");
        }

        case 0 -> {
            salir = true;
            System.out.println("Saliendo...");
        }

        default -> System.out.println("Opción no válida.");
    }
}

        sc.close();
    }

    private static void menuCliente(UsuarioCliente u, Banco banco, Scanner sc) {
        boolean salir = false;
        while (!salir) {
            System.out.println("\n--- MENU CLIENTE (" + u.getNombre() + ") ---");
            System.out.println("1. Mostrar datos");
            System.out.println("2. Ver mis cuentas");
            System.out.println("3. Consultar saldo (ingrese número cuenta)");
            System.out.println("4. Ver movimientos (número cuenta)");
            System.out.println("5. Depositar en mi cuenta");
            System.out.println("6. Retirar de mi cuenta");
            System.out.println("0. Cerrar sesión");
            int op = Validador.leerEntero(sc, "Opción inválida: ");
            switch (op) {
                case 1 -> u.mostrarDatos();
                case 2 -> u.mostrarCuentas();
                case 3 -> {
                    String num = Validador.leerNumeroCuenta(sc, "Número de cuenta: ");
                    banco.consultarSaldo(num);
                }
                case 4 -> {
                    String num = Validador.leerNumeroCuenta(sc, "Número de cuenta: ");
                    banco.verMovimientos(num);
                }
                case 5 -> {
                    String num = Validador.leerNumeroCuenta(sc, "Número de cuenta (propia): ");
                    double monto = Validador.leerDoublePositivo(sc, "Monto a depositar: ");
                    // buscar cuenta en lista de usuario
                    Cuenta cuenta = null;
                    for (Cuenta c : u.getCuentas()) if (c.getNumeroCuenta().equals(num)) cuenta = c;
                    if (cuenta == null) System.out.println("Cuenta no encontrada entre sus cuentas.");
                    else banco.registrarDeposito(num, monto, null);
                }
                case 6 -> {
                    String num = Validador.leerNumeroCuenta(sc, "Número de cuenta (propia): ");
                    double monto = Validador.leerDoublePositivo(sc, "Monto a retirar: ");
                    Cuenta cuenta = null;
                    for (Cuenta c : u.getCuentas()) if (c.getNumeroCuenta().equals(num)) cuenta = c;
                    if (cuenta == null) System.out.println("Cuenta no encontrada entre sus cuentas.");
                    else {
                        // verificar límites según tipo de cliente
                        if (u instanceof ClienteNormal) {
                            ClienteNormal cn = (ClienteNormal) u;
                            if (monto > cn.getLimiteRetiro()) {
                                System.out.println("Límite excedido para ClienteNormal: S/ " + cn.getLimiteRetiro());
                                break;
                            }
                        } else if (u instanceof ClienteVIP) {
                            ClienteVIP cv = (ClienteVIP) u;
                            if (monto > cv.getLimiteRetiro()) {
                                System.out.println("Límite excedido para ClienteVIP: S/ " + cv.getLimiteRetiro());
                                break;
                            }
                        }
                        banco.registrarRetiro(num, monto, null);
                    }
                }
                case 0 -> salir = true;
                default -> System.out.println("Opción no válida.");
            }
        }
    }

    private static void menuCajero(Cajero cajero, Banco banco, Scanner sc) {
        boolean salir = false;
        while (!salir) {
            System.out.println("\n--- MENU CAJERO (" + cajero.getNombre() + ") ---");
            System.out.println("1. Procesar depósito");
            System.out.println("2. Procesar retiro");
            System.out.println("3. Consultar saldo");
            System.out.println("4. Ver movimientos");
            System.out.println("0. Cerrar sesión");
            int op = Validador.leerEntero(sc, "Opción inválida: ");
            switch (op) {
                case 1 -> {
                    String num = Validador.leerNumeroCuenta(sc, "Número de cuenta: ");
                    double monto = Validador.leerDoublePositivo(sc, "Monto a depositar: ");
                    banco.registrarDeposito(num, monto, cajero);
                }
                case 2 -> {
                    String num = Validador.leerNumeroCuenta(sc, "Número de cuenta: ");
                    double monto = Validador.leerDoublePositivo(sc, "Monto a retirar: ");
                    banco.registrarRetiro(num, monto, cajero);
                }
                case 3 -> {
                    String num = Validador.leerNumeroCuenta(sc, "Número de cuenta: ");
                    banco.consultarSaldo(num);
                }
                case 4 -> {
                    String num = Validador.leerNumeroCuenta(sc, "Número de cuenta: ");
                    banco.verMovimientos(num);
                }
                case 0 -> salir = true;
                default -> System.out.println("Opción no válida.");
            }
        }
    }

    private static void menuGerente(Gerente gerente, Banco banco, Scanner sc) {
        boolean salir = false;
        while (!salir) {
            System.out.println("\n--- MENU GERENTE (" + gerente.getNombre() + ") ---");
            System.out.println("1. Procesar depósito");
            System.out.println("2. Procesar retiro");
            System.out.println("3. Consultar saldo");
            System.out.println("4. Ver movimientos");
            System.out.println("5. Crear cuenta");
            System.out.println("6. Listar clientes");
            System.out.println("7. Listar cuentas");
            System.out.println("0. Cerrar sesión");
            int op = Validador.leerEntero(sc, "Opción inválida: ");
            switch (op) {
                case 1 -> {
                    String num = Validador.leerNumeroCuenta(sc, "Número de cuenta: ");
                    double monto = Validador.leerDoublePositivo(sc, "Monto a depositar: ");
                    banco.registrarDeposito(num, monto, gerente);
                }
                case 2 -> {
                    String num = Validador.leerNumeroCuenta(sc, "Número de cuenta: ");
                    double monto = Validador.leerDoublePositivo(sc, "Monto a retirar: ");
                    banco.registrarRetiro(num, monto, gerente);
                }
                case 3 -> {
                    String num = Validador.leerNumeroCuenta(sc, "Número de cuenta: ");
                    banco.consultarSaldo(num);
                }
                case 4 -> {
                    String num = Validador.leerNumeroCuenta(sc, "Número de cuenta: ");
                    banco.verMovimientos(num);
                }
                case 5 -> {
                    String num = Validador.leerNumeroCuenta(sc, "Número de cuenta nueva: ");
                    String tipo = Validador.leerTipoCuenta(sc);
                    double saldo = Validador.leerDoublePositivo(sc, "Saldo inicial: ");
                    Cuenta c = new Cuenta(num, tipo, saldo, LocalDate.now());
                    banco.crearCuenta(c);
                }
                case 6 -> banco.listarClientes();
                case 7 -> banco.listarCuentas();
                case 0 -> salir = true;
                default -> System.out.println("Opción no válida.");
            }
        }
    }

    private static void menuEmpleadoGenerico(UsuarioEmpleado empleado, Banco banco, Scanner sc) {
        System.out.println("Accediendo al menú genérico de empleado...");
        if (empleado instanceof Cajero) menuCajero((Cajero) empleado, banco, sc);
        else if (empleado instanceof Gerente) menuGerente((Gerente) empleado, banco, sc);
        else {
            menuCajero(new Cajero(empleado.getId(), empleado.getCorreo(), empleado.getContraseña(), empleado.getEstado(), empleado.getNombre(), empleado.getApellido(), empleado.direccion, empleado.telefono), banco, sc);
        }
    }

    private static void menuAdmin(Administrador admin, Banco banco, GestorUsuarios gestor, Scanner sc) {
        boolean salir = false;
        while (!salir) {
            System.out.println("\n--- MENU ADMIN (" + admin.getNombre() + ") ---");
            System.out.println("1. Crear cliente");
            System.out.println("2. Crear empleado (Cajero/Gerente)");
            System.out.println("3. Crear cuenta");
            System.out.println("4. Eliminar cliente");
            System.out.println("5. Eliminar empleado");
            System.out.println("6. Eliminar cuenta");
            System.out.println("7. Listar clientes");
            System.out.println("8. Listar empleados");
            System.out.println("9. Listar cuentas");
            System.out.println("0. Cerrar sesión");
            int op = Validador.leerEntero(sc, "Opción inválida: ");
            switch (op) {
                case 1 -> {
                    System.out.println("--- CREAR CLIENTE ---");
                    String id = Validador.leerId(sc, "ID Cliente (10 chars): ");
                    String correo = Validador.leerCorreo(sc, "Correo cliente: ");
                    String pass = GestorUsuarios.generarContraseñaSegura();
                    String dni = Validador.leerDni(sc, "DNI: ");
                    String nombre = Validador.leerNombre(sc, "Nombre: ");
                    String apellido = Validador.leerNombre(sc, "Apellido: ");
                    String direccion = Validador.leerNombre(sc, "Dirección: ");
                    String telefono = Validador.leerTelefono(sc, "Teléfono: ");
                    System.out.println("Tipo cliente: 1) Normal  2) VIP");
                    int t = Validador.leerEntero(sc, "Seleccione: ");
                    UsuarioCliente nuevo = (t == 2) ?
                            new ClienteVIP(id, correo, pass, "activo", nombre, apellido, direccion, telefono) :
                            new ClienteNormal(id, correo, pass, "activo", nombre, apellido, direccion, telefono);
                    gestor.registrarUsuario(nuevo);
                    System.out.println("Cliente creado. Contraseña asignada: " + pass);
                }
                case 2 -> {
                    System.out.println("--- CREAR EMPLEADO ---");
                    String id = Validador.leerId(sc, "ID Empleado (10 chars): ");
                    String correo = Validador.leerCorreo(sc, "Correo empleado: ");
                    String pass = GestorUsuarios.generarContraseñaSegura();
                    String nombre = Validador.leerNombre(sc, "Nombre: ");
                    String apellido = Validador.leerNombre(sc, "Apellido: ");
                    String direccion = Validador.leerNombre(sc, "Dirección: ");
                    String telefono = Validador.leerTelefono(sc, "Teléfono: ");
                    System.out.println("Tipo empleado: 1) Cajero  2) Gerente");
                    int t = Validador.leerEntero(sc, "Seleccione: ");
                    UsuarioEmpleado nuevo = (t == 2) ?
                            new Gerente(id, correo, pass, "activo", nombre, apellido, direccion, telefono) :
                            new Cajero(id, correo, pass, "activo", nombre, apellido, direccion, telefono);
                    gestor.registrarUsuario(nuevo);
                    System.out.println("Empleado creado. Contraseña asignada: " + pass);
                }
                case 3 -> {
                    String num = Validador.leerNumeroCuenta(sc, "Número de cuenta: ");
                    String tipo = Validador.leerTipoCuenta(sc);
                    double saldo = Validador.leerDoublePositivo(sc, "Saldo inicial: ");
                    Cuenta c = new Cuenta(num, tipo, saldo, LocalDate.now());
                    banco.crearCuenta(c);
                }
                case 4 -> {
                    String id = Validador.leerId(sc, "ID Cliente a eliminar: ");
                    banco.eliminarCliente(id);
                }
                case 5 -> {
                    String id = Validador.leerId(sc, "ID Empleado a eliminar: ");
                    banco.eliminarEmpleado(id);
                }
                case 6 -> {
                    String num = Validador.leerNumeroCuenta(sc, "Número de cuenta a eliminar: ");
                    banco.eliminarCuenta(num);
                }
                case 7 -> banco.listarClientes();
                case 8 -> banco.listarEmpleados();
                case 9 -> banco.listarCuentas();
                case 0 -> salir = true;
                default -> System.out.println("Opción no válida.");
            }
        }
    }
}
