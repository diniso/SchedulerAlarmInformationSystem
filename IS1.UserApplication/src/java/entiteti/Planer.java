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
@Table(name = "planer")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Planer.findAll", query = "SELECT p FROM Planer p"),
    @NamedQuery(name = "Planer.findByIdPlanera", query = "SELECT p FROM Planer p WHERE p.idPlanera = :idPlanera")})
public class Planer implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idPlanera")
    private Integer idPlanera;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "planer")
    private List<Obaveza> obavezaList;
    @JoinColumn(name = "idKorisnika", referencedColumnName = "idKorisnika")
    @ManyToOne(optional = false)
    private Korisnik korisnik;
    @JoinColumn(name = "IdPesme", referencedColumnName = "idPesme")
    @ManyToOne(optional = false)
    private Pesma pesma;

    public Planer() {
    }

    public Planer(Integer idPlanera) {
        this.idPlanera = idPlanera;
    }

    public Integer getIdPlanera() {
        return idPlanera;
    }

    public void setIdPlanera(Integer idPlanera) {
        this.idPlanera = idPlanera;
    }

    @XmlTransient
    public List<Obaveza> getObavezaList() {
        return obavezaList;
    }

    public void setObavezaList(List<Obaveza> obavezaList) {
        this.obavezaList = obavezaList;
    }

    public Korisnik getKorisnika() {
        return korisnik;
    }

    public void setIdKorisnika(Korisnik kor) {
        this.korisnik = kor;
    }

    public Pesma getPesme() {
        return pesma;
    }

    public void setIdPesme(Pesma pes) {
        this.pesma = pes;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPlanera != null ? idPlanera.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Planer)) {
            return false;
        }
        Planer other = (Planer) object;
        if ((this.idPlanera == null && other.idPlanera != null) || (this.idPlanera != null && !this.idPlanera.equals(other.idPlanera))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entiteti.Planer[ idPlanera=" + idPlanera + " ]";
    }
    
}
