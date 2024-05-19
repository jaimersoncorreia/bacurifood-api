create table atividade
(
    id               bigint      not null auto_increment,
    nome             varchar(80) not null,
    descricao        text,
    status_atividade varchar(10),
    data_inicio      date,
    data_fim         date,
    primary key (id)
) engine = InnoDB
  default charset = utf8mb4;