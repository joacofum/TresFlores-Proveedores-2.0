/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;

/**
 *
 * @author joaco
 */
@Entity
public class IVA implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    
    @Enumerated(EnumType.ORDINAL)
    private tipoIVA tipo;
    private int porcentaje;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fecha;   
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaRegir;
    
    //LINKS-----------------------//
    @ManyToOne
    private Administrador admin;
    
    @OneToMany(mappedBy = "iva")
    private List<Articulo> articulos;
    //----------------------------//
         
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public tipoIVA getTipo() {
        return tipo;
    }

    public void setTipo(tipoIVA tipo) {
        this.tipo = tipo;
    }

    public int getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(int porcentaje) {
        this.porcentaje = porcentaje;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Administrador getAdmin() {
        return admin;
    }

    public void setAdmin(Administrador admin) {
        this.admin = admin;
    }

    public List<Articulo> getProductos() {
        return articulos;
    }

    public void setProductos(List<Articulo> articulos) {
        this.articulos = articulos;
    }

    public Date getFechaRegir() {
        return fechaRegir;
    }

    public void setFechaRegir(Date fechaRegir) {
        this.fechaRegir = fechaRegir;
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
        if (!(object instanceof IVA)) {
            return false;
        }
        IVA other = (IVA) object;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");       
        return this.getTipo().toString() + " - " + this.getPorcentaje() + "% - " + sdf.format(this.getFechaRegir());
    }
    
}
