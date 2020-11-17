/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.htlgrieskirchen.pos3.pcp;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Producer implements Runnable {

    private final String name;
    private final Storage storage;
    private final int sleepTime;

    private final List<Integer> sent;
    private final int numberOfItems;

    public Producer(String name, Storage storage, int sleepTime, int numberOfItems) {
        this.name = name;
        this.storage = storage;
        this.sleepTime = sleepTime;
        this.numberOfItems = numberOfItems;
        sent = new ArrayList<>();
        // implement this
    }

    // implement this
    public List<Integer> getSent() {
        // implement this
        return this.sent;
    }

    @Override
    public void run() {
        for (int i = 0; i < this.numberOfItems; i++) {
            /*while (!sent.contains(i)) {
                if (storage.put(i)) {
                    sent.add(i);
                } else {
                    try {
                        Thread.sleep(this.sleepTime);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Producer.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

            }*/
            boolean success;
            do {
                success = storage.put(i);
                if (success) {
                    sent.add(i);
                } else {
                    try {
                        Thread.sleep(this.sleepTime);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Producer.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

            } while (!sent.contains(i));

        }
        storage.setProductionComplete();
    }

}
