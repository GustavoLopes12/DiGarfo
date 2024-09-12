/*drop schema if exists digarfo;*//*cuidado com esta linha*//*so use se necessario e olha la em*/
create schema if not exists digarfo;
use digarfo;
create table if not exists receita(
	id_receita bigint not null auto_increment PRIMARY KEY,
    nome_receita VARCHAR(40) NOT NULL,
    custo varchar(30) NOT NULL,
    categoria varchar(40) NOT NULL,
    dificuldade varchar(30) NOT NULL,
    tempo_prep varchar(30) NOT NULL,
    ingredientes text NOT NULL,
    modo_prep text NOT NULL,
    img_receita varchar(200),
    aprovacao boolean
);
create table if not exists usuario(
	nome_usuario varchar(60) NOT NULL,
    banido boolean,
    img_user varchar(200),
    email varchar(60) NOT NULL PRIMARY KEY,
    senha varchar(40) NOT NULL,
    descricao text
);
create table if not exists adm(
    email varchar(60) NOT NULL PRIMARY KEY,
    senha varchar(40) NOT NULL
);
create table if not exists comentarios(
	id_comentario bigint not null auto_increment primary key,
    conteudo text NOT NUll
);
create table if not exists usuario_faz_comentario(
	id bigint primary key auto_increment,
	email_usuario_fk varchar(60) not null,
    id_comentario_fk bigint not null,
	foreign key (email_usuario_fk) references usuario(email),
    foreign key (id_comentario_fk) references comentarios(id_comentario)
); 
create table if not exists receita_tem_comentarios(
	id bigint primary key auto_increment,
	id_receita_fk bigint not null,
    id_comentario_fk bigint not null,
    foreign key (id_receita_fk) references receita(id_receita),
    foreign key (id_comentario_fk) references comentarios(id_comentario)
);
create table if not exists usuario_cria_receita(
	id bigint primary key auto_increment,
	email_usuario_fk varchar(60) not null,
    id_receita_fk bigint not null,
    data_criacao date,
	foreign key (email_usuario_fk) references usuario(email),
    foreign key (id_receita_fk) references receita(id_receita)
);
create table if not exists usuario_avalia_receita(
    id bigint primary key auto_increment,
	email_usuario_fk varchar(60) not null,
    id_receita_fk bigint not null,
	numero int,
    foreign key (email_usuario_fk) references usuario(email),
    foreign key (id_receita_fk) references receita(id_receita)
);
create table if not exists usuario_favorita_receita(
	id bigint primary key auto_increment,
	email_usuario_fk varchar(60) not null,
    id_receita_fk bigint not null,
	foreign key (email_usuario_fk) references usuario(email),
    foreign key (id_receita_fk) references receita(id_receita)
);
create table if not exists usuario_denuncia_receita(
	id bigint primary key auto_increment,
	email_usuario_fk varchar(60) not null,
    id_receita_fk bigint not null,
    motivo text,
	foreign key (email_usuario_fk) references usuario(email),
    foreign key (id_receita_fk) references receita(id_receita)
);
create table if not exists usuario_denuncia_comentario(
	id bigint primary key auto_increment,
	email_usuario_fk varchar(60) not null,
    id_coment_fk bigint not null,
    motivo text,
    foreign key (email_usuario_fk) references usuario(email),
    foreign key (id_coment_fk) references comentarios(id_comentario)
);
create table if not exists adm_bani_usuario(
	id bigint primary key auto_increment,
	email_adm_fk varchar(60) not null,
    email_usuario_fk varchar(60) not null,
    tempo varchar(30),
    data_ban date,
    motivo text,
    foreign key (email_adm_fk) references adm(email),
    foreign key (email_usuario_fk) references usuario(email)
);
create table if not exists adm_avalia_receita(
    id bigint primary key auto_increment,
	email_adm_fk varchar(60) not null,
    id_receita_fk bigint not null,
    coment_avaliacao text,
    resultado_avaliacao boolean,
    foreign key (email_adm_fk) references adm(email),
    foreign key (id_receita_fk) references receita(id_receita)
);

use digarfo;
select*from usuario;
