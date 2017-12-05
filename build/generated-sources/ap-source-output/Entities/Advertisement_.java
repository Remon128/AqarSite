package Entities;

import Entities.Useraccount;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-12-05T20:33:03")
@StaticMetamodel(Advertisement.class)
public class Advertisement_ { 

    public static volatile SingularAttribute<Advertisement, String> propstatus;
    public static volatile SingularAttribute<Advertisement, byte[]> image;
    public static volatile SingularAttribute<Advertisement, Integer> size;
    public static volatile SingularAttribute<Advertisement, String> description;
    public static volatile SingularAttribute<Advertisement, String> proptype;
    public static volatile SingularAttribute<Advertisement, Integer> id;
    public static volatile SingularAttribute<Advertisement, Integer> floor;
    public static volatile SingularAttribute<Advertisement, Useraccount> userID;

}