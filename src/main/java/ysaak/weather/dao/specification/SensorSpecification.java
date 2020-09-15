package ysaak.weather.dao.specification;

import org.springframework.data.jpa.domain.Specification;
import ysaak.weather.data.DeviceType;
import ysaak.weather.data.Sensor;

public final class SensorSpecification {
    private SensorSpecification() { /**/ }

    public static Specification<Sensor> isEnabled(boolean enabled) {
        return (root, query, builder) -> builder.equal(root.get("enabled"), enabled);
    }

    public static Specification<Sensor> isDeviceType(DeviceType deviceType) {
        return (root, query, builder) -> builder.equal(root.get("deviceType"), deviceType);
    }
}
