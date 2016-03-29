/*==============================================================*/
/* DBMS name:      PostgreSQL 8                                 */
/* Created on:     29.03.2016 14:15:24                          */
/*==============================================================*/


drop index "Also_S/A_FK";

drop index "Also_D/A2_FK";

drop index Actor_PK;

drop table Actor;

drop index "Also_D/S_FK";

drop index "Also_D/A_FK";

drop index Director_PK;

drop table Director;

drop index Genre_related_FK;

drop index Genre_related2_FK;

drop index Genre_related_PK;

drop table Genre_related;

drop index Genres_PK;

drop table Genres;

drop index Movie_PK;

drop table Movie;

drop index Plays_FK;

drop index Plays2_FK;

drop index Plays_PK;

drop table Plays;

drop index User_Rated_FK;

drop index Rating_PK;

drop table Rating;

drop index Rating_related2_FK;

drop index Rating_related_FK;

drop index Rating_related_PK;

drop table Rating_related;

drop index "Also_D/S2_FK";

drop index "Also_S/A2_FK";

drop index Scenarist_PK;

drop table Scenarist;

drop index Screenplay_FK;

drop index Screenplay2_FK;

drop index Screenplay_PK;

drop table Screenplay;

drop index Shoots_FK;

drop index Shoots2_FK;

drop index Shoots_PK;

drop table Shoots;

drop index Tag_related_FK;

drop index Tag_related2_FK;

drop index Tag_related_PK;

drop table Tag_related;

drop index Tags_PK;

drop table Tags;

drop index User_PK;

drop table "User";

/*==============================================================*/
/* Table: Actor                                                 */
/*==============================================================*/
create table Actor (
   ID_Actor             INT4                 not null,
   ID_Director          INT4                 null,
   ID_Scenarist         INT4                 null,
   Name                 TEXT                 not null,
   Surname              TEXT                 not null,
   Year                 INT2                 null,
   Description          TEXT                 null,
   constraint PK_ACTOR primary key (ID_Actor)
);

/*==============================================================*/
/* Index: Actor_PK                                              */
/*==============================================================*/
create unique index Actor_PK on Actor (
ID_Actor
);

/*==============================================================*/
/* Index: "Also_D/A2_FK"                                        */
/*==============================================================*/
create  index "Also_D/A2_FK" on Actor (
ID_Director
);

/*==============================================================*/
/* Index: "Also_S/A_FK"                                         */
/*==============================================================*/
create  index "Also_S/A_FK" on Actor (
ID_Scenarist
);

/*==============================================================*/
/* Table: Director                                              */
/*==============================================================*/
create table Director (
   ID_Director          INT4                 not null,
   ID_Actor             INT4                 null,
   ID_Scenarist         INT4                 null,
   Name                 TEXT                 not null,
   Surname              TEXT                 not null,
   Year                 INT2                 null,
   Description          TEXT                 null,
   constraint PK_DIRECTOR primary key (ID_Director)
);

/*==============================================================*/
/* Index: Director_PK                                           */
/*==============================================================*/
create unique index Director_PK on Director (
ID_Director
);

/*==============================================================*/
/* Index: "Also_D/A_FK"                                         */
/*==============================================================*/
create  index "Also_D/A_FK" on Director (
ID_Actor
);

/*==============================================================*/
/* Index: "Also_D/S_FK"                                         */
/*==============================================================*/
create  index "Also_D/S_FK" on Director (
ID_Scenarist
);

/*==============================================================*/
/* Table: Genre_related                                         */
/*==============================================================*/
create table Genre_related (
   ID_Genre             INT4                 not null,
   ID_movie             INT4                 not null,
   constraint PK_GENRE_RELATED primary key (ID_Genre, ID_movie)
);

/*==============================================================*/
/* Index: Genre_related_PK                                      */
/*==============================================================*/
create unique index Genre_related_PK on Genre_related (
ID_Genre,
ID_movie
);

/*==============================================================*/
/* Index: Genre_related2_FK                                     */
/*==============================================================*/
create  index Genre_related2_FK on Genre_related (
ID_movie
);

/*==============================================================*/
/* Index: Genre_related_FK                                      */
/*==============================================================*/
create  index Genre_related_FK on Genre_related (
ID_Genre
);

/*==============================================================*/
/* Table: Genres                                                */
/*==============================================================*/
create table Genres (
   ID_Genre             INT4                 not null,
   Type                 TEXT                 not null,
   constraint PK_GENRES primary key (ID_Genre)
);

/*==============================================================*/
/* Index: Genres_PK                                             */
/*==============================================================*/
create unique index Genres_PK on Genres (
ID_Genre
);

/*==============================================================*/
/* Table: Movie                                                 */
/*==============================================================*/
create table Movie (
   ID_movie             INT4                 not null,
   "Cover image"        TEXT                 not null,
   NameCZ               TEXT                 not null,
   NameEN               TEXT                 not null,
   Year                 INT2                 not null,
   Description          TEXT                 null,
   constraint PK_MOVIE primary key (ID_movie)
);

/*==============================================================*/
/* Index: Movie_PK                                              */
/*==============================================================*/
create unique index Movie_PK on Movie (
ID_movie
);

/*==============================================================*/
/* Table: Plays                                                 */
/*==============================================================*/
create table Plays (
   ID_Actor             INT4                 not null,
   ID_movie             INT4                 not null,
   constraint PK_PLAYS primary key (ID_Actor, ID_movie)
);

/*==============================================================*/
/* Index: Plays_PK                                              */
/*==============================================================*/
create unique index Plays_PK on Plays (
ID_Actor,
ID_movie
);

/*==============================================================*/
/* Index: Plays2_FK                                             */
/*==============================================================*/
create  index Plays2_FK on Plays (
ID_movie
);

/*==============================================================*/
/* Index: Plays_FK                                              */
/*==============================================================*/
create  index Plays_FK on Plays (
ID_Actor
);

/*==============================================================*/
/* Table: Rating                                                */
/*==============================================================*/
create table Rating (
   ID_Rate              INT4                 not null,
   ID_User              INT4                 not null,
   Stars                INT2                 not null,
   constraint PK_RATING primary key (ID_Rate)
);

/*==============================================================*/
/* Index: Rating_PK                                             */
/*==============================================================*/
create unique index Rating_PK on Rating (
ID_Rate
);

/*==============================================================*/
/* Index: User_Rated_FK                                         */
/*==============================================================*/
create  index User_Rated_FK on Rating (
ID_User
);

/*==============================================================*/
/* Table: Rating_related                                        */
/*==============================================================*/
create table Rating_related (
   ID_Rate              INT4                 not null,
   ID_movie             INT4                 not null,
   constraint PK_RATING_RELATED primary key (ID_Rate, ID_movie)
);

/*==============================================================*/
/* Index: Rating_related_PK                                     */
/*==============================================================*/
create unique index Rating_related_PK on Rating_related (
ID_Rate,
ID_movie
);

/*==============================================================*/
/* Index: Rating_related_FK                                     */
/*==============================================================*/
create  index Rating_related_FK on Rating_related (
ID_Rate
);

/*==============================================================*/
/* Index: Rating_related2_FK                                    */
/*==============================================================*/
create  index Rating_related2_FK on Rating_related (
ID_movie
);

/*==============================================================*/
/* Table: Scenarist                                             */
/*==============================================================*/
create table Scenarist (
   ID_Scenarist         INT4                 not null,
   ID_Actor             INT4                 null,
   ID_Director          INT4                 null,
   Name                 TEXT                 not null,
   Surname              TEXT                 not null,
   Year                 INT2                 null,
   Description          TEXT                 null,
   constraint PK_SCENARIST primary key (ID_Scenarist)
);

/*==============================================================*/
/* Index: Scenarist_PK                                          */
/*==============================================================*/
create unique index Scenarist_PK on Scenarist (
ID_Scenarist
);

/*==============================================================*/
/* Index: "Also_S/A2_FK"                                        */
/*==============================================================*/
create  index "Also_S/A2_FK" on Scenarist (
ID_Actor
);

/*==============================================================*/
/* Index: "Also_D/S2_FK"                                        */
/*==============================================================*/
create  index "Also_D/S2_FK" on Scenarist (
ID_Director
);

/*==============================================================*/
/* Table: Screenplay                                            */
/*==============================================================*/
create table Screenplay (
   ID_Scenarist         INT4                 not null,
   ID_movie             INT4                 not null,
   constraint PK_SCREENPLAY primary key (ID_Scenarist, ID_movie)
);

/*==============================================================*/
/* Index: Screenplay_PK                                         */
/*==============================================================*/
create unique index Screenplay_PK on Screenplay (
ID_Scenarist,
ID_movie
);

/*==============================================================*/
/* Index: Screenplay2_FK                                        */
/*==============================================================*/
create  index Screenplay2_FK on Screenplay (
ID_movie
);

/*==============================================================*/
/* Index: Screenplay_FK                                         */
/*==============================================================*/
create  index Screenplay_FK on Screenplay (
ID_Scenarist
);

/*==============================================================*/
/* Table: Shoots                                                */
/*==============================================================*/
create table Shoots (
   ID_Director          INT4                 not null,
   ID_movie             INT4                 not null,
   constraint PK_SHOOTS primary key (ID_Director, ID_movie)
);

/*==============================================================*/
/* Index: Shoots_PK                                             */
/*==============================================================*/
create unique index Shoots_PK on Shoots (
ID_Director,
ID_movie
);

/*==============================================================*/
/* Index: Shoots2_FK                                            */
/*==============================================================*/
create  index Shoots2_FK on Shoots (
ID_movie
);

/*==============================================================*/
/* Index: Shoots_FK                                             */
/*==============================================================*/
create  index Shoots_FK on Shoots (
ID_Director
);

/*==============================================================*/
/* Table: Tag_related                                           */
/*==============================================================*/
create table Tag_related (
   ID_Tag               INT4                 not null,
   ID_movie             INT4                 not null,
   constraint PK_TAG_RELATED primary key (ID_Tag, ID_movie)
);

/*==============================================================*/
/* Index: Tag_related_PK                                        */
/*==============================================================*/
create unique index Tag_related_PK on Tag_related (
ID_Tag,
ID_movie
);

/*==============================================================*/
/* Index: Tag_related2_FK                                       */
/*==============================================================*/
create  index Tag_related2_FK on Tag_related (
ID_movie
);

/*==============================================================*/
/* Index: Tag_related_FK                                        */
/*==============================================================*/
create  index Tag_related_FK on Tag_related (
ID_Tag
);

/*==============================================================*/
/* Table: Tags                                                  */
/*==============================================================*/
create table Tags (
   ID_Tag               INT4                 not null,
   Type                 TEXT                 not null,
   constraint PK_TAGS primary key (ID_Tag)
);

/*==============================================================*/
/* Index: Tags_PK                                               */
/*==============================================================*/
create unique index Tags_PK on Tags (
ID_Tag
);

/*==============================================================*/
/* Table: "User"                                                */
/*==============================================================*/
create table "User" (
   ID_User              INT4                 not null,
   Nickname             TEXT                 not null,
   Password             TEXT                 not null,
   isAdmin              BOOL                 not null,
   constraint PK_USER primary key (ID_User)
);

/*==============================================================*/
/* Index: User_PK                                               */
/*==============================================================*/
create unique index User_PK on "User" (
ID_User
);

alter table Actor
   add constraint "FK_ACTOR_ALSO_D/A2_DIRECTOR" foreign key (ID_Director)
      references Director (ID_Director)
      on delete restrict on update restrict;

alter table Actor
   add constraint "FK_ACTOR_ALSO_S/A_SCENARIS" foreign key (ID_Scenarist)
      references Scenarist (ID_Scenarist)
      on delete restrict on update restrict;

alter table Director
   add constraint "FK_DIRECTOR_ALSO_D/A_ACTOR" foreign key (ID_Actor)
      references Actor (ID_Actor)
      on delete restrict on update restrict;

alter table Director
   add constraint "FK_DIRECTOR_ALSO_D/S_SCENARIS" foreign key (ID_Scenarist)
      references Scenarist (ID_Scenarist)
      on delete restrict on update restrict;

alter table Genre_related
   add constraint FK_GENRE_RE_GENRE_REL_GENRES foreign key (ID_Genre)
      references Genres (ID_Genre)
      on delete restrict on update restrict;

alter table Genre_related
   add constraint FK_GENRE_RE_GENRE_REL_MOVIE foreign key (ID_movie)
      references Movie (ID_movie)
      on delete restrict on update restrict;

alter table Plays
   add constraint FK_PLAYS_PLAYS_ACTOR foreign key (ID_Actor)
      references Actor (ID_Actor)
      on delete restrict on update restrict;

alter table Plays
   add constraint FK_PLAYS_PLAYS2_MOVIE foreign key (ID_movie)
      references Movie (ID_movie)
      on delete restrict on update restrict;

alter table Rating
   add constraint FK_RATING_USER_RATE_USER foreign key (ID_User)
      references "User" (ID_User)
      on delete restrict on update restrict;

alter table Rating_related
   add constraint FK_RATING_R_RATING_RE_RATING foreign key (ID_Rate)
      references Rating (ID_Rate)
      on delete restrict on update restrict;

alter table Rating_related
   add constraint FK_RATING_R_RATING_RE_MOVIE foreign key (ID_movie)
      references Movie (ID_movie)
      on delete restrict on update restrict;

alter table Scenarist
   add constraint "FK_SCENARIS_ALSO_D/S2_DIRECTOR" foreign key (ID_Director)
      references Director (ID_Director)
      on delete restrict on update restrict;

alter table Scenarist
   add constraint "FK_SCENARIS_ALSO_S/A2_ACTOR" foreign key (ID_Actor)
      references Actor (ID_Actor)
      on delete restrict on update restrict;

alter table Screenplay
   add constraint FK_SCREENPL_SCREENPLA_SCENARIS foreign key (ID_Scenarist)
      references Scenarist (ID_Scenarist)
      on delete restrict on update restrict;

alter table Screenplay
   add constraint FK_SCREENPL_SCREENPLA_MOVIE foreign key (ID_movie)
      references Movie (ID_movie)
      on delete restrict on update restrict;

alter table Shoots
   add constraint FK_SHOOTS_SHOOTS_DIRECTOR foreign key (ID_Director)
      references Director (ID_Director)
      on delete restrict on update restrict;

alter table Shoots
   add constraint FK_SHOOTS_SHOOTS2_MOVIE foreign key (ID_movie)
      references Movie (ID_movie)
      on delete restrict on update restrict;

alter table Tag_related
   add constraint FK_TAG_RELA_TAG_RELAT_TAGS foreign key (ID_Tag)
      references Tags (ID_Tag)
      on delete restrict on update restrict;

alter table Tag_related
   add constraint FK_TAG_RELA_TAG_RELAT_MOVIE foreign key (ID_movie)
      references Movie (ID_movie)
      on delete restrict on update restrict;

