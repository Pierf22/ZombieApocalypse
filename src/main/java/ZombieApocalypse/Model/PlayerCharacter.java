package ZombieApocalypse.Model;

import ZombieApocalypse.Settings;
import ZombieApocalypse.View.Audio;
import javax.sound.sampled.*;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;


public class PlayerCharacter {
    //Gestisce il player e i suoi movimenti
    private int x = 20;
    private int y = 20;
    int money=0;
    int health=10;
    boolean movement = false;
    boolean hit=true;  //prova
    private final World world=new World();
    public enum movementDirection{RIGHT, LEFT, UP, DOWN};
    movementDirection dir;

    Audio audio = new Audio();

    // Metodo usato per musica in loop, riceve come argomento index da array Audio e lo passa a SetFile
    public void playMusic(int i) {

        audio.setFile(i);
        audio.play();
        audio.loop();
    }
    public void stopMusic() {
        audio.stop();
    }
    // Metodo usato per i sound effects
    public void playSE(int i) {
        audio.setFile(i);
        audio.play();
    }



    void startMovementUp() {
        movement = true;
        dir=movementDirection.UP;
        playSE(0);
    }
    void startMovementDown() {
        movement = true;
        dir=movementDirection.DOWN;
        playSE(0);
    }
    void startMovementRight() {
        movement = true;
        dir=movementDirection.RIGHT;
        playSE(0);
    }
    void startMovementLeft() {
        movement = true;
        dir=movementDirection.LEFT;
        playSE(0);
    }

    void stopMovement() {
        movement = false;
    }

    public boolean isMoving() {
        return movement;
    }
    public boolean getHit() {
        return hit;
    }


    public void move() {
        if(dir==movementDirection.RIGHT && world.isValidCoordinate(getX()+10, getY()))
            x += 10;
        else if(dir==movementDirection.LEFT && world.isValidCoordinate(getX()-10, getY()))
            x -= 10;
        else if(dir==movementDirection.UP && world.isValidCoordinate(getX(), getY()-10))
            y -= 10;
        else if(dir==movementDirection.DOWN && world.isValidCoordinate(getX(), getY()+10))
            y += 10;
        else
            movement=false;


    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}