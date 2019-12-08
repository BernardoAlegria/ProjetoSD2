///**************************************************************************/
///****     Copyright (C) 2014                                           ****/
///****     Antonio Manuel Rodrigues Manso                               ****/
///****     e-mail: manso@ipt.pt                                         ****/
///****     url   : http://orion.ipt.pt/~manso    manso@ipt.pt           ****/
///****     Instituto Politecnico de Tomar                               ****/
///****     Escola Superior de Tecnologia de Tomar                       ****/
///****                                                                  ****/
///**************************************************************************/
///****     This software was build with the purpose of learning.        ****/
///****     Its use is free and is not provided any guarantee            ****/
///****     or support.                                                  ****/
///****     If you met bugs, please, report them to the author           ****/
///****                                                                  ****/
///**************************************************************************/
///**************************************************************************/
package myUtils;


import java.math.BigDecimal;
import java.math.MathContext;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author manso
 */
public class PI_BBP extends Thread {
    //-------------------------
     MathContext type;
    //--------------------------
    BigDecimal sumPI;    
    AtomicInteger ticket;    

    public PI_BBP(AtomicInteger counter, int precision) {
        this.ticket = counter;
        type = new MathContext((int) (precision * 1.3));
    }

    @Override
    public void run() {
        sumPI = new BigDecimal(0.0);
        BigDecimal one = new BigDecimal(1.0);
        BigDecimal two = new BigDecimal(2.0);
        BigDecimal four = new BigDecimal(4.0);
        BigDecimal five = new BigDecimal(5.0);
        BigDecimal six = new BigDecimal(6.0);
        BigDecimal eight = new BigDecimal(8.0);
        BigDecimal sixteen = new BigDecimal(16.0);
        while (true) {
            //tirar um ticket
            int i = ticket.getAndDecrement();
            //se terminaram os tickets
            if( i < 0)
                break;
            //fazer o calculo
            BigDecimal k = new BigDecimal(i);
            BigDecimal k8 = eight.multiply(k);
            BigDecimal t0 = one.divide(sixteen.pow((int) i), type);
            BigDecimal t1 = four.divide(k8.add(one), type);
            BigDecimal t2 = two.divide(k8.add(four), type);
            BigDecimal t3 = one.divide(k8.add(five), type);
            BigDecimal t4 = one.divide(k8.add(six), type);
            BigDecimal difs = t1.subtract(t2).subtract(t3).subtract(t4);
            sumPI = sumPI.add(t0.multiply(difs));
        }
    }

    public static BigDecimal calculate(int iterations) {
        //----------------------------------------------------------------------
        //----------- parametrizar o algoritmo para computador actual ---------
        //----------------------------------------------------------------------
        int numProcessors = Runtime.getRuntime().availableProcessors();        
        //Distribuidor de trabalho - objecto partilhado
        AtomicInteger numberOfTerm = new AtomicInteger(iterations);
        //array de Threads
        PI_BBP[] thr = new PI_BBP[numProcessors];
        //----------------------------------------------------------------------
        //----- construir e por a threads a correr -----------
        //----------------------------------------------------------------------
        for (int i = 0; i < thr.length; i++) {
            //Cada Thread possui:
            //       o distribuidor de trabalho (ticket)
            //       o numero de iteracoes  
            thr[i] = new PI_BBP(numberOfTerm,iterations);
            thr[i].start();
        }
        //------------------------------------------------------------
        //valor do calculate
        BigDecimal PI = new BigDecimal(0);
        //----------------------------------------------------------------------
        //----- esperar que a threads concluam o trabalho ------
        //----------------------------------------------------------------------
        for (int i = 0; i < thr.length; i++) {
            try {
                thr[i].join();
                PI = PI.add(thr[i].sumPI);
            } catch (InterruptedException ex) {
                Logger.getLogger(PI_BBP.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        //----------------------------------------------------------------------
        //----- retornar o objecto partilhado ------
        //----------------------------------------------------------------------
        return PI;
    }
    
    
    public static void main(String[] args) {
        BigDecimal pi = calculate(1000);
        System.out.println(pi.toPlainString());
    }
}
