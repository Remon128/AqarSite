package Entities;

import Entities.Useraccount;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-12-13T14:23:02")
@StaticMetamodel(Preference.class)
public class Preference_ { 

    public static volatile SingularAttribute<Preference, Integer> accountID;
    public static volatile SingularAttribute<Preference, Integer> size;
    public static volatile SingularAttribute<Preference, String> propType;
    public static volatile SingularAttribute<Preference, Integer> floor;
    public static volatile SingularAttribute<Preference, Useraccount> useraccount;
    public static volatile SingularAttribute<Preference, String> propStatus;

}