/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;


/**
 *
 * @author joaco
 */

public enum tipoIVA {
    Basico("Básico"),
    Minimo("Mínimo"),
    Excento("Excento");
     
    public final String label;
    
    private tipoIVA(String label){
        this.label = label;
    }
    
    @Override
    public String toString(){
        return label;
    }
}
