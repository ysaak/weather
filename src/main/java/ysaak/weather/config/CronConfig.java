package ysaak.weather.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import ysaak.weather.service.DataFetcherService;

@Configuration
@EnableScheduling
public class CronConfig implements SchedulingConfigurer {
    private final DataFetcherService dataFetcherService;

    private final WeatherAppConfig weatherAppConfig;

    @Bean
    public TaskScheduler taskScheduler() {
        return new ConcurrentTaskScheduler();
    }

    @Autowired
    public CronConfig(DataFetcherService dataFetcherService, WeatherAppConfig weatherAppConfig) {
        this.dataFetcherService = dataFetcherService;
        this.weatherAppConfig = weatherAppConfig;
    }

    @Override
    public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
        scheduledTaskRegistrar.addCronTask(dataFetcherService::fetchDataForAllSensors, weatherAppConfig.getDataFetchInterval());
    }
}
