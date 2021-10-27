package entiteti;

import entiteti.Korisnik;
import entiteti.Obaveza;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.0.v20170811-rNA", date="2021-02-10T10:23:58")
@StaticMetamodel(Mesto.class)
public class Mesto_ { 

    public static volatile SingularAttribute<Mesto, Integer> idMesta;
    public static volatile SingularAttribute<Mesto, String> adresa;
    public static volatile SingularAttribute<Mesto, Double> x;
    public static volatile SingularAttribute<Mesto, Double> y;
    public static volatile SingularAttribute<Mesto, String> nazivGrada;
    public static volatile ListAttribute<Mesto, Obaveza> obavezaList;
    public static volatile ListAttribute<Mesto, Korisnik> korisnikList;

}