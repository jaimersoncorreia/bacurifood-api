alter table restaurante add aberto tinyint(1) not null;
update restaurante set aberto = true where aberto = false;