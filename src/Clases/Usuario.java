/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;

/**
 *
 * @author joaco
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    private String cedula;
    private String nombre;
    private String apellido;
    private String contrasenia;
    
    //LINKS---------------------------------//   
    @OneToMany(mappedBy = "usuario")
    private List<Proveedor> proveedores;
    
    @OneToMany(mappedBy = "usuario")
    private List<Comprobante> comprobantes;
    
    @OneToMany(mappedBy = "usuario")
    private List<Articulo> articulos;
    //-------------------------------------//

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public List<Proveedor> getProveedores() {
        return proveedores;
    }

    public void setProveedores(List<Proveedor> proveedores) {
        this.proveedores = proveedores;
    }

    public List<Comprobante> getComprobantes() {
        return comprobantes;
    }

    public void setComprobantes(List<Comprobante> comprobantes) {
        this.comprobantes = comprobantes;
    }

    public List<Articulo> getProductos() {
        return articulos;
    }

    public void setProductos(List<Articulo> articulos) {
        this.articulos = articulos;
    }    
  
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Usuario)) {
            return false;
        }
        Usuario other = (Usuario) object;
        if (this.cedula == null ? other.cedula != null : !this.cedula.equals(other.cedula)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Clases.Usuario[ id=" + cedula + " ]";
    }
    
}
