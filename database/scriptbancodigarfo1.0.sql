create schema if not exists digarfo;
use digarfo;
create table if not exists receita(
	id_receita bigint auto_increment PRIMARY KEY,
    nome_receita VARCHAR(40) NOT NULL,
    custo varchar(30) NOT NULL,
    dificuldade varchar(30) NOT NULL,
    tempo_prep varchar(30) NOT NULL,
    ingredientes text NOT NULL,
    modo_prep text NOT NULL,
    img_receita blob,
    status_avaliacao boolean,
    aprovacao boolean
);
create table if not exists usuario(
	id_usuario bigint auto_increment PRIMARY KEY,
	nome_usuario varchar(60) NOT NULL,
    banido boolean,
    img_user blob,
    email varchar(60) NOT NULL,
    senha varchar(40) NOT NULL,
    descrição text
);
create table if not exists adm(
	id_adm bigint auto_increment PRIMARY KEY,
    email varchar(60) NOT NULL,
    senha varchar(40) NOT NULL
);
create table if not exists comentarios(
	id_comentario bigint auto_increment primary key,
    conteudo text NOT NUll
);
create table if not exists usuario_faz_comentario(
	id_usuario bigint primary key,
    id_comentario bigint primary key,
	foreign key (id_usuario) references usuario(id_usuario),
    foreign key (id_coment) references comentarios(id_comentario)
); 
create table if not exists receita_tem_comentarios(
	id_receita bigint primary key,
    id_coment bigint primary key,
    foreign key (id_receita) references receita(id_receita),
    foreign key (id_coment) references comentario(id_comentario)
);
create table if not exists usuario_cria_receita(
	id_usuario bigint primary key,
    id_receita bigint primary key,
    data_criacao date,
	foreign key (id_usuario) references usuario(id_usuario),
    foreign key (id_receita) references receita(id_receita)
);
create table if not exists usuario_avalia_receita(
	id_usuario bigint primary key,
    id_receita bigint primary key,
	numero int,
    foreign key (id_usuario) references usuario(id_usuario),
    foreign key (id_receita) references receita(id_receita)
);
create table if not exists usuario_favorita_receita(
	id_usuario bigint primary key,
    id_receita bigint primary key,
	foreign key (id_usuario) references usuario(id_usuario),
    foreign key (id_receita) references receita(id_receita)
);
create table if not exists usuario_denuncia_receita(
	id_usuario bigint primary key,
    id_receita bigint primary key,
    motivo text,
	foreign key (id_usuario) references usuario(id_usuario),
    foreign key (id_receita) references receita(id_receita)
);
create table if not exists usuario_denuncia_comentario(
	id_usuario bigint primary key,
    id_coment bigint primary key,
    motivo text,
    foreign key (id_usuario) references usuario(id_usuario),
    foreign key (id_coment) references comentarios(id_comentario)
);
create table if not exists adm_bani_usuario(
	id_adm bigint primary key,
    id_usuario bigint primary key,
    tempo varchar(30),
    data_ban date,
    motivo text,
    foreign key (id_adm) references adm(id_adm),
    foreign key (id_usuario) references usuario(id_usuario)
);
create table if not exists adm_avalia_receita(
	id_adm bigint primary key,
    id_receita bigint primary key,
    coment_avaliacao text,
    resultado_avaliacao boolean,
    foreign key (id_adm) references adm(id_adm),
    foreign key (id_receita) references receita(id_receita)
);

