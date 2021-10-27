/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entiteti;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author admin
 */
@Entity
@Table(name = "obaveza")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Obaveza.findAll", query = "SELECT o FROM Obaveza o"),
    @NamedQuery(name = "Obaveza.findByIdObaveze", query = "SELECT o FROM Obaveza o WHERE o.idObaveze = :idObaveze"),
    @NamedQuery(name = "Obaveza.findByNaziv", query = "SELECT o FROM Obaveza o WHERE o.naziv = :naziv"),
    @NamedQuery(name = "Obaveza.findByDatumVremePocetka", query = "SELECT o FROM Obaveza o WHERE o.datumVremePocetka = :datumVremePocetka"),
    @NamedQuery(name = "Obaveza.findByDatumVremeKraja", query = "SELECT o FROM Obaveza o WHERE o.datumVremeKraja = :datumVremeKraja")})
public class Obaveza implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idObaveze")
    private Integer idObaveze;
    @Basic(optional = false)
    @Column(name = "naziv")
    private String naziv;
    @Basic(optional = false)
    @Column(name = "datumVremePocetka")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datumVremePocetka;
    @Basic(optional = false)
    @Column(name = "datumVremeKraja")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datumVremeKraja;
    @JoinColumn(name = "IdAlarma", referencedColumnName = "idAlarma")
    @ManyToOne
    private Alarm alarm;
    @JoinColumn(name = "IdMes", referencedColumnName = "idMesta")
    @ManyToOne(optional = false)
    private Mesto mesto;
    @JoinColumn(name = "IdPlanera", referencedColumnName = "idPlanera")
    @ManyToOne(optional = false)
    private Planer planer;

    public Obaveza() {
    }

    public Obaveza(Integer idObaveze) {
        this.idObaveze = idObaveze;
    }

    public Obaveza(Integer idObaveze, String naziv, Date datumVremePocetka, Date datumVremeKraja) {
        this.idObaveze = idObaveze;
        this.naziv = naziv;
        this.datumVremePocetka = datumVremePocetka;
        this.datumVremeKraja = datumVremeKraja;
    }

    public Integer getIdObaveze() {
        return idObaveze;
    }

    public void setIdObaveze(Integer idObaveze) {
        this.idObaveze = idObaveze;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public Date getDatumVremePocetka() {
        return datumVremePocetka;
    }

    public void setDatumVremePocetka(Date datumVremePocetka) {
        this.datumVremePocetka = datumVremePocetka;
    }

    public Date getDatumVremeKraja() {
        return datumVremeKraja;
    }

    public void setDatumVremeKraja(Date datumVremeKraja) {
        this.datumVremeKraja = datumVremeKraja;
    }

    public Alarm getAlarm() {
        return alarm;
    }

    public void setAlarma(Alarm alarm) {
        this.alarm = alarm;
    }

    public Mesto getMesto() {
        return mesto;
    }

    public void setMesto(Mesto mesto) {
        this.mesto = mesto;
    }

    public Planer getPlaner() {
        return planer;
    }

    public void setPlaner(Planer planer) {
        this.planer = planer;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idObaveze != null ? idObaveze.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Obaveza)) {
            return false;
        }
        Obaveza other = (Obaveza) object;
        if ((this.idObaveze == null && other.idObaveze != null) || (this.idObaveze != null && !this.idObaveze.equals(other.idObaveze))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entiteti.Obaveza[ idObaveze=" + idObaveze + " ]";
    }
    
}
