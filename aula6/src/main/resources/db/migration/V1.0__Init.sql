CREATE SEQUENCE public.categoria_id_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;

CREATE TABLE public.categoria
(
    id bigint NOT NULL DEFAULT nextval('categoria_id_seq'::regclass),
    nome character varying(50) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT categoria_pkey PRIMARY KEY (id)
);

CREATE SEQUENCE public.marca_id_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;

CREATE TABLE public.marca
(
    id bigint NOT NULL DEFAULT nextval('marca_id_seq'::regclass),
    nome character varying(50) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT marca_pkey PRIMARY KEY (id)
);

CREATE SEQUENCE public.permissao_id_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;
CREATE TABLE public.permissao
(
    id integer NOT NULL DEFAULT nextval('permissao_id_seq'::regclass),
    nome character varying(20) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT permissao_pkey PRIMARY KEY (id)
);
CREATE SEQUENCE public.produto_id_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;
CREATE TABLE public.produto
(
    id bigint NOT NULL DEFAULT nextval('produto_id_seq'::regclass),
    descricao character varying(1024) COLLATE pg_catalog."default" NOT NULL,
    nome character varying(100) COLLATE pg_catalog."default" NOT NULL,
    valor double precision NOT NULL,
    categoria_id bigint,
    marca_id bigint,
    CONSTRAINT produto_pkey PRIMARY KEY (id),
    CONSTRAINT fk_produto_marca FOREIGN KEY (marca_id)
        REFERENCES public.marca (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fk_produto_categoria FOREIGN KEY (categoria_id)
        REFERENCES public.categoria (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);
CREATE SEQUENCE public.usuario_id_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;
CREATE TABLE public.usuario
(
    id bigint NOT NULL DEFAULT nextval('usuario_id_seq'::regclass),
    nome character varying(255) COLLATE pg_catalog."default" NOT NULL,
    password character varying(1024) COLLATE pg_catalog."default" NOT NULL,
    username character varying(100) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT usuario_pkey PRIMARY KEY (id)
);
CREATE TABLE public.usuario_permissoes
(
    usuario_id bigint NOT NULL,
    permissoes_id integer NOT NULL,
    CONSTRAINT usuario_permissoes_pkey PRIMARY KEY (usuario_id, permissoes_id),
    CONSTRAINT fk_usuario_permissoes_usuario FOREIGN KEY (usuario_id)
        REFERENCES public.usuario (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fk_usuario_permissoes_permissao FOREIGN KEY (permissoes_id)
        REFERENCES public.permissao (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)