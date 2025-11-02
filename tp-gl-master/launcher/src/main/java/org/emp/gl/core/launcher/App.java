package org.emp.gl.core.launcher;

import org.emp.gl.clients.CompteARebours;
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
    
    // Instancier plusieurs horloges
    Horloge horloge1 = new Horloge("Num 1", timerService);
    Horloge horloge2 = new Horloge("Num 2", timerService);
    
    // Tester CompteARebours avec valeur 5
    CompteARebours compte1 = new CompteARebours("Compte1", timerService, 5);
    
    // Instancier 10 CompteARebours avec valeurs aléatoires
    java.util.Random random = new java.util.Random();
    for (int i = 0; i < 2; i++) {
        int valeurInitiale = 10 + random.nextInt(11); // 10-20
        new CompteARebours("Compte-" + (i+2), timerService, valeurInitiale);
    }
    
    // Garder l'application en vie
    try {
        Thread.sleep(30000); // Attendre 30 secondes
    } catch (InterruptedException e) {
        e.printStackTrace();
    }
}

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}