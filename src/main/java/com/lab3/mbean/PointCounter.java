package com.lab3.mbean;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.ejb.Singleton;

import javax.management.MBeanServer;
import javax.management.Notification;
import javax.management.NotificationBroadcasterSupport;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;
import java.math.BigDecimal;

@Singleton
public class PointCounter extends NotificationBroadcasterSupport implements PointCounterMBean {

    private int totalPoints = 0;
    private int hitPoints = 0;
    private long sequenceNumber = 0;

    @PostConstruct
    public void register() {
        try {
            MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
            ObjectName name = new ObjectName("com.lab3.mbean:type=PointCounter");
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
            ObjectName name = new ObjectName("com.lab3.mbean:type=PointCounter");
            if (mbs.isRegistered(name)) {
                mbs.unregisterMBean(name);
            }
        } catch (Exception ignored) {
        }
    }

    @Override
    public int getTotalPoints() {
        return totalPoints;
    }

    @Override
    public int getHitPoints() {
        return hitPoints;
    }

    @Override
    public void reset() {
        totalPoints = 0;
        hitPoints = 0;
    }

    @Override
    public void addPoint(boolean hit) {
        totalPoints++;
        if (hit) {
            hitPoints++;
        }
    }

    @Override
    public void checkAndNotifyOutOfBounds(BigDecimal x, BigDecimal y, BigDecimal r) {
        if (x.abs().compareTo(r) > 0 || y.abs().compareTo(r) > 0) {
            Notification notification = new Notification(
                    "point.outOfBounds",
                    this,
                    sequenceNumber++,
                    System.currentTimeMillis(),
                    "Точка вышла за пределы отображаемой области координатной плоскости: " +
                            "x=" + x + ", y=" + y + ", r=" + r
            );
            sendNotification(notification);
        }
    }
}
