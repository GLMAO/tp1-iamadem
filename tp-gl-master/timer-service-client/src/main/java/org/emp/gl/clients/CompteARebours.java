package org.emp.gl.clients;

import org.emp.gl.timer.service.TimerService;
import org.emp.gl.timer.service.TimerChangeListener;

public class CompteARebours implements TimerChangeListener {
    private String name;
    private TimerService timerService;
    private int compteur;
    private boolean actif = true;

    public CompteARebours(String name, TimerService timerService, int initialValue) {
        this.name = name;
        this.timerService = timerService;
        this.compteur = initialValue;
        
        // S'inscrire comme observer
        this.timerService.addTimeChangeListener(this);
        
        System.out.println("CompteARebours " + name + " initialisé avec " + initialValue);
    }

    @Override
    public void propertyChange(String prop, Object oldValue, Object newValue) {
        if (!actif) return;
        
        // Décrémenter à chaque seconde
        if (TimerChangeListener.SECONDE_PROP.equals(prop)) {
            compteur--;
            System.out.println(name + " : " + compteur);
            
            if (compteur <= 0) {
                System.out.println(name + " : Terminé!");
                // Se désinscrire
                timerService.removeTimeChangeListener(this);
                actif = false;
            }
        }
    }
}