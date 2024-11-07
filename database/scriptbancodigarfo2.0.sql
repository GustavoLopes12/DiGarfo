/*drop schema digarfo;*/
create schema if not exists digarfo;
use digarfo;
create table if not exists usuario( /*usuario é banido por adm*/
    nome_usuario varchar(60) NOT NULL,
    banido boolean, /*banido ou nao*/
    motivo_banimento text, /*motivo*/
    img_user varchar(200),
    email varchar(60) NOT NULL PRIMARY KEY,
    senha varchar(40) NOT NULL,
    descricao text,
    id_adm_fk varchar(60),
    foreign key (id_adm_fk) references adm(email)/*adm consegue banir usuario*/
);
create table if not exists adm(
    email varchar(60) NOT NULL PRIMARY KEY,
    senha varchar(40) NOT NULL
);
create table if not exists comentario(
	id_comentario bigint not null auto_increment primary key,
    conteudo text NOT NUll,
    id_usuario_fk varchar(60),
    id_receita_fk bigint,
    data_coment date,
    foreign key (id_usuario_fk) references usuario(email),
    foreign key (id_receita_fk) references receita(id_receita)
);
create table if not exists usuario_denuncia_comentario(
	id_usuario_fk varchar(60) not null,
    id_coment_fk bigint not null,
    motivo text,
    foreign key (id_usuario_fk) references usuario(email),
    foreign key (id_coment_fk) references comentarios(id_comentario)
);
create table if not exists receita( /*receita é avaliada por adm e criada por usuario*/
	id_receita bigint not null auto_increment PRIMARY KEY,
    id_autor varchar(60), /*criador da receita*/
    id_adm varchar(60), /*adm que avalia a receita*/
    nome_receita VARCHAR(200) NOT NULL,
    custo varchar(30) NOT NULL,
    categoria varchar(60) NOT NULL,
    dificuldade varchar(30) NOT NULL,
    tempo_prep varchar(30) NOT NULL,
    ingredientes text NOT NULL,
    modo_prep text NOT NULL,
    img_receita varchar(200),
    aprovada boolean,
    foreign key (id_autor) references usuario(email), /*pegando quem criou a receita*/
    foreign key (id_adm) references adm(email) /*pegando o adm que avaliou a rct*/
);
create table if not exists usuario_denuncia_receita( /*usuario pode denunciar a receita*/
	email_usuario_fk varchar(60) not null,
    id_receita_fk bigint not null,
    motivo text,
	foreign key (email_usuario_fk) references usuario(email),
    foreign key (id_receita_fk) references receita(id_receita)
);
create table if not exists usuario_avalia_receita(/*usuario consegue avaliar receita*/
	email_usuario_fk varchar(60) not null,
    id_receita_fk bigint not null,
	valor integer,
    foreign key (email_usuario_fk) references usuario(email),
    foreign key (id_receita_fk) references receita(id_receita)
);
create table if not exists usuario_favorita_receita(
	email_usuario_fk varchar(60) not null,
    id_receita_fk bigint not null,
	foreign key (email_usuario_fk) references usuario(email),
    foreign key (id_receita_fk) references receita(id_receita)
);

select * from usuario;

select * from receita;
select * from adm;
