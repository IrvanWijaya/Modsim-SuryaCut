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
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;

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

    private HashMap<String, String> mapLblTextPencukur = new HashMap<String, String>();
    private HashMap<String, Label> mapLblPencukur = new HashMap<String, Label>();
    private Label temp;

    @FXML
    private void handleBtnSubmit(ActionEvent event) {
        String pencukur = this.choiceBoxPelayan.getSelectionModel().getSelectedItem().toString();
//        dd += "\n" + this.fieldNama.getText();
//        dadangQ.setText(dd);
        mapLblTextPencukur.put(pencukur, mapLblTextPencukur.get(pencukur) + "\n" + this.fieldNama.getText());
        temp = mapLblPencukur.get(pencukur);
        temp.setText(mapLblTextPencukur.get(pencukur));
        mapLblPencukur.put(pencukur, temp);

        this.fieldNama.clear();
    }
    
    @FXML
    private void handleBtnSelesai(ActionEvent event) {
        System.out.println(tabTukangCukur.getSelectionModel().getSelectedItem().getText());
    }

    public void changeChoiceBox(String gender) {
        this.choiceBoxPelayan.getItems().clear();
        if (gender.equals("Pria")) {
            this.choiceBoxPelayan.getItems().addAll("Dadang", "Sarwa");
        } else {
            this.choiceBoxPelayan.getItems().addAll("Sany", "Brigitta");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.start();

        ToggleGroup groupRdbGender = new ToggleGroup();
        this.rdbPria.setToggleGroup(groupRdbGender);
        rdbPria.setUserData("Pria");
        this.rdbWanita.setToggleGroup(groupRdbGender);
        rdbWanita.setUserData("Wanita");

        groupRdbGender.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            public void changed(ObservableValue<? extends Toggle> ov,
                    Toggle old_toggle, Toggle new_toggle) {
                if (groupRdbGender.getSelectedToggle() != null) {
                    changeChoiceBox(groupRdbGender.getSelectedToggle().getUserData().toString());
                }
            }
        });

    }

    public void start() {
        //Buat tukang cukurnya di sini.
        String namaPencukur[] = {"Dadang", "Sarwa", "Sany", "Brigitta"};
        int i;
        for (i = 0; i < namaPencukur.length; i++) {
            mapLblTextPencukur.put(namaPencukur[i], "");
            mapLblPencukur.put(namaPencukur[i], new Label());
        }

        for (i = 0; i < namaPencukur.length; i++) {
            Tab tab = new Tab(namaPencukur[i]);
            Label label = new Label();
            label.textProperty().bind(this.mapLblPencukur.get(namaPencukur[i]).textProperty());
            tab.setContent(label);
            tabTukangCukur.getTabs().add(tab);
        }

    }
}
