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
public class Reduction_jomp {


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

// OMP PARALLEL BLOCK BEGINS
{
  __omp_Class0 __omp_Object0 = new __omp_Class0();
  // shared variables
  __omp_Object0.x = x;
  __omp_Object0.n = n;
  __omp_Object0.args = args;
  // firstprivate variables
  try {
    jomp.runtime.OMP.doParallel(__omp_Object0);
  } catch(Throwable __omp_exception) {
    System.err.println("OMP Warning: Illegal thread exception ignored!");
    System.err.println(__omp_exception);
  }
  // reduction variables
  b  += __omp_Object0._rd_b;
  // shared variables
  x = __omp_Object0.x;
  n = __omp_Object0.n;
  args = __omp_Object0.args;
}
// OMP PARALLEL BLOCK ENDS

        
        System.out.println("Valor final de b = " + b);
    }

// OMP PARALLEL REGION INNER CLASS DEFINITION BEGINS
private static class __omp_Class0 extends jomp.runtime.BusyTask {
  // shared variables
  int [ ] [ ] x;
  int n;
  String [ ] args;
  // firstprivate variables
  // variables to hold results of reduction
  int _rd_b;

  public void go(int __omp_me) throws Throwable {
  // firstprivate variables + init
  // private variables
  int myId;
  int i;
  // reduction variables, init to default
  int b = 0;
    // OMP USER CODE BEGINS

        {
            myId = OMP.getThreadNum();

            for (i = 0; i < n; i++) {
                b += x[myId][i];
            }
            
            System.out.println("Thread " + myId + " total = " + b);
        }
    // OMP USER CODE ENDS
  // call reducer
  b = (int) jomp.runtime.OMP.doPlusReduce(__omp_me, b);
  // output to _rd_ copy
  if (jomp.runtime.OMP.getThreadNum(__omp_me) == 0) {
    _rd_b = b;
  }
  }
}
// OMP PARALLEL REGION INNER CLASS DEFINITION ENDS

}

