package Entities;

import Entities.Adcomment;
import Entities.Advertisement;
import Entities.Notification;
import Entities.Preference;
import Entities.Rating;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-12-13T14:23:02")
@StaticMetamodel(Useraccount.class)
public class Useraccount_ { 

    public static volatile CollectionAttribute<Useraccount, Notification> notificationCollection;
    public static volatile SingularAttribute<Useraccount, String> userPassword;
    public static volatile SingularAttribute<Useraccount, String> phone;
    public static volatile SingularAttribute<Useraccount, Preference> preference;
    public static volatile SingularAttribute<Useraccount, String> fullName;
    public static volatile SingularAttribute<Useraccount, Integer> id;
    public static volatile SingularAttribute<Useraccount, String> userName;
    public static volatile CollectionAttribute<Useraccount, Advertisement> advertisementCollection;
    public static volatile SingularAttribute<Useraccount, String> picture;
    public static volatile CollectionAttribute<Useraccount, Rating> ratingCollection;
    public static volatile SingularAttribute<Useraccount, String> email;
    public static volatile CollectionAttribute<Useraccount, Adcomment> adcommentCollection;

}