import java.time.LocalDate;

public class CuentaCredito {

    //Atributos
    private String numeroCuenta;
    private double saldo;
    private double limiteCredito;
    private double porcentajeInteres;
    private String tipo;
    private LocalDate fechaEmision;
    private LocalDate fechaVencimiento;

    //Métodos
    //Constructor: el saldo siempre inicia en $0
    public CuentaCredito(String numeroCuenta, double limiteCredito, double porcentajeInteres, String tipo, String fechaEmision, byte anios){
        this.numeroCuenta = numeroCuenta;
        this.saldo = 0;
        this.limiteCredito = limiteCredito;
        this.porcentajeInteres = porcentajeInteres;
        this.tipo = tipo;
        this.fechaEmision = LocalDate.parse(fechaEmision);
        fechaVencimiento = this.fechaEmision.plusYears(anios);
    }

    //Getters
    public String getNumeroCuenta(){ return numeroCuenta; }

    public double getSaldo(){ return saldo; }

    public double getLimiteCredito(){ return limiteCredito; }

    public double getPorcentajeInteres(){ return porcentajeInteres; }

    public String getTipo(){ return tipo; }

    public LocalDate getFechaEmision(){ return fechaEmision; }

    public LocalDate getFechaVencimiento(){ return fechaVencimiento; }

    //Setters
    public void setNumeroCuenta(String numeroCuenta) { this.numeroCuenta = numeroCuenta; }

    public void setSaldo(double saldo) {
        if (saldo < 0 || saldo > limiteCredito) throw new IllegalArgumentException("El saldo de una cuenta de crédito debe estar entre $0 y su límite de crédito.");
        this.saldo = saldo;
    }

    public void setLimiteCredito(double limiteCredito) { this.limiteCredito = limiteCredito; }

    public void setPorcentajeInteres(double porcentajeInteres) { this.porcentajeInteres = porcentajeInteres; }

    public void setTipo(String tipo) { this.tipo = tipo; }

    public void setFechaEmision(LocalDate fechaEmision) { this.fechaEmision = fechaEmision; }

    public void setFechaVencimiento(LocalDate fechaVencimiento) { this.fechaVencimiento = fechaVencimiento; }

    //equals()
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof CuentaCredito)) return false;
        return this.numeroCuenta.equals(((CuentaCredito) obj).numeroCuenta);
    }

    @Override
    public int hashCode() { return numeroCuenta.hashCode(); }

    //toString()
    @Override
    public String toString(){
        return "Cuenta de crédito: " + numeroCuenta + "\nTipo: " + tipo +
        "\nSaldo (deuda): $" + saldo + "\nLímite de crédito: $" + limiteCredito +
        "\nPorcentaje de interés: " + porcentajeInteres + "%" +
        "\nFecha de emisión: " + fechaEmision.toString() +
        "\nFecha de vencimiento: " + fechaVencimiento.toString() + "\n";
    }

    public boolean estaVigente(){
        return LocalDate.now().isBefore(fechaVencimiento);
    }

    private boolean cuentaVigente(){
        if (!estaVigente()) {
            System.out.println("Transacción bloqueada: la cuenta de crédito " + numeroCuenta + " venció el " + fechaVencimiento + ".");
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

    // Un pago con la tarjeta aumenta la deuda sin superar el límite de crédito
    public void pagar(double monto){
        if (!puedeTransar(monto)) return;
        if (saldo + monto > limiteCredito) System.out.println("Pago rechazado: la cuenta de crédito " + numeroCuenta + " superaría su límite de $" + limiteCredito + ".");
        else {
            saldo += monto;
            System.out.println("Pago de $" + monto + " realizado con la cuenta de crédito " + numeroCuenta + ". Saldo (deuda): $" + saldo);
        }
    }

    // Un abono reduce la deuda sin dejar el saldo negativo
    public void abonar(double monto){
        if (!puedeTransar(monto)) return;
        if (monto > saldo) System.out.println("Abono rechazado: el monto supera la deuda actual de $" + saldo + " de la cuenta " + numeroCuenta + ".");
        else {
            saldo -= monto;
            System.out.println("Abono de $" + monto + " realizado a la cuenta de crédito " + numeroCuenta + ". Saldo (deuda): $" + saldo);
        }
    }

    public void generarIntereses(){
        if (!cuentaVigente()) return;
        double intereses = saldo * porcentajeInteres / 100;
        if (saldo + intereses > limiteCredito) System.out.println("Intereses no aplicados: la cuenta de crédito " + numeroCuenta + " superaría su límite de $" + limiteCredito + ".");
        else {
            saldo += intereses;
            System.out.println("Intereses de $" + intereses + " generados en la cuenta de crédito " + numeroCuenta + ". Saldo (deuda): $" + saldo);
        }
    }

}
