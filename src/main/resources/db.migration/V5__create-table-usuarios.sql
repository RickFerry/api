create table if not exists 'medicos'(
    'id' bigint not null auto_increment primary key,
    'login' varchar(100) not null,
    'senha' varchar(255) not null
)ENGINE=InnoDB DEFAULT CHARSET=UTF8;