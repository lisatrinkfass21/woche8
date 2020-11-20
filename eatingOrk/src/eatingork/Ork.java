/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eatingork;

import static java.lang.Thread.sleep;
import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Lisa
 */
public class Ork extends Thread {

    private final int orkID;
    private final Semaphore dagger;
    private final Semaphore neighborDagger;
    private final Random rand = new Random();
    private int meals = 1;// jeder ork muss 5 Meals essen

    public Ork(int orkID, Semaphore dagger, Semaphore neighborDabber) {
        this.orkID = orkID;
        this.dagger = dagger;
        this.neighborDagger = neighborDabber;
    }

    private void drinking() {
        try {
            System.out.println(orkID + ": trint");
            sleep(rand.nextInt(1000)); // warted für unbestimmte Zeit

        } catch (InterruptedException ex) {
            System.out.println(orkID + ": wurde beim drinken(warten) unterbrochen");
            // Logger.getLogger(Ork.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void trytoeat() {
        try {
            dagger.acquire(); // warted auf sein eigenes dagger fals notwendig
            //versucht in den kritischen Bereich einzutretten -> wenn belegt wartet -> minimiert die Erlaubnisse auf 0 -> kein anderer kann dolch benutzen

            if (!neighborDagger.tryAcquire()) {//wen der Dolch vom Nachbar besetzt ist
                System.out.println(this.orkID + ": konnte den Dolch vom Nachbarn nicht bekommen");
                return;
            }

            System.out.println(this.orkID + ": isst");
            sleep(rand.nextInt(1000));
            this.neighborDagger.release(); // gibt den Dolch vom Nachbarn frei
            meals--;

        } catch (InterruptedException ex) {
            Logger.getLogger(Ork.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            dagger.release();//gibt seinen eigenen Dolch frei
        }

    }

    @Override
    public void run() {

        while (meals > 0) {
            drinking();// wartet eine gewisse Zeit
            trytoeat();
        }
        System.out.println(this.orkID + ": hat fertig gegessen");
        System.out.println(System.currentTimeMillis());
    }

}
