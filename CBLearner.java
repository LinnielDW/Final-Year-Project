package Linniel;

import javax.persistence.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;

public class CBLearner {
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("cases.odb"); //creates connection with the database and resource manager
    EntityManager em = emf.createEntityManager(); //application connection to the database

    Query countquery = em.createQuery("SELECT COUNT(p) FROM Case p");
    Query getall = em.createQuery("SELECT p FROM Case p", Case.class);
    Query getlastid = em.createQuery("select c.id from Case c order by c.id desc");



    //Learner.Linniel.Case casegot;
    //long dbSize = (long) countquery.getSingleResult();
    ArrayList<Case> casebase = new ArrayList<>();
    ArrayList<Case> casestoadd = new ArrayList<>();
    ArrayList<Long> idstoremove = new ArrayList<>();
    ArrayList<ChangestoMake> changestodb = new ArrayList<>();
    //int[][] changestodb = new int[][];

    Case successCase, proxy;
    int[][] subboard;
    int[][][] tempboard;


    int index;

    int[][] caseofinterest;

    int[] revYX= new int[2];
    int save = 0, count;
    boolean foundmatch, newclick, tobeadded;
    boolean first, second;
    //int id = 1;
    long dupeid;
    int gameswon = 0;
    int flushfirstwins = 0;
    int gamesflushfirst = 0;
    int tilesrevealed = 0;
    int matchesfound = 0;
    long lastid;
    boolean flushfirst;
    double dbsize;
    Board b;



    public void getCasebase(){
        casebase = (ArrayList<Case>) getall.getResultList();
        //count = (int)

        dbsize = (long) countquery.getSingleResult();


        getlastid.setMaxResults(1);
        if(dbsize > 0){
            lastid = (long) getlastid.getSingleResult();
        }
    }

    //initialises variables for the AI
    public  void makeAI(Board b){
        first = true;
        second = false;
        //getCasebase();
        //lessSecond = true;
        this.b = b;
        flushfirst = false;
    }

    //The deciding algorithm
    //line commented with 'SCORE' indicates that it is used in the hierarchical 'score' version of the CBR
    public int[] decide(Board b){
        //System.out.println("Decide");
        foundmatch = false;
        newclick = false;
        successCase = new Case();
        proxy = new Case();


        tempboard = new int[2][b.getY()+4][b.getX()+4];
        for (int i = 2; i <= b.getY() + 1; i++) {
            for (int j = 2; j <= b.getX() + 1; j++) {
                tempboard[0][i][j] = b.getBoard()[0][i][j];
                tempboard[1][i][j] = b.getBoard()[1][i][j];
            }
        }
        //System.out.println("Tempboard: ");
        //b.renderboard(tempboard);

        if(!first) {
            second = true;
            for(int score = 19; score>= 0; score--){    //SCORE
                if(foundmatch == false){                //SCORE
                    //System.out.println(score);
            for (int i = 2; i <= b.getY() + 1; i++) {
                for (int j = 2; j <= b.getX() + 1; j++) {

                    //b.renderboard(tempboard);

                    if(tempboard[0][i][j]==0 ) {
                        //System.out.println("Newest Added Learner.Linniel.Case"+Arrays.deepToString(casestoadd.get(casestoadd.size()-1).getState()));
                        //System.out.println("tempboard read");
                        //System.out.println(i +", " +j);
                        //System.out.println("Stuck");
                        //System.out.println("Newest Added Casexxxx "+Arrays.deepToString(casestoadd.get(casestoadd.size()-1).getState()));


                        for (int rotation = 0; rotation < 4; rotation++) {
                            if(true) {
                                subboard = getView(i - 1, j - 1, rotation);

                                //System.out.println("subboard score: "+ minescore(i-1, j-1, rotation));
                                if(minescore(i-1, j-1, rotation) == score ){        //SCORE


                                //System.out.println(casestoadd.size());
                                //System.out.println("Newest Added Case1"+Arrays.deepToString(casestoadd.get(casestoadd.size()-1).getState()));
                                //System.out.println("SB" + Arrays.deepToString(subboard));
                                //System.out.println("Newest Added Case2"+Arrays.deepToString(casestoadd.get(casestoadd.size()-1).getState()));
                                if (dbsize > 0) {
                                    for (int m = 0; m < casebase.size(); m++ /*DONE: Replace next part with a 'best move' part*/) {

                                        //------------
                                        if (successCase == null) {
                                            proxy = casebase.get(m);

                                            if (Arrays.deepEquals(proxy.getState(), subboard) && proxy.getChance() >49) {
                                                //System.out.println("Compare Case: " + Arrays.deepToString(proxy.getState()));
                                                //System.out.println("Chance: " + proxy.getChance());
                                                revYX[0] = i - 1;
                                                revYX[1] = j - 1;
                                                foundmatch = true;
                                                tobeadded = false;
                                                index = m;
                                                successCase = proxy;
                                                break;
                                            }
                                        } else {
                                            proxy = casebase.get(m);
                                            if (Arrays.deepEquals(proxy.getState(), subboard) && successCase.getChance() < proxy.getChance()&& proxy.getChance() >49) {
                                                revYX[0] = i - 1;
                                                revYX[1] = j - 1;
                                                foundmatch = true;
                                                tobeadded = false;
                                                index = m;
                                                successCase = proxy;
                                                break;
                                            }

                                        }
                                        //------------
                                        /*
                                        successCase  = casebase.get(m);
                                        if (Arrays.deepEquals(successCase.getState(), subboard)) {
                                            //System.out.println("Compare Learner.Linniel.Case: " + Arrays.deepToString(successCase.getState()));
                                            revYX[0] = i - 1;
                                            revYX[1] = j - 1;
                                            foundmatch = true;
                                            tobeadded = false;5836+
                                            5@?~~?@?@
                                            break;
                                        }
                                        */
                                    }
                                }
                                if (casestoadd.size() > 0) {
                                    for (int m = 0; m < casestoadd.size(); m++) {
                                        //System.out.println("Temp: "+Arrays.deepToString(temp.getState()));
                                        if (successCase == null) {
                                            proxy = casestoadd.get(m);
                                            if (Arrays.deepEquals(proxy.getState(), subboard) && proxy.getChance() >49 /* && proxy.getTries() > 10*/) {
                                                //System.out.println("Compare Learner.Linniel.Case: " + Arrays.deepToString(successCase.getState()));
                                                revYX[0] = i - 1;
                                                revYX[1] = j - 1;
                                                foundmatch = true;
                                                tobeadded = true;
                                                index = m;
                                                successCase = proxy;
                                                break;
                                            }
                                        } else {
                                            proxy = casestoadd.get(m);
                                            if (Arrays.deepEquals(proxy.getState(), subboard) && successCase.getChance() < proxy.getChance()&& proxy.getChance() >49) {
                                                revYX[0] = i - 1;
                                                revYX[1] = j - 1;
                                                foundmatch = true;
                                                tobeadded = true;
                                                index = m;
                                                successCase = proxy;
                                                break;
                                            }

                                        }
                                    }
                                }
                            }   //SCORE
                            }
                        }
                       /* for (int[][] temp : casestoadd) {
                            successCase = temp;
                            if (Arrays.deepEquals(temp, subboard)) {
                                revYX[0] = i;
                                revYX[1] = j;
                                foundmatch = true;
                                break;
                            }
                        }*/
                    }
                }
            }
                } //fore SCORE scrolling
            }   //SCORE


        }
        else{
            //first = false;
        }
        if(foundmatch==false){
            //System.out.println("random click");
            while(!newclick){
                revYX[0] = ThreadLocalRandom.current().nextInt(1, b.getY() + 1);
                revYX[1] = ThreadLocalRandom.current().nextInt(1, b.getX() + 1);
                //System.out.println(revYX[0]+", " + revYX[1]);
                if(tempboard[0][revYX[0]+1][revYX[1]+1]==0){
                    newclick = true;
                }
            }
        }
        //System.out.println(revYX[0]+", " + revYX[1]);
        //System.out.println(save);
        //Save(save);
        return revYX;
    }

    //This method is used for persistence of the casebase
    public void Save(int save){
        if(save > 1000){




            //System.out.println("Saves: "+save);
            //System.out.println("Save process beginning");
            em.getTransaction().begin();
            //if() {
            //System.out.println("Cases to be added: "+casestoadd.size());
                for (/*int i = 0;i<casestoadd.size();i++*/ Case temp : casestoadd) {
                    //System.out.println(Arrays.deepToString(temp.getState()));
                    //System.out.println("this worked");
                    em.persist(temp);
                }
            //}
            em.getTransaction().commit();

            //System.out.println("Cases to be removed: " + idstoremove.size());

            /*
            if(idstoremove.size()>0) {
                for (int i = 0; i < idstoremove.size(); i++) {
                    System.out.println(i);
                    em.remove(em.find(Case.class, idstoremove.get(i)));
                }
            }
            */

            /*
            em.getTransaction().begin();
            for(long i : idstoremove){ //EVERY ID IS 0, WHY? DONE
                //System.out.println(i);
                //System.out.println(idstoremove.get((int)i));
                //System.out.println(i);
                //int prox = idstoremove.get((int) i).intValue();
                //System.out.println(prox);
                //if(prox>0) {

                int prox = (int) i;
                em.remove(em.find(Linniel.Case.class, prox));
                //}
            }

            em.getTransaction().commit();
            */


            // cases to change winrates of
            em.getTransaction().begin();

            for(ChangestoMake i : changestodb){
                Case tochange = em.find(Case.class,i.getId());
                //System.out.println("id: "+tochange.getId()+"\nSuccesses: "+tochange.getSuccess()+"\nTries: "+tochange.getTries());
                tochange.updatetriesandsuccess(i.getSucc(),i.getTries());
                //System.out.println("id: "+tochange.getId()+"\nSuccesses: "+tochange.getSuccess()+"\nTries: "+tochange.getTries()+"\n");
            }

            em.getTransaction().commit();


            casebase.clear();
            casestoadd.clear();
            //idstoremove.clear();
            changestodb.clear();
            getCasebase();
            this.save = 0;

            System.out.println(dbsize);
            System.out.println("epoc finished");







            /*
             * epocs
             * games won in epoc
             * total cases
             * highest id
             * games won where there was a zero flush on first move
             * games where there was a zero flush on first move
             * time of save
             * average number of clicks per 1000 clicks
             * average number of random clicks per 1000 clicks
             * */
            try(FileWriter fw = new FileWriter("stats.txt", true);
                BufferedWriter bw = new BufferedWriter(fw);
                PrintWriter out = new PrintWriter(bw)

                // PrintWriter out = new PrintWriter( "filename.txt" )
            ){
                Date date = new Date();
                out.println(date.toString() +"\t"+ gameswon+"\t"+ dbsize +"\t"+lastid +"\t"+ gamesflushfirst +"\t"+ flushfirstwins +"\t"+tilesrevealed+"\t"+matchesfound+"\n");
            }
            catch (Exception e ){
                e.printStackTrace();
            }

            gameswon = 0;
            gamesflushfirst= 0;
            flushfirstwins = 0;
            tilesrevealed = 0;
            matchesfound = 0;
        }
        else{
            this.save++;
        }

    }

    /*public void RemoveCase(int id){
        em.remove(em.find(Learner.Linniel.Case.class,id));
    }*/

    /**     . . . . .
     *      . . . . .
     *      . . X . .
     */

    //Used to get a subsection of the board
    public int[][] getView(int i, int j, int rot){
        /*if(casestoadd.size()>0) {
            System.out.println("Newest Added Learner.Linniel.Case b" + Arrays.deepToString(casestoadd.get(casestoadd.size() - 1).getState()));
        }*/
        i++;
        j++;

        /*if(casestoadd.size()>0) {
            System.out.println("Newest Added Learner.Linniel.Case (inside getview)" + Arrays.deepToString(casestoadd.get(casestoadd.size() - 1).getState()));
        }*/


        if(rot==0) {
            int k = 2;
            int l = 0;
            caseofinterest = new int[3][5];
            for (int m = i; m >= i - 2; m--) {
                for (int n = j - 2; n <= j + 2; n++) {
                    //caseofinterest[k][l] = tempboard[0][m][n];
                    if (tempboard[0][m][n] == 1) {
                        caseofinterest[k][l] = tempboard[1][m][n];
                    }/* else { //out of bounds caseofinterest cell = 10           from here
                        if(m<2||n<2||m>b.getY()+2||n>b.getX()+2){
                            caseofinterest[k][l] = 10;
                        }*/ //                                                    to here
                        else{
                        caseofinterest[k][l] = 9;
                       // } //                                                  and this
                    }
                    if (l < 4) {
                        l++;
                    } else {
                        l = 0;
                        k--;
                    }
                }
            }
        }

        if(rot == 1){
            int k = 4;
            int l = 0;
            caseofinterest = new int[5][3];
            for (int m = i+2; m >= i - 2; m--) {
                for (int n = j; n <= j + 2; n++) {
                    if (tempboard[0][m][n] == 1) {
                        caseofinterest[k][l] = tempboard[1][m][n];
                    } /*else { //out of bounds caseofinterest cell = 10
                        if(m<2||n<2||m>b.getY()+2||n>b.getX()+2){
                            caseofinterest[k][l] = 10;
                        }*/
                        else{
                            caseofinterest[k][l] = 9;
                        //}
                    }
                    if (l < 2) {
                        l++;
                    } else {
                        l = 0;
                        k--;
                    }
                }
            }
        }

        if(rot == 2){
            int k = 0;
            int l = 0;
            caseofinterest = new int[3][5];
            for (int m = i; m <= i + 2; m++) {
                for (int n = j - 2; n <= j + 2; n++) {
                    if (tempboard[0][m][n] == 1) {
                        caseofinterest[k][l] = tempboard[1][m][n];
                    }/* else { //out of bounds caseofinterest cell = 10
                        if(m<2||n<2||m>b.getY()+2||n>b.getX()+2){
                            caseofinterest[k][l] = 10;
                        }*/
                        else{
                            caseofinterest[k][l] = 9;
                       // }
                    }
                    if (l < 4) {
                        l++;
                    } else {
                        l = 0;
                        k++;
                    }
                }
            }
        }

        if(rot == 3){
            int k = 4;
            int l = 2;
            caseofinterest = new int[5][3];
            for (int m = i+2; m >= i - 2; m--) {
                for (int n = j; n >= j - 2; n--) {
                    if (tempboard[0][m][n] == 1) {
                        caseofinterest[k][l] = tempboard[1][m][n];
                    }/* else { //out of bounds caseofinterest cell = 10
                        if(m<2||n<2||m>b.getY()+2||n>b.getX()+2){
                            caseofinterest[k][l] = 10;
                        }*/
                        else{
                            caseofinterest[k][l] = 9;
                        //}
                    }
                    if (l >0) {
                        l--;
                    } else {
                        l = 2;
                        k--;
                    }
                }
            }
        }

        caseofinterest=rotatearray(caseofinterest,4-rot);


        return caseofinterest;

    }

    //Used as the scoring method for my edge-detection CBR
    public int minescore(int i, int j, int rotation){
        i++;
        j++;
        int score = 0;
        if(rotation==0){
            for(int n = j - 2; n <= j + 2; n++) {
                if(tempboard[0][i+1][n]==0 /*&& !(i<2 ||i>b.getY()+1) && !(n<2 || n>b.getX()+1)*/){
                    score++;
                }
            }
            for (int m = i; m >= i - 2; m--) {
                for (int n = j - 2; n <= j + 2; n++) {
                    if(tempboard[0][m][n]==1){
                        score++;
                    }
                }
            }
        }

        if(rotation==1){
            for(int n = i - 2; n <= i + 2; n++) {
                if(tempboard[0][n][j-1]==0){
                    score++;
                }
            }
            for (int m = i+2; m >= i - 2; m--) {
                for (int n = j; n <= j + 2; n++) {
                    if(tempboard[0][m][n]==1){
                        score++;
                    }
                }
            }
        }

        if(rotation==2){
            for(int n = j - 2; n <= j + 2; n++) {
                if(tempboard[0][i-1][n]==0){
                    score++;
                }
            }
            for (int m = i; m <= i + 2; m++) {
                for (int n = j - 2; n <= j + 2; n++) {
                    if(tempboard[0][m][n]==1){
                        score++;
                    }
                }
            }
        }

        if(rotation==3){
            for(int n = i - 2; n <= i + 2; n++) {
                if(tempboard[0][n][j+1]==0){
                    score++;
                }
            }
            for (int m = i+2; m >= i - 2; m--) {
                for (int n = j; n >= j - 2; n--) {
                    if(tempboard[0][m][n]==1){
                        score++;
                    }
                }
            }
        }

        return score;
    }

    //Rotates array
    public int[][] rotatearray(int[][] rotarray, int rotateno){
        if(rotateno == 0 || rotateno == 4){
            return rotarray;
        }
        else {
            for(int rotcount = 0; rotcount < rotateno; rotcount++) {
                int[][] rotated = new int[rotarray[0].length][rotarray.length];
                for (int i = 0; i < rotarray.length; i++) {
                    for (int j = 0; j < rotarray[0].length; j++) {
                        rotated[j][rotarray.length-1-i] = rotarray[i][j];
                    }
                }
                rotarray = Arrays.copyOf(rotated, rotated.length);
            }
            return rotarray;
        }
    }

    //Checks the casebase for duplicates
    public int duplicatecheck(int[][] view) {
        int duplicate = 0;
        if(casebase.size()>0) {
            for (Case i : casebase) {
                //System.out.println("dupecheck: "+Arrays.deepToString(i.getState()));
                if (Arrays.deepEquals(i.getState(), view)) {
                    duplicate = 1;
                    dupeid = i.getId();
                    break;
                }
            }

        }
        if(casestoadd.size()>0 && duplicate == 0) {
            for (Case i : casestoadd) {
                //System.out.println("dupecheck: "+Arrays.deepToString(i.getState()));
                if (Arrays.deepEquals(i.getState(), view)) {
                    duplicate = 2;
                    dupeid = casestoadd.indexOf(i);
                    break;
                }
            }
        }
        return duplicate;
    }

    //Used to make changes to existing cases in the casebase
    public void incchanges(long id, boolean win){
        //duplicate check
        int index = -1;
        //boolean duplicate = false;
        for(int i =0; i < changestodb.size();i++){
          if(changestodb.get(i).getId() == id){
              //duplicate = true;
              index = i;
              break;
          }
        }

        if(index > -1){
            if(win){
                changestodb.get(index).incSucc();
            }
            else{
                changestodb.get(index).incTries();
            }
        }
        else{
            if(win){
                changestodb.add(new ChangestoMake(id,1,1));
            }
            else{
                changestodb.add(new ChangestoMake(id,0,1));
            }
            //changestodb.add(new Linniel.ChangestoMake(id,));
        }
    }
}
