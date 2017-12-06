use aqarsite;

create table UserAccount (
	ID int primary key,
    userName text not null,
    userPassword text not null,
    fullName text,
    email text not null,
    picture blob,
    phone text
);

create table Advertisement(
	ID int primary key,
    size int,
    title text not null,
    Description text,
    floor int,
    propStatus text,
    propType text,
    adType int,
    accountID int not null,
    foreign key(accountID) references UserAccount(ID)
);

create table AdPhoto(
	ID int primary key,
    adID int not null,
    photo blob not null,
    foreign key(adID) references Advertisement(ID)
);

create table AdComment(
	ID int primary key,
    accountID int not null,
    adID int not null,
    AdComment text not null,
    foreign key(adID) references Advertisement(ID),
    foreign key(accountID) references UserAccount(ID)
);

create table Rating(
	adID int not null,
    accountID int not null,
    rateValue int not null,
    foreign key(adID) references Advertisement(ID),
    foreign key(accountID) references UserAccount(ID),
    primary key(adID,accountID)
);

create table Preference(
	accountID  int primary key,
    size int,
    floor int,
    propStatus text,
    propType text,
    foreign key(accountID) references UserAccount(ID)
);

create table Notification(
	ID  int primary key,
    accountID int not null,
    adID int,
    content text not null,
    foreign key(adID) references Advertisement(ID),
    foreign key(accountID) references UserAccount(ID)
);