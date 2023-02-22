create table usuarios(
    id bigint not null auto_increment primary key,
    login varchar(100) not null,
    senha varchar(255) not null
);

INSERT INTO usuarios (login, senha) VALUES ('ricardo.martins@voll.med', '$2a$12$OYn5VhLUKiOVCHh.j6itj.4jD7UXI2psYC8YnHbBOcKIQj3elFtv2'); --senha(123456)
INSERT INTO usuarios (login, senha) VALUES ('kalel.martins@voll.med', '$2a$12$n4p.2HtlJSdUT7n2rnR4QOqjqFbW.WinfZdLLhD6hzDpuHQz.zOXG'); --senha(654321)
