-- apply changes
create table driver (
  id                            uuid not null,
  name                          varchar(45) not null,
  license_number                varchar(45) not null,
  version                       bigint not null,
  when_created                  timestamptz not null,
  when_modified                 timestamptz not null,
  constraint uq_driver_name unique (name),
  constraint uq_driver_license_number unique (license_number),
  constraint pk_driver primary key (id)
);

create table message (
  id                            uuid not null,
  driver_id                     uuid not null,
  message_content               varchar(255) not null,
  version                       bigint not null,
  when_created                  timestamptz not null,
  when_modified                 timestamptz not null,
  constraint pk_message primary key (id)
);

create table vehicle (
  id                            uuid not null,
  driver_id                     uuid not null,
  plate_number                  varchar(255) not null,
  make                          varchar(255) not null,
  model                         varchar(255) not null,
  year                          varchar(255) not null,
  telematics                    json not null,
  version                       bigint not null,
  when_created                  timestamptz not null,
  when_modified                 timestamptz not null,
  constraint uq_vehicle_plate_number unique (plate_number),
  constraint pk_vehicle primary key (id)
);

create index ix_message_driver_id on message (driver_id);
alter table message add constraint fk_message_driver_id foreign key (driver_id) references driver (id) on delete restrict on update restrict;

create index ix_vehicle_driver_id on vehicle (driver_id);
alter table vehicle add constraint fk_vehicle_driver_id foreign key (driver_id) references driver (id) on delete restrict on update restrict;

