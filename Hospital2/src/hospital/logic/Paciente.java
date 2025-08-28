package hospital.logic;

public class Paciente {

    private String id;
    private String nombre;
    private int numeroTelefonico;


    Paciente(String iD,String nombre,int numero){

        this.id=iD;
        this.nombre=nombre;
        this.numeroTelefonico=numero;

    }
    Paciente(){
        this.id=" ";
        this.numeroTelefonico=0;
        this.nombre=" ";

    }
    public String getId() {
        return id;
    }
    public String getNombre(){return nombre;}
    public String getNumeroTelefonico(){return numeroTelefonico;}
}