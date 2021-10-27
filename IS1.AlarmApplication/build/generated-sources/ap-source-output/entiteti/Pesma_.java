package entiteti;

import entiteti.Planer;
import entiteti.Pustena;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.0.v20170811-rNA", date="2021-02-07T17:15:56")
@StaticMetamodel(Pesma.class)
public class Pesma_ { 

    public static volatile SingularAttribute<Pesma, String> uRLLokacija;
    public static volatile SingularAttribute<Pesma, Integer> idPesme;
    public static volatile ListAttribute<Pesma, Planer> planerList;
    public static volatile ListAttribute<Pesma, Pustena> pustenaList;
    public static volatile SingularAttribute<Pesma, String> nazivPesme;

}