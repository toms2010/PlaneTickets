
CREATE TABLE plane_type
(
	id smallint PRIMARY KEY  NOT NULL IDENTITY(1,1),
	brand varchar(10) NOT NULL,
	model varchar(10) NOT NULL,
	seats_rows tinyint NOT NULL,
	number_of_seats_in_row tinyint NOT NULL,
	number_of_premium_rows tinyint NOT NULL,
	create_date datetime2,
	create_user_id smallint
)

CREATE TABLE flight
(
	id smallint PRIMARY KEY NOT NULL IDENTITY(1,1),
	departure_airport char(3) NOT NULL,
	arrival_airport char (3) NOT NULL,
	flight_number varchar(10) NOT NULL,
	departure_date datetimeoffset(0) NOT NULL,
	flight_time time(0) NOT NULL,
	plane_type_id smallint NOT NULL,
	create_date datetime2,
	create_user_id smallint

	CONSTRAINT FK_FLIGHT_TO_PLANE_TYPE
	FOREIGN KEY (plane_type_id) REFERENCES plane_type(id)
)

CREATE TABLE price_group
(
	id smallint PRIMARY KEY NOT NULL IDENTITY(1,1),
	[name] varchar(10),
	symbol varchar(5) NOT NULL,
	price decimal(19, 4) NOT NULL,
	create_date datetime2,
	create_user_id smallint
)

CREATE TABLE passager
(
	id int PRIMARY KEY NOT NULL IDENTITY(1,1),
	[name] varchar(20) NOT NULL,
	surname varchar(20) NOT NULL,
	nationality varchar(20),
	adress varchar(255),
	create_date datetime2,
	create_user_id smallint
)

CREATE TABLE seat
(
	id smallint PRIMARY KEY NOT NULL IDENTITY(1,1),
	number smallint NOT NULL,
	passager_id int,
	flight_id smallint,
	price_group_id smallint,
	[status] char(1),
	create_date datetime2,
	create_user_id smallint,
	passager_temp varchar(50)

	CONSTRAINT FK_SEAT_TO_PASSAGER
	FOREIGN KEY (passager_id) REFERENCES passager (id),
	
	CONSTRAINT FK_SEAT_TO_FLIGHT
	FOREIGN KEY (flight_id) REFERENCES flight (id),

	CONSTRAINT FK_SEAT_TO_PRICE_GROUP
	FOREIGN KEY (price_group_id) REFERENCES price_group (id),
)