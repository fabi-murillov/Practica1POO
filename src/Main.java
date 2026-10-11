public class Main {

    public static void main(String[] args) {

        // Administrador (contraseña válida) y validación de credenciales
        Administrador admin = new Administrador("Romario Salas Cerdas", "1-1111-1111", "romario@banco.com", "Admin#2026");
        System.out.println(admin);
        System.out.println("¿Credenciales del administrador válidas? " + admin.validarCredenciales("romario@banco.com", "Admin#2026"));
        System.out.println("¿Credenciales con contraseña errónea? " + admin.validarCredenciales("romario@banco.com", "incorrecta"));

        // Una contraseña que no cumple las condiciones es rechazada
        try {
            new Cliente("Prueba", "0-0000-0000", "M", "prueba@correo.com", "Tester", "San José", "abc123");
        } catch (IllegalArgumentException e) {
            System.out.println("Contraseña rechazada: " + e.getMessage());
        }
        System.out.println();

        // Dos clientes registrados por el administrador (el duplicado es rechazado)
        Cliente ana = new Cliente("Ana Mora Jiménez", "2-2222-2222", "F", "ana@correo.com", "Ingeniera", "Heredia", "Ana*1234");
        Cliente luis = new Cliente("Luis Vargas Soto", "3-3333-3333", "M", "luis@correo.com", "Contador", "Alajuela", "Luis$5678");
        admin.registrarCliente(ana);
        admin.registrarCliente(luis);
        admin.registrarCliente(ana);
        System.out.println("¿Credenciales de Ana válidas? " + ana.validarCredenciales("ana@correo.com", "Ana*1234"));
        System.out.println();

        // Cuentas: vigentes y una de débito ya vencida
        CuentaAhorro ahorro = new CuentaAhorro("AH-001", 500, 2.5, "2025-01-10", (byte) 5);
        CuentaDebito debito = new CuentaDebito("DB-001", 300, 1.0, "2025-01-10", (byte) 5);
        CuentaDebito debitoVencida = new CuentaDebito("DB-002", 200, 1.0, "2015-01-10", (byte) 5);
        CuentaCredito credito = new CuentaCredito("CR-001", 1000, 3.0, "Cashback", "2025-01-10", (byte) 5);
        ana.agregarCuentaAhorro(ahorro);
        ana.agregarCuentaDebito(debito);
        ana.agregarCuentaDebito(debitoVencida);
        ana.agregarCuentaCredito(credito);
        ana.agregarCuentaAhorro(ahorro);
        luis.agregarCuentaAhorro(new CuentaAhorro("AH-002", 1000, 2.0, "2024-06-01", (byte) 4));
        luis.agregarCuentaCredito(new CuentaCredito("CR-002", 2000, 2.5, "Millas", "2024-06-01", (byte) 4));

        // Una cuenta de ahorro no puede crearse con menos de $100
        try {
            new CuentaAhorro("AH-999", 50, 2.0, "2025-01-10", (byte) 5);
        } catch (IllegalArgumentException e) {
            System.out.println("Cuenta rechazada: " + e.getMessage());
        }
        System.out.println();

        // Cuenta de ahorro: pago válido, pago que dejaría menos de $100, depósito e intereses
        ahorro.pagar(100);
        ahorro.pagar(350);
        ahorro.depositar(200);
        ahorro.generarIntereses();
        System.out.println();

        // Cuenta de débito: pago válido y pago mayor al saldo
        debito.pagar(100);
        debito.pagar(500);
        debito.depositar(50);
        debito.generarIntereses();
        System.out.println();

        // Cuenta de débito vencida: todas las transacciones se bloquean
        debitoVencida.pagar(10);
        debitoVencida.depositar(10);
        debitoVencida.generarIntereses();
        System.out.println();

        // Cuenta de crédito: pago, pago sobre el límite, abono, abono excesivo e intereses
        credito.pagar(400);
        credito.pagar(700);
        credito.abonar(150);
        credito.abonar(900);
        credito.generarIntereses();
        System.out.println();

        // Reporte del cliente con saldo consolidado
        ana.imprimirReporte();

        // Reportes del administrador
        admin.imprimirClientes();
        admin.imprimirCuentas();

        // equals() de las clases de entidad
        System.out.println("¿Mismo cliente por cédula? " + ana.equals(new Cliente("Otro Nombre", "2-2222-2222", "F", "otro@correo.com", "Otra", "Otra", "Otra#1234")));
        System.out.println("¿Misma cuenta de ahorro por número? " + ahorro.equals(new CuentaAhorro("AH-001", 100, 1, "2025-01-01", (byte) 1)));
    }
}
