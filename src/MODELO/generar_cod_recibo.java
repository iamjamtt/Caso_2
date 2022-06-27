package MODELO;

public class generar_cod_recibo {

    private int dato;
    private int cont = 1;
    private String num = "";

    //aqui se genera el codigo de cita
    //por ejemplo si el dato=3
    public void generar(int dato) {
        this.dato = dato;
        
        // 3 esta en el rango de 100 a 1000? No
        if ((this.dato >= 100) || (this.dato < 1000)) {
            int can = cont + this.dato;
            num = "" + can;
        }
        
        // 3 esta en el rango de 9 a 100? No
        if ((this.dato >= 9) || (this.dato < 100)) {
            int can = cont + this.dato;
            num = "0" + can;
        }
        
        // pero 3 si es menor que 9, entonces me ingresa a esta condicion
        // entonces can = 1 + 3
        // ahora la cantidad viene a ser 4
        // entonces concatenamos que nun = "00" + 4, num = "004" 
        if (this.dato < 9) {
            int can = cont + this.dato;
            num = "00" + can;
        }

    }

    //aqui ya retornamos num = "004"
    public String serie() {
        return this.num;
    }
}
