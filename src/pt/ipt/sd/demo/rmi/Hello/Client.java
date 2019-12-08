///*****************************************************************************/
///****     Copyright (C) 2010                                             ****/
///****     António Manuel Rodrigues Manso                                  ****/
///****     e-mail: manso@ipt.pt                                            ****/
///****     url   : http://orion.ipt.pt/~manso    manso@ipt.pt              ****/
///****     Instituto Politécnico de Tomar                                  ****/
///****     Escola Superior de Tecnologia de Tomar                          ****/
///****                                                                     ****/
///*****************************************************************************/
///****     This software was build with the purpose of learning.           ****/
///****     Its use is free and is not provided any guarantee               ****/
///****     or support.                                                     ****/
///****     If you met bugs, please, report them to the author              ****/
///****                                                                     ****/
///*****************************************************************************/
///*****************************************************************************/
package pt.ipt.sd.demo.rmi.Hello;

import java.rmi.Naming;

/**
 *
 * @author manso
 */
public class Client {

    public static void main(String[] args) throws Exception {
        //String host = "solarsystemgo.ipt.pt";
       // String host = "algorithmi.ipt.pt";
        //String host = "letrinhas.ipt.pt";
        //String host = "localhost";
        
        String host = "194.210.113.41";

        String remoteObject = String.format("//%s:%d/%s", host, Server.remotePort,Server.remoteName);

        RemoteInterface remoteHello = (RemoteInterface) Naming.lookup(remoteObject);

        System.out.println(remoteHello.getMessage());
    }

}
