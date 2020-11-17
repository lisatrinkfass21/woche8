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

public class Consumer implements Runnable {

    private final String name;
    private final Storage storage;
    private final int sleepTime;

    private final List<Integer> received;
    private boolean running;

    public Consumer(String name, Storage storage, int sleepTime) {
        this.name = name;
        this.storage = storage;
        this.sleepTime = sleepTime;
        this.received = new ArrayList<>();
        this.running = true;
        // implement this
    }

    // implement this
    public List<Integer> getReceived() {
        // implement this
        return this.received;
    }

    @Override
    public void run() {
        Integer num;
        while (running) {
            try {
                num = storage.get();
                running = !storage.isProductionComplete() || num != null;
                if (num != null) {
                    this.received.add(num);
                }
                Thread.sleep(sleepTime);
            } catch (InterruptedException ex) {
                Logger.getLogger(Consumer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }
}
