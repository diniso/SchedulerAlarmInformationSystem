/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entiteti;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 *
 * @author admin
 */

@Entity
@Table(name = "alarm")
@DiscriminatorValue(value="N")
public class NeperiodicniAlarm extends Alarm{
    
    
    public NeperiodicniAlarm() {}
    
    public NeperiodicniAlarm(Integer idAlarma) {
        this.idAlarma = idAlarma;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAlarma != null ? idAlarma.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof NeperiodicniAlarm)) {
            return false;
        }
        NeperiodicniAlarm other = (NeperiodicniAlarm) object;
        if ((this.idAlarma == null && other.idAlarma != null) || (this.idAlarma != null && !this.idAlarma.equals(other.idAlarma))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entiteti.Neperiodicnialarm[ idAlarma=" + idAlarma + " ]";
    }
}
