package Entities;

import Entities.Adcomment;
import Entities.Adphoto;
import Entities.Notification;
import Entities.Rating;
import Entities.Useraccount;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-12-08T19:19:50")
@StaticMetamodel(Advertisement.class)
public class Advertisement_ { 

    public static volatile CollectionAttribute<Advertisement, Notification> notificationCollection;
    public static volatile CollectionAttribute<Advertisement, Adphoto> adphotoCollection;
    public static volatile SingularAttribute<Advertisement, String> description;
    public static volatile SingularAttribute<Advertisement, String> title;
    public static volatile CollectionAttribute<Advertisement, Rating> ratingCollection;
    public static volatile SingularAttribute<Advertisement, Useraccount> accountID;
    public static volatile SingularAttribute<Advertisement, Integer> adType;
    public static volatile SingularAttribute<Advertisement, Integer> size;
    public static volatile SingularAttribute<Advertisement, Integer> id;
    public static volatile SingularAttribute<Advertisement, String> propType;
    public static volatile SingularAttribute<Advertisement, Integer> floor;
    public static volatile SingularAttribute<Advertisement, String> propStatus;
    public static volatile CollectionAttribute<Advertisement, Adcomment> adcommentCollection;

}