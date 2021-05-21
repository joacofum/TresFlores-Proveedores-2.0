/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 *
 * @author joaco
 */
@Entity
public class Proveedor implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int codigo;
    private String RUT;
    private String razonSocial;
    private String direccion;
    private String telefono;
    private boolean tipoFacturacion;
    private String mailProveedor;
    private String contactoProveedor;
    private boolean deshabilitado;
    
    //LINKS-------------------------------//
    @ManyToMany(mappedBy = "proveedores", fetch = FetchType.EAGER)
    private List<Articulo> articulos;
    
    @OneToMany(mappedBy = "proveedor")
    private List<Historial> historiales;
    
    @OneToMany(mappedBy = "proveedor")
    private List<Comprobante> comprobantes;
    
    @ManyToOne
    private Usuario usuario;
    //--------------------------------------//

    public Proveedor() {
        this.articulos = new ArrayList<>();
    }
          
    public int getId() {
        return codigo;
    }

    public void setId(int id) {
        this.codigo = id;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getRUT() {
        return RUT;
    }

    public void setRUT(String RUT) {
        this.RUT = RUT;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public boolean isTipoFacturacion() {
        return tipoFacturacion;
    }

    public void setTipoFacturacion(boolean tipoFacturacion) {
        this.tipoFacturacion = tipoFacturacion;
    }

    public String getMailProveedor() {
        return mailProveedor;
    }

    public void setMailProveedor(String mailProveedor) {
        this.mailProveedor = mailProveedor;
    }

    public String getContactoProveedor() {
        return contactoProveedor;
    }

    public void setContactoProveedor(String contactoProveedor) {
        this.contactoProveedor = contactoProveedor;
    }

    public List<Articulo> getArticulos() {
        return articulos;
    }

    public void setArticulos(List<Articulo> articulos) {
        this.articulos = articulos;
    }

    public List<Historial> getHistoriales() {
        return historiales;
    }

    public void setHistoriales(List<Historial> historiales) {
        this.historiales = historiales;
    }

    public List<Comprobante> getComprobantes() {
        return comprobantes;
    }

    public void setComprobantes(List<Comprobante> comprobantes) {
        this.comprobantes = comprobantes;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public boolean isDeshabilitado() {
        return deshabilitado;
    }

    public void setDeshabilitado(boolean deshabilitado) {
        this.deshabilitado = deshabilitado;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) codigo;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Proveedor)) {
            return false;
        }
        Proveedor other = (Proveedor) object;
        if (this.codigo != other.codigo) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return this.getRazonSocial();
    }
    
}
