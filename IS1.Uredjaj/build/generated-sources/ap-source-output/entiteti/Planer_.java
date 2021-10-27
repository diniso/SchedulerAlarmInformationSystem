package entiteti;

import entiteti.Korisnik;
import entiteti.Obaveza;
import entiteti.Pesma;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.0.v20170811-rNA", date="2021-02-11T00:36:13")
@StaticMetamodel(Planer.class)
public class Planer_ { 

    public static volatile SingularAttribute<Planer, Pesma> pesma;
    public static volatile ListAttribute<Planer, Obaveza> obavezaList;
    public static volatile SingularAttribute<Planer, Integer> idPlanera;
    public static volatile SingularAttribute<Planer, Korisnik> korisnik;

}