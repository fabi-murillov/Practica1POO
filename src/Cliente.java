import java.util.ArrayList;

public class Cliente {

    //Atributos
    private String nombreCompleto;
    private String cedula;
    private String sexo;
    private String correoElectronico;
    private String profesion;
    private String direccion;
    private String contrasenia;
    private ArrayList<CuentaAhorro> cuentasAhorro;
    private ArrayList<CuentaDebito> cuentasDebito;
    private ArrayList<CuentaCredito> cuentasCredito;

    //Métodos
    //Constructor
    public Cliente(String nombreCompleto, String cedula, String sexo, String correoElectronico, String profesion, String direccion, String contrasenia){
        this.nombreCompleto = nombreCompleto;
        this.cedula = cedula;
        this.sexo = sexo;
        this.correoElectronico = correoElectronico;
        this.profesion = profesion;
        this.direccion = direccion;
        setContrasenia(contrasenia);
        cuentasAhorro = new ArrayList<CuentaAhorro>();
        cuentasDebito = new ArrayList<CuentaDebito>();
        cuentasCredito = new ArrayList<CuentaCredito>();
    }

    //Getters
    public String getNombreCompleto(){ return nombreCompleto; }

    public String getCedula(){ return cedula; }

    public String getSexo(){ return sexo; }

    public String getCorreoElectronico(){ return correoElectronico; }

    public String getProfesion(){ return profesion; }

    public String getDireccion(){ return direccion; }

    public ArrayList<CuentaAhorro> getCuentasAhorro(){ return cuentasAhorro; }

    public ArrayList<CuentaDebito> getCuentasDebito(){ return cuentasDebito; }

    public ArrayList<CuentaCredito> getCuentasCredito(){ return cuentasCredito; }

    //Setters
    public void setNombreCompleto(String nombreCompleto) { this.nombreCompleto = nombreCompleto; }

    public void setCedula(String cedula) { this.cedula = cedula; }

    public void setSexo(String sexo) { this.sexo = sexo; }

    public void setCorreoElectronico(String correoElectronico) { this.correoElectronico = correoElectronico; }

    public void setProfesion(String profesion) { this.profesion = profesion; }

    public void setDireccion(String direccion) { this.direccion = direccion; }

    public void setContrasenia(String contrasenia) {
        if (!esContraseniaValida(contrasenia)) throw new IllegalArgumentException("La contraseña debe tener al menos 8 caracteres, una minúscula, una mayúscula, un dígito y un carácter especial.");
        this.contrasenia = contrasenia;
    }

    //equals()
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Cliente)) return false;
        return this.cedula.equals(((Cliente) obj).cedula);
    }

    @Override
    public int hashCode() { return cedula.hashCode(); }

    //toString()
    @Override
    public String toString(){
        return "Nombre completo: " + nombreCompleto + "\nCédula: " + cedula + "\nSexo: " + sexo +
        "\nCorreo electrónico: " + correoElectronico + "\nProfesión: " + profesion +
        "\nDirección: " + direccion + "\n";
    }

    private boolean esContraseniaValida(String contrasenia){
        if (contrasenia == null || contrasenia.length() < 8) return false;
        boolean minuscula = false, mayuscula = false, digito = false, especial = false;
        for (int i = 0; i < contrasenia.length(); i++){
            char c = contrasenia.charAt(i);
            if (Character.isLowerCase(c)) minuscula = true;
            else if (Character.isUpperCase(c)) mayuscula = true;
            else if (Character.isDigit(c)) digito = true;
            else if (!Character.isWhitespace(c)) especial = true;
        }
        return minuscula && mayuscula && digito && especial;
    }

    public boolean validarCredenciales(String correoElectronico, String contrasenia){
        return this.correoElectronico.equals(correoElectronico) && this.contrasenia.equals(contrasenia);
    }

    public void agregarCuentaAhorro(CuentaAhorro cuenta){
        if (cuentasAhorro.contains(cuenta)) System.out.println("La cuenta de ahorro " + cuenta.getNumeroCuenta() + " ya está registrada.");
        else cuentasAhorro.add(cuenta);
    }

    public void agregarCuentaDebito(CuentaDebito cuenta){
        if (cuentasDebito.contains(cuenta)) System.out.println("La cuenta de débito " + cuenta.getNumeroCuenta() + " ya está registrada.");
        else cuentasDebito.add(cuenta);
    }

    public void agregarCuentaCredito(CuentaCredito cuenta){
        if (cuentasCredito.contains(cuenta)) System.out.println("La cuenta de crédito " + cuenta.getNumeroCuenta() + " ya está registrada.");
        else cuentasCredito.add(cuenta);
    }

    public double calcularSaldoConsolidado(){
        double total = 0;
        for (CuentaAhorro cuenta : cuentasAhorro) total += cuenta.getSaldo();
        for (CuentaDebito cuenta : cuentasDebito) total += cuenta.getSaldo();
        for (CuentaCredito cuenta : cuentasCredito) total -= cuenta.getSaldo();
        return total;
    }

    public void imprimirReporte(){
        System.out.println("===== Reporte de cuentas de " + nombreCompleto + " =====");
        for (CuentaAhorro cuenta : cuentasAhorro) System.out.println(cuenta);
        for (CuentaDebito cuenta : cuentasDebito) System.out.println(cuenta);
        for (CuentaCredito cuenta : cuentasCredito) System.out.println(cuenta);
        System.out.println("Saldo final consolidado: $" + calcularSaldoConsolidado() + "\n");
    }

}
