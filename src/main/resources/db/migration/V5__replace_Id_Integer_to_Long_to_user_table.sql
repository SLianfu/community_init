/*alter table USER
    alter column ID LONG(10) default auto_increment;*/
alter table user alter column ID BIGINT default not null auto_increment;
