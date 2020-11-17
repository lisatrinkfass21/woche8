/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.htlgrieskirchen.pos3.pcp;

import java.util.concurrent.ArrayBlockingQueue;

public class Storage {

    private final ArrayBlockingQueue<Integer> queue;

    private int fetchedCounter;
    private int storedCounter;
    private int underflowCounter;
    private int overflowCounter;
    private boolean productionComplete;

    public Storage() {
        this.storedCounter = 0;
        this.overflowCounter = 0;
        this.underflowCounter = 0;
        this.productionComplete = false;
        this.fetchedCounter = 0;

        this.queue = new ArrayBlockingQueue<>(10);
        // implement this
    }

    public synchronized boolean put(Integer data) {
        boolean success = queue.offer(data);
        if (success) {
            this.storedCounter++;
            return true;
        }
        this.overflowCounter++;
        return false;
    }

    public synchronized Integer get() {
        if (queue.isEmpty()) {
            this.underflowCounter++;
            return null;
        }
        this.fetchedCounter++;
        return queue.poll();
    }

    public boolean isProductionComplete() {
        // implement this
        return this.productionComplete;
    }

    public synchronized void setProductionComplete() {
        this.productionComplete = true;
        // implement this
    }

    public int getFetchedCounter() {
        // implement this
        return this.fetchedCounter;
    }

    public int getStoredCounter() {
        // implement this
        return this.storedCounter;
    }

    public int getUnderflowCounter() {
        // implement this
        return this.underflowCounter;
    }

    public int getOverflowCounter() {
        // implement this
        return this.overflowCounter;
    }
}
