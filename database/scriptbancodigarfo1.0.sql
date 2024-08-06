create schema if not exists digarfo;
use digarfo;
create table if not exists receita(
	id_receita bigint auto_increment PRIMARY KEY,
    nome_receita VARCHAR(40) NOT NULL,
    estrelas int,
    custo varchar(30) NOT NULL,
    dificuldade varchar(30) NOT NULL,
    tempo_prep varchar(30) NOT NULL,
    ingredientes text NOT NULL,
    modo_prep text NOT NULL,
    img_receita blob NOT NULL,
    id_autor bigint NOT NULL,
    status_avaliacao varchar(40),
    foreign key (id_autor) references juncao_adm_usuario(id_juncao)
);
create table if not exists juncao_adm_usuario(
	id_adm bigint,
    id_usuario bigint,
    id_juncao bigint auto_increment primary key,
    foreign key (id_usuario) references usuario(id_usuario),
    foreign key (id_adm) references adm(id_adm)
);
create table if not exists usuario(
	id_usuario bigint auto_increment PRIMARY KEY,
	nome_usuario varchar(60) NOT NULL,
    img_user blob,
    email varchar(60) NOT NULL,
    senha varchar(40) NOT NULL,
    descrição text
);
create table if not exists adm(
	id_adm bigint PRIMARY KEY,
    FOREIGN KEY (id_adm) REFERENCES usuario(id_usuario)
);
create table if not exists comentarios(
	id bigint auto_increment primary key,
    texto text NOT NUll,
    id_autor bigint,
    foreign key (id_autor) references juncao_adm_usuario(id_juncao)
);
create table if not exists usuario_faz_comentario(
	id_usuario bigint primary key,
    id_coment bigint primary key,
	foreign key (id_usuario) references usuario(id_usuario),
    foreign key (id_coment) references comentarios(id)
); 
create table if not exists receita_tem_comentarios(
	id_receita bigint primary key,
    id_coment bigint primary key,
    foreign key (id_receita) references receita(id_receita),
    foreign key (id_coment) references comentario(id)
);
create table if not exists usuario_avalia_e_cria_receita(
	id_usuario bigint primary key,
    id_receita bigint primary key,
    numero_avaliacao int,
    datacriacaodareceita date,
	foreign key (id_usuario) references usuario(id_usuario),
    foreign key (id_receita) references receita(id_receita)
);
create table if not exists usuario_favorita_receita(
	id_usuario bigint primary key,
    id_receita bigint primary key,
	foreign key (id_usuario) references usuario(id_usuario),
    foreign key (id_receita) references receita(id_receita)
);
create table if not exists denuncia(
	id_usuario bigint primary key,
    id_coment bigint primary key,
    motivo text,
    foreign key (id_usuario) references usuario(id_usuario),
    foreign key (id_coment) references comentarios(id)
);
create table if not exists adm_bani_usuario(
	id_adm bigint primary key,
    id_usuario bigint primary key,
    tempo varchar(30),
    motivo text,
    foreign key (id_adm) references adm(id_adm),
    foreign key (id_usuario) references usuario(id_usuario)
);
create table if not exists adm_avalia_receita(
	id_adm bigint primary key,
    id_receita bigint primary key,
    coment_avaliacao varchar(200),
    resultado_avaliacao varchar(40),
    foreign key (id_adm) references adm(id_adm),
    foreign key (id_receita) references receita(id_receita)
);

