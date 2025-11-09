import java.time.*;
import java.util.*;

public class Banco {
    private Map<String, Cliente> clientes = new HashMap<>();
    private Map<String, Empleado> empleados = new HashMap<>();
    private Map<String, Cuenta> cuentas = new HashMap<>();
    private List<Titularidad> titularidades = new ArrayList<>();



    public void registrarCliente(Cliente c) {
        clientes.put(c.getIdCliente(), c);
        System.out.println(" Cliente registrado: " + c.getNombre() + " " + c.getApellido());
    }

    public void registrarEmpleado(Empleado e) {
        empleados.put(e.getIdEmpleado(), e);
        System.out.println(" Empleado registrado: " + e.getNombre() + " " + e.getApellido());
    }

    public void crearCuenta(Cuenta c) {
        cuentas.put(c.getNumeroCuenta(), c);
        System.out.println(" Cuenta creada: " + c.getNumeroCuenta());
    }


    public Cliente buscarClientePorId(String id) {
        return clientes.get(id);
    }

    public Empleado buscarEmpleadoPorId(String id) {
        return empleados.get(id);
    }

    public Cuenta buscarCuentaPorNumero(String numero) {
        return cuentas.get(numero);
    }

    // -------------------------
    // TITULARIDAD
    // -------------------------

    public void asignarTitular(Cliente cliente, Cuenta cuenta, LocalDate fechaInicio, String tipoTitular) {
        Titularidad t = new Titularidad(cliente, cuenta, fechaInicio, tipoTitular);
        titularidades.add(t);
        cliente.agregarTitularidad(t);
        System.out.println("Titularidad asignada: " + cliente.getNombre() + "  Cuenta " + cuenta.getNumeroCuenta());
    }


    public void registrarDeposito(String numeroCuenta, double monto, String idEmpleado) {
        Cuenta c = buscarCuentaPorNumero(numeroCuenta);
        Empleado e = buscarEmpleadoPorId(idEmpleado);

        if (c == null) {
            System.out.println(" Cuenta no encontrada");
            return;
        }
        if (e == null) {
            System.out.println(" Empleado no encontrado");
            return;
        }

        Deposito d = new Deposito(c, monto);
        e.procesarTransaccion(d);
    }

    public void registrarRetiro(String numeroCuenta, double monto, String idEmpleado) {
        Cuenta c = buscarCuentaPorNumero(numeroCuenta);
        Empleado e = buscarEmpleadoPorId(idEmpleado);

        if (c == null) {
            System.out.println("Cuenta no encontrada");
            return;
        }
        if (e == null) {
            System.out.println(" Empleado no encontrado");
            return;
        }

        Retiro r = new Retiro(c, monto);
        e.procesarTransaccion(r);
    }

    public void consultarSaldo(String numeroCuenta) {
        Cuenta c = buscarCuentaPorNumero(numeroCuenta);
        if (c == null) {
            System.out.println(" Cuenta no encontrada");
            return;
        }
        System.out.println(" Saldo actual de la cuenta " + numeroCuenta + ": " + c.getSaldo());
    }

    public void verMovimientos(String numeroCuenta) {
        Cuenta c = buscarCuentaPorNumero(numeroCuenta);
        if (c == null) {
            System.out.println("Cuenta no encontrada");
            return;
        }
        c.verMovimientos();
    }

    public void listarCuentasCliente(String idCliente) {
        Cliente cl = buscarClientePorId(idCliente);
        if (cl == null) {
            System.out.println(" Cliente no encontrado");
            return;
        }

        List<Cuenta> cuentasCliente = cl.consultarCuentas();
        if (cuentasCliente.isEmpty()) {
            System.out.println("ℹ El cliente no tiene cuentas registradas.");
        } else {
            System.out.println(" Cuentas del cliente " + cl.getNombre() + ":");
            for (Cuenta c : cuentasCliente) {
                System.out.println(" - " + c);
            }
        }
    }
    public void listarClientes() {
        if (clientes.isEmpty()) {
            System.out.println("No hay clientes registrados.");
            return;
        }
        System.out.println(" Lista de clientes:");
        for (Cliente c : clientes.values()) {
            System.out.println(" - " + c.getIdCliente() + ": " + c.getNombre() + " " + c.getApellido());
        }
    }

    public void listarEmpleados() {
        if (empleados.isEmpty()) {
            System.out.println("No hay empleados registrados.");
            return;
        }
        System.out.println(" Lista de empleados:");
        for (Empleado e : empleados.values()) {
            System.out.println(" - " + e.getIdEmpleado() + ": " + e.getNombre() + " " + e.getApellido());
        }
    }

    public void listarCuentas() {
        if (cuentas.isEmpty()) {
            System.out.println("No hay cuentas creadas.");
            return;
        }
        System.out.println(" Lista de cuentas:");
        for (Cuenta c : cuentas.values()) {
            System.out.println(" - " + c);
        }
    }
}
