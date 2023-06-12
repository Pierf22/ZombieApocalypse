package ZombieApocalypse.Model.Items;

import ZombieApocalypse.Model.Game;
import ZombieApocalypse.View.ItemView;

import java.awt.*;

public  class Item {
    int x;
    int y;
    int wight, height;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWight() {
        return wight;
    }
    public int getHeight(){return height;}
   ItemView view;
    Items.ItemType type;
    Rectangle hitBox;
    public ItemView getView(){
        return view;
    }
public boolean taken=true;
    public Item(int x, int y, Items.ItemType e){
        this.wight=Items.getInstance().getWight(e);
        this.height=Items.getInstance().getHeight(e);

        this.x=x;
        this.y=y;

        this.view=new ItemView(e, wight, height);
        this.type=e;
        this.hitBox=new Rectangle(x,y,wight,height);
        taken=true;
    }

    public boolean update() {
        if(hitBox.intersects(Game.getInstance().getPlayerCharacter().hitBox) && taken){
             if(Game.getInstance().getMenuBar().collect() && (type!= Items.ItemType.AMMO0 && type!= Items.ItemType.AMMO1)){

                    Game.getInstance().getMenuBar().addItem(type);
                 getView().setTaken(true);
                 taken=false;

        }
         if (Game.getInstance().getMenuBar().collectAmmo(type) && (type== Items.ItemType.AMMO0 || type== Items.ItemType.AMMO1)) {
                Game.getInstance().getMenuBar().addAmmo(type);
             getView().setTaken(true);
             taken=false;
            }

        }

        if(Game.getInstance().getBackMenu()){
            getView().setTaken(true);
            taken=false;
        }

        return taken;
}}
