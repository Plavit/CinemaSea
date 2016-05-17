/*==============================================================*/
/* DBMS name:      Sybase SQL Anywhere 12                       */
/* Created on:     17.05.2016 9:48:31                           */
/*==============================================================*/
/*==============================================================*/
/* Table: Actor                                                 */
/*==============================================================*/
create table Actor 
(
   ID_Actor             integer                        not null,
   ID_Director          integer                        null,
   ID_Scenarist         integer                        null,
   Name                 text                           not null,
   Surname              text                           not null,
   Year                 smallint                       null,
   Description          text                           null,
   last_edited          timestamp without time zone    null,
   edit_count           integer                        null,
   constraint PK_ACTOR primary key (ID_Actor)
);

/*==============================================================*/
/* Table: Director                                              */
/*==============================================================*/
create table Director 
(
   ID_Director          integer                        not null,
   ID_Actor             integer                        null,
   ID_Scenarist         integer                        null,
   Name                 text                           not null,
   Surname              text                           not null,
   Year                 smallint                       null,
   Description          text                           null,
   last_edited          timestamp without time zone    null,
   edit_count           integer                        null,
   constraint PK_DIRECTOR primary key (ID_Director)
);
/*==============================================================*/
/* Table: Genre_related                                         */
/*==============================================================*/
create table Genre_related 
(
   ID_Genre             integer                        not null,
   ID_movie             integer                        not null,
   constraint PK_GENRE_RELATED primary key (ID_Genre, ID_movie)
);
/*==============================================================*/
/* Table: Genres                                                */
/*==============================================================*/
create table Genres 
(
   ID_Genre             integer                        not null,
   Type                 text                           not null,
   constraint PK_GENRES primary key (ID_Genre)
);
/*==============================================================*/
/* Table: Movie                                                 */
/*==============================================================*/
create table Movie 
(
   ID_movie             integer                        not null,
   "Cover image"        text                           null,
   NameCZ               text                           not null,
   NameEN               text                           not null,
   Year                 smallint                       not null,
   Description          text                           null,
   last_rated           timestamp without time zone    null,
   rate_count           integer                        null,
   last_edited          timestamp without time zone    null,
   edit_count           integer                        null,
   constraint PK_MOVIE primary key (ID_movie)
);
/*==============================================================*/
/* Table: Plays                                                 */
/*==============================================================*/
create table Plays 
(
   ID_Actor             integer                        not null,
   ID_movie             integer                        not null,
   constraint PK_PLAYS primary key (ID_Actor, ID_movie)
);

/*==============================================================*/
/* Table: Rating                                                */
/*==============================================================*/
create table Rating 
(
   ID_Rate              integer                        not null,
   ID_User              integer                        not null,
   Stars                smallint                       not null,
   constraint PK_RATING primary key (ID_Rate)
);

/*==============================================================*/
/* Table: Rating_related                                        */
/*==============================================================*/
create table Rating_related 
(
   ID_Rate              integer                        not null,
   ID_movie             integer                        not null,
   constraint PK_RATING_RELATED primary key (ID_Rate, ID_movie)
);

/*==============================================================*/
/* Table: Scenarist                                             */
/*==============================================================*/
create table Scenarist 
(
   ID_Scenarist         integer                        not null,
   ID_Actor             integer                        null,
   ID_Director          integer                        null,
   Name                 text                           not null,
   Surname              text                           not null,
   Year                 smallint                       null,
   Description          text                           null,
   last_edited          timestamp without time zone    null,
   edit_count           integer                        null,
   constraint PK_SCENARIST primary key (ID_Scenarist)
);

/*==============================================================*/
/* Table: Screenplay                                            */
/*==============================================================*/
create table Screenplay 
(
   ID_Scenarist         integer                        not null,
   ID_movie             integer                        not null,
   constraint PK_SCREENPLAY primary key (ID_Scenarist, ID_movie)
);

/*==============================================================*/
/* Table: Shoots                                                */
/*==============================================================*/
create table Shoots 
(
   ID_Director          integer                        not null,
   ID_movie             integer                        not null,
   constraint PK_SHOOTS primary key (ID_Director, ID_movie)
);

/*==============================================================*/
/* Table: Tag_related                                           */
/*==============================================================*/
create table Tag_related 
(
   ID_Tag               integer                        not null,
   ID_movie             integer                        not null,
   constraint PK_TAG_RELATED primary key (ID_Tag, ID_movie)
);

/*==============================================================*/
/* Table: Tags                                                  */
/*==============================================================*/
create table Tags 
(
   ID_Tag               integer                        not null,
   Type                 text                           not null,
   constraint PK_TAGS primary key (ID_Tag)
);

/*==============================================================*/
/* Table: "User"                                                */
/*==============================================================*/
create table "User" 
(
   ID_User                    integer                        not null,
   Nickname                   text                           not null,
   Password                   text                           not null,
   isAdmin                    boolean                        not null,
   registration_time          timestamp without time zone    null,
   constraint PK_USER primary key (ID_User)
);

alter table Actor
   add constraint "FK_ACTOR_ALSO_D/A2_DIRECTOR" foreign key (ID_Director)
      references Director (ID_Director)
      on update restrict
      on delete restrict;

alter table Actor
   add constraint "FK_ACTOR_ALSO_S/A_SCENARIS" foreign key (ID_Scenarist)
      references Scenarist (ID_Scenarist)
      on update restrict
      on delete restrict;

alter table Director
   add constraint "FK_DIRECTOR_ALSO_D/A_ACTOR" foreign key (ID_Actor)
      references Actor (ID_Actor)
      on update restrict
      on delete restrict;

alter table Director
   add constraint "FK_DIRECTOR_ALSO_D/S_SCENARIS" foreign key (ID_Scenarist)
      references Scenarist (ID_Scenarist)
      on update restrict
      on delete restrict;

alter table Genre_related
   add constraint FK_GENRE_RE_GENRE_REL_GENRES foreign key (ID_Genre)
      references Genres (ID_Genre)
      on update restrict
      on delete restrict;

alter table Genre_related
   add constraint FK_GENRE_RE_GENRE_REL_MOVIE foreign key (ID_movie)
      references Movie (ID_movie)
      on update restrict
      on delete restrict;

alter table Plays
   add constraint FK_PLAYS_PLAYS_ACTOR foreign key (ID_Actor)
      references Actor (ID_Actor)
      on update restrict
      on delete restrict;

alter table Plays
   add constraint FK_PLAYS_PLAYS2_MOVIE foreign key (ID_movie)
      references Movie (ID_movie)
      on update restrict
      on delete restrict;

alter table Rating
   add constraint FK_RATING_USER_RATE_USER foreign key (ID_User)
      references "User" (ID_User)
      on update restrict
      on delete restrict;

alter table Rating_related
   add constraint FK_RATING_R_RATING_RE_RATING foreign key (ID_Rate)
      references Rating (ID_Rate)
      on update restrict
      on delete restrict;

alter table Rating_related
   add constraint FK_RATING_R_RATING_RE_MOVIE foreign key (ID_movie)
      references Movie (ID_movie)
      on update restrict
      on delete restrict;

alter table Scenarist
   add constraint "FK_SCENARIS_ALSO_D/S2_DIRECTOR" foreign key (ID_Director)
      references Director (ID_Director)
      on update restrict
      on delete restrict;

alter table Scenarist
   add constraint "FK_SCENARIS_ALSO_S/A2_ACTOR" foreign key (ID_Actor)
      references Actor (ID_Actor)
      on update restrict
      on delete restrict;

alter table Screenplay
   add constraint FK_SCREENPL_SCREENPLA_SCENARIS foreign key (ID_Scenarist)
      references Scenarist (ID_Scenarist)
      on update restrict
      on delete restrict;

alter table Screenplay
   add constraint FK_SCREENPL_SCREENPLA_MOVIE foreign key (ID_movie)
      references Movie (ID_movie)
      on update restrict
      on delete restrict;

alter table Shoots
   add constraint FK_SHOOTS_SHOOTS_DIRECTOR foreign key (ID_Director)
      references Director (ID_Director)
      on update restrict
      on delete restrict;

alter table Shoots
   add constraint FK_SHOOTS_SHOOTS2_MOVIE foreign key (ID_movie)
      references Movie (ID_movie)
      on update restrict
      on delete restrict;

alter table Tag_related
   add constraint FK_TAG_RELA_TAG_RELAT_TAGS foreign key (ID_Tag)
      references Tags (ID_Tag)
      on update restrict
      on delete restrict;

alter table Tag_related
   add constraint FK_TAG_RELA_TAG_RELAT_MOVIE foreign key (ID_movie)
      references Movie (ID_movie)
      on update restrict
      on delete restrict;

