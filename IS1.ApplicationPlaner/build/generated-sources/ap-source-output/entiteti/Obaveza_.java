package entiteti;

import entiteti.Alarm;
import entiteti.Mesto;
import entiteti.Planer;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.0.v20170811-rNA", date="2021-02-10T10:23:58")
@StaticMetamodel(Obaveza.class)
public class Obaveza_ { 

    public static volatile SingularAttribute<Obaveza, Integer> idObaveze;
    public static volatile SingularAttribute<Obaveza, String> naziv;
    public static volatile SingularAttribute<Obaveza, Alarm> alarm;
    public static volatile SingularAttribute<Obaveza, Planer> planer;
    public static volatile SingularAttribute<Obaveza, Mesto> mesto;
    public static volatile SingularAttribute<Obaveza, Date> datumVremePocetka;
    public static volatile SingularAttribute<Obaveza, Date> datumVremeKraja;

}