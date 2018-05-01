/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package suryacut.model;

import java.util.LinkedList;
import java.util.Queue;

/**
 *
 * @author CorneliusDavid
 */
public class TukangCukur {
    private Queue antrean;
    private String nama;

    public TukangCukur(String nama) {
        this.nama = nama;
        antrean=new LinkedList();
    }
    
}
