import java.time.LocalDate;

public class CuentaAhorro {

    //Atributos
    public static final double SALDO_MINIMO = 100;
    private String numeroCuenta;
    private double saldo;
    private double porcentajeInteres;
    private LocalDate fechaEmision;
    private LocalDate fechaVencimiento;

    //Métodos
    //Constructor
    public CuentaAhorro(String numeroCuenta, double saldo, double porcentajeInteres, String fechaEmision, byte anios){
        if (saldo < SALDO_MINIMO) throw new IllegalArgumentException("Una cuenta de ahorro debe tener al menos $" + SALDO_MINIMO + " de saldo.");
        this.numeroCuenta = numeroCuenta;
        this.saldo = saldo;
        this.porcentajeInteres = porcentajeInteres;
        this.fechaEmision = LocalDate.parse(fechaEmision);
        fechaVencimiento = this.fechaEmision.plusYears(anios);
    }

    //Getters
    public String getNumeroCuenta(){ return numeroCuenta; }

    public double getSaldo(){ return saldo; }

    public double getPorcentajeInteres(){ return porcentajeInteres; }

    public LocalDate getFechaEmision(){ return fechaEmision; }

    public LocalDate getFechaVencimiento(){ return fechaVencimiento; }

    //Setters
    public void setNumeroCuenta(String numeroCuenta) { this.numeroCuenta = numeroCuenta; }

    public void setSaldo(double saldo) {
        if (saldo < SALDO_MINIMO) throw new IllegalArgumentException("Una cuenta de ahorro debe tener al menos $" + SALDO_MINIMO + " de saldo.");
        this.saldo = saldo;
    }

    public void setPorcentajeInteres(double porcentajeInteres) { this.porcentajeInteres = porcentajeInteres; }

    public void setFechaEmision(LocalDate fechaEmision) { this.fechaEmision = fechaEmision; }

    public void setFechaVencimiento(LocalDate fechaVencimiento) { this.fechaVencimiento = fechaVencimiento; }

    //equals()
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof CuentaAhorro)) return false;
        return this.numeroCuenta.equals(((CuentaAhorro) obj).numeroCuenta);
    }

    @Override
    public int hashCode() { return numeroCuenta.hashCode(); }

    //toString()
    @Override
    public String toString(){
        return "Cuenta de ahorro: " + numeroCuenta + "\nSaldo: $" + saldo +
        "\nPorcentaje de interés: " + porcentajeInteres + "%" +
        "\nFecha de emisión: " + fechaEmision.toString() +
        "\nFecha de vencimiento: " + fechaVencimiento.toString() + "\n";
    }

    public boolean estaVigente(){
        return LocalDate.now().isBefore(fechaVencimiento);
    }

    private boolean cuentaVigente(){
        if (!estaVigente()) {
            System.out.println("Transacción bloqueada: la cuenta de ahorro " + numeroCuenta + " venció el " + fechaVencimiento + ".");
            return false;
        }
        return true;
    }

    private boolean puedeTransar(double monto){
        if (!cuentaVigente()) return false;
        if (monto <= 0) {
            System.out.println("Transacción bloqueada: el monto debe ser mayor a $0.");
            return false;
        }
        return true;
    }

    public void pagar(double monto){
        if (!puedeTransar(monto)) return;
        if (saldo - monto < SALDO_MINIMO) System.out.println("Pago rechazado: la cuenta de ahorro " + numeroCuenta + " debe mantener al menos $" + SALDO_MINIMO + " de saldo.");
        else {
            saldo -= monto;
            System.out.println("Pago de $" + monto + " realizado desde la cuenta de ahorro " + numeroCuenta + ". Saldo: $" + saldo);
        }
    }

    public void depositar(double monto){
        if (!puedeTransar(monto)) return;
        saldo += monto;
        System.out.println("Depósito de $" + monto + " realizado en la cuenta de ahorro " + numeroCuenta + ". Saldo: $" + saldo);
    }

    public void generarIntereses(){
        if (!cuentaVigente()) return;
        double intereses = saldo * porcentajeInteres / 100;
        saldo += intereses;
        System.out.println("Intereses de $" + intereses + " generados en la cuenta de ahorro " + numeroCuenta + ". Saldo: $" + saldo);
    }

}
