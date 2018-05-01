/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package suryacut.model;

import java.util.Queue;

/**
 *
 * @author CorneliusDavid
 */
public class Pelayan {
    protected Queue<Pelanggan> queue;
    
    public void insertPelangganIntoQueue(Pelanggan p){
        queue.offer(p);
    }
    
    public boolean removePelangganFromQueue(Pelanggan p){
        return queue.remove(p);
    }
    
    public Pelanggan getNextServed(){
        return queue.peek();
    }
    
    public int getUrutanPelanggan(Pelanggan p){
        Pelanggan[] tmp=(Pelanggan[]) queue.toArray();
        for (int i = 0; i < tmp.length; i++) {
            if(tmp[i]==p)return i;
        }
        return -1;
    }
    
    public Pelanggan[] getQueue(){
        if(this.queue.isEmpty())return null;
        return (Pelanggan[])this.queue.toArray();
    }
}
