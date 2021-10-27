package entiteti;

import entiteti.Mesto;
import entiteti.Planer;
import entiteti.Pustena;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.0.v20170811-rNA", date="2021-02-07T17:15:55")
@StaticMetamodel(Korisnik.class)
public class Korisnik_ { 

    public static volatile SingularAttribute<Korisnik, String> lozinka;
    public static volatile SingularAttribute<Korisnik, Mesto> idMesta;
    public static volatile ListAttribute<Korisnik, Planer> planerList;
    public static volatile SingularAttribute<Korisnik, Integer> idKorisnika;
    public static volatile ListAttribute<Korisnik, Pustena> pustenaList;
    public static volatile SingularAttribute<Korisnik, String> korisnickoIme;

}