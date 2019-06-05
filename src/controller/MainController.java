package controller;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.sun.javafx.PlatformUtil;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import utils.Colorify;
import model.ColorData;

public class MainController {

	private double xOffset = 0;
	private double yOffset = 0;
	private Stage primaryStage = null;

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
	private Button grabButton;

	@FXML
	private ImageView closeButton;

	@FXML
	private ImageView minimizeButton;

	@FXML
	private JFXTextField colorHueField;
	
	@FXML
	private JFXTextField colorNameField;

	@FXML
	private JFXTextField rgbField;

	@FXML
	private JFXTextField hexField;

	@FXML
	void grabColor(ActionEvent event) {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setUndecorated(true);
		frame.setLayout(null);
		frame.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
		
		
		// Check if UNIX
		if (PlatformUtil.isLinux() || PlatformUtil.isMac()) {
			frame.getContentPane().setPreferredSize(Toolkit.getDefaultToolkit().getScreenSize());
			frame.pack();
			frame.setResizable(false);			
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					Point p = new Point(0, 0);
					SwingUtilities.convertPointToScreen(p, frame.getContentPane());
					Point l = frame.getLocation();
					l.x -= p.x;
					l.y -= p.y;
					frame.setLocation(p);
				}
			});
			frame.setBackground(new Color(0, 0, 0, 0));
			frame.setVisible(true);
			frame.setBounds(100, 100, (int) screenSize.getWidth(), (int) screenSize.getHeight());
			frame.setLocationRelativeTo(null);
		} else if (PlatformUtil.isWindows()) {
			frame.setOpacity((float) 0.01);
			frame.setVisible(true);
			frame.setSize((int) screenSize.getWidth() ,(int) screenSize.getHeight());
			frame.setExtendedState(Frame.MAXIMIZED_BOTH);
			frame.setLocationRelativeTo(null);
		}
		frame.addMouseListener(new MouseAdapter() {
			@Override
		     public void mouseClicked(java.awt.event.MouseEvent e) {		    
		    	int x = (int) Math.round(e.getX());
				int y = (int) Math.round(e.getY());
				BufferedImage img = Colorify.takeSS(screenSize.getWidth(), screenSize.getHeight());
				Color clickedPixel = Colorify.getPixelValue(img, x, y);
				ColorData color = Colorify.rgbToColorName(clickedPixel.getRed(), clickedPixel.getGreen(),
						clickedPixel.getBlue());			   
				ArrayList<Integer> rgb = color.getRgb();
				colorHueField.setText(Colorify.getColorHue(rgb.get(0), rgb.get(1), rgb.get(2)));
				colorNameField.setText(color.getPrettyName());
				rgbField.setText("(" + rgb.get(0) + "," + rgb.get(1) + "," + rgb.get(2) + ")");
				hexField.setText(color.getHex());
				frame.setVisible(false);
		     }
		  });
	}

	/*
	 * Below are methods that are equal for all "tabs". Including basic
	 * functionality for dragging, tab switching, closing and minimizing.
	 */

	@FXML
	void switchToSchemes(MouseEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/SchemesWindow.fxml"));
		VBox schemesPalette = (VBox) loader.load();
		rootPane.getChildren().setAll(schemesPalette);
		if (hexField.getText().length() >= 4) {
			Color grabbedColor = Colorify.hexToRGB(hexField.getText());
			String hue = colorHueField.getText();
			loader.<SchemesController>getController().onSwitchFromMain(grabbedColor, hue);
		}
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

}
