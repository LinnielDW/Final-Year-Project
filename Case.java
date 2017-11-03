package Linniel;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public class Case implements Serializable {

    @Id
    @GeneratedValue
    private long id;
    private int[][] state;
    private int tries;
    private int success;
    private float chance;

    public Case(){
    }

    //Used as a class constructor
    public Case(int[][] state, int tries, int success){
        this.state = state;
        this.tries = tries;
        this.success = success;
        this.chance = (success * 100)/tries;
    }

    public void stateCaseDim(int y, int x){
        this.state = new int[y][x];
    }

    public long getId() {
        return id;
    }

    public int[][] getState() {
        return state;
    }

    public int getTries() {
        return tries;
    }

    public int getSuccess() {
        return success;
    }

    public float getChance() {
        return chance;
    }

    public void updatetriesandsuccess(int success, int tries) {
        this.success = this.success + success;
        this.tries = this.tries + tries;
        updatechance();
    }




    public void incSuccess() {
        tries = tries+1;
        success = success+1;
        updatechance();
    }



    public void incTries() {
        tries = tries+1;
        updatechance();
    }


    private void updatechance() {
        chance = (success * 100)/tries;
    }


    //Used to output case used.
    @Override
    public String toString() {
        String tempstring = new String();
        for(int i = 0;i<3;i++){
            tempstring = tempstring+"%n";
            for(int j = 0;j<3;j++){
                tempstring = tempstring + String.valueOf(this.state[i][j]) + " ";
            }
            if(i==2){
                tempstring = tempstring+"%n";
            }
        }
        tempstring=tempstring.replace(",", "");  //remove the commas
        tempstring=tempstring.replace("[", "");  //remove the right bracket
        tempstring=tempstring.replace("]", "");  //remove the left bracket
        tempstring=tempstring.trim();
        return String.format(tempstring);
    }
}