package ysaak.weather.service.datafetcher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ysaak.weather.config.WeatherAppConfig;
import ysaak.weather.data.ProcessExecutionResult;
import ysaak.weather.data.Sensor;
import ysaak.weather.data.SensorData;
import ysaak.weather.service.ProcessExecutionService;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

public class XiaomiMijiaDataFetcher implements DataFetcher {
    private static final Logger LOGGER = LoggerFactory.getLogger(XiaomiMijiaDataFetcher.class);

    private static final String XFD_LINE_TYPE_DATA = "D";
    private static final String XFD_LINE_TYPE_ERROR = "E";
    private static final String XFD_LINE_TYPE_UNREACHABLE = "N";

    private static final String XFD_DATA_KEY_TEMPERATURE = "temperature";
    private static final String XFD_DATA_KEY_HUMIDITY = "humidity";
    private static final String XFD_DATA_KEY_BATTERY_VOLTAGE = "battery_voltage";
    private static final String XFD_DATA_KEY_BATTERY_LEVEL = "battery_level";

    private final WeatherAppConfig.XiaomiMijiaDataFetcherConfig fetcherConfig;
    private final ProcessExecutionService processExecutionService;

    public XiaomiMijiaDataFetcher(WeatherAppConfig.XiaomiMijiaDataFetcherConfig fetcherConfig, ProcessExecutionService processExecutionService) {
        this.fetcherConfig = fetcherConfig;
        this.processExecutionService = processExecutionService;
    }

    @Override
    public Optional<SensorData> fetchData(Sensor sensor) {
        final ProcessExecutionResult result;
        SensorData data = null;

        // Read data using python script
        try {
            result = processExecutionService.execute(Arrays.asList(
                    fetcherConfig.getPath(),
                    "--device",
                    sensor.getDeviceAddress()
            ), fetcherConfig.getTimeout(), fetcherConfig.isTraceOutput());
        }
        catch (IOException | InterruptedException | ExecutionException e) {
            LOGGER.error("Error while executing reading script for sensor " + sensor.getCode(), e);
            return Optional.empty();
        }

        if (result.isProcessExited()) {
            if (result.getExitVal() == 0) {
                data = parseData(result.getOutput());
            } else {
                LOGGER.error("Error while reading data of sensor {}. Exit val: {} ; output: {}", sensor.getCode(), result.getExitVal(), result.getOutput());
            }
        }
        else {
            LOGGER.error("Unable to connect to sensor. Execution trace: {}", result.getOutput());
            data = new SensorData();
            data.setReachable(false);
        }

        return Optional.ofNullable(data);
    }

    private SensorData parseData(String fetcherOutput) {
        final SensorData data = new SensorData();
        data.setReachable(true);

        for (String line : fetcherOutput.split("\n")) {
            String[] lineParts = line.split("\\|");

            if (XFD_LINE_TYPE_ERROR.equals(lineParts[0])) {
                LOGGER.error("Data fetcher error: {} ", lineParts[1]);
            }
            else if (XFD_LINE_TYPE_UNREACHABLE.equals(lineParts[0])) {
                data.setReachable(false);
                break;
            }
            else if (XFD_LINE_TYPE_DATA.equals(lineParts[0])) {
                if (XFD_DATA_KEY_TEMPERATURE.equals(lineParts[1])) {
                    data.setTemperature(parseDouble(lineParts[2]));
                }
                else if (XFD_DATA_KEY_HUMIDITY.equals(lineParts[1])) {
                    data.setHumidity(parseDouble(lineParts[2]));
                }
                else if (XFD_DATA_KEY_BATTERY_VOLTAGE.equals(lineParts[1])) {
                    data.setBatteryVoltage(parseDouble(lineParts[2]));
                }
                else if (XFD_DATA_KEY_BATTERY_LEVEL.equals(lineParts[1])) {
                    data.setBatteryLevel(parseInt(lineParts[2]));
                }
            }
        }

        return data;
    }

    private static Integer parseInt(String input) {
        try {
            return Integer.parseInt(input);
        }
        catch (NumberFormatException e) {
            LOGGER.error("Error while parsing int value: " + input, e);
            return null;
        }
    }

    private static Double parseDouble(String input) {
        try {
            double value = Double.parseDouble(input);
            return Math.round(value * 100.) / 100.;
        }
        catch (NumberFormatException e) {
            LOGGER.error("Error while parsing double value: " + input, e);
            return null;
        }
    }
}
