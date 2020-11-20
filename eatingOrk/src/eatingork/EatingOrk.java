/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eatingork;

import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Lisa
 */
public class EatingOrk {

    public static void main(String[] args) {
        final int anzahl = 5; // 5 Dolche + 5 Orks

        Semaphore[] daggers = new Semaphore[anzahl]; // Semaphore: Datenstruktur aus einer Ganzzahl und Nutzungorperatoren "Besetzen" und "Freigeben"
        for (int i = 0; i < anzahl; i++) {
            daggers[i] = new Semaphore(1, true); //nur 1 Ork(Thread) darf auf den kritischen Abschnitt //eine faire Vergabe -> jeder Thread kommt gleich oft dran
        }

        Ork[] orks = new Ork[anzahl];
        for (int i = 0; i < anzahl; i++) {
            int neighbor = i + 1;
            if (neighbor == anzahl) {
                neighbor = 0;
            }
            orks[i] = new Ork(i, daggers[i], daggers[neighbor]); //neuer Ork wird erstellt mit benötigten Besteck

        }

        for (int i = 0; i < anzahl; i++) {
            orks[i].start();
        }

        for (int i = 0; i < anzahl; i++) {
            try {
                orks[i].join();
            } catch (InterruptedException ex) {
                Logger.getLogger(EatingOrk.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
