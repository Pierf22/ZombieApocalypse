package ZombieApocalypse.Model.Enemy;

import ZombieApocalypse.Model.Game;
import ZombieApocalypse.Model.Items.Items;
import ZombieApocalypse.Utility.GameData;
import ZombieApocalypse.Utility.PlayWav;
import ZombieApocalypse.Utility.Settings;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

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
                case BOSS -> {if(player.distance(enem)<64) return false;}
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
        switch (Game.getInstance().getDifficulty()){
            case EASY -> enemyNumber=m.nextInt(5,7);
            case MEDIUM -> enemyNumber= m.nextInt(7,15);
            case HARD ->  enemyNumber= m.nextInt(15,25);
        }
        int x,y;
        int c=0;
        int t;

        while (c<enemyNumber ){
            t=m.nextInt(0, EnemiesType.values().length-2);

            x=m.nextInt(0, Settings.WINDOW_SIZEX);
            y=m.nextInt(0, Settings.WINDOW_SIZEY);
            if( Game.getInstance().getWorld().isSpawnable(x,y) && Game.getInstance().getWorld().isWalkable(x,y)){
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


        }
    }


    public enum EnemiesType{SKINNYZOMBIE, FATZOMBIE, KIDZOMBIE,TURRETZOMBIE,BANDIT,BOMBBANDIT, BOSS,EMPTY};
    private final List<Enemy> enemies=new ArrayList<>();
    private static final ZombieApocalypse.Model.Enemy.Enemies instance=new ZombieApocalypse.Model.Enemy.Enemies();

    public Enemies(){}

    public static ZombieApocalypse.Model.Enemy.Enemies getInstance(){return instance;}




    public  List<Enemy> getEnemies(){return this.enemies;
    }

    public void update(){
        Iterator<Enemy> e=enemies.iterator();
        while (e.hasNext()){
            Enemy b=e.next();
            if(!b.update()){
                enemyNumber--;
                e.remove();
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
        else if(b.type.equals(EnemiesType.BANDIT) || b.type.equals(EnemiesType.BOMBBANDIT)) {
            if (GameData.sound)
                PlayWav.getInstance().playHurtSound();
        }
    }
}
