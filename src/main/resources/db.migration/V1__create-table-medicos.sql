create table if not exists 'medicos'(
    'id' bigint not null auto_increment primary key,
    'nome' varchar(100) not null,
    'email' varchar(100) not null,
    'crm' varchar(6) not null unique,
    'especialidade' varchar(100) not null,
    'logradouro' varchar(100) not null,
    'bairro' varchar(100) not null,
    'cep' varchar(9) not null,
    'complemento' varchar(100),
    'numero' varchar(20),
    'uf' char(2) not null,
    'cidade' varchar(100) not null
)ENGINE=InnoDB DEFAULT CHARSET=UTF8;