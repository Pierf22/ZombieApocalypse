package ZombieApocalypse.View;

import ZombieApocalypse.Model.Game;
import ZombieApocalypse.Model.PlayerCharacter;
import ZombieApocalypse.Settings;

import java.awt.*;

public class CharacterView {
    //Gestisce la connessione fra Gameloop e Player

    private final CharacterAnimation runAnimationUp;
    private final CharacterAnimation runAnimationLeft;
    private final CharacterAnimation runAnimationDown;
    private final CharacterAnimation runAnimationRight;
    private final CharacterAnimation hitUp;
    private final CharacterAnimation hitDown;
    private final CharacterAnimation hitLeft;
    private final CharacterAnimation hitRight;

    final int width = Settings.CELL_SIZEX;
    final int height = Settings.CELL_SIZEY;

    private Image currentImage;

    public CharacterView() {
        runAnimationUp = new CharacterAnimation("PlayerIndietro",3);
        runAnimationDown = new CharacterAnimation("PlayerAvanti",3);
        runAnimationLeft = new CharacterAnimation("PlayerSinistra",3);
        runAnimationRight = new CharacterAnimation("PlayerDestra",3);
        hitUp=new CharacterAnimation("PlayerDannoAvanti",3);
        hitDown=new CharacterAnimation("PlayerDannoIndietro",3);
        hitLeft=new CharacterAnimation("PlayerDannoSinistra",3);
        hitRight=new CharacterAnimation("PlayerDannoDestra",3);
        currentImage = runAnimationDown.getDefaultImage();
    }

    public void update() {

        if(Game.getInstance().getPlayerCharacter().isMoving() && Game.getInstance().getPlayerDirection()== PlayerCharacter.movementDirection.UP)
            currentImage = runAnimationUp.update();
        else if(Game.getInstance().getPlayerCharacter().isMoving() && Game.getInstance().getPlayerDirection()== PlayerCharacter.movementDirection.DOWN)
            currentImage = runAnimationDown.update();
        else if(Game.getInstance().getPlayerCharacter().isMoving() && Game.getInstance().getPlayerDirection()== PlayerCharacter.movementDirection.LEFT)
            currentImage = runAnimationLeft.update();
        else if(Game.getInstance().getPlayerCharacter().isMoving() && Game.getInstance().getPlayerDirection()== PlayerCharacter.movementDirection.RIGHT)
            currentImage = runAnimationRight.update();
        else
            currentImage = runAnimationDown.getDefaultImage();
    }

    public Image getCurrentImage() {
        return currentImage;
    }
}
