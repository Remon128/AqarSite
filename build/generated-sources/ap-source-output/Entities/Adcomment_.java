package Entities;

import Entities.Advertisement;
import Entities.Useraccount;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-12-13T14:23:02")
@StaticMetamodel(Adcomment.class)
public class Adcomment_ { 

    public static volatile SingularAttribute<Adcomment, Useraccount> accountID;
    public static volatile SingularAttribute<Adcomment, Advertisement> adID;
    public static volatile SingularAttribute<Adcomment, Integer> id;
    public static volatile SingularAttribute<Adcomment, String> adComment;

}