/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package suryacut;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import suryacut.model.*;

/**
 *
 * @author Irvan
 */
public class FXMLDocumentController implements Initializable {

    @FXML
    private TextField fieldNama;
    @FXML
    private RadioButton rdbPria;
    @FXML
    private RadioButton rdbWanita;
    @FXML
    private ChoiceBox choiceBoxPelayan;
    @FXML
    private TextField fieldPencarian;
    @FXML
    private Button btnCari;
    @FXML
    private Button btnSubmit;
    @FXML
    private Button btnSelesai;
    @FXML
    private Label lblQueueKeramas;
    @FXML
    private TabPane tabTukangCukur;
    @FXML
    private CheckBox chkKeramas;
    @FXML
    private Button btnTmpatKeramas1;
    @FXML
    private Button btnTmpatKeramas2;
    @FXML
    private Button btnTmpatKeramas3;

    private HashMap<String, String> mapLblTextPencukur = new HashMap<String, String>();
    private HashMap<String, Label> mapLblPencukur = new HashMap<String, Label>();
    private Label temp;

    TukangCukur tempTC;
    Pelanggan tempPelanggan;

    String[] antrean;
    String antreanText = "";

    //Object- object tukang cukur dan pelanggan dalam map
    private HashMap<String, TukangCukur> mapTukangCukur;
    private HashMap<String, Pelanggan> mapPelanggan;
    private TempatKeramas tempatKeramas;

    //Menambahkan pelanggan ke antrian yang tepat.
    @FXML
    private void handleBtnSubmit(ActionEvent event) {
        String namaPelanggan = this.fieldNama.getText();
        String namaPelayan = this.choiceBoxPelayan.getSelectionModel().getSelectedItem().toString();
        char gender = (this.rdbPria.isSelected()) ? 'P' : 'W';

        //Menyimpan model pelanggan yang datang
        tempPelanggan = new Pelanggan(namaPelanggan, gender, this.chkKeramas.isSelected());

        //System.out.println(mapLblTextPencukur.get(pencukur));
        tempTC = mapTukangCukur.get(namaPelayan);
        tempTC.insertPelangganIntoQueue(tempPelanggan);
        if (tempTC.getCurrentlyServed() == null) {
            tempTC.serveNext();
            tempPelanggan.setState("Sedang dicukur");
            System.out.println("Sedang dicukur");
        } else {
            tempPelanggan.setState("Menunggu cukur");
            System.out.println("Menunggu");

            //this.mengantrikanKembali(tempTC);
            //code untuk mengatur tampilan queue pada tukang cukur
            mapLblTextPencukur.put(namaPelayan, mapLblTextPencukur.get(namaPelayan) + namaPelanggan + "\n");
            temp = mapLblPencukur.get(namaPelayan);
            temp.setText(mapLblTextPencukur.get(namaPelayan));
            mapLblPencukur.put(namaPelayan, temp);
        }

        mapPelanggan.put(namaPelanggan, tempPelanggan);
        this.fieldNama.clear();
    }

    //Melakukan pop pada antrian dari tukang cukur yang tab panenya sedang aktif
    @FXML
    private void handleBtnSelesai(ActionEvent event) {
        tempTC = mapTukangCukur.get(tabTukangCukur.getSelectionModel().getSelectedItem().getText());
        tempPelanggan = tempTC.getCurrentlyServed();

        if (tempPelanggan.getKeramas()) {
            tempPelanggan.setState("Sedang Keramas");
            System.out.println(tempPelanggan.getNama() + " Keramas");
            this.layaniKeramas(tempPelanggan, 1);
        } else {
            mapPelanggan.remove(tempPelanggan);
        }

        if (tempTC.serveNext()) {
            tempPelanggan = tempTC.getCurrentlyServed();
            tempPelanggan.setState("Sedang dicukur");
            System.out.println(tempPelanggan.getNama() + " Sedang dicukur");
        } else {
            System.out.println("Tukang cukur nganggur");
        }

        this.mengantrikanKembali(tempTC);
    }

    @FXML
    private void handleBtnKeramas(ActionEvent event) {
        Node node = (Node) event.getSource();
        String data = (String) node.getUserData();
        int value = Integer.parseInt(data);

        System.out.println(value);
        tempPelanggan = tempatKeramas.finishServe(value - 1);
        layaniKeramas(tempatKeramas.getNextServed(), 2);

        mapPelanggan.remove(tempPelanggan.getNama());
    }
    
    @FXML
    private void handleSearchButton(ActionEvent event) {
        tempPelanggan = mapPelanggan.get(this.fieldPencarian.getText());
        if(tempPelanggan != null){
            Popup.display(tempPelanggan.getNama(), tempPelanggan.getState());
        }else{
            Popup.display("Pencarian gagal!!", "Nama '"+this.fieldPencarian.getText() +"' tidak ditemukan");
        }
    }

    public void layaniKeramas(Pelanggan pelanggan, int from) {
        //1 = from button selesai, 2 from button keramas
        if (from == 1) {
            tempatKeramas.insertPelangganIntoQueue(pelanggan);
            antreanText = this.lblQueueKeramas.getText() + pelanggan.getNama() + "\n";
        } else {
            antrean = this.lblQueueKeramas.getText().split("\n");
            antreanText = "";
            for (int i = 1; i < antrean.length; i++) {
                antreanText += antrean[i] + "\n";
            }
        }

        //-1 penuh, -2 tidak ada yg mw keramas lagi
        int status = tempatKeramas.servePelangan();
        if (status == -1) {
            pelanggan.setState("Menunggu Keramas");
            System.out.println(pelanggan.getNama() + " Menunggu Keramas");
            this.lblQueueKeramas.setText(antreanText);
        } else if (status == -2) {
            System.out.println("Tempat Keramas Nganggur");
        } else {
            pelanggan.setState("Sedang Keramas");
            System.out.println(pelanggan.getNama() + " Sedang Keramas");
            if (from == 2) {
                this.lblQueueKeramas.setText(antreanText);
            }
        }
    }

    //Membuat tabpane dan inisialisasi tukang cukur.
    public void createTabPane() {
        String namaPencukur[] = {"Dadang", "Sarwa", "Sany", "Brigitta"};
        int i;
        for (i = 0; i < namaPencukur.length; i++) {
            mapLblTextPencukur.put(namaPencukur[i], "");
            mapLblPencukur.put(namaPencukur[i], new Label());
            mapTukangCukur.put(namaPencukur[i], new TukangCukur(namaPencukur[i]));
        }

        for (i = 0; i < namaPencukur.length; i++) {
            Tab tab = new Tab(namaPencukur[i]);
            Label label = new Label();
            label.textProperty().bind(this.mapLblPencukur.get(namaPencukur[i]).textProperty());
            tab.setContent(label);
            tabTukangCukur.getTabs().add(tab);
        }

    }

    //Mengganti isi choice box berdasar gender
    public void changeChoiceBox(String gender) {
        this.choiceBoxPelayan.getItems().clear();
        if (gender.equals("Pria")) {
            this.choiceBoxPelayan.getItems().addAll("Dadang", "Sarwa");
        } else {
            this.choiceBoxPelayan.getItems().addAll("Sany", "Brigitta");
        }
    }

    public void mengantrikanKembali(TukangCukur tc) {
        String[] antrean = mapLblTextPencukur.get(tc.getNama()).split("\n");
        String antreanText = "";

        for (int i = 1; i < antrean.length; i++) {
            antreanText += antrean[i] + "\n";
        }
        mapLblTextPencukur.put(tc.getNama(), antreanText);
        temp = mapLblPencukur.get(tc.getNama());
        temp.setText(mapLblTextPencukur.get(tc.getNama()));
        mapLblPencukur.put(tc.getNama(), temp);

//        Pelanggan[] arrTempPelanggan;
//        System.out.println(tc.getNextServed().getNama());
//        System.out.println(tc.getQueue()[0].getNama());
//        if (tc.getQueue() != null) {
//            arrTempPelanggan = (Pelanggan[]) tc.getQueue();
//            for (int i = 0; i < arrTempPelanggan.length; i++) {
//                //code untuk mengatur tampilan queue pada tukang cukur
//                mapLblTextPencukur.put(tc.getNama(), mapLblTextPencukur.get(tc.getNama()) + "\n" + arrTempPelanggan[i].getNama());
//                temp = mapLblPencukur.get(tc.getNama());
//                temp.setText(mapLblTextPencukur.get(tc.getNama()));
//                mapLblPencukur.put(tc.getNama(), temp);
//            }
//        } else {
//            System.out.println("Tidak ada lagi di antrian");
//        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Buat tukang cukurnya di sini.
        mapTukangCukur = new HashMap<String, TukangCukur>();
        mapPelanggan = new HashMap<String, Pelanggan>();
        tempatKeramas = TempatKeramas.getInstance();
        this.createTabPane();

        //Membuat group untuk radio button
        ToggleGroup groupRdbGender = new ToggleGroup();
        this.rdbPria.setToggleGroup(groupRdbGender);
        rdbPria.setUserData("Pria");
        this.rdbWanita.setToggleGroup(groupRdbGender);
        rdbWanita.setUserData("Wanita");

        //Handle radio button ketika dipilih, mengganti konten dari choicebox
        groupRdbGender.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            public void changed(ObservableValue<? extends Toggle> ov,
                    Toggle old_toggle, Toggle new_toggle) {
                if (groupRdbGender.getSelectedToggle() != null) {
                    changeChoiceBox(groupRdbGender.getSelectedToggle().getUserData().toString());
                }
            }
        });

    }
}
