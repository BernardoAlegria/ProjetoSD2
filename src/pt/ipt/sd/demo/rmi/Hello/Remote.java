/** ************************************************************************** */
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

import java.net.InetAddress;
import java.rmi.RemoteException;
import java.rmi.server.RemoteServer;
import java.rmi.server.ServerNotActiveException;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author manso
 */
public class Remote extends UnicastRemoteObject implements RemoteInterface {

    String host = "unknow";

    public Remote(int port) throws RemoteException {
        super(port);
        try {
            host = InetAddress.getLocalHost().getHostName();
        } catch (Exception e) {
        }
    }

    public String getMessage() throws RemoteException {
       String client = "";
        try {
           client = RemoteServer.getClientHost();
            System.out.println("CLIENT : " +client);
           
        } catch (ServerNotActiveException ex) {
            Logger.getLogger(Remote.class.getName()).log(Level.SEVERE, null, ex);
        }
         return client + " Hello from " + host;
    }
}
