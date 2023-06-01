package ZombieApocalypse.View.Gun;

import ZombieApocalypse.Model.Character;
import ZombieApocalypse.Model.Game;
import ZombieApocalypse.Model.Guns.Bullet;
import ZombieApocalypse.Model.Guns.Bullets;
import ZombieApocalypse.Utility.ResourcesLoader;

import java.awt.*;

public class BulletView {

    public Image currentImage;
    public Image [] bullet=new Image[6];
    public Image [] grenadeBullet=new Image[7];
    public Image [] grenade=new Image[4];
    int count=0;
    boolean ending=false;
    boolean isGrenade=false;

    public BulletView( int dimension, boolean b) {
        isGrenade=b;
        if(isGrenade){
            for(int i=0; i<6; i++){
                grenadeBullet[i]= ResourcesLoader.getInstance().getImage("/ArmieOggetti/Esplosione"+i+".png",dimension ,dimension,true);
            }
            for(int i=0; i<4; i++){
                grenade[i]= ResourcesLoader.getInstance().getImage("/ArmieOggetti/Granata"+i+".png", Game.getInstance().getGrenadeModel().getWidth(),Game.getInstance().getGrenadeModel().getHeight(),true);
            }
        }else {
            for (int i = 0; i < 7; i++)
                bullet[i] = ResourcesLoader.getInstance().getImage("/ArmieOggetti/ProiettilePistola" + i + ".png", dimension+(i*10), dimension+(i*10), true);
        }
    }
    public void isEnding(){
        count++;
        ending=true;
    }
    public boolean menu=false;

    public void update() {


        if(isGrenade){
            if(menu){
                currentImage=grenadeBullet[6];
                count=23;
                ending=true;
            return;}
            if(!ending){
                currentImage=grenade[0];

            }else{
                if(count<10 )
                    currentImage=grenade[0];
                if(count>=10 &&count<12)
                    currentImage=grenadeBullet[0];
                if(count>=12 && count<14)
                    currentImage=grenadeBullet[1];
                if(count>=14 && count<16)
                    currentImage=grenadeBullet[2];
                if(count>16 && count<18)
                    currentImage=grenadeBullet[3];
                if(count>18 && count<20)
                    currentImage=grenadeBullet[4];
                if(count>20 && count<22)
                    currentImage=grenadeBullet[5];
                if(count>22)
                    currentImage=grenadeBullet[6];

            }
        }else{
            if(!ending) {
                if (count<2)
                    currentImage = bullet[0];
                else if (count == 2)
                    currentImage = bullet[1];
                else
                    currentImage = bullet[2];
            }else{
                if(count==0)
                    currentImage=bullet[3];
                else if(count==1)
                    currentImage=bullet[4];
                else if(count==2)
                    currentImage=bullet[5];
            } count++;}


    }
    public Image getCurrentImage() {
        return currentImage;
    }

}
