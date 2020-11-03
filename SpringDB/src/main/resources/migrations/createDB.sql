create table databases (
    id serial,
    db_name varchar(255) not null unique,
    primary key (id)
);

create table db_tables (
    id serial,
    table_name varchar(255) not null,
    database_id integer not null,
    primary key (id),
    foreign key (database_id) references databases(id)
);

create table rows (
    id serial,
    table_id integer not null,
    primary key (id),
    foreign key (table_id) references db_tables(id)
);

CREATE TABLE columns (
    id serial,
    column_name varchar(255),
    type varchar(255),
    min_val integer,
    max_val integer,
    table_id integer not null,
    primary key (id),
    foreign key (table_id) references db_tables(id)
);

create table values (
    id serial,
    data varchar(255),
    file text,
    column_id integer not null,
    row_id integer not null,
    primary key (id),
    foreign key (column_id) references columns(id),
    foreign key (row_id) references rows(id)
);
