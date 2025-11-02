package org.emp.gl.core.launcher;

import org.emp.gl.clients.Horloge;
import org.emp.gl.time.service.impl.DummyTimeServiceImpl;
import org.emp.gl.timer.service.TimerService;

public class App {
    public static void main(String[] args) {
        testDuTimeService();
    }

    private static void testDuTimeService() {
        // Instancier le TimerService
        TimerService timerService = new DummyTimeServiceImpl();
        
        // Instancier plusieurs horloges avec injection de dépendance
        Horloge horloge1 = new Horloge("Num 1", timerService);
        Horloge horloge2 = new Horloge("Num 2", timerService);
        Horloge horloge3 = new Horloge("Num 3", timerService);
        
        Horloge horloge34 = new Horloge("Num 4", timerService);
        // Garder l'application en vie pour voir les résultats
        try {
            Thread.sleep(10000); // Attendre 10 secondes
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}