/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package suryacut;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import suryacut.model.simulation.*;

/**
 *
 * @author Irvan
 */
public class Tester {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        int banyakPelanggan = sc.nextInt();
        int banyakTukangCukur = sc.nextInt();
        int banyakTukangKeramas = sc.nextInt();
        boolean bolehMilih = true;
        int utilitas = 1;
        Simulator sm = new Simulator(banyakTukangCukur, banyakTukangKeramas);
        
        int waktuKedatangan[] = new int[banyakPelanggan];
        int tukangCukurPilihan[] = new int[banyakPelanggan];
        
        Random rand = new Random();
        int waktu = 0;
        
        int i;
        for(i =0; i < waktuKedatangan.length; i++ ){
            waktu = waktu + rand.nextInt(10) + 1;
            waktuKedatangan[i] = waktu;
            if(bolehMilih){
                tukangCukurPilihan[i] = rand.nextInt(banyakTukangCukur + 1) - 1;
            }
            else{
                tukangCukurPilihan[i] = -1;
            }
            
            int selesaiCukur = sm.insertCukur(waktuKedatangan[i], tukangCukurPilihan[i], utilitas);
            sm.insertKeramas(selesaiCukur, utilitas);
        }
        
        System.out.println(sm.getWaktuAkhirSimulasi());
        System.out.println(sm.getDelayAverage(banyakPelanggan));
        System.out.println(sm.getWaitAverage(banyakPelanggan));
        
    }
}
