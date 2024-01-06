insert into bacurifood.cozinha (id, nome) values (1, 'Tailandeza');
insert into bacurifood.cozinha (id, nome) values (2, 'Indiana');

insert into restaurante (nome, taxa_frete, cozinha_id) values ('Thai Gourmet', 10, 1);
insert into restaurante (nome, taxa_frete, cozinha_id) values ('Thai Delivery', 9.50, 1);
insert into restaurante (nome, taxa_frete, cozinha_id) values ('Tuk Tuk Indiana', 15, 2);

insert into estado (nome) values ('Amazonas');
insert into estado (nome) values ('Roraima');
insert into estado (nome) values ('Acre');

insert into cidade (nome, estado_id) values ('Manaus', 1);
insert into cidade (nome, estado_id) values ('Careiro da Várzea', 1);
insert into cidade (nome, estado_id) values ('Rorainópolis', 2);
