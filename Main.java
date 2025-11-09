import java.time.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Banco gestor = new Banco();
        Scanner sc = new Scanner(System.in);
        boolean salir = false;

        while (!salir) {
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

            int opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {
                case 1:
                    System.out.println("\n--- REGISTRAR CLIENTE ---");
                    System.out.print("ID Cliente: ");
                    String idCliente = sc.nextLine();
                    System.out.print("DNI: ");
                    String dni = sc.nextLine();
                    System.out.print("Nombre: ");
                    String nombre = sc.nextLine();
                    System.out.print("Apellido: ");
                    String apellido = sc.nextLine();
                    System.out.print("Dirección: ");
                    String direccion = sc.nextLine();
                    System.out.print("Teléfono: ");
                    String telefono = sc.nextLine();
                    System.out.print("Correo: ");
                    String correo = sc.nextLine();

                    Cliente cliente = new Cliente(idCliente, dni, nombre, apellido, direccion, telefono, correo, "activo");
                    gestor.registrarCliente(cliente);
                    break;

                case 2:
                    System.out.println("\n--- REGISTRAR EMPLEADO ---");
                    System.out.print("ID Empleado: ");
                    String idEmpleado = sc.nextLine();
                    System.out.print("DNI: ");
                    String dniE = sc.nextLine();
                    System.out.print("Nombre: ");
                    String nomE = sc.nextLine();
                    System.out.print("Apellido: ");
                    String apeE = sc.nextLine();
                    System.out.print("Dirección: ");
                    String dirE = sc.nextLine();
                    System.out.print("Teléfono: ");
                    String telE = sc.nextLine();
                    System.out.print("Cargo: ");
                    String cargo = sc.nextLine();

                    Empleado empleado = new Empleado(idEmpleado, dniE, nomE, apeE, dirE, telE, cargo);
                    gestor.registrarEmpleado(empleado);
                    break;

                case 3:
                    System.out.println("\n--- CREAR CUENTA ---");
                    System.out.print("Número de cuenta: ");
                    String numCuenta = sc.nextLine();
                    System.out.print("Tipo de cuenta (Ahorros / Corriente): ");
                    String tipo = sc.nextLine();
                    System.out.print("Saldo inicial: ");
                    double saldoInicial = sc.nextDouble();
                    sc.nextLine();

                    Cuenta cuenta = new Cuenta(numCuenta, tipo, saldoInicial, LocalDate.now());
                    gestor.crearCuenta(cuenta);
                    break;

                case 4:
                    System.out.println("\n--- ASIGNAR TITULAR ---");
                    System.out.print("ID Cliente: ");
                    String idC = sc.nextLine();
                    System.out.print("Número de cuenta: ");
                    String nCuenta = sc.nextLine();
                    System.out.print("Tipo titular (Principal / Secundario): ");
                    String tipoTitular = sc.nextLine();

                    Cliente cl = gestor.buscarClientePorId(idC);
                    Cuenta cu = gestor.buscarCuentaPorNumero(nCuenta);

                    if (cl != null && cu != null) {
                        gestor.asignarTitular(cl, cu, LocalDate.now(), tipoTitular);
                    } else {
                        System.out.println(" Cliente o cuenta no encontrada");
                    }
                    break;

                case 5:
                    System.out.println("\n--- DEPÓSITO ---");
                    System.out.print("Número de cuenta: ");
                    String numDep = sc.nextLine();
                    System.out.print("Monto a depositar: ");
                    double montoDep = sc.nextDouble();
                    sc.nextLine();
                    System.out.print("ID Empleado que procesa: ");
                    String idEmpDep = sc.nextLine();
                    gestor.registrarDeposito(numDep, montoDep, idEmpDep);
                    break;

                case 6:
                    System.out.println("\n--- RETIRO ---");
                    System.out.print("Número de cuenta: ");
                    String numRet = sc.nextLine();
                    System.out.print("Monto a retirar: ");
                    double montoRet = sc.nextDouble();
                    sc.nextLine();
                    System.out.print("ID Empleado que procesa: ");
                    String idEmpRet = sc.nextLine();
                    gestor.registrarRetiro(numRet, montoRet, idEmpRet);
                    break;

                case 7:
                    System.out.println("\n--- CONSULTAR SALDO ---");
                    System.out.print("Número de cuenta: ");
                    String numSaldo = sc.nextLine();
                    gestor.consultarSaldo(numSaldo);
                    break;

                case 8:
                    System.out.println("\n--- VER MOVIMIENTOS ---");
                    System.out.print("Número de cuenta: ");
                    String numMov = sc.nextLine();
                    gestor.verMovimientos(numMov);
                    break;

                case 9:
                    System.out.println("\n--- CUENTAS DE UN CLIENTE ---");
                    System.out.print("ID Cliente: ");
                    String idCliList = sc.nextLine();
                    gestor.listarCuentasCliente(idCliList);
                    break;

                case 10:
                    System.out.println("\n--- LISTAR CLIENTES ---");
                    gestor.listarClientes();
                    break;

                case 11:
                    System.out.println("\n--- LISTAR EMPLEADOS ---");
                    gestor.listarEmpleados();
                    break;

                case 12:
                    System.out.println("\n--- LISTAR CUENTAS ---");
                    gestor.listarCuentas();
                    break;

                case 0:
                    salir = true;
                    System.out.println(" Fin del programa.");
                    break;

                default:
                    System.out.println(" Opción no válida, intente de nuevo.");
            }
        }

        sc.close();
    }
}
