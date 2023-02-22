create table usuarios(
    id bigint not null auto_increment primary key,
    login varchar(100) not null,
    senha varchar(255) not null
);

--INSERT INTO usuarios(login, senha) VALUES('ricardo.martins@voll.med.com', '$2a$12$isfU1RFwum4KC4nzO0njWuB1ww2XEPd4Wc1QK4WarPLu4jx9xljry'); --senha(123456)
