/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package suryacut;

import java.io.IOException;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import suryacut.model.simulation.Simulator;

/**
 * FXML Controller class
 *
 * @author Irvan
 */
public class FXMLSimulasiController implements Initializable {

    @FXML
    private AnchorPane rootPane;
    @FXML
    private Button btnBack;
    @FXML
    private Button btnSubmit;
    @FXML
    private TextField txtJumlahPelanggan;
    @FXML
    private TextField txtJumlahPencukur;
    @FXML
    private TextField txtJumlahPengeramas;
    @FXML
    private RadioButton rdbYa;
    @FXML
    private RadioButton rdbTidak;
    @FXML
    private RadioButton rdbLamaKerja;
    @FXML
    private RadioButton rdbNomor;
    @FXML
    private Label lblHasil;
    @FXML
    private Button btnRandomTc;

    private boolean bolehMilih;
    private int utilitas;
    private int waktuKedatangan[];
    private int tukangCukurPilihan[];
    private int banyakPelanggan;
    private int banyakTukangCukur;
    private int banyakTukangKeramas;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        ToggleGroup groupPilihPencukur = new ToggleGroup();
        this.rdbYa.setToggleGroup(groupPilihPencukur);
        rdbYa.setUserData("Ya");
        this.rdbTidak.setToggleGroup(groupPilihPencukur);
        rdbTidak.setUserData("Tidak");

        ToggleGroup groupUrutanKerja = new ToggleGroup();
        this.rdbLamaKerja.setToggleGroup(groupUrutanKerja);
        rdbLamaKerja.setUserData("Utilitas");
        this.rdbNomor.setToggleGroup(groupUrutanKerja);
        rdbNomor.setUserData("Nomor");

        groupPilihPencukur.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            public void changed(ObservableValue<? extends Toggle> ov,
                    Toggle old_toggle, Toggle new_toggle) {
                if (groupPilihPencukur.getSelectedToggle() != null) {
                    if (groupPilihPencukur.getSelectedToggle().getUserData().toString().equals("Ya")) {
                        bolehMilih = true;
                    } else {
                        bolehMilih = false;
                    }
                }
            }
        });

        groupUrutanKerja.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            public void changed(ObservableValue<? extends Toggle> ov,
                    Toggle old_toggle, Toggle new_toggle) {
                if (groupUrutanKerja.getSelectedToggle() != null) {
                    if (groupUrutanKerja.getSelectedToggle().getUserData().toString().equals("Utilitas")) {
                        utilitas = 1;
                    } else {
                        utilitas = 0;
                    }
                }
            }
        });
    }

    @FXML
    public void loadSistem() throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        rootPane.getChildren().setAll(pane);
    }

    @FXML
    public void submit() throws IOException {

        Simulator sm = new Simulator(banyakTukangCukur, banyakTukangKeramas);

        int i;
        for (i = 0; i < waktuKedatangan.length; i++) {
            int selesaiCukur;
            if (bolehMilih) {
                selesaiCukur = sm.insertCukur(waktuKedatangan[i], tukangCukurPilihan[i], utilitas);
            } else {
                selesaiCukur = sm.insertCukur(waktuKedatangan[i], -1, utilitas);
            }

            sm.insertKeramas(selesaiCukur, utilitas);
        }

        String hasil = "Waktu kedatangan Pelanggan \n";

        for (i = 0; i < banyakPelanggan; i++) {
            hasil += waktuKedatangan[i];
            if (i != banyakPelanggan - 1) {
                hasil += ", ";
            }
        }
        hasil += "\n";
        if (bolehMilih) {
            hasil += "Pemilihan tukang cukur tiap pelanggan \n(-1 berarti pelanggan mau dicukur oleh siapapun, 1 berarti tukang cukur 1, dst)\n";
            for (i = 0; i < banyakPelanggan; i++) {
                hasil += tukangCukurPilihan[i];
                if (i != banyakPelanggan - 1) {
                    hasil += ", ";
                }
            }
            hasil += "\n";
        }

        hasil += "Waktu akhir simulasi : " + sm.getWaktuAkhirSimulasi() + "\n"
                + "Rata- rata delay : " + sm.getDelayAverage(banyakPelanggan) + "\n"
                + "Rata- rata wait : " + sm.getWaitAverage(banyakPelanggan) + "\n";

        lblHasil.setText(hasil);
    }

    @FXML
    public void randomUlang() {
        this.banyakPelanggan = Integer.parseInt(txtJumlahPelanggan.getText());
        this.banyakTukangCukur = Integer.parseInt(txtJumlahPencukur.getText());
        this.banyakTukangKeramas = Integer.parseInt(txtJumlahPengeramas.getText());

        this.waktuKedatangan = new int[banyakPelanggan];
        this.tukangCukurPilihan = new int[banyakPelanggan];
        Random rand = new Random();

        int waktu = 0;

        int i;
        for (i = 0; i < waktuKedatangan.length; i++) {
            waktu = waktu + rand.nextInt(10) + 1;
            waktuKedatangan[i] = waktu;
            tukangCukurPilihan[i] = rand.nextInt(banyakTukangCukur + 1) - 1;
        }
    }

}
