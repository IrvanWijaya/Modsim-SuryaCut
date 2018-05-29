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
public class Simulator {

    private TcSimulator[] tukangCukur;
    private TkSimulator[] tukangKeramas;

    public Simulator(int jumlahTc, int jumlahTk) {
        int i;
        tukangCukur = new TcSimulator[jumlahTc];
        for (i = 0; i < jumlahTc; i++) {
            tukangCukur[i] = new TcSimulator();
        }
        tukangKeramas = new TkSimulator[jumlahTk];
        for (i = 0; i < jumlahTk; i++) {
            tukangKeramas[i] = new TkSimulator();
        }
    }

    public int insertCukur(int waktuKedatangan, int tcPilihan, int utilitas) {
        //-1 artinya bebas tukang cukurnya

        if (tcPilihan == -1) {
            int pilihan = 0;
            //1 berarti berdasar orang yang paling banyak nganggur
            if (utilitas == 1) {
                int lamaKerja = Integer.MAX_VALUE;
                for (int i = 0; i < tukangCukur.length; i++) {
                    if (lamaKerja > tukangCukur[i].getTotalKerja()) {
                        lamaKerja = tukangCukur[i].getTotalKerja();
                        pilihan = i;
                    }
                }
            } else {
                int tercepat = Integer.MAX_VALUE;
                for (int i = 0; i < tukangCukur.length; i++) {
                    if (tercepat > tukangCukur[i].getWaktuSelesaiMelayani()) {
                        tercepat = tukangCukur[i].getWaktuSelesaiMelayani();
                        pilihan = i;
                    }
                }
            }

            return tukangCukur[pilihan].insertPelanggan(waktuKedatangan);

        } else {
            return tukangCukur[tcPilihan].insertPelanggan(waktuKedatangan);
        }
    }

    public void insertKeramas(int waktuKedatangan, int utilitas) {
        //-1 artinya bebas tukang cukurnya
        int pilihan = 0;
        //1 berarti berdasar orang yang paling banyak nganggur
        if (utilitas == 1) {
            int lamaKerja = Integer.MAX_VALUE;
            for (int i = 0; i < tukangCukur.length; i++) {
                if (lamaKerja > tukangCukur[i].getTotalKerja()) {
                    lamaKerja = tukangCukur[i].getTotalKerja();
                    pilihan = i;
                }
            }
        } else {
            int tercepat = Integer.MAX_VALUE;
            for (int i = 0; i < tukangCukur.length; i++) {
                if (tercepat > tukangCukur[i].getWaktuSelesaiMelayani()) {
                    tercepat = tukangCukur[i].getWaktuSelesaiMelayani();
                    pilihan = i;
                }
            }
        }

        tukangKeramas[pilihan].insertPelanggan(waktuKedatangan);

    }

    public int getWaktuAkhirSimulasi() {
        int waktuAkhir = 0;

        for (int i = 0; i < tukangKeramas.length; i++) {
            waktuAkhir = Math.max(waktuAkhir, tukangKeramas[i].getWaktuSelesaiMelayani());
        }

        return waktuAkhir;
    }
    
    public double getDelayAverage(int jumlahPelanggan){
        int total = 0;
        int i,j;
        for(i = 0; i < tukangCukur.length; i++){
            total += tukangCukur[i].getTotalDelay();
        }
        
        for(i = 0; i < tukangCukur.length; i++){
            total += tukangKeramas[i].getTotalDelay();
        }
        
        return total * 1.0/jumlahPelanggan;
    }

}
