package controller;

import java.awt.Color;
import java.util.ArrayList;

import com.jfoenix.controls.JFXTextField;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.ColorData;
import utils.Colorify;

public class InfoPopupController {

	private double xOffset = 0;
	private double yOffset = 0;
	private Stage primaryStage = null;

	@FXML
	private VBox rootPane;

	@FXML
	private ImageView minimizeButton;

	@FXML
	private ImageView closeButton;

	@FXML
	private JFXTextField colorNameField;

	@FXML
	private JFXTextField rgbField;

	@FXML
	private JFXTextField hexField;
	
	@FXML
	private JFXTextField colorHueField;

	@FXML
	private Text title;

	@FXML
	private Pane topBar;

	public void fillColorInfo(String hex, int box) {
		Color c = Colorify.hexToRGB(hex);
		ColorData color = Colorify.rgbToColorName(c.getRed(), c.getGreen(), c.getBlue());
		ArrayList<Integer> rgb = color.getRgb();
		colorHueField.setText(Colorify.getColorHue(rgb.get(0), rgb.get(1), rgb.get(2)));
		colorNameField.setText(color.getPrettyName());
		rgbField.setText("(" + rgb.get(0) + "," + rgb.get(1) + "," + rgb.get(2) + ")");
		hexField.setText(color.getHex());
		title.setText("#" + box);
		topBar.setStyle("-fx-background-color: " + hex + "; -fx-border-color: black");
	}

	/*
	 * Below are methods that are equal for all "tabs". Including basic
	 * functionality for dragging, tab switching, closing and minimizing.
	 */

	@FXML
	void dragStage(MouseEvent event) {
		primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		primaryStage.setX(event.getScreenX() - xOffset);
		primaryStage.setY(event.getScreenY() - yOffset);
	}

	@FXML
	void getOffset(MouseEvent event) {
		xOffset = event.getSceneX();
		yOffset = event.getSceneY();
	}

	@FXML
	void closeButtonEnter(MouseEvent event) {
		closeButton.setImage(new Image(getClass().getResourceAsStream("/view/images/close_hover.png")));
	}

	@FXML
	void closeButtonPressed(MouseEvent event) {
		closeButton.setImage(new Image(getClass().getResourceAsStream("/view/images/close_click.png")));
	}

	@FXML
	void closeButtonRestore(MouseEvent event) {
		closeButton.setImage(new Image(getClass().getResourceAsStream("/view/images/close.png")));
	}

	@FXML
	void closeButtonClicked(MouseEvent event) {
		((Node) (event.getSource())).getScene().getWindow().hide();
	}

	@FXML
	void minimizeButtonEnter(MouseEvent event) {
		minimizeButton.setImage(new Image(getClass().getResourceAsStream("/view/images/minimize_hover.png")));
	}

	@FXML
	void minimizeButtonPressed(MouseEvent event) {
		minimizeButton.setImage(new Image(getClass().getResourceAsStream("/view/images/minimize_click.png")));
	}

	@FXML
	void minimizeButtonRestore(MouseEvent event) {
		minimizeButton.setImage(new Image(getClass().getResourceAsStream("/view/images/minimize.png")));
	}

	@FXML
	void minimizeButtonClicked(MouseEvent event) {
		primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		primaryStage.setIconified(true);
	}

}
