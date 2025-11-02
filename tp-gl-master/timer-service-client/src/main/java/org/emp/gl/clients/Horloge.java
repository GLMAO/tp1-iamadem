package org.emp.gl.clients;

import org.emp.gl.timer.service.TimerService;
import org.emp.gl.timer.service.TimerChangeListener;

public class Horloge implements TimerChangeListener {
    String name;
    TimerService timerService;

    public Horloge(String name, TimerService timerService) {
        this.name = name;
        this.timerService = timerService;
        
        // S'inscrire comme observer
        this.timerService.addTimeChangeListener(this);
        
        System.out.println("Horloge " + name + " initialized!");
    }

    public void afficherHeure() {
        if (timerService != null)
            System.out.println(name + " affiche " + 
                                timerService.getHeures() + ":" +
                                timerService.getMinutes() + ":" +
                                timerService.getSecondes());
    }

    @Override
    public void propertyChange(String prop, Object oldValue, Object newValue) {
        // Afficher l'heure seulement quand la seconde change
        if (TimerChangeListener.SECONDE_PROP.equals(prop)) {
            afficherHeure();
        }
    }
}