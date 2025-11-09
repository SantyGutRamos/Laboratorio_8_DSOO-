public class Retiro extends Transaccion {
    public Retiro(Cuenta cuenta, double monto) {
        super(cuenta, monto);
    }

    @Override
    public void procesar() {
        if (getMonto() <= 0) throw new IllegalArgumentException("Monto debe ser mayor que 0");

        if (getMonto() > getCuenta().getSaldo()) {
            throw new RuntimeException("Fondos insuficientes para retiro");
        }
        getCuenta().retirar(getMonto());
        getCuenta().agregarTransaccion(this);
    }
}
