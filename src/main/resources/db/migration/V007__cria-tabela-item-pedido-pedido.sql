# Pedido
create table pedido
(
    id                   bigint         not null auto_increment,
    subtotal             decimal(10, 2) not null,
    taxa_frete           decimal(10, 2) not null,
    valor_total          decimal(10, 2) not null,

    restaurante_id       bigint         not null,
    usuario_cliente_id   bigint         not null,
    forma_pagamento_id   bigint         not null,


    endereco_cidade_id   bigint         not null,
    endereco_cep         varchar(9)     not null,
    endereco_logradouro  varchar(100)   not null,
    endereco_numero      varchar(20)    not null,
    endereco_complemento varchar(60)    not null,
    endereco_bairro      varchar(60)    not null,

    status_pedido        varchar(10)    not null,
    data_criacao         datetime       not null,
    data_confirmacao     datetime,
    data_cancelamento    datetime,
    data_entrega         datetime,

    primary key (id)
) engine = InnoDB
  default charset = utf8mb4;

# endereco_cidade_id
alter table pedido
    add constraint fk_pedido_endereco_cidade
        foreign key (endereco_cidade_id) references cidade (id);

# restaurante_id
alter table pedido
    add constraint fk_pedido_restaurante
        foreign key (restaurante_id) references restaurante (id);

# usuario_cliente_id
alter table pedido
    add constraint fk_pedido_usuario_cliente
        foreign key (usuario_cliente_id) references usuario (id);

# forma_pagamento_id
alter table pedido
    add constraint fk_pedido_forma_pagamento
        foreign key (forma_pagamento_id) references forma_pagamento (id);

# ItemPedido
create table item_pedido
(
    id             bigint         not null auto_increment,
    quantidades    smallint(6)    not null,
    preco_unitario decimal(10, 2) not null,
    preco_otal     decimal(10, 2) not null,
    observacao     text           null,
    pedido_id      bigint         not null,
    produto_id     bigint         not null,

    primary key (id),
    unique key uk_item_pedido_produto (pedido_id, produto_id)
) engine = InnoDB
  default charset = utf8mb4;

#pedido_id
alter table item_pedido
    add constraint fk_item_pedido_pedido
        foreign key (pedido_id) references pedido (id);

#produto_id
alter table item_pedido
    add constraint fk_item_pedido_produto
        foreign key (produto_id) references produto (id);