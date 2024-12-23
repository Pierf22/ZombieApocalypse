package ZombieApocalypse.Loop;

import ZombieApocalypse.Controller.PlayerController;
import ZombieApocalypse.Utility.ResultsPanel;

import java.util.concurrent.*;

public class GameLoop {
    private final PlayerController controller;
    private  ScheduledExecutorService executor;
    public GameLoop(PlayerController controller) {
        this.controller = controller;
    }
    public void start() {
        if(executor==null)
            executor=Executors.newSingleThreadScheduledExecutor();
        try{
        this.executor.scheduleAtFixedRate(this.controller::update,
                    0L, 60L, TimeUnit.MILLISECONDS);
        }catch (RejectedExecutionException | NullPointerException| IllegalArgumentException e){
            ResultsPanel.getInstance().showError("Errore nell'aggiornamento del GameLoop", 81, e);
        }
    }
    public void stop(){executor.shutdown();}
}