create table usuarios(
    id bigint not null auto_increment primary key,
    login varchar(100) not null,
    senha varchar(255) not null
);