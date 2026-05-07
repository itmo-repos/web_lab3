package com.lab3.mbean;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.ejb.Singleton;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;

@Singleton
public class MissRatio implements MissRatioMBean {

    private int totalClicks = 0;
    private int misses = 0;

    @PostConstruct
    public void register() {
        try {
            MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
            ObjectName name = new ObjectName("com.lab3.mbean:type=MissRatio");
            if (!mbs.isRegistered(name)) {
                mbs.registerMBean(this, name);
            }
        } catch (Exception ignored) {
        }
    }

    @PreDestroy
    public void unregister() {
        try {
            MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
            ObjectName name = new ObjectName("com.lab3.mbean:type=MissRatio");
            if (mbs.isRegistered(name)) {
                mbs.unregisterMBean(name);
            }
        } catch (Exception ignored) {
        }
    }

    @Override
    public double getMissRatio() {
        if (totalClicks == 0) {
            return 0.0;
        }
        return (double) misses / totalClicks * 100.0;
    }

    @Override
    public int getTotalClicks() {
        return totalClicks;
    }

    @Override
    public int getMisses() {
        return misses;
    }

    @Override
    public void reset() {
        totalClicks = 0;
        misses = 0;
    }

    @Override
    public void addClick(boolean hit) {
        totalClicks++;
        if (!hit) {
            misses++;
        }
    }
}
