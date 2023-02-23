create table usuarios(
    id bigint not null auto_increment primary key,
    login varchar(100) not null,
    senha varchar(255) not null
);

insert into usuarios (id, login, senha) values (1, 'kalel.martins@voll.med', '$2a$12$vgn6srbi6jUB0uDgooPDOusdeTQttzu3gY3zuqW2xgpcOIsi999LG');