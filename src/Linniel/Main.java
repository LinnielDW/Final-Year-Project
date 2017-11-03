package Linniel;

public class Main {

    public static void main(String[] args) {
        //initialised board and player objects
        Board b = new Board();
        Player p = new Player();

        //Used for manual play testing
        //b.initboard(8,8,3);
        //b.reveal(2,2);
        //p.play(b);


        //started program
        p.startUp(b);

    }
}
