create table customer
(
    id int auto_increment,
    mobile varchar(255) null,
    cnic varchar(255) null,
    otp varchar(255) null,
    constraint customer_pk
        primary key (id)
);

create table product
(
    id int auto_increment,
    code varchar(255) not null,
    name varchar(255) null,
    constraint product_pk
        primary key (id)
);


create table action_logs
(
    id int auto_increment,
    uri varchar(255) null,
    method varchar(255) null,
    created_on mediumtext null,
    status varchar(255) null,
    status_code int null,
    status_description varchar(255) null,
    created_by int null,
    request_object longtext not null,
    response_object longtext null,
    third_party_request_object varchar(1000) null,
    third_party_response_object varchar(1000) null,
    constraint actions_logs_pk
        primary key (id)
);

create table transactions
(
    id int auto_increment,
    customer_id int null,
    status_id long null,
    retailer_ref_number varchar(255) null,
    description varchar(255) null,
    create_on mediumtext null,
    updated_on mediumtext null,
    account_id int null,
    details_json varchar(1000) null,
    total_amount long null,
    discounted_amount long null,
    constraint transactions_pk
        primary key (id),
    constraint transactions_customer_id_fk
        foreign key (customer_id) references customer (id)
);

