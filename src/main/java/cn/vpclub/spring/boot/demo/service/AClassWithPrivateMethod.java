package cn.vpclub.spring.boot.demo.service;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * AClassWithPrivateMethod
 * Created by johnd on 07/01/2017.
 */
@Slf4j
public class AClassWithPrivateMethod {

    private final boolean noStatsAvailable = true;

    /**
     * A public method
     *
     * @throws InterruptedException
     */
    public String calculateStats() throws InterruptedException {

        if (noStatsAvailable) {
            crunchNumbers();
        }

        return getStatsFromCache();
    }

    /**
     * Calculate some statistic taking a long time.
     */
    private boolean crunchNumbers() throws InterruptedException {
        log.info("start time consuming method");
        TimeUnit.SECONDS.sleep(60);
        log.info("end time consuming method");
        return true;
    }

    private String getStatsFromCache() {
        log.info("get stats from cache");
        return "100%";
    }
}

