package Linniel;

import java.util.Scanner;

public class Player {
    Scanner userin = new Scanner(System.in);
    int revy, revx;
    private CBLearner l = new CBLearner();
    int[] revYX;


    //Manually Play Minesweeper
    public void play(Board b) {
        boolean alive = true;
        while(true) {
            if (alive) {
                System.out.println("Please choose a y to detect");
                revy = userin.nextInt();
                System.out.println("Please choose an x to detect");
                revx = userin.nextInt();
                alive = b.reveal(revy, revx);
            }
            else {
                //Death Script
                System.out.println("You Lose!\n");
                break;
            }
            //Win Script
            if (b.moves() == b.getminecount() && alive) {
                System.out.println("You Won!\n");
                break;
            }
        }
    }


    //Used to make the AI play 1 game of minesweeper
    public void playAI(Board b){
        boolean alive = true;






        while(true){ // FIND OUT WHY THERE IS AN INFINITE LOOP HERE DONE
            if(alive) {
                //System.out.println("llama");
                revYX = l.decide(b);
                //System.out.println(revYX[0]+", " + revYX[1]); USED TO SHOW WHAT CELL WAS SELECTED
                //System.out.println(Arrays.deepToString(l.getView(revYX[0]+1, revYX[1]+1)));
                //System.out.println(Arrays.deepToString(l.getView(revYX[0], revYX[1]))); USED TO CORRECTLY IDENTIFY IF THE SECTION OF BARD IN QUESTION WAS INDEED CORRECTLY BEING SELECTED
                alive = b.reveal(revYX[0], revYX[1]);

                l.tilesrevealed++;

                if(l.first == true && b.zeroFfirst == true){
                    l.flushfirst = true;
                    l.gamesflushfirst++;
                }

                l.first = false;

                if(l.foundmatch == true){
                    l.matchesfound++;

                }

                //b.renderboard(l.tempboard); USED TO TEST IF THE PREVIOUS BOARD IS BEING CORRECTLY RECORDED
                //System.out.println("cases to add db size " + l.casestoadd.size());
                if (alive ){
                    if (l.foundmatch == false && l.second == true) {
                        //System.out.println("Random clicked above\n");
                       // System.out.println(Arrays.deepToString(l.getView(revYX[0], revYX[1])));


                        for(int rot = 0; rot < 4; rot++) {
                            // CHECK IF THERE IS NO DUPLICATES done
                            if (l.duplicatecheck(l.getView(revYX[0], revYX[1], 4-rot)) == 0) {
                                //System.out.println(Arrays.deepToString(l.getView(revYX[0], revYX[1])));
                                //System.out.println("Saved: "+ Arrays.deepToString(l.getView(revYX[0], revYX[1])));
                                l.casestoadd.add(new Case(l.getView(revYX[0], revYX[1], 4-rot), 1, 1));

                                //System.out.println("Newest Added Learner.Linniel.Case"+Arrays.deepToString(l.casestoadd.get(l.casestoadd.size()-1).getState()));
                                //System.out.println(Arrays.deepToString(l.getView(revYX[0], revYX[1])));
                            }
                            else{
                                if (l.duplicatecheck(l.getView(revYX[0], revYX[1], 4-rot)) == 1) {
                                    l.incchanges(l.dupeid,true);

                                }
                                else{
                                    l.casestoadd.get((int) l.dupeid).incSuccess();
                                }
                            }
                        }
                    }
                    else{
                    //if found match == true and l.second == true add all 4 rotations anyway, gather that information here
                        if (l.second == true && l.foundmatch == true ) {

                            // If it did find a match and isn't dead, then increment by one, but how do you store it? DONE

                            //System.out.println("match found" );
                            if(!l.tobeadded){
                                l.incchanges(l.successCase.getId(),true);
                                //System.out.println("it reached this too" + l.successCase.getTries());
                            }
                            else{
                                l.casestoadd.get(l.index).incSuccess();
                                //System.out.println("to add: " + l.casestoadd.get(l.index).getTries());
                            }



                            //Win-learning
                            for(int rot = 0; rot < 4; rot++) {
                                if (l.duplicatecheck(l.getView(revYX[0], revYX[1], 4-rot)) == 0) {
                                    //System.out.println(Arrays.deepToString(l.getView(revYX[0], revYX[1])));
                                    //System.out.println("Saved: "+ Arrays.deepToString(l.getView(revYX[0], revYX[1])));
                                    l.casestoadd.add(new Case(l.getView(revYX[0], revYX[1], 4-rot), 1, 1));

                                    //System.out.println("Newest Added Learner.Linniel.Case"+Arrays.deepToString(l.casestoadd.get(l.casestoadd.size()-1).getState()));
                                    //System.out.println(Arrays.deepToString(l.getView(revYX[0], revYX[1])));
                                }
                            }
                        }
                    }
                }
                else{
                    if(l.foundmatch){ //DON'T JUST REMOVE THEM, INCREMENT THEIR FAILURE TO NOT LEARN IT AGAIN DONE
                        //System.out.println("Found match");
                        if(!l.tobeadded) {
                            /*
                            //REMOVE FROM CASEBASE
                            //System.out.println("Learner.Linniel.Case found to be bad in db: "+l.successCase.getId());
                            if(!l.idstoremove.contains(l.successCase.getId())) {
                                //System.out.println("Id removed");
                                l.idstoremove.add(l.successCase.getId());
                            }
                            l.casebase.remove(l.casebase.indexOf(l.successCase));
                            */


                            //Arrays.deepToString(l.successCase.getState());



                            //l.casestoadd.get(l.index).incTries();
                            //System.out.println("not to be added: "+l.casestoadd.get(l.index).getTries());

                            l.incchanges(l.successCase.getId(),false);
                            //System.out.println("it reached this"+ l.successCase.getTries());

                        }
                        else{
                            /*
                            // REMOVE FROM CASES TO BE ADDED
                            //System.out.println("case to be addded removed");
                            //System.out.println(l.casestoadd.indexOf(l.successCase));
                            l.casestoadd.remove(l.casestoadd.indexOf(l.successCase));
                            */


                            l.casestoadd.get(l.index).incTries();
                            //System.out.println("not to be added: "+l.casestoadd.get(l.index).getTries());
                            //l.incchanges(l.successCase.getId(),false);
                        }
                    }

                    // MOVE LOSE SCRIPT TO HERE maybe?
                }
            }
            else {
                //System.out.println("You Lose!\n");
                l.Save(l.save);
                break;

            }
            if (b.moves() == b.getminecount() && alive) {
                System.out.println("You Won!\n");
                b.renderboard(b.getBoard());
                l.gameswon++;
                if(l.flushfirst == true){
                    l.flushfirstwins++;
                }
                l.Save(l.save);
                break;
            }
        }
    }


    //Used to initiate the type of program you want. Includes instructions.
    public void startUp(Board b) {
        boolean cont = true;
        while (cont) {
            System.out.println("Hello there,\n" +
                    "Please input your game mode:\n" +
                    "b:\t\tManual Minesweeper Beginner.\n" +
                    "i:\t\tManual Minesweeper Intermediate.\n" +
                    "e:\t\tManual Minesweeper Expert.\n" +
                    "c:\t\tManual Minesweeper with custom Properties.\n" +
                    "a:\t\tCBR will play 1 game minesweeper.\n" +
                    "acont:\tContinuously have the AI minesweeper play.\n" +
                    "q:\t\tTo quit this program.\n");
            String command = userin.next();

            switch (command) {
                case "b":
                case "beginner":
                    b.initboard(8, 8, 10);
                    play(b);
                    break;
                case "i":
                    b.initboard(16, 16, 40);
                    play(b);
                    break;
                case "e":
                    b.initboard(30, 16, 99);
                    play(b);
                    break;
                case "c":
                    System.out.println("Please choose a height");
                    int height = userin.nextInt();
                    System.out.println("Please choose a width");
                    int width = userin.nextInt();
                    System.out.println("Please choose the amount of mines");
                    int count = userin.nextInt();

                    b.initboard(height, width, count);
                    play(b);
                    break;

                case "ai":
                case "a":
                    l.getCasebase();
                    b.initboard(8, 8, 10);
                    //b.initboard(16, 16, 40);
                    //b.initboard(16, 30, 99);
                    //l = new Learner.CBLearner();
                    l.makeAI(b);
                    playAI(b);
                    break;

                case "acont":
                case "aicont":
                    l.getCasebase();



                    while (true) {
                        b.initboard(8, 8, 10);
                        //b.initboard(16, 16, 40);
                        //b.initboard(16, 30, 99);
                        l.makeAI(b);
                        playAI(b);
                    }
                case "win":
                    System.out.print("It Won!");

                    //break;

                case "q":
                    cont = false;
                    break;
            }
        }
    }
}
