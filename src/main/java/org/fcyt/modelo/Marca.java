package org.fcyt.modelo;

public class Marca {
    private  int id;
    private String descripcion;

    public Marca(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return descripcion; // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }

    @Override
    public boolean equals(Object obj) {
        Integer cod1 = getId();
        Integer cod2 =  ((Marca)obj).getId();
        
        if(cod1.equals(cod2))return true;
        
        return false;
    }
   
    
    
}
