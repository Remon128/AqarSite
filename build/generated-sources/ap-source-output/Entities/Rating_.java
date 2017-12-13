package Entities;

import Entities.Advertisement;
import Entities.RatingPK;
import Entities.Useraccount;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-12-13T14:23:02")
@StaticMetamodel(Rating.class)
public class Rating_ { 

    public static volatile SingularAttribute<Rating, Integer> rateValue;
    public static volatile SingularAttribute<Rating, RatingPK> ratingPK;
    public static volatile SingularAttribute<Rating, Advertisement> advertisement;
    public static volatile SingularAttribute<Rating, Useraccount> useraccount;

}