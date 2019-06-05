package controller;

import java.awt.Color;
import java.io.IOException;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.ColorData;
import utils.Colorify;

public class SchemesController {

	private double xOffset = 0;
	private double yOffset = 0;
	private Stage primaryStage = null;
	ObservableList<String> schemaTypeList = FXCollections.observableArrayList("Brightness", "Hue", "Saturation");

	@FXML
	private VBox rootPane;

	@FXML
	private JFXButton homeButton;

	@FXML
	private JFXButton paletteButton;

	@FXML
	private JFXButton schemesButton;

	@FXML
	private Pane topBar;

	@FXML
	private ImageView closeButton;

	@FXML
	private ImageView minimizeButton;

	@FXML
	private Pane colorBox1;

	@FXML
	private Pane colorBox2;

	@FXML
	private Pane colorBox3;

	@FXML
	private Pane colorBox4;

	@FXML
	private Pane colorBox5;

	@FXML
	private JFXTextField schemeColor1;

	@FXML
	private JFXTextField schemeColor2;

	@FXML
	private JFXTextField schemeColor3;

	@FXML
	private JFXTextField schemeColor4;

	@FXML
	private JFXTextField schemeColor5;

	@FXML
	private Button generateButton;

	@FXML
	private JFXTextField colorHueField;
	
	@FXML
	private JFXTextField colorNameField;

	@FXML
	private ColorPicker colorPicker;

	@FXML
	private ChoiceBox<String> schemaBox;

	@FXML
	private Button previewButton1;

	@FXML
	void setColors(ActionEvent event) {
		javafx.scene.paint.Color fx = colorPicker.getValue();
		Color selectedColor = new java.awt.Color((float) fx.getRed(), (float) fx.getGreen(), (float) fx.getBlue(),
				(float) fx.getOpacity());
		generateScheme((int) selectedColor.getRed(), (int) selectedColor.getGreen(), (int) selectedColor.getBlue());
	}

	@FXML
	public void generateScheme(int r, int g, int b) {
		ColorData color = Colorify.rgbToColorName(r, g, b);
		ColorData[] c = null;
		if (schemaBox.getValue() == "Hue") {
			c = Colorify.generateHueScheme(color.getRgb(), 4);
		} else if (schemaBox.getValue() == "Brightness") {
			c = Colorify.generateBrightnessScheme(color.getRgb(), 4);
		} else if (schemaBox.getValue() == "Saturation") {
			c = Colorify.generateSaturationScheme(color.getRgb(), 4);
		}
	 
		// Update the background of each box.
		String hex = Colorify.rgbToHex(r, g, b);
		colorBox1.setStyle("-fx-background-color: " + hex);
		colorBox2.setStyle("-fx-background-color: " + c[0].getHex());
		colorBox3.setStyle("-fx-background-color: " + c[1].getHex());
		colorBox4.setStyle("-fx-background-color: " + c[2].getHex());
		colorBox5.setStyle("-fx-background-color: " + c[3].getHex());

		// Update the HEX-value below each box.
		schemeColor1.setText(hex);
		schemeColor2.setText(c[0].getHex());
		schemeColor3.setText(c[1].getHex());
		schemeColor4.setText(c[2].getHex());
		schemeColor5.setText(c[3].getHex());
	}

	@FXML
	void updateSelectedColor(ActionEvent event) {
		javafx.scene.paint.Color fx = colorPicker.getValue();
		Color selectedColor = new java.awt.Color((float) fx.getRed(), (float) fx.getGreen(), (float) fx.getBlue(),
				(float) fx.getOpacity());
		ColorData color = Colorify.rgbToColorName((int) selectedColor.getRed(), (int) selectedColor.getGreen(),
				(int) selectedColor.getBlue());
		colorHueField.setText(Colorify.getColorHue((int) selectedColor.getRed(), (int) selectedColor.getGreen(),
				(int) selectedColor.getBlue()));
		colorNameField.setText(color.getPrettyName());
	}

	@FXML
	void previewScheme(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ExampleWindow.fxml"));
			VBox root = (VBox) loader.load();
			Scene scene = new Scene(root, 640, 400);
			Stage examplePopup = new Stage();
			examplePopup.setScene(scene);
			examplePopup.initStyle(StageStyle.UNDECORATED);
			examplePopup.setMinWidth(640);
			examplePopup.setMinHeight(400);
			examplePopup.setMaxWidth(640);
			examplePopup.setMaxHeight(400);
			examplePopup.getIcons().add(new Image(getClass().getResourceAsStream("/view/images/icon.png")));
			examplePopup.setTitle("Colorify Mock");
			examplePopup.show();
			loader.<ExampleController>getController().injectStyle(schemeColor1.getText(), schemeColor2.getText(),
					schemeColor3.getText(), schemeColor4.getText(), schemeColor5.getText());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	void copyContent(MouseEvent event) {
		Node node = ((Node) event.getTarget());
		final Clipboard clipboard = Clipboard.getSystemClipboard();
		final ClipboardContent content = new ClipboardContent();
		if (node instanceof JFXTextField) {
			content.putString(((JFXTextField) node).getText());
		}

		if (node instanceof Text) {
			content.putString(((Text) node).getText());
		}
		clipboard.setContent(content);
	}

	@FXML
	void colorBox1Click(MouseEvent event) {
		infoPopup(schemeColor1.getText(), 1);
	}

	@FXML
	void colorBox2Click(MouseEvent event) {
		infoPopup(schemeColor2.getText(), 2);
	}

	@FXML
	void colorBox3Click(MouseEvent event) {
		infoPopup(schemeColor3.getText(), 3);
	}

	@FXML
	void colorBox4Click(MouseEvent event) {
		infoPopup(schemeColor4.getText(), 4);
	}

	@FXML
	void colorBox5Click(MouseEvent event) {
		infoPopup(schemeColor5.getText(), 5);
	}

	@FXML
	void infoPopup(String hex, int box) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/InfoPopupWindow.fxml"));
			AnchorPane root = (AnchorPane) loader.load();
			Scene scene = new Scene(root, 250, 260);
			Stage examplePopup = new Stage();
			examplePopup.setScene(scene);
			examplePopup.initStyle(StageStyle.UNDECORATED);
			examplePopup.setMinWidth(250);
			examplePopup.setMinHeight(260);
			examplePopup.setMaxWidth(250);
			examplePopup.setMaxHeight(260);
			examplePopup.getIcons().add(new Image(getClass().getResourceAsStream("/view/images/icon.png")));
			examplePopup.setTitle("Colorify Mock");
			examplePopup.show();
			loader.<InfoPopupController>getController().fillColorInfo(hex, box);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void onSwitchFromMain(Color c, String hue) {
		generateScheme(c.getRed(), c.getGreen(), c.getBlue());
		ColorData color = Colorify.rgbToColorName(c.getRed(), c.getGreen(), c.getBlue());
		colorNameField.setText(color.getPrettyName());
		colorHueField.setText(hue);
	}

	@FXML
	void initialize() {
		schemaBox.setItems(schemaTypeList);
		schemaBox.getSelectionModel().selectFirst();
		colorPicker.valueProperty().addListener((observable, oldValue, newValue) -> {
			updateSelectedColor(null);
		});
	}

	/*
	 * Below are methods that are equal for all "tabs". Including basic
	 * functionality for dragging, tab switching, closing and minimizing.
	 */

	@FXML
	void switchToHome(MouseEvent event) throws IOException {
		VBox palettePane = FXMLLoader.load(getClass().getResource("/view/MainWindow.fxml"));
		rootPane.getChildren().setAll(palettePane);
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
		Platform.exit();
		System.exit(0);
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

}
