package controller;

import java.awt.Color;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import utils.Colorify;

public class ExampleController {

	private double xOffset = 0;
	private double yOffset = 0;
	private Stage primaryStage = null;

	@FXML
	private VBox rootPane;

	@FXML
	private ImageView closeButton;

	@FXML
	private Text titleText;

	@FXML
	private Pane example1;

	@FXML
	private JFXTextArea example2;

	@FXML
	private JFXButton example3;

	@FXML
	private Pane example4;

	@FXML
	private JFXButton example5;

	public void injectStyle(String hex1, String hex2, String hex3, String hex4, String hex5) {
		example1.setStyle("-fx-background-color: " + hex1);

		float[] hsv1 = new float[3];
		Color hex1rgb = Colorify.hexToRGB(hex2);
		Color.RGBtoHSB(hex1rgb.getRed(), hex1rgb.getGreen(), hex1rgb.getBlue(), hsv1);
		int rgb1 = Color.HSBtoRGB((float) (hsv1[0] + 0.5), hsv1[1], hsv1[2]);
		int red1 = (rgb1 >> 16) & 0xFF;
		int green1 = (rgb1 >> 8) & 0xFF;
		int blue1 = rgb1 & 0xFF;
		// example2.setStyle("-fx-background-color: " + hex2 + "; " + "-fx-text-fill:
		// rgb(" + red1 + ", " + green1 + ", "
		// + blue1 + ")");
		example2.setStyle("-fx-text-fill: " + hex2);

		float[] hsv2 = new float[3];
		Color hex2rgb = Colorify.hexToRGB(hex3);
		Color.RGBtoHSB(hex2rgb.getRed(), hex2rgb.getGreen(), hex2rgb.getBlue(), hsv2);
		int rgb2 = Color.HSBtoRGB((float) (hsv2[0] + 0.5), hsv2[1], hsv2[2]);
		int red2 = (rgb2 >> 16) & 0xFF;
		int green2 = (rgb2 >> 8) & 0xFF;
		int blue2 = rgb2 & 0xFF;
		example3.setStyle("-fx-background-color: " + hex3 + "; " + "-fx-text-fill: rgb(" + red2 + ", " + green2 + ", "
				+ blue2 + ")");

		example4.setStyle("-fx-background-color: " + hex4);

		float[] hsv3 = new float[3];
		Color hex3rgb = Colorify.hexToRGB(hex5);
		Color.RGBtoHSB(hex3rgb.getRed(), hex3rgb.getGreen(), hex3rgb.getBlue(), hsv3);
		int rgb3 = Color.HSBtoRGB((float) (hsv3[0] + 0.5), hsv3[1], hsv3[2]);
		int red3 = (rgb3 >> 16) & 0xFF;
		int green3 = (rgb3 >> 8) & 0xFF;
		int blue3 = rgb3 & 0xFF;
		// example5.setStyle("-fx-background-color: " + hex5 + "; " + "-fx-text-fill:
		// rgb(" + red3 + ", " + green3 + ", " + blue3 + ")");
		example5.setStyle(
				"-fx-background-color: " + hex5 + "; " + "-fx-text-fill: rgb(" + 255 + ", " + 255 + ", " + 255 + ")");

	}

	/*
	 * Below are methods that are equal for all "tabs". Including basic functionality
	 * for dragging, tab switching, closing and minimizing.
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

}
