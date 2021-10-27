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
@Table(name = "mesto")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Mesto.findAll", query = "SELECT m FROM Mesto m"),
    @NamedQuery(name = "Mesto.findByIdMesta", query = "SELECT m FROM Mesto m WHERE m.idMesta = :idMesta"),
    @NamedQuery(name = "Mesto.findByNazivGrada", query = "SELECT m FROM Mesto m WHERE m.nazivGrada = :nazivGrada"),
    @NamedQuery(name = "Mesto.findByAdresa", query = "SELECT m FROM Mesto m WHERE m.adresa = :adresa"),
    @NamedQuery(name = "Mesto.findByX", query = "SELECT m FROM Mesto m WHERE m.x = :x"),
    @NamedQuery(name = "Mesto.findByY", query = "SELECT m FROM Mesto m WHERE m.y = :y")})
public class Mesto implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idMesta")
    private Integer idMesta;
    @Basic(optional = false)
    @Column(name = "NazivGrada")
    private String nazivGrada;
    @Basic(optional = false)
    @Column(name = "Adresa")
    private String adresa;
    @Basic(optional = false)
    @Column(name = "x")
    private double x;
    @Basic(optional = false)
    @Column(name = "y")
    private double y;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "mesto")
    private List<Obaveza> obavezaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idMesta")
    private List<Korisnik> korisnikList;

    public Mesto() {
    }

    public Mesto(Integer idMesta) {
        this.idMesta = idMesta;
    }

    public Mesto(Integer idMesta, String nazivGrada, String adresa, double x, double y) {
        this.idMesta = idMesta;
        this.nazivGrada = nazivGrada;
        this.adresa = adresa;
        this.x = x;
        this.y = y;
    }

    public Integer getIdMesta() {
        return idMesta;
    }

    public void setIdMesta(Integer idMesta) {
        this.idMesta = idMesta;
    }

    public String getNazivGrada() {
        return nazivGrada;
    }

    public void setNazivGrada(String nazivGrada) {
        this.nazivGrada = nazivGrada;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    @XmlTransient
    public List<Obaveza> getObavezaList() {
        return obavezaList;
    }

    public void setObavezaList(List<Obaveza> obavezaList) {
        this.obavezaList = obavezaList;
    }

    @XmlTransient
    public List<Korisnik> getKorisnikList() {
        return korisnikList;
    }

    public void setKorisnikList(List<Korisnik> korisnikList) {
        this.korisnikList = korisnikList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idMesta != null ? idMesta.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Mesto)) {
            return false;
        }
        Mesto other = (Mesto) object;
        if ((this.idMesta == null && other.idMesta != null) || (this.idMesta != null && !this.idMesta.equals(other.idMesta))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entiteti.Mesto[ idMesta=" + idMesta + " ]";
    }
    
}
