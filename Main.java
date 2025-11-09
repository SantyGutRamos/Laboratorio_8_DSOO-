import java.time.LocalDate;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Banco gestor = new Banco();
        Scanner sc = new Scanner(System.in);
        boolean salir = false;

        while (!salir) {
            mostrarMenu();
            int opcion = Validador.leerEntero(sc, "Opción inválida. Intente de nuevo: ");
            sc.nextLine();

            switch (opcion) {
                case 1 -> registrarCliente(gestor, sc);
                case 2 -> registrarEmpleado(gestor, sc);
                case 3 -> crearCuenta(gestor, sc);
                case 4 -> asignarTitular(gestor, sc);
                case 5 -> registrarDeposito(gestor, sc);
                case 6 -> registrarRetiro(gestor, sc);
                case 7 -> consultarSaldo(gestor, sc);
                case 8 -> verMovimientos(gestor, sc);
                case 9 -> listarCuentasCliente(gestor, sc);
                case 10 -> gestor.listarClientes();
                case 11 -> gestor.listarEmpleados();
                case 12 -> gestor.listarCuentas();
                case 0 -> {
                    salir = true;
                    System.out.println(" Fin del programa.");
                }
                default -> System.out.println(" Opción no válida.");
            }
        }
        sc.close();
    }

    private static void mostrarMenu() {
        System.out.println("\n======= SISTEMA BANCARIO =======");
        System.out.println("1. Registrar cliente");
        System.out.println("2. Registrar empleado");
        System.out.println("3. Crear cuenta");
        System.out.println("4. Asignar titular a cuenta");
        System.out.println("5. Registrar depósito");
        System.out.println("6. Registrar retiro");
        System.out.println("7. Consultar saldo");
        System.out.println("8. Ver movimientos de cuenta");
        System.out.println("9. Listar cuentas de un cliente");
        System.out.println("10. Listar todos los clientes");
        System.out.println("11. Listar todos los empleados");
        System.out.println("12. Listar todas las cuentas");
        System.out.println("0. Salir");
        System.out.print("Seleccione una opción: ");
    }

    // === REGISTRAR CLIENTE ===
    private static void registrarCliente(Banco gestor, Scanner sc) {
        System.out.println("\n--- REGISTRAR CLIENTE ---");
        String idCliente = Validador.leerId(sc, "ID Cliente: ");
        String dni = Validador.leerDni(sc, "DNI (8 dígitos): ");
        String nombre = Validador.leerNombre(sc, "Nombre: ");
        String apellido = Validador.leerNombre(sc, "Apellido: ");
        String direccion = Validador.leerNombre(sc, "Dirección: ");
        String telefono = Validador.leerTelefono(sc, "Teléfono (9 dígitos): ");
        String correo = Validador.leerCorreo(sc, "Correo: ");

        Cliente cliente = new Cliente(idCliente, dni, nombre, apellido, direccion, telefono, correo, "activo");
        gestor.registrarCliente(cliente);
    }

    // === REGISTRAR EMPLEADO ===
    private static void registrarEmpleado(Banco gestor, Scanner sc) {
        System.out.println("\n--- REGISTRAR EMPLEADO ---");
        String idEmpleado = Validador.leerId(sc, "ID Empleado: ");
        String dni = Validador.leerDni(sc, "DNI (8 dígitos): ");
        String nombre = Validador.leerNombre(sc, "Nombre: ");
        String apellido = Validador.leerNombre(sc, "Apellido: ");
        String direccion = Validador.leerNombre(sc, "Dirección: ");
        String telefono = Validador.leerTelefono(sc, "Teléfono (9 dígitos): ");
        String cargo = Validador.leerNombre(sc, "Cargo: ");

        Empleado empleado = new Empleado(idEmpleado, dni, nombre, apellido, direccion, telefono, cargo);
        gestor.registrarEmpleado(empleado);
    }
    private static void crearCuenta(Banco gestor, Scanner sc) {
        System.out.println("\n--- CREAR CUENTA ---");
        String numCuenta = Validador.leerNumeroCuenta(sc, "Número de cuenta: ");
        String tipo = Validador.leerTipoCuenta(sc);
        double saldoInicial = Validador.leerDoublePositivo(sc, "Saldo inicial: ");

        Cuenta cuenta = new Cuenta(numCuenta, tipo, saldoInicial, LocalDate.now());
        gestor.crearCuenta(cuenta);
    }

    // === ASIGNAR TITULAR ===
    private static void asignarTitular(Banco gestor, Scanner sc) {
        System.out.println("\n--- ASIGNAR TITULAR ---");
        String idC = Validador.leerId(sc, "ID Cliente: ");
        String nCuenta = Validador.leerNumeroCuenta(sc, "Número de cuenta: ");
        String tipoTitular = Validador.leerTipoTitular(sc);

        Cliente cl = gestor.buscarClientePorId(idC);
        Cuenta cu = gestor.buscarCuentaPorNumero(nCuenta);

        if (cl != null && cu != null) {
            gestor.asignarTitular(cl, cu, LocalDate.now(), tipoTitular);
        } else {
            System.out.println(" Cliente o cuenta no encontrada.");
        }
    }
    private static void registrarDeposito(Banco gestor, Scanner sc) {
        System.out.println("\n--- DEPÓSITO ---");
        String numDep = Validador.leerNumeroCuenta(sc, "Número de cuenta: ");
        double montoDep = Validador.leerDoublePositivo(sc, "Monto a depositar: ");
        String idEmpDep = Validador.leerId(sc, "ID Empleado que procesa: ");
        gestor.registrarDeposito(numDep, montoDep, idEmpDep);
    }

    // === RETIRO ===
    private static void registrarRetiro(Banco gestor, Scanner sc) {
        System.out.println("\n--- RETIRO ---");
        String numRet = Validador.leerNumeroCuenta(sc, "Número de cuenta: ");
        double montoRet = Validador.leerDoublePositivo(sc, "Monto a retirar: ");
        String idEmpRet = Validador.leerId(sc, "ID Empleado que procesa: ");
        gestor.registrarRetiro(numRet, montoRet, idEmpRet);
    }
    private static void consultarSaldo(Banco gestor, Scanner sc) {
        String num = Validador.leerNumeroCuenta(sc, "Número de cuenta: ");
        gestor.consultarSaldo(num);
    }

    private static void verMovimientos(Banco gestor, Scanner sc) {
        String num = Validador.leerNumeroCuenta(sc, "Número de cuenta: ");
        gestor.verMovimientos(num);
    }
    private static void listarCuentasCliente(Banco gestor, Scanner sc) {
        String id = Validador.leerId(sc, "ID Cliente: ");
        gestor.listarCuentasCliente(id);
    }
}