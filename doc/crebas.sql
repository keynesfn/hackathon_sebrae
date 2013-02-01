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

      
============ 0.6 =================
      
create table TAREFACOMENTARIO (
   TARC_ID              NUMERIC(15)          not null,
   TAR_ID               NUMERIC(15)          null,
   TARC_DATA            DATA                 null,
   TARC_HORA            VARCHAR(20)          null,
   TARC_COMENTARIO      VARCHAR(250)         null,
   constraint PK_TAREFACOMENTARIO primary key (TARC_ID)
);

alter table TAREFACOMENTARIO
   add constraint FK_TARC_TAR foreign key (TAR_ID)
      references TAREFA (TAR_ID)
      on delete restrict on update restrict;
