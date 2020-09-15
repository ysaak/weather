package ysaak.weather.data;

import ysaak.common.dao.SerializableEnum;

public enum DeviceType implements SerializableEnum {
    XIAOMI_MIJIA("XIAOMI_MI")
    ;

    private final String dbCode;

    DeviceType(String dbCode) {
        this.dbCode = dbCode;
    }

    @Override
    public String getDbCode() {
        return dbCode;
    }
}
