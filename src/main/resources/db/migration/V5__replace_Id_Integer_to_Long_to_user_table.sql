alter table USER
    alter column ID LONG(10) default (NEXT VALUE FOR PUBLIC.SYSTEM_SEQUENCE_2F242FE2_F51C_4BF7_8F63_B950EC3EDF0B) auto_increment;
