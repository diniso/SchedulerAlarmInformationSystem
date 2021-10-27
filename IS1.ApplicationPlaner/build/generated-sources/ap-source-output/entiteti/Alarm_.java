package entiteti;

import entiteti.Korisnik;
import entiteti.Obaveza;
import entiteti.Pesma;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.0.v20170811-rNA", date="2021-02-10T10:23:58")
@StaticMetamodel(Alarm.class)
public abstract class Alarm_ { 

    public static volatile SingularAttribute<Alarm, Date> datumVreme;
    public static volatile SingularAttribute<Alarm, Pesma> pesma;
    public static volatile ListAttribute<Alarm, Obaveza> obavezaList;
    public static volatile SingularAttribute<Alarm, Integer> idAlarma;
    public static volatile SingularAttribute<Alarm, Korisnik> korisnik;

}