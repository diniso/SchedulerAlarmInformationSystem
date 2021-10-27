/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entiteti;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author admin
 */
@Entity
@Table(name = "alarm")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(
    name="discriminator",
    discriminatorType=DiscriminatorType.STRING
)
@DiscriminatorValue(value="A")
public abstract class Alarm implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idAlarma")
    protected Integer idAlarma;
    @Basic(optional = false)
    @Column(name = "DatumVreme")
    @Temporal(TemporalType.TIMESTAMP)
    protected Date datumVreme;
    @JoinColumn(name = "IdKor", referencedColumnName = "idKorisnika")
    @ManyToOne(optional = false)
    protected Korisnik korisnik;
    @JoinColumn(name = "IdPes", referencedColumnName = "idPesme")
    @ManyToOne(optional = false)
    protected Pesma pesma;
    @OneToMany(mappedBy = "alarm")
    protected List<Obaveza> obavezaList;

    public Alarm() {
    }

    public Alarm(Integer idAlarma) {
        this.idAlarma = idAlarma;
    }

    public Alarm(Integer idAlarma, Date datumVreme) {
        this.idAlarma = idAlarma;
        this.datumVreme = datumVreme;
    }

    public Integer getIdAlarma() {
        return idAlarma;
    }

    public void setIdAlarma(Integer idAlarma) {
        this.idAlarma = idAlarma;
    }

    public Date getDatumVreme() {
        return datumVreme;
    }

    public void setDatumVreme(Date datumVreme) {
        this.datumVreme = datumVreme;
    }

    public Korisnik getKorisnik() {
        return korisnik;
    }

    public void setKorisnik(Korisnik kor) {
        this.korisnik = kor;
    }

    public Pesma getPesma() {
        return pesma;
    }

    public void setPesma(Pesma pes) {
        this.pesma = pes;
    }

    @XmlTransient
    public List<Obaveza> getObavezaList() {
        return obavezaList;
    }

    public void setObavezaList(List<Obaveza> obavezaList) {
        this.obavezaList = obavezaList;
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
        if (!(object instanceof Alarm)) {
            return false;
        }
        Alarm other = (Alarm) object;
        if ((this.idAlarma == null && other.idAlarma != null) || (this.idAlarma != null && !this.idAlarma.equals(other.idAlarma))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entiteti.Alarm[ idAlarma=" + idAlarma + " ]";
    }
    
}
