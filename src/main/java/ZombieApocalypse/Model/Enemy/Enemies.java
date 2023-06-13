package ZombieApocalypse.Model.Enemy;

import ZombieApocalypse.Loop.TimeLoop;
import ZombieApocalypse.Model.Game;
import ZombieApocalypse.Model.Items.Items;
import ZombieApocalypse.Utility.*;
import ZombieApocalypse.View.GameFrame;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Enemies {

    public void addSkinnyZombie(int x, int y) {
        this.enemies.add(new SkinnyZombie(x, y));
    }


    //Controllo della collisione con il player
    public boolean checkCollision(int x, int y, int ceX, int ceY) {
        Point player=new Point(x+ ceX,y+ceY);
        Point enem=new Point();
        Iterator var1=this.enemies.iterator();
        while(var1.hasNext()){
            Enemy b=(Enemy) var1.next();
            enem.x=b.getX()+ b.centerX;
            enem.y=b.getY()+b.centerY;
            switch (b.type){
                case BOSS -> {if(player.distance(enem)<80) return false;}
                case BANDIT, BOMBBANDIT, SKINNYZOMBIE, TURRETZOMBIE -> {if(player.distance(enem)<16) return false;}
                case FATZOMBIE -> {if(player.distance(enem)<24) return false;}
                case KIDZOMBIE -> {if(player.distance(enem)<13) return false;}
            }
        }return true;
    }
    //Controllo della collisione con l'esplosione della granata (che è di forma rotonda)
    public void checkCollisionHit(int x, int y, int ceX, int ceY, int damage) {
        Point p=new Point(x+ ceX,y+ceY);
        Point enem=new Point();
        for (Enemy b : this.enemies) {
            enem.x = b.getX() + b.getCenterX();
            enem.y = b.getY() + b.getCenterY();
            switch (b.type) {
                case BOSS -> {
                    if (p.distance(enem) < 120){ b.gettingHit(damage); hitSound(b);}
                }
                case BANDIT, BOMBBANDIT, SKINNYZOMBIE, TURRETZOMBIE -> {
                    if (p.distance(enem) < 30){ b.gettingHit(damage); hitSound(b);}
                }
                case FATZOMBIE -> {
                    if (p.distance(enem) < 45){ b.gettingHit(damage); hitSound(b);}
                }
                case KIDZOMBIE -> {
                    if (p.distance(enem) < 15){ b.gettingHit(damage); hitSound(b);}
                }
            }

        }
        }

    //Controllo della collisione con l'hitbox del coltello

    public void checkHitBox(Rectangle hitBox, int damage) {
        for (Enemy b : this.enemies) {
            if (b.hitBox.intersects(hitBox)) {
                hitSound(b);
                b.gettingHit(damage);
            }
        }
    }
    //Controllo della collisione con i proiettili (deve restituire booleana per eliminare proiettili)
    public boolean checkBulletHitBox(Rectangle hitBox, int damage) {
        for (Enemy b : this.enemies) {
            if (b.hitBox.intersects(hitBox)) {
                hitSound(b);
                b.gettingHit(damage);
                return true;
            }
        }
        return false;
    }

    public void addFatZombie(int x, int y) {
        this.enemies.add(new FatZombie(x, y));
    }

    public void addKidZombie(int x, int y) {
        this.enemies.add(new KidZombie(x, y));
    }
    public void addTurretZombie(int x,int y){this.enemies.add(new TurretZombie(x, y));}
    public void addBandit(int x,int y){this.enemies.add(new Bandit(x, y));}
    public void addBombBandit(int x,int y){this.enemies.add(new BombBandit(x, y));}
    public void addBoss(int x,int y){this.enemies.add(new Boss(x, y));}



    public boolean checkBulletHitBoxPlayer(Rectangle hitBox, int damage) {
        Rectangle hit=Game.getInstance().getPlayerCharacter().hitBox;
        if(hitBox.intersects(hit)){
            Game.getInstance().getPlayerCharacter().hit();
            return true;
        } return false;

    }
    Random m=new Random();
    public int enemyNumber=0;

    public void generateRandomEnemies() {
        switch (Settings.diff){
            case EASY -> enemyNumber=m.nextInt(5,15);
            case MEDIUM -> enemyNumber= m.nextInt(16,25);
            case HARD ->  enemyNumber= m.nextInt(26,35);
        }
        int x,y;
        int c=0;
        int t;
        boolean finalLevel= Settings.campainMapIndex ==Settings.campainMaps ;

        while (c<enemyNumber ){
            t=m.nextInt(0, EnemiesType.values().length-2);

            x=m.nextInt(0, Settings.WINDOW_SIZEX);
            y=m.nextInt(0, Settings.WINDOW_SIZEY);
            if(checkSpawn(x,y , EnemiesType.values()[t])){
                c++;
                switch (t){
                    case 0-> Enemies.getInstance().addSkinnyZombie(x,y);
                    case 1-> Enemies.getInstance().addFatZombie(x,y);
                    case 2-> Enemies.getInstance().addKidZombie(x,y);
                    case 3->Enemies.getInstance().addTurretZombie(x,y);
                    case 4-> Enemies.getInstance().addBandit(x,y);
                    case 5->Enemies.getInstance().addBombBandit(x,y);
                }
            }

        } while (finalLevel){
            x=m.nextInt(0, Settings.WINDOW_SIZEX);
            y=m.nextInt(0, Settings.WINDOW_SIZEY);
            if(checkSpawn(x, y, EnemiesType.BOSS)){
                Enemies.getInstance().addBoss(x,y);
                finalLevel=false;
            }

        }
    }

    private boolean checkSpawn(int x, int y, EnemiesType enem) {
        boolean distanzaDalPlayer=Game.getInstance().getWorld().isSpawnable(x+(getWight(enem)/2),y+(getHeight(enem)/2));
        if(distanzaDalPlayer){
            for( int i=x; i<getWight(enem)+x; i++){
                for(int j=y; j<getHeight(enem)+y; j++){
                    if(!Game.getInstance().getWorld().isWalkable(i,j))
                        return false;
                }
            } return true;

        }else
            return false;
        }

    public int getWight(EnemiesType type) {
        switch (type){
            case FATZOMBIE -> {return Settings.CELL_SIZEX+(Settings.CELL_SIZEX/2);}
            case BANDIT, BOMBBANDIT, SKINNYZOMBIE, TURRETZOMBIE -> {return Settings.CELL_SIZEX;}
            case BOSS -> {return Settings.CELL_SIZEX*4;}
            case KIDZOMBIE -> {return (Settings.CELL_SIZEX/2)+10;}
        }
        return 0;
    }

    public int getHeight(EnemiesType type) {
        switch (type){
            case FATZOMBIE -> {return Settings.CELL_SIZEY+(Settings.CELL_SIZEY/2);}
            case BANDIT, BOMBBANDIT, SKINNYZOMBIE, TURRETZOMBIE -> {return Settings.CELL_SIZEY;}
            case BOSS -> {return Settings.CELL_SIZEY*4;}
            case KIDZOMBIE -> {return (Settings.CELL_SIZEY/2)+10;}
        }
        return 0;
    }


    public enum EnemiesType{SKINNYZOMBIE, FATZOMBIE, KIDZOMBIE,TURRETZOMBIE,BANDIT,BOMBBANDIT, BOSS,EMPTY};
    private final ConcurrentLinkedDeque<Enemy> enemies=new ConcurrentLinkedDeque<>();
    private static final ZombieApocalypse.Model.Enemy.Enemies instance=new ZombieApocalypse.Model.Enemy.Enemies();

    public Enemies(){}

    public static ZombieApocalypse.Model.Enemy.Enemies getInstance(){return instance;}


    public  ConcurrentLinkedDeque<Enemy> getEnemies(){return this.enemies;
    }

    public void update(){
        Iterator<Enemy> e=enemies.iterator();
        while (e.hasNext()){
            Enemy b=e.next();
            if(!b.update()){
                e.remove();
                enemyNumber--;
                if(enemyNumber == 0 && !Game.getInstance().getBackMenu()){
                    Game.getInstance().setPause(true);
                    if(!Settings.isEditor){
                        if(Settings.nextCampaignMap())
                            ResultsPanel.getInstance().showContinue();
                        else
                            ResultsPanel.getInstance().showFinal();
                    }
                    else
                        ResultsPanel.getInstance().showFinal();
                }
            }
            if(b.type==EnemiesType.BANDIT )
                b.updateGunPosition();
        }
    }


    private void hitSound(Enemy b) {
        if(b.type.equals(EnemiesType.FATZOMBIE) || b.type.equals(EnemiesType.TURRETZOMBIE) || b.type.equals(EnemiesType.BOSS) || b.type.equals(EnemiesType.KIDZOMBIE) || b.type.equals(EnemiesType.SKINNYZOMBIE)){
            if(GameData.sound)
                PlayWav.getInstance().playZombieHit();
        }
        else if(b.type.equals(EnemiesType.BANDIT)) {
            if (GameData.sound)
                PlayWav.getInstance().playHurt2Sound();
        }
        else if(b.type.equals(EnemiesType.BOMBBANDIT)){
            if (GameData.sound)
                PlayWav.getInstance().playHurt3Sound();
        }
    }
}
