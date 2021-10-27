/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entiteti;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author admin
 */
@Entity
@Table(name = "pustena")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Pustena.findAll", query = "SELECT p FROM Pustena p"),
    @NamedQuery(name = "Pustena.findByIdPustena", query = "SELECT p FROM Pustena p WHERE p.idPustena = :idPustena")})
public class Pustena implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IdPustena")
    private Integer idPustena;
    @JoinColumn(name = "IdKorisnika", referencedColumnName = "idKorisnika")
    @ManyToOne(optional = false)
    private Korisnik idKorisnika;
    @JoinColumn(name = "IdPesme", referencedColumnName = "idPesme")
    @ManyToOne(optional = false)
    private Pesma idPesme;

    public Pustena() {
    }

    public Pustena(Integer idPustena) {
        this.idPustena = idPustena;
    }

    public Integer getIdPustena() {
        return idPustena;
    }

    public void setIdPustena(Integer idPustena) {
        this.idPustena = idPustena;
    }

    public Korisnik getIdKorisnika() {
        return idKorisnika;
    }

    public void setIdKorisnika(Korisnik idKorisnika) {
        this.idKorisnika = idKorisnika;
    }

    public Pesma getIdPesme() {
        return idPesme;
    }

    public void setIdPesme(Pesma idPesme) {
        this.idPesme = idPesme;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPustena != null ? idPustena.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Pustena)) {
            return false;
        }
        Pustena other = (Pustena) object;
        if ((this.idPustena == null && other.idPustena != null) || (this.idPustena != null && !this.idPustena.equals(other.idPustena))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entiteti.Pustena[ idPustena=" + idPustena + " ]";
    }
    
}
