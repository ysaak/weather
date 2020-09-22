package ysaak.weather.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.convert.DurationUnit;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

@Configuration
@ConfigurationProperties(prefix = "weather")
public class WeatherAppConfig {
    private String dataFetchInterval;
    private XiaomiMijiaDataFetcherConfig xiaomiMijiaDataFetcher;

    public String getDataFetchInterval() {
        return dataFetchInterval;
    }

    public void setDataFetchInterval(String dataFetchInterval) {
        this.dataFetchInterval = dataFetchInterval;
    }

    public XiaomiMijiaDataFetcherConfig getXiaomiMijiaDataFetcher() {
        return xiaomiMijiaDataFetcher;
    }

    public void setXiaomiMijiaDataFetcher(XiaomiMijiaDataFetcherConfig xiaomiMijiaDataFetcher) {
        this.xiaomiMijiaDataFetcher = xiaomiMijiaDataFetcher;
    }

    public static class XiaomiMijiaDataFetcherConfig {
        private String path;
        private boolean traceOutput;
        @DurationUnit(ChronoUnit.SECONDS)
        private Duration timeout;

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public boolean isTraceOutput() {
            return traceOutput;
        }

        public void setTraceOutput(boolean traceOutput) {
            this.traceOutput = traceOutput;
        }

        public Duration getTimeout() {
            return timeout;
        }

        public void setTimeout(Duration timeout) {
            this.timeout = timeout;
        }
    }
}
