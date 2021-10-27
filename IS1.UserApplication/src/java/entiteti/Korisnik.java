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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "korisnik")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Korisnik.findAll", query = "SELECT k FROM Korisnik k"),
    @NamedQuery(name = "Korisnik.findByIdKorisnika", query = "SELECT k FROM Korisnik k WHERE k.idKorisnika = :idKorisnika"),
    @NamedQuery(name = "Korisnik.findByKorisnickoIme", query = "SELECT k FROM Korisnik k WHERE k.korisnickoIme = :korisnickoIme"),
    @NamedQuery(name = "Korisnik.findByLozinka", query = "SELECT k FROM Korisnik k WHERE k.lozinka = :lozinka")})
public class Korisnik implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idKorisnika")
    private Integer idKorisnika;
    @Basic(optional = false)
    @Column(name = "korisnickoIme")
    private String korisnickoIme;
    @Basic(optional = false)
    @Column(name = "lozinka")
    private String lozinka;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "korisnik")
    private List<Planer> planerList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idKorisnika")
    private List<Pustena> pustenaList;
    @JoinColumn(name = "IdMesta", referencedColumnName = "idMesta")
    @ManyToOne(optional = false)
    private Mesto idMesta;

    public Korisnik() {
    }

    public Korisnik(Integer idKorisnika) {
        this.idKorisnika = idKorisnika;
    }

    public Korisnik(Integer idKorisnika, String korisnickoIme, String lozinka) {
        this.idKorisnika = idKorisnika;
        this.korisnickoIme = korisnickoIme;
        this.lozinka = lozinka;
    }

    public Integer getIdKorisnika() {
        return idKorisnika;
    }

    public void setIdKorisnika(Integer idKorisnika) {
        this.idKorisnika = idKorisnika;
    }

    public String getKorisnickoIme() {
        return korisnickoIme;
    }

    public void setKorisnickoIme(String korisnickoIme) {
        this.korisnickoIme = korisnickoIme;
    }

    public String getLozinka() {
        return lozinka;
    }

    public void setLozinka(String lozinka) {
        this.lozinka = lozinka;
    }

/*    @XmlTransient
    public List<Alarm> getAlarmList() {
        return alarmList;
    }

    public void setAlarmList(List<Alarm> alarmList) {
        this.alarmList = alarmList;
    }
*/
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

    public Mesto getIdMesta() {
        return idMesta;
    }

    public void setIdMesta(Mesto idMesta) {
        this.idMesta = idMesta;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idKorisnika != null ? idKorisnika.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Korisnik)) {
            return false;
        }
        Korisnik other = (Korisnik) object;
        if ((this.idKorisnika == null && other.idKorisnika != null) || (this.idKorisnika != null && !this.idKorisnika.equals(other.idKorisnika))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entiteti.Korisnik[ idKorisnika=" + idKorisnika + " ]";
    }
    
}
