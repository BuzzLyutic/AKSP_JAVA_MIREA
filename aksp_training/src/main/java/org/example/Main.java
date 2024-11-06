package org.example;

public class Main {
    public static void main(String[] args) {
        PingPongPrinter printer = new PingPongPrinter();
        try {
            printer.print();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        /*waitNotifyPrinter printer = new waitNotifyPrinter();
        pingThread pingThread = new pingThread(printer);
        pongThread pongThread = new pongThread(printer);
        pingThread.start();
        pongThread.start();*/
    }
}