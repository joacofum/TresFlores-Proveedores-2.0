/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;

/**
 *
 * @author joaco
 */
@Entity
public class Historial implements Serializable,Comparable<Historial> {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    
    private float precio;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fecha;
    
    //LINKS---------------------//
    @ManyToOne
    private Proveedor proveedor;
    
    @ManyToOne
    private Articulo articulo;
    //--------------------------//

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }

    public Articulo getProducto() {
        return articulo;
    }

    public void setProducto(Articulo articulo) {
        this.articulo = articulo;
    }       

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) id;
        return hash;
    }   

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Historial)) {
            return false;
        }
        Historial other = (Historial) object;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Clases.Historial[ id=" + id + " ]";
    }

    @Override
    public int compareTo(Historial o) {
        return getFecha().compareTo(o.getFecha());
    }
    
}
