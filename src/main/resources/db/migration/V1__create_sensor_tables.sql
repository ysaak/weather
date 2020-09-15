CREATE TABLE SENSOR (
    SENS_ID                         VARCHAR(30) NOT NULL,
    SENS_CODE                       VARCHAR(20) NOT NULL,
    SENS_NAME                       VARCHAR(200) NOT NULL,
    SENS_DESCRIPTION                TEXT,
    SENS_DEVICE_TYPE                VARCHAR(20) NOT NULL,
    SENS_DEVICE_ADDRESS             VARCHAR(50),
    SENS_ON_BATTERY                 TINYINT UNSIGNED NOT NULL,
    SENS_BATTERY_VOLTAGE_THRESHOLD  DECIMAL(5, 2),
    SENS_BATTERY_LEVEL_THRESHOLD    TINYINT UNSIGNED,
    SENS_ENABLED                    TINYINT UNSIGNED NOT NULL,
    SENS_LAST_DATA_ID               VARCHAR(30),
    SENS_LAST_BATTERY_VALUE         DECIMAL(5, 2),
    SENS_BATTERY_WARNING            TINYINT UNSIGNED
);

ALTER TABLE SENSOR ADD PRIMARY KEY (SENS_ID);

CREATE TABLE SENSOR_DATA (
    SEDA_ID                 VARCHAR(30) NOT NULL,
    SEDA_SENS_ID            VARCHAR(30) NOT NULL,
    SEDA_RECEPTION_TIME     TIMESTAMP NOT NULL,
    SEDA_REACHABLE          TINYINT UNSIGNED NOT NULL,
    SEDA_TEMPERATURE        DECIMAL(5, 2),
    SEDA_HUMIDITY           DECIMAL(5, 2),
    SEDA_BATTERY_VOLTAGE    DECIMAL(5, 2),
    SEDA_BATTERY_LEVEL      TINYINT UNSIGNED
);

ALTER TABLE SENSOR_DATA ADD PRIMARY KEY (SEDA_ID);