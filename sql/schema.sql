create schema tool_app;

create domain status as varchar(10)
    CHECK ( value in ('Pending', 'Accepted', 'Denied') );

CREATE Table tool_app.user (
    username varchar(20) primary key,
    password varchar(20) not null ,
    email varchar(20) not null unique,
    first_name varchar(20) not null ,
    last_name varchar(20) not null ,
    creation_date date not null,
    last_access_date date not null
);

create table tool_app.tool (
    barcode varchar(12) primary key,
    name varchar(50) not null ,
    description varchar,
    purchase_date date,
    purchase_price decimal(12,2),
    shareable bool default true,
    owner varchar(20) not null references tool_app.user (username)
);

create table tool_app.request (
    username varchar(20) references tool_app.user (username),
    tool_barcode varchar(12) references tool_app.tool (barcode),
    status status not null,
    date_required date not null,
    duration int not null check (duration > 0),
    date_responded date,
    date_needed_return date,
    date_returned date,
    primary key (username, tool_barcode)
);

create table tool_app.category (
    category_id int primary key,
    category_name varchar(20) not null,
    category_created_user varchar(20) not null references tool_app.user (username)
);

create table tool_app.category_tool (
    category_id int references tool_app.category (category_id),
    tool_barcode varchar(12) references tool_app.tool (barcode),
    primary key (category_id, tool_barcode)
);