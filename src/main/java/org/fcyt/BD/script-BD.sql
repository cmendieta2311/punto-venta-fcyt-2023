create sequence marca_id_seq
    cycle;

alter sequence marca_id_seq owner to postgres;

create sequence empresa_id;

alter sequence empresa_id owner to postgres;

create sequence empresa_id_seq
    as integer;

alter sequence empresa_id_seq owner to postgres;

create table marca
(
    id          integer generated always as identity (cycle)
        constraint marca_pk
            primary key,
    descripcion varchar(45) not null
);

alter table marca
    owner to postgres;

alter sequence marca_id_seq owned by marca.id;

create table iva
(
    id          serial
        constraint iva_pk
            primary key,
    descripcion varchar(45) not null
);

alter table iva
    owner to postgres;

create table producto
(
    id            integer generated always as identity
        constraint producto_pk
            primary key,
    descripcion   varchar(45),
    precio_compra integer default 0 not null,
    precio_venta  integer default 0 not null,
    idmarca       integer
        constraint producto_marca_id_fk
            references marca,
    idiva         integer
        constraint producto_iva_id_fk
            references iva,
    idempresa     integer
);

alter table producto
    owner to postgres;

create table empresa
(
    id        integer generated always as identity
        constraint empresa_pk
            primary key,
    nombre    varchar(45),
    direccion varchar(45),
    telefono  varchar(45),
    ruc       varchar(15)
);

alter table empresa
    owner to postgres;

alter sequence empresa_id owned by empresa.id;

create table cliente
(
    id        serial
        constraint cliente_pk
            primary key,
    ruc       varchar(15),
    nombre    varchar(45),
    telefono  varchar(45),
    direccion varchar(45),
    idempresa integer
);

alter table cliente
    owner to postgres;

create table caja
(
    id          serial
        constraint caja_pk
            primary key,
    descripcion varchar(45),
    nro_caja    integer,
    idempresa   integer
        constraint caja_empresa_id_fk
            references empresa
);

alter table caja
    owner to postgres;

create table usuario
(
    id        serial
        constraint usuario_pk
            primary key,
    usuario   varchar(45),
    clave     varchar(45),
    idempresa integer
        constraint usuario_empresa_id_fk
            references empresa
);

alter table usuario
    owner to postgres;

create table factura
(
    id          serial
        constraint factura_pk
            primary key,
    idempresa   integer
        constraint factura_empresa_id_fk
            references empresa,
    fecha       timestamp,
    cond_venta  integer,
    idcliente   integer
        constraint factura_cliente_id_fk
            references cliente,
    nro_factura integer,
    total       integer,
    idcaja      integer
        constraint factura_caja_id_fk
            references caja,
    idusuario   integer
        constraint factura_usuario_id_fk
            references usuario
);

comment on column factura.cond_venta is '1=Contado, 2=Credito';

alter table factura
    owner to postgres;

create table detalle_factura
(
    idproducto  integer
        constraint detalle_factura_producto_id_fk
            references producto,
    idfactura   integer
        constraint detalle_factura_factura_id_fk
            references factura,
    descripcion varchar(45),
    cantidad    integer,
    importe     integer,
    idiva       integer
);

alter table detalle_factura
    owner to postgres;


