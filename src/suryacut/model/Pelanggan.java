/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package suryacut.model;

/**
 *
 * @author CorneliusDavid
 */
public class Pelanggan {
    private String nama;
    private char gender;
    private boolean keramas;
    private String state;
    private long waktuDilayani;
    private String namaTukangCukur;

    public Pelanggan(String nama, char gender, boolean keramas, String namaTukangCukur) {
        this.nama = nama;
        this.gender = gender;
        this.keramas = keramas;
        this.namaTukangCukur = namaTukangCukur;
    }

    public String getNama() {
        return nama;
    }

    public char getGender() {
        return gender;
    }
    
    public boolean getKeramas(){
        return this.keramas;
    }
    
    public void setState(String state){
        this.state = state;
    }
    
    public String getState(){
        return this.state;
    }
    
    public void setWaktuDilayani(long time){
        this.waktuDilayani = time;
    }
    
    public long getWaktuDilayani(){
        return this.waktuDilayani;
    }
    
    public String getNamaPelayan(){
        return this.namaTukangCukur;
    }
}
