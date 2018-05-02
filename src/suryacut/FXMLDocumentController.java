/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package suryacut;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
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
//    @FXML
//    private Label lblQueueKeramas;
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
    @FXML
    private ListView lvQueueKeramas;
    private List<String> listQueueKeramas = new ArrayList<>();
    private ListProperty<String> propertyQueueKeramas = new SimpleListProperty<>();

    //Untuk list view
    //Set nilainya dengan hmObservedList
    private HashMap<String, ListProperty<String>> hmListPropertyPencukur = new HashMap<String, ListProperty<String>>();
    //Ubah array list nya untuk mengupdate nilai list property
    private HashMap<String, List<String>> hmObservedList = new HashMap<String, List<String>>();

    //diganti sama listProperty
//    private HashMap<String, String> mapLblTextPencukur = new HashMap<String, String>();
//    private HashMap<String, Label> mapLblPencukur = new HashMap<String, Label>();
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
        tempPelanggan = new Pelanggan(namaPelanggan, gender, this.chkKeramas.isSelected(), namaPelayan);

        //System.out.println(mapLblTextPencukur.get(pencukur));
        tempTC = mapTukangCukur.get(namaPelayan);
        tempTC.insertPelangganIntoQueue(tempPelanggan);
        if (tempTC.getCurrentlyServed() == null) {
            tempTC.serveNext();
            tempPelanggan.setState("Sedang dicukur");
            tempPelanggan.setWaktuDilayani(System.currentTimeMillis());
            System.out.println("Sedang dicukur");
        } else {
            tempPelanggan.setState("Menunggu cukur");
            System.out.println("Menunggu");

            //code untuk mengatur tampilan queue pada tukang cukur
            hmObservedList.get(namaPelayan).add(namaPelanggan);
            hmListPropertyPencukur.get(namaPelayan)
                    .set(FXCollections.observableArrayList(hmObservedList.get(namaPelayan)));
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
            tempPelanggan.setState("Sedang keramas");
            tempPelanggan.setWaktuDilayani(System.currentTimeMillis());
            System.out.println(tempPelanggan.getNama() + " Keramas");
            this.layaniKeramas(tempPelanggan, 1);
        } else {
            mapPelanggan.remove(tempPelanggan);
        }

        if (tempTC.serveNext()) {
            tempPelanggan = tempTC.getCurrentlyServed();
            tempPelanggan.setState("Sedang dicukur");
            tempPelanggan.setWaktuDilayani(System.currentTimeMillis());
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
        layaniKeramas(tempatKeramas.getNextServed(), 0);

        mapPelanggan.remove(tempPelanggan.getNama());
    }

    public void layaniKeramas(Pelanggan pelanggan, int from) {

        //from 1  = btn selesai, else = btn keramas
        if (from == 1) {
            tempatKeramas.insertPelangganIntoQueue(pelanggan);
        }

        //-1 penuh, -2 tidak ada yg mw keramas lagi
        int status = tempatKeramas.servePelangan();
        if (status == -1) {
            pelanggan.setState("Menunggu Keramas");
            System.out.println(pelanggan.getNama() + " Menunggu Keramas");
            listQueueKeramas.add(pelanggan.getNama());
            propertyQueueKeramas.set(FXCollections.observableArrayList(listQueueKeramas));
            //this.lblQueueKeramas.setText(antreanText);
        } else if (status == -2) {
            System.out.println("Tempat Keramas Nganggur");
        } else {
            pelanggan.setState("Sedang keramas");
            pelanggan.setWaktuDilayani(System.currentTimeMillis());
            System.out.println(pelanggan.getNama() + " Sedang Keramas");
            if (!listQueueKeramas.isEmpty()) {
                listQueueKeramas.remove(0);
                propertyQueueKeramas.set(FXCollections.observableArrayList(listQueueKeramas));
            }

        }
    }

    public void mengantrikanKembali(TukangCukur tc) {
        if (!hmObservedList.get(tc.getNama()).isEmpty()) {
            hmObservedList.get(tc.getNama()).remove(0);
        }
        hmListPropertyPencukur.get(tc.getNama())
                .set(FXCollections.observableArrayList(hmObservedList.get(tc.getNama())));
    }

    @FXML
    private void handleSearchButton(ActionEvent event) {
        tempPelanggan = mapPelanggan.get(this.fieldPencarian.getText());
        //System.out.println(estimasi);
        if (tempPelanggan == null) {
            Popup.display("Pencarian gagal!!", "Nama '" + this.fieldPencarian.getText()
                    + "' tidak ditemukan", "Anda mungkin melakukan kesalahan pengetikan nama.");
        } else {
            int pengali = (tempPelanggan.getState().equals("Menunggu Keramas")) ? 5 : 20;
            tempTC = mapTukangCukur.get(tempPelanggan.getNamaPelayan());

            //System.out.println(tempPelanggan.getNama());
            int tempUrutan = tempTC.getUrutanPelanggan(tempPelanggan);
            long waktuSekarang = System.currentTimeMillis();
            int estimasi = 0;
            if (pengali == 5) {
                tempUrutan = tempatKeramas.getUrutanPelanggan(tempPelanggan);
                estimasi = (tempUrutan + 1) * 5;
            } else {
                long waktuPelayanan = mapTukangCukur.get(tabTukangCukur.getSelectionModel().getSelectedItem().getText())
                        .getCurrentlyServed()
                        .getWaktuDilayani();
                int menungguYangDilayani = 20 - (int) (((waktuSekarang - waktuPelayanan) / (1000 * 60)) % 60);
                estimasi = menungguYangDilayani + (tempUrutan) * 20;
            }
            Popup.display(tempPelanggan.getNama(), tempPelanggan.getState(), "Perkiraan akan dilayani dalam " + estimasi + " menit");
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

    //Membuat tabpane dan inisialisasi tukang cukur.
    public void createTabPane() {
        String namaPencukur[] = {"Dadang", "Sarwa", "Sany", "Brigitta"};
        int i;

        for (i = 0; i < namaPencukur.length; i++) {
            //Siapkan list
            hmListPropertyPencukur.put(namaPencukur[i], new SimpleListProperty<>());
            hmObservedList.put(namaPencukur[i], new ArrayList<>());

            //buat tab berisi listview yang sudah terbind
            Tab tab = new Tab(namaPencukur[i]);
            ListView lv = new ListView();
            lv.itemsProperty().bind(hmListPropertyPencukur.get(namaPencukur[i]));

            tab.setContent(lv);
            tabTukangCukur.getTabs().add(tab);

            //inisialisasi tukang cukur
            mapTukangCukur.put(namaPencukur[i], new TukangCukur(namaPencukur[i]));

            lv.setOnMouseClicked(new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent click) {
                    //System.out.println(tabTukangCukur.getSelectionModel().getSelectedItem().getText());
                    if (click.getClickCount() == 2) {
                        tempPelanggan = mapPelanggan.get(lv.getSelectionModel()
                                .getSelectedItem());
                        //System.out.println(tempPelanggan.getNama());
                        int tempUrutan = mapTukangCukur.get(tabTukangCukur.getSelectionModel().getSelectedItem().getText())
                                .getUrutanPelanggan(tempPelanggan);
                        long waktuSekarang = System.currentTimeMillis();
                        long waktuPelayanan = mapTukangCukur.get(tabTukangCukur.getSelectionModel().getSelectedItem().getText())
                                .getCurrentlyServed()
                                .getWaktuDilayani();
                        int menungguYangDilayani = 20 - (int) (((waktuSekarang - waktuPelayanan) / (1000 * 60)) % 60);
                        int estimasi = menungguYangDilayani + (tempUrutan) * 20;
                        //System.out.println(estimasi);
                        if (tempPelanggan != null) {
                            Popup.display(tempPelanggan.getNama(), tempPelanggan.getState(), "Perkiraan akan dilayani dalam " + estimasi + " menit");
                        } else {
                            Popup.display("Pencarian gagal!!", "Nama '" + lv.getSelectionModel()
                                    .getSelectedItem() + "' tidak ditemukan", "Anda mungkin melakukan kesalahan pengetikan nama.");
                        }
                    }
                }
            });

        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Buat tukang cukurnya di sini.
        mapTukangCukur = new HashMap<String, TukangCukur>();
        mapPelanggan = new HashMap<String, Pelanggan>();
        tempatKeramas = TempatKeramas.getInstance();

        //buat tab pane
        this.createTabPane();

        //bind observable dan listview queue keramas
        lvQueueKeramas.itemsProperty().bind(propertyQueueKeramas);

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

        lvQueueKeramas.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent click) {
                if (click.getClickCount() == 2) {
                    tempPelanggan = mapPelanggan.get(lvQueueKeramas.getSelectionModel()
                            .getSelectedItem());
                    //System.out.println(tempPelanggan.getNama());
                    int tempUrutan = tempatKeramas.getUrutanPelanggan(tempPelanggan);
                    int estimasi = (tempUrutan + 1) * 5;
                    //System.out.println(estimasi);
                    if (tempPelanggan != null) {
                        Popup.display(tempPelanggan.getNama(), tempPelanggan.getState(), "Perkiraan akan dilayani dalam " + estimasi + " menit");
                    } else {
                        Popup.display("Pencarian gagal!!", "Nama '" + lvQueueKeramas.getSelectionModel()
                                .getSelectedItem() + "' tidak ditemukan", "Anda mungkin melakukan kesalahan pengetikan nama.");
                    }
                }
            }
        });
    }
}
