/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication8;

import jomp.runtime.*;

/**
 *
 * @author Gabriel
 */
public class Reduction {

    public static void main(String[] args) {
        int myId;
        int n = 40;
        OMP.setNumThreads(n);
        int[][] x = new int[n][n];
        int i = 0;
        for(i = 0; i < n; i++){
            for(int j = 0; j < n; j++){
                x[i][j] = (int) (Math.random()*10);
            }
        }
        int b = 0;

        //omp parallel private(myId, i) reduction(+:b)
        {
            myId = OMP.getThreadNum();

            for (i = 0; i < n; i++) {
                b += x[myId][i];
            }
            
            System.out.println("Thread " + myId + " total = " + b);
        }
        
        System.out.println("Valor final de b = " + b);
    }
}
