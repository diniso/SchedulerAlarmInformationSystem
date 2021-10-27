/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entiteti;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author admin
 */
@Entity
@Table(name = "pesma")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Pesma.findAll", query = "SELECT p FROM Pesma p"),
    @NamedQuery(name = "Pesma.findByIdPesme", query = "SELECT p FROM Pesma p WHERE p.idPesme = :idPesme"),
    @NamedQuery(name = "Pesma.findByURLLokacija", query = "SELECT p FROM Pesma p WHERE p.uRLLokacija = :uRLLokacija"),
    @NamedQuery(name = "Pesma.findByNazivPesme", query = "SELECT p FROM Pesma p WHERE p.nazivPesme = :nazivPesme")})
public class Pesma implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idPesme")
    private Integer idPesme;
    @Basic(optional = false)
    @Column(name = "URLLokacija")
    private String uRLLokacija;
    @Basic(optional = false)
    @Column(name = "NazivPesme")
    private String nazivPesme;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pesma")
    private List<Planer> planerList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idPesme")
    private List<Pustena> pustenaList;

    public Pesma() {
    }

    public Pesma(Integer idPesme) {
        this.idPesme = idPesme;
    }

    public Pesma(Integer idPesme, String uRLLokacija, String nazivPesme) {
        this.idPesme = idPesme;
        this.uRLLokacija = uRLLokacija;
        this.nazivPesme = nazivPesme;
    }

    public Integer getIdPesme() {
        return idPesme;
    }

    public void setIdPesme(Integer idPesme) {
        this.idPesme = idPesme;
    }

    public String getURLLokacija() {
        return uRLLokacija;
    }

    public void setURLLokacija(String uRLLokacija) {
        this.uRLLokacija = uRLLokacija;
    }

    public String getNazivPesme() {
        return nazivPesme;
    }

    public void setNazivPesme(String nazivPesme) {
        this.nazivPesme = nazivPesme;
    }

  /*  @XmlTransient
    public List<Alarm> getAlarmList() {
        return alarmList;
    }

    public void setAlarmList(List<Alarm> alarmList) {
        this.alarmList = alarmList;
    }*/

    @XmlTransient
    public List<Planer> getPlanerList() {
        return planerList;
    }

    public void setPlanerList(List<Planer> planerList) {
        this.planerList = planerList;
    }

    @XmlTransient
    public List<Pustena> getPustenaList() {
        return pustenaList;
    }

    public void setPustenaList(List<Pustena> pustenaList) {
        this.pustenaList = pustenaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPesme != null ? idPesme.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Pesma)) {
            return false;
        }
        Pesma other = (Pesma) object;
        if ((this.idPesme == null && other.idPesme != null) || (this.idPesme != null && !this.idPesme.equals(other.idPesme))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entiteti.Pesma[ idPesme=" + idPesme + " ]";
    }
    
}
