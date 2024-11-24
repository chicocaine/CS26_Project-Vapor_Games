package Games;

import java.util.ArrayList;

public class Games_Test {
    public static void main (String[] args) {

    }

    public static ArrayList<String> arrayToArrayList (String[] arr) {
        ArrayList<String> arrList = new ArrayList<String>();

        for (String x : arr) {
            arrList.add(x);
        }

        return arrList;
    } 
    

}
