package ysaak.weather.view.dto;

public class GraphDataDto {
    private final long time;
    private final Double value;

    public GraphDataDto(long time, Double value) {
        this.time = time;
        this.value = value;
    }

    public long getTime() {
        return time;
    }

    public Double getValue() {
        return value;
    }
}
