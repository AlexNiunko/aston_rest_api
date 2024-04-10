CREATE SCHEMA tool_box
    AUTHORIZATION postgres;
create table tool_box.users
(
    user_id    bigint  not null
        primary key
        unique,
    login      varchar(50)                                                     not null
        unique,
    password   varchar(50)                                                     not null
        unique,
    name       varchar(30)                                                     not null,
    surname    varchar(30)                                                     not null,
    users_role integer                                                         not null
);

alter table tool_box.users
    owner to postgres;
CREATE TABLE tool_box.products
(
    id_product bigint NOT NULL ,
    product_name character varying(50) COLLATE pg_catalog."default" NOT NULL,
    product_price numeric,
    amount_of_product integer,
    CONSTRAINT products_pkey PRIMARY KEY (id_product),
    CONSTRAINT products_id_product_key UNIQUE (id_product)
)
    WITH (
        OIDS = FALSE
    )
    TABLESPACE pg_default;

ALTER TABLE tool_box.products
    OWNER to postgres;
create table tool_box.product_descriptions
(
    id_description    bigint not null
        primary key
        constraint product_descriptions_id_descriptions_key
            unique,
    product_id        integer                                                                                not null
        constraint product_descriptions_products_id_product_fk
            references tool_box.products on delete cascade
        ,
    country_of_origin varchar(50),
    type_of_product   varchar(50)                                                                            not null,
    brand_of_product  varchar(50),
    issue_date        date                                                                                   not null
);

alter table tool_box.product_descriptions
    owner to postgres;

create table tool_box.sales
(
    id_sale        bigint
        primary key
        unique,
    buyer_id       bigint
        constraint sales_users_user_id_fk
            references tool_box.users on delete set null ,
    product_id     bigint
        constraint sales_products_id_product_fk
            references tool_box.products
            on update set null
on DELETE set null ,
    date_of_sale   date    not null,
    amount_of_sale integer not null
);

alter table tool_box.sales
    owner to postgres;

INSERT INTO tool_box.users
(user_id, login, password, name, surname, users_role) VALUES
(1,'michai@gmail.com','123','Michail','Radzivil',1),
(22,'stefan@tut.by','12345','Stefan','Batoyi',1),
(33,'stanslau@mail.ru','12459','Stanislau','Poniatovskiy',1)
;

INSERT INTO tool_box.products
(id_product, product_name, product_price, amount_of_product) VALUES
(1,'hammer',10.25,5),
(2,'hammer drill',125.25,2),
(3,'grinder tool',84.15,4)
;

INSERT INTO tool_box.product_descriptions
(id_description, product_id, country_of_origin, type_of_product, brand_of_product, issue_date) VALUES
(1,1,'China','hand tool','Expert','2024-02-09'),
(2,2,'Japan','electric tool','Makita','2022-08-02'),
(3,3,'China','electric tool','PIT','2023-01-24');
INSERT INTO tool_box.sales
(id_sale, buyer_id, product_id, date_of_sale, amount_of_sale) VALUES
(1,1,1,'2024-04-08',1),
(2,1,1,'2024-03-25',1),
(3,22,2,'2024-01-25',2);



