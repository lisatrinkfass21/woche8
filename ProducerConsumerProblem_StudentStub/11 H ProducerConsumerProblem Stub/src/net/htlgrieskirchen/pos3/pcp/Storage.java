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
        this.underflowCounter = 1;
        this.productionComplete = false;
        this.fetchedCounter = 0;

        this.queue = new ArrayBlockingQueue<>(10);
        // implement this
    }

    public synchronized boolean put(Integer data) throws InterruptedException {
        if (queue.size() < 10) {
            queue.put(data);
            this.storedCounter += 1;
            return true;
        }
        this.overflowCounter += 1;
        return false;
    }

    public synchronized Integer get() {
        if (queue.isEmpty()) {
            this.underflowCounter += 1;
            return null;
        }
        Integer tmp = queue.poll();
        this.fetchedCounter += 1;
        // implement this
        return tmp;
    }

    public boolean isProductionComplete() {
        // implement this
        return this.productionComplete;
    }

    public void setProductionComplete() {
        this.productionComplete = true;
        // implement this
    }

    public int getFetchedCounter() {
        // implement this
        return this.storedCounter;
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
