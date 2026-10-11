import java.util.ArrayList;

public class Administrador {

    //Atributos
    private String nombreCompleto;
    private String cedula;
    private String correoElectronico;
    private String contrasenia;
    private ArrayList<Cliente> clientes;

    //Métodos
    //Constructor
    public Administrador(String nombreCompleto, String cedula, String correoElectronico, String contrasenia){
        this.nombreCompleto = nombreCompleto;
        this.cedula = cedula;
        this.correoElectronico = correoElectronico;
        setContrasenia(contrasenia);
        clientes = new ArrayList<Cliente>();
    }

    //Getters
    public String getNombreCompleto(){ return nombreCompleto; }

    public String getCedula(){ return cedula; }

    public String getCorreoElectronico(){ return correoElectronico; }

    public ArrayList<Cliente> getClientes(){ return clientes; }

    //Setters
    public void setNombreCompleto(String nombreCompleto) { this.nombreCompleto = nombreCompleto; }

    public void setCedula(String cedula) { this.cedula = cedula; }

    public void setCorreoElectronico(String correoElectronico) { this.correoElectronico = correoElectronico; }

    public void setContrasenia(String contrasenia) {
        if (!esContraseniaValida(contrasenia)) throw new IllegalArgumentException("La contraseña debe tener al menos 8 caracteres, una minúscula, una mayúscula, un dígito y un carácter especial.");
        this.contrasenia = contrasenia;
    }

    //equals()
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Administrador)) return false;
        return this.cedula.equals(((Administrador) obj).cedula);
    }

    @Override
    public int hashCode() { return cedula.hashCode(); }

    //toString()
    @Override
    public String toString(){
        return "Nombre completo: " + nombreCompleto + "\nCédula: " + cedula +
        "\nCorreo electrónico: " + correoElectronico + "\n";
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

    public void registrarCliente(Cliente cliente){
        if (clientes.contains(cliente)) System.out.println("El cliente con cédula " + cliente.getCedula() + " ya está registrado.");
        else clientes.add(cliente);
    }

    public void imprimirClientes(){
        System.out.println("===== Clientes registrados =====");
        if (clientes.isEmpty()) System.out.println("No hay clientes registrados.\n");
        for (Cliente cliente : clientes) System.out.println(cliente);
    }

    public void imprimirCuentas(){
        System.out.println("===== Cuentas de ahorro registradas =====");
        for (Cliente cliente : clientes)
            for (CuentaAhorro cuenta : cliente.getCuentasAhorro()) System.out.println("Cliente: " + cliente.getNombreCompleto() + "\n" + cuenta);
        System.out.println("===== Cuentas de débito registradas =====");
        for (Cliente cliente : clientes)
            for (CuentaDebito cuenta : cliente.getCuentasDebito()) System.out.println("Cliente: " + cliente.getNombreCompleto() + "\n" + cuenta);
        System.out.println("===== Cuentas de crédito registradas =====");
        for (Cliente cliente : clientes)
            for (CuentaCredito cuenta : cliente.getCuentasCredito()) System.out.println("Cliente: " + cliente.getNombreCompleto() + "\n" + cuenta);
    }

}
