/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entiteti;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author admin
 */
@Entity
@Table(name = "alarm")
@DiscriminatorValue(value="P")
public class PeriodicniAlarm extends Alarm{
    
    @Column(name = "perioda")
    private Integer perioda;
    @Column(name = "DatumVremePoslednjegZvona")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datumVremePoslednjegZvona;
    
    public PeriodicniAlarm() {}
    
    public PeriodicniAlarm(Integer idAlarma) {
        this.idAlarma = idAlarma;
    }
    
    public int getPerioda() {
        return perioda;
    }

    public void setPerioda(int perioda) {
        this.perioda = perioda;
    }

    public Date getDatumVremePoslednjegZvona() {
        return datumVremePoslednjegZvona;
    }

    public void setDatumVremePoslednjegZvona(Date datumVremePoslednjegZvona) {
        this.datumVremePoslednjegZvona = datumVremePoslednjegZvona;
    }
}
