package Linniel;

/**
 * Created by Linniel on 08/04/2017.
 */
public class ChangestoMake {
    private long id;
    private int succ;
    private int tries;


    public int getSucc() {
        return succ;
    }

    public int getTries() {
        return tries;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setSucc(int succ) {
        this.succ = succ;
    }

    public void setTries(int tries) {
        this.tries = tries;
    }

    public void incTries() {
        this.tries = this.tries+1;
    }

    public void incSucc() {
        this.succ = this.succ+1;
        this.tries = this.tries+1;
    }

    public ChangestoMake(){

    }

    public ChangestoMake(long id, int succ, int tries){
        this.id = id;
        this.succ = succ;
        this.tries = tries;
    }
}
