DROP KEYSPACE IF EXISTS brs;
CREATE KEYSPACE brs WITH replication={'class': 'SimpleStrategy', 'replication_factor': '1'};
USE brs;

CREATE TABLE Station ( station_id TEXT, name TEXT, total_docks INT, avail_bikes INT, PRIMARY KEY (station_id));
CREATE TABLE StationBike (bike_id TEXT, station_id TEXT, PRIMARY KEY (bike_id));
CREATE TABLE RsvdBike (bike_id TEXT, station_id TEXT, user_id TEXT, txn_id TEXT, PRIMARY KEY (user_id));
CREATE TABLE OutBike (bike_id TEXT, user_id TEXT, txn_id TEXT, from_station_id TEXT, checkout_time TIMESTAMP, PRIMARY KEY(bike_id));
CREATE TABLE InBike (txn_id TEXT, bike_id TEXT, user_id TEXT, from_station_id TEXT, to_station_id TEXT, checkout_time TIMESTAMP, checkin_time TIMESTAMP, grand_total float, PRIMARY KEY (txn_id));


INSERT INTO Station (station_id, name, total_docks, avail_bikes) VALUES ('s001', 'King Library', 8, 6);
INSERT INTO Station (station_id, name, total_docks, avail_bikes) VALUES ('s002', 'San Fernando & 7th', 8, 4);
INSERT INTO Station (station_id, name, total_docks, avail_bikes) VALUES ('s003', 'San Salvador & 9th', 8, 0);

INSERT INTO StationBike (bike_id, station_id) VALUES ('b0001', 's001');
INSERT INTO StationBike (bike_id, station_id) VALUES ('b0002', 's001');
INSERT INTO StationBike (bike_id, station_id) VALUES ('b0003', 's001');
INSERT INTO StationBike (bike_id, station_id) VALUES ('b0004', 's001');
INSERT INTO StationBike (bike_id, station_id) VALUES ('b0005', 's001');
INSERT INTO StationBike (bike_id, station_id) VALUES ('b0006', 's001');
INSERT INTO StationBike (bike_id, station_id) VALUES ('b0007', 's002');
INSERT INTO StationBike (bike_id, station_id) VALUES ('b0008', 's002');
INSERT INTO StationBike (bike_id, station_id) VALUES ('b0009', 's002');
INSERT INTO StationBike (bike_id, station_id) VALUES ('b0010', 's002');

 
