create table estado
(
    id   bigint      not null auto_increment,
    nome varchar(80) not null,
    primary key (id)
) engine = InnoDB
  default charset = utf8mb4;

insert into estado (nome) select distinct nome_estado from cidade;

alter table cidade add column estado_id bigint not null;

update cidade c set c.estado_id = (select id from estado e where c.nome_estado = e.nome);

alter table cidade add constraint fk_cidade_estado foreign key (estado_id) references estado(id);

alter table cidade drop column nome_estado;

alter table cidade change nome_cidade nome varchar(80) not null ;