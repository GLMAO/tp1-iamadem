package org.emp.gl.core.launcher;

import javax.swing.SwingUtilities;

import org.emp.gl.clients.CompteARebours;
import org.emp.gl.clients.Horloge;
import org.emp.gl.time.service.impl.DummyTimeServiceImpl;
import org.emp.gl.timer.service.TimerService;

public class App {
    public static void main(String[] args) {
        testDuTimeService();
    }

   private static void testDuTimeService() {
    TimerService timerService1 = new DummyTimeServiceImpl();
    TimerService timerService2 = new DummyTimeServiceImpl();

    // Interface graphique
    SwingUtilities.invokeLater(() -> {
        new HorlogeGUI(timerService1);
    });
      SwingUtilities.invokeLater(() -> {
        new HorlogeGUI(timerService2);
    });
    
    // Console
    Horloge horlogeConsole1 = new Horloge("Console", timerService1);
        Horloge horlogeConsole2 = new Horloge("Console", timerService2);

    // Garder l'application en vie
    try {
        Thread.sleep(60000);
    } catch (InterruptedException e) {
        e.printStackTrace();
    }
}

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}