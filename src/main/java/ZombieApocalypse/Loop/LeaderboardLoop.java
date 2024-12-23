package ZombieApocalypse.Loop;

import ZombieApocalypse.Utility.ResultsPanel;
import ZombieApocalypse.View.MenuView;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class LeaderboardLoop {
    MenuView view;
    private ScheduledExecutorService executor;
    public LeaderboardLoop(MenuView view){
        this.view = view;
    }

    public void start(){
        //Game loop con un thread che agisce ogni minuto per aggiornare la classifica
        if (executor != null)
            return;
        executor = Executors.newSingleThreadScheduledExecutor();
        executor.scheduleAtFixedRate(() -> {
                    try { view.updateLeaderboard(); } catch (MalformedURLException e) { throw new RuntimeException(e); } catch (IOException e) { ResultsPanel.getInstance().showConnectionError(); }
                },
                0, 1, TimeUnit.MINUTES);
    }

    public void stop(){
        executor.shutdown();
    }
}
