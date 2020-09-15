package ysaak.weather.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

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
    }
}
