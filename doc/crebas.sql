################### ORACLE #################

create table USUARIO  (
   USU_ID               NUMBER(15)                      not null,
   USU_NOME             NVARCHAR2(250),
   USU_USUARIO          NVARCHAR2(250),
   USU_SENHA            NVARCHAR2(250),
   USU_EMAIL            NVARCHAR2(250),
   constraint PK_USU primary key (USU_ID)
);

create table PROJETO  (
   PRO_ID               NUMBER(15)                      not null,
   USU_ID               NUMBER(15),
   PRO_CADASTRO         DATE,
   PRO_DESCRICAO        NVARCHAR2(250),
   PRO_OBSERVACAO       NVARCHAR2(2000),
   constraint PK_PRO primary key (PRO_ID)
);

alter table PROJETO
   add constraint FK_PRO_USU foreign key (USU_ID)
      references USUARIO (USU_ID);

create table TAREFA  (
   TAR_ID               NUMBER(15)                      not null,
   PRO_ID               NUMBER(15),
   TAR_CADASTRO         DATE,
   TAR_SITUACAO         NUMBER(15),
   TAR_TITULO           NVARCHAR2(250),
   TAR_DESCRICAO        NVARCHAR2(250),
   TAR_COR              NVARCHAR2(250),
   TAR_QUADRO           NUMBER(15),
   constraint PK_TAR primary key (TAR_ID)
);

alter table TAREFA
   add constraint FK_TAR_PRO foreign key (PRO_ID)
      references PROJETO (PRO_ID);

      
      
      
################# POSTGRES #######################
create table USUARIO (
   USU_ID               NUMERIC(15)          not null,
   USU_NOME             VARCHAR(250)         null,
   USU_USUARIO          VARCHAR(250)         null,
   USU_SENHA            VARCHAR(250)         null,
   USU_EMAIL            VARCHAR(250)         null,
   constraint PK_USU primary key (USU_ID)
);

create table PROJETO (
   PRO_ID               NUMERIC(15)          not null,
   USU_ID               NUMERIC(15)          null,
   PRO_CADASTRO         DATE                 null,
   PRO_DESCRICAO        VARCHAR(250)         null,
   PRO_OBSERVACAO       VARCHAR(2000)        null,
   constraint PK_PRO primary key (PRO_ID)
);

alter table PROJETO
   add constraint FK_PRO_USU foreign key (USU_ID)
      references USUARIO (USU_ID)
      on delete restrict on update restrict;
      
create table TAREFA (
   TAR_ID               NUMERIC(15)          not null,
   PRO_ID               NUMERIC(15)          null,
   TAR_CADASTRO         DATE                 null,
   TAR_SITUACAO         NUMERIC(15)          null,
   TAR_TITULO           VARCHAR(250)         null,
   TAR_DESCRICAO        VARCHAR(250)         null,
   TAR_COR              VARCHAR(250)         null,
   TAR_QUADRO           NUMERIC(15)          null,
   constraint PK_TAR primary key (TAR_ID)
);

alter table TAREFA
   add constraint FK_TAR_PRO foreign key (PRO_ID)
      references PROJETO (PRO_ID)
      on delete restrict on update restrict;
