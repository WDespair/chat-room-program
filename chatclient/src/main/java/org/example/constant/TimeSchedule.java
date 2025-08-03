package org.example.constant;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class TimeSchedule {
    public static ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(4);
}
