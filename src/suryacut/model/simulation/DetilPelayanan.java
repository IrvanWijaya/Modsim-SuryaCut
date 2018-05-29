/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package suryacut.model.simulation;

/**
 *
 * @author Irvan
 */
public class DetilPelayanan {
    
    private int waktuKedatangan;
    private int waktuMulaiDilayani;
    private int waktuPelayanan;
    private int delay;
    private int wait;
    private int waktuSelesai;

    public DetilPelayanan(int waktuKedatangan, int waktuMulaiDilayani, int waktuPelayanan, int delay, int wait, int waktuSelesai) {
        this.waktuKedatangan = waktuKedatangan;
        this.waktuMulaiDilayani = waktuMulaiDilayani;
        this.waktuPelayanan = waktuPelayanan;
        this.delay = delay;
        this.wait = wait;
        this.waktuSelesai = waktuSelesai;
    }

    public int getWaktuKedatangan() {
        return waktuKedatangan;
    }

    public int getWaktuMulaiDilayani() {
        return waktuMulaiDilayani;
    }

    public int getWaktuPelayanan() {
        return waktuPelayanan;
    }

    public int getDelay() {
        return delay;
    }

    public int getWait() {
        return wait;
    }

    public int getWaktuSelesai() {
        return waktuSelesai;
    }
    
}
