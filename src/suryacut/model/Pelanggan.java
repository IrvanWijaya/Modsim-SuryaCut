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

    public Pelanggan(String nama, char gender) {
        this.nama = nama;
        this.gender = gender;
    }

    public String getNama() {
        return nama;
    }

    public char getGender() {
        return gender;
    }
}
