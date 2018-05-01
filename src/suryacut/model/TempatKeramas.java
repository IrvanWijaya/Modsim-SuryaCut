/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package suryacut.model;

import java.util.LinkedList;

/**
 *
 * @author CorneliusDavid
 */
public class TempatKeramas extends Pelayan{
    private static TempatKeramas instance;
    private Pelanggan[] servers;
    
    private TempatKeramas(){
        queue=new LinkedList<>();
        servers=new Pelanggan[3];
    }
    
    public static TempatKeramas getInstance(){
        if(instance==null){
            instance=new TempatKeramas();
        }
        return instance;
    }

    public Pelanggan[] getServers() {
        return servers;
    }
    
    public int servePelangan(){
        if(queue.isEmpty())return -1;
        Pelanggan p=queue.poll();
        for (int i = 0; i < servers.length; i++) {
            if(servers[i]==null){
                servers[i]=p;
                return i;
            }
        }
        return -1;
    }
    
    public Pelanggan finishServe(int idx){
        Pelanggan p=servers[idx];
        servers[idx]=null;
        return p;
    }
}
