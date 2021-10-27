package entiteti;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.0.v20170811-rNA", date="2021-02-07T17:15:56")
@StaticMetamodel(PeriodicniAlarm.class)
public class PeriodicniAlarm_ extends Alarm_ {

    public static volatile SingularAttribute<PeriodicniAlarm, Date> datumVremePoslednjegZvona;
    public static volatile SingularAttribute<PeriodicniAlarm, Integer> perioda;

}