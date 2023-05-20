package ZombieApocalypse.LoginMenu;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class LoginLoop {
    LoginPanel controller;
    private ScheduledExecutorService executor;
    public LoginLoop(LoginPanel controller){
        this.controller = controller;
    }

    public void start(){
        //Game loop con un thread che agisce ogni 60 millisecondi chiamando la funzione updatePosition che serve per l'animazione del titolo
        if (executor != null)
            return;
        executor = Executors.newSingleThreadScheduledExecutor();
        executor.scheduleAtFixedRate(() -> controller.updatePosition(),
                0, 60, TimeUnit.MILLISECONDS);
    }

    public void stop(){
        executor.shutdown();
    }
}