package org.example;

import org.example.server.MyServer;
import org.example.server.Server;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    static ExecutorService thread = Executors.newCachedThreadPool();

    public static void main(String[] args) {
        Server server = new MyServer(3000);
        thread.submit(server::start);

    }
}