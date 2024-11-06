package org.example;

import java.util.concurrent.locks.ReentrantLock;

public class PingPongPrinter {
    private final String[] messages = {"Ping", "Pong"};
    private int index;
    private final ReentrantLock lock = new ReentrantLock();

    public void print() throws InterruptedException {
        Thread t1 = new Thread(this::printThread);
        Thread t2 = new Thread(this::printThread);
        t1.start();
        t2.start();
        t1.join();
        t2.join();
    }

    private void printThread() {
        while (true) {
            try {
                lock.lockInterruptibly();
                if (index == 0) {
                    System.out.print(messages[0]);
                    index++;
                } else {
                    System.out.print(messages[1]);
                    index--;
                }
                System.out.print(" ");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            } finally {
                lock.unlock();
            }
        }
    }
}