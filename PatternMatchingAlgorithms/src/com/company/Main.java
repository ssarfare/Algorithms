package com.company;

import java.util.Formatter;
import java.util.HashMap;

public class Main {

    public static void main(String[] args) {
	// write your code here

        String [] Testpatterns= new String[10];
        String [] Testtexts= new String[10];

        Testtexts[0]="THIS IS A TEST TEXT";
        Testpatterns[0]="TEST";

        Testtexts[1]="AAAAAAAAAAH";
        Testpatterns[1]="AAAAH";

        Testtexts[2]="JIM_SAW_ME_IN_A_BARBERSHOP";
        Testpatterns[2]="BARBER";

        Testtexts[3]="BARD LOVES BANANAS";
        Testpatterns[3]="BOABAB";

        Testtexts[4]="BARD LOVES BANANAS";
        Testpatterns[4]="BANANA";

        Testtexts[5]="bess new about baobabs";
        Testpatterns[5]="baobab";

        Testtexts[6]="ABABABAEABABACBD";
        Testpatterns[6]="ABABACB";

        Testtexts[7]="KRISHNA IS GREAT";
        Testpatterns[7]="GREAT";

        Testtexts[8]="I Went for a run";
        Testpatterns[8]="run";

        Testtexts[9]="Happy Thanks Giving";
        Testpatterns[9]="Giving";


        Formatter  formatter;
        formatter = new Formatter();
        int eventMaxLength = 50; // assign the max length
        String eventFormat = "%-" + eventMaxLength + "." + eventMaxLength + "s";
        System.out.print(formatter.format(eventFormat,"Inputs"));
        formatter = new Formatter();
        eventMaxLength = 50; // assign the max length
        System.out.print(formatter.format(eventFormat,"Brute Force"));
        formatter = new Formatter();
        eventMaxLength = 40; // assign the max length
        eventFormat = "%-" + eventMaxLength + "." + eventMaxLength + "s";
        System.out.print(formatter.format(eventFormat,"Horspool"));
        formatter = new Formatter();
        eventMaxLength = 40; // assign the max length
        eventFormat = "%-" + eventMaxLength + "." + eventMaxLength + "s";
        System.out.print(formatter.format(eventFormat,"KMP Algorithm"));
        System.out.println();

        formatter = new Formatter();
        eventMaxLength = 45; // assign the max length
        eventFormat = "%-" + eventMaxLength + "." + eventMaxLength + "s";
        System.out.print(formatter.format(eventFormat,"  "));
        formatter = new Formatter();
        eventMaxLength = 15; // assign the max length
        eventFormat = "%-" + eventMaxLength + "." + eventMaxLength + "s";
        System.out.print(formatter.format(eventFormat,"Index"));
        formatter = new Formatter();
        eventMaxLength = 30;//assign the max length
        eventFormat = "%-" + eventMaxLength + "." + eventMaxLength + "s";
        System.out.print(formatter.format(eventFormat,"Num of Comparisons"));


        formatter = new Formatter();
        eventMaxLength = 15; //sign the max length
        eventFormat = "%-" + eventMaxLength + "." + eventMaxLength + "s";
        System.out.print(formatter.format(eventFormat,"Index"));
        formatter = new Formatter();
        eventMaxLength = 30;// assign the max length
        eventFormat = "%-" + eventMaxLength + "." + eventMaxLength + "s";
        System.out.print(formatter.format(eventFormat,"Num of Comparisons"));
        formatter = new Formatter();



        formatter = new Formatter();
        eventMaxLength = 20; // assign the max length
        eventFormat = "%-" + eventMaxLength + "." + eventMaxLength + "s";
        System.out.print(formatter.format(eventFormat,"Index"));
        formatter = new Formatter();
        eventMaxLength = 20; // assign the max length
        eventFormat = "%-" + eventMaxLength + "." + eventMaxLength + "s";
        System.out.print(formatter.format(eventFormat,"Num of Comparisons"));
        formatter = new Formatter();
        System.out.println();












        for(int i=0; i<Testtexts.length;i++){
            int [] resultArray=bruteForce(Testtexts[i],Testpatterns[i]);
            int bruteResult=resultArray[0];
            int bruteNumberOfComparision=resultArray[1];
            formatter = new Formatter();
            eventMaxLength = 40; // assign the max length
            eventFormat = "%-" + eventMaxLength + "." + eventMaxLength + "s";
            System.out.print("Text: "+ formatter.format(eventFormat,Testtexts[i]));
            formatter = new Formatter();
            eventMaxLength = 20; // assign the max length
            eventFormat = "%-" + eventMaxLength + "." + eventMaxLength + "s";
            System.out.print(formatter.format(eventFormat,bruteResult==-1?"Unsuccessful":bruteResult));
            formatter = new Formatter();
            eventMaxLength = 25; // assign the max length
            eventFormat = "%-" + eventMaxLength + "." + eventMaxLength + "s";
            System.out.print(formatter.format(eventFormat,bruteNumberOfComparision));




            resultArray=horspool(Testtexts[i],Testpatterns[i]);
            int horsResult=resultArray[0];
            int horsNumberOfComparision=resultArray[1];
            formatter = new Formatter();
            eventMaxLength = 20; // assign the max length
            eventFormat = "%-" + eventMaxLength + "." + eventMaxLength + "s";
            System.out.print(formatter.format(eventFormat,horsResult==-1?"Unsuccessful":horsResult));
            formatter = new Formatter();
            eventMaxLength = 25; // assign the max length
            eventFormat = "%-" + eventMaxLength + "." + eventMaxLength + "s";
            System.out.print(formatter.format(eventFormat,horsNumberOfComparision));




            resultArray=kmp(Testtexts[i],Testpatterns[i]);
            int kmpResult=resultArray[0];
            int kmpNumberOfComparision=resultArray[1];
            formatter = new Formatter();
            eventMaxLength = 20; // assign the max length
            eventFormat = "%-" + eventMaxLength + "." + eventMaxLength + "s";
            System.out.print(formatter.format(eventFormat,kmpResult==-1?"Unsuccessful":kmpResult));
            formatter = new Formatter();
            eventMaxLength = 25; // assign the max length
            eventFormat = "%-" + eventMaxLength + "." + eventMaxLength + "s";
            System.out.print(formatter.format(eventFormat,kmpNumberOfComparision));
            System.out.println();
            System.out.println("Pattern : "+Testpatterns[i]);
            System.out.println();
        }
    }


    public static int[] bruteForce(String text,String pattern){
        int numberOfComparision=0;
        for(int i=0;i<=text.length()-pattern.length();i++){
            int count=0;
            for(int j=0;j<pattern.length();j++){
                numberOfComparision++;
                if(text.charAt(i+j)==pattern.charAt(j))
                    count++;
                else break;
            }
            if(count==pattern.length())
                return new int[]{i,numberOfComparision};
        }
        return new int[]{-1,numberOfComparision};
    }

    public static int[] horspool(String text,String pattern){
        HashMap<Character,Integer> shiftTable=new HashMap<>();

        int patternLength=pattern.length();
        for(int i=0;i<patternLength;i++){
            if(!shiftTable.containsKey(pattern.charAt(i)))
                shiftTable.put(pattern.charAt(i),patternLength);
        }
        for(int i=0;i<=patternLength-2;i++){
            int charIndex=pattern.lastIndexOf(pattern.charAt(i));
            if(charIndex<patternLength-1)
                shiftTable.replace(pattern.charAt(i),patternLength-1-charIndex);
            else
                shiftTable.replace(pattern.charAt(i),patternLength-1-i);
        }

        int i=patternLength-1;
        int numberOfComparision=0;
        while(i<text.length()){
            int k=0;

            while(k<patternLength ){

                if(text.charAt(i-k)==pattern.charAt(patternLength-1-k)) {
                    numberOfComparision++;
                    k++;
                    if (k == patternLength)
                        return new int[]{i - patternLength + 1, numberOfComparision};
                }
                else {
                    numberOfComparision++;
                    if(shiftTable.containsKey(text.charAt(i)))
                        i=i+shiftTable.get(text.charAt(i));
                    else i=i+patternLength;
                    break;
                }
            }
        }
        return new int[]{-1,numberOfComparision};
    }
    public static int[] kmp(String text,String pattern){
        int[] failureTable=generateFailureTable(pattern);
        int j=0;
        int comparisionCount=0;
        for (int i = 0; i < text.length(); i++) {
            while (j > 0 && text.charAt(i) != pattern.charAt(j)) {
                comparisionCount++;
                j = failureTable[j - 1];
            }
            if (text.charAt(i) == pattern.charAt(j)) {
                comparisionCount++;
                j++;
                if (j == pattern.length())
                    return new int[]{i - (j - 1),comparisionCount};
            }else
                comparisionCount++;
        }
        return new int[]{-1,comparisionCount};
    }

    public static int[] generateFailureTable(String pattern){

        int patternLength=pattern.length();
        int[] failureTable=new int[patternLength];
        failureTable[0]=0;
        for(int i=1;i<patternLength;i++){
            int k=failureTable[i-1];
            while(k>0 && pattern.charAt(i)!=pattern.charAt(k))
                k=failureTable[k-1];
            if(pattern.charAt(i)==pattern.charAt(k))
                k++;
            failureTable[i]=k;
        }
        return failureTable;
    }
}

