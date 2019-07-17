/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mangocomputer.mangochatbot;

import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author Jose Andres Morales Linares
 */

public class LevenshteinDistance {
    private static int minimum(int a, int b, int c) {
         return Math.min(a, Math.min(b, c));
    }

    public static int computeLevenshteinDistance(String str1, String str2) {
        return computeLevenshteinDistance(str1.toCharArray(),
                                          str2.toCharArray());
    }
    private static int computeLevenshteinDistance(char [] str1, char [] str2) {
        int [][]distance = new int[str1.length+1][str2.length+1];

        for(int i=0;i<=str1.length;i++){
                distance[i][0]=i;
        }
        for(int j=0;j<=str2.length;j++){
                distance[0][j]=j;
        }
        for(int i=1;i<=str1.length;i++){
            for(int j=1;j<=str2.length;j++){ 
                  distance[i][j]= minimum(distance[i-1][j]+1,
                                        distance[i][j-1]+1,
                                        distance[i-1][j-1]+
                                        ((str1[i-1]==str2[j-1])?0:1));
            }
        }
        return distance[str1.length][str2.length];
        
    }
    
    public static int computeLevenshteinDistancePalabras(ArrayList<String> str1, ArrayList<String> str2) {
        
        String[] a = new String[str1.size()];
        String[] b = new String[str2.size()];
        
        Iterator iter = str1.iterator();
        int i=0;
        while (iter.hasNext())
        {
            a[i] = (String)iter.next();
            i++;
        }
        
        iter = str2.iterator();
        i=0;
        while (iter.hasNext())
        {
            b[i] = (String)iter.next();
            i++;
        }

        return computeLevenshteinDistancePalabras(a,b);
    }

    public static int computeLevenshteinDistancePalabras(String [] str1, String [] str2) {
        int [][]distance = new int[str1.length+1][str2.length+1];

        for(int i=0;i<=str1.length;i++){
                distance[i][0]=i;
        }
        for(int j=0;j<=str2.length;j++){
                distance[0][j]=j;
        }
        for(int i=1;i<=str1.length;i++){
            for(int j=1;j<=str2.length;j++){ 
                  distance[i][j]= minimum(distance[i-1][j]+1,
                                        distance[i][j-1]+1,
                                        distance[i-1][j-1]+
                                        ((str1[i-1].equals(str2[j-1]))?0:1));
            }
        }
        return distance[str1.length][str2.length];
        
    }

    
}