create domain status as varchar(10)
    CHECK ( value in ('Pending', 'Accepted', 'Denied') );

CREATE Table "user" (
    username varchar(20) primary key,
    password varchar(20) not null ,
    email varchar(20) not null unique,
    first_name varchar(20) not null ,
    last_name varchar(20) not null ,
    creation_date date not null,
    last_access_date date not null
);

create table tool (
    barcode varchar(12) primary key,
    name varchar(50) not null ,
    description varchar,
    purchase_date date,
    purchase_price decimal(12,2),
    shareable bool default true,
    owner varchar(20) not null references "user" (username)
);

create table request (
    username varchar(20) references "user" (username),
    tool_barcode varchar(12) references tool (barcode),
    status status not null,
    date_required date not null,
    duration int not null check (duration > 0),
    primary key (username, tool_barcode)
);

create table category (
    category_id int primary key,
    category_name varchar(20) not null,
    category_created_user varchar(20) not null references "user" (username)
);

create table category_tool (
    category_id int references category (category_id),
    tool_barcode varchar(12) references tool (barcode),
    primary key (category_id, tool_barcode)
);