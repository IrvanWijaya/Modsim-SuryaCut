/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package suryacut;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author Irvan
 */
public class Popup {

    public static void display(String nama, String state, String estimasi) {
        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Hasil Pencarian");
        window.setMinWidth(250);

        Label labelNama = new Label();
        labelNama.setText(nama);
        Label labelState = new Label();
        labelState.setText(state);
        Label labelEstimasi = new Label();
        if (state.equals("Sedang keramas") || state.equals("Sedang dicukur")) {
            labelEstimasi.setText("Sedang dilayani");
        } else {
            labelEstimasi.setText(estimasi);
        }

        Button okay = new Button("Okay");
        okay.setOnAction(e -> window.close());

        VBox layout = new VBox(10);
        layout.getChildren().addAll(labelNama, labelState, labelEstimasi, okay);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
    }

}
