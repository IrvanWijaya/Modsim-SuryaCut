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
public class TukangCukur extends Pelayan{
    private String nama;
    private Pelanggan currentlyServed;

    public TukangCukur(String nama) {
        this.nama = nama;
        queue=new LinkedList<>();
    }
    
    public boolean serveNext(){
        if(queue.isEmpty())return false;
        currentlyServed=queue.poll();
        return true;
    }

    public String getNama() {
        return nama;
    }

    public Pelanggan getCurrentlyServed() {
        return currentlyServed;
    }
}
