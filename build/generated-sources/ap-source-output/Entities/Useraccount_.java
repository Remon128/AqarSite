package Entities;

import Entities.Advertisement;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-12-05T20:33:03")
@StaticMetamodel(Useraccount.class)
public class Useraccount_ { 

    public static volatile SingularAttribute<Useraccount, byte[]> image;
    public static volatile SingularAttribute<Useraccount, Integer> id;
    public static volatile CollectionAttribute<Useraccount, Advertisement> advertisementCollection;
    public static volatile SingularAttribute<Useraccount, String> email;
    public static volatile SingularAttribute<Useraccount, String> username;
    public static volatile SingularAttribute<Useraccount, String> userpassword;

}