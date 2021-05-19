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

public enum tipoComprobante {
    Contado("Contado"),
    Credito("Crédito"),
    DevolucionContado("Devolución Contado"),
    NotaCredito("Nota de crédito");
    
    public final String label;
    
    private tipoComprobante(String label){
        this.label = label;
    }
    
    @Override
    public String toString(){
        return label;
    }
}
