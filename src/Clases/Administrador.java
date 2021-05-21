/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.OneToMany;


/**
 *
 * @author joaco
 */
@Entity
public class Administrador extends Usuario implements Serializable {

    private boolean superAdmin;

    //LINKS-----------------------//
    @OneToMany(mappedBy = "admin")
    private List<IVA> IVAs;

    //---------------------------//
    public List<IVA> getIVAs() {
        return IVAs;
    }

    public void setIVAs(List<IVA> IVAs) {
        this.IVAs = IVAs;
    }

    public boolean isSuperAdmin() {
        return superAdmin;
    }

    public void setSuperAdmin(boolean superAdmin) {
        this.superAdmin = superAdmin;
    }


}
