/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package suryacut.model.simulation;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Irvan
 */
//TUKANG KERAMAS
public class TkSimulator {
    private int waktuPelayanan;
    private List<DetilPelayanan> listDetil;
    private int totalKerja;
    
    public TkSimulator() {
        this.waktuPelayanan = 5;
        listDetil = new ArrayList<DetilPelayanan>();
        this.totalKerja = 0;
    }

    public int getWaktuSelesaiMelayani() {
        if (listDetil.isEmpty()) {
            return 0;
        }
        return listDetil.get(listDetil.size() - 1).getWaktuSelesai();
    }

    public void insertPelanggan(int waktuKedatangan) {
        this.totalKerja += this.waktuPelayanan;
        int waktuMulaiDilayani = this.getWaktuSelesaiMelayani();
        int delay = waktuMulaiDilayani - waktuKedatangan;
        int wait, waktuSelesai;
        if (waktuMulaiDilayani < waktuKedatangan) {
            waktuMulaiDilayani = waktuKedatangan;
            delay = 0;
        }
        wait = delay + this.waktuPelayanan;
        waktuSelesai = wait + waktuKedatangan;
        DetilPelayanan detil = new DetilPelayanan(waktuKedatangan, waktuMulaiDilayani, this.waktuPelayanan, delay, wait, waktuSelesai);
        listDetil.add(detil);
    }
    
    public int getTotalKerja() {
        return totalKerja;
    }
    
    public int getTotalDelay(){
        int total = 0;
        for(int i = 0; i < listDetil.size() ; i++){
            total += listDetil.get(i).getDelay();
        }
        return total;
    }
    
    public int getTotalWait(){
        int total = 0;
        for(int i = 0; i < listDetil.size() ; i++){
            total += listDetil.get(i).getWait();
        }
        return total;
    }
    
}
