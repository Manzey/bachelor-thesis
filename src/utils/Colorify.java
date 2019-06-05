package utils;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;

import org.json.simple.JSONObject;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.ColorData;
import model.ColorHue;

public class Colorify {

	static ArrayList<ColorData> colors = new ArrayList<ColorData>();

	/*
	 * Function to read all the colors from the json-file to avoid having to call
	 * the json-file everytime. Should be run on start.
	 */
	public void readColors() throws FileNotFoundException, IOException {
		URL url = getClass().getResource("/utils/Colors.json");
		// objectMapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
		ObjectMapper mapper = new ObjectMapper();
		@SuppressWarnings("unchecked")
		Map<String, Object> map = mapper.readValue(url, Map.class);
		JSONObject obj = new JSONObject(map);
		for (@SuppressWarnings("unchecked")
		Iterator<String> iterator = obj.keySet().iterator(); iterator.hasNext();) {
			String key = (String) iterator.next();
			JsonNode jsonNodeMap = mapper.convertValue(obj.get(key), JsonNode.class);
			JSONObject color = mapper.convertValue(obj.get(key), JSONObject.class);
			JsonNode rgb = jsonNodeMap.get("rgb");
			ArrayList<Integer> rgbArr = new ArrayList<Integer>();
			for (int i = 0; i < rgb.size(); i++) {
				rgbArr.add((rgb.get(i)).intValue());
			}

			ColorData colorData = new ColorData((String) color.get("id"), (String) color.get("name"),
					(String) color.get("hex"), rgbArr);
			colors.add(colorData);
		}

	}

	/*
	 * Function to convert RGB to hexadecimal.
	 * 
	 * @String
	 */
	public static String rgbToHex(int r, int g, int b) {
		// Capitalize / De-capitalize the X-es to change capitalization of HEX.
		String hex = String.format("#%02X%02X%02X", r, g, b);
		return hex;
	}

	/*
	 * Function to convert hexadecimal to RGB.
	 * 
	 * @Color
	 */
	public static Color hexToRGB(String hex) {
		int i = Integer.decode(hex);
		int[] rgb = new int[] { (i >> 16) & 0xFF, (i >> 8) & 0xFF, i & 0xFF };
		// return new Color(Integer.valueOf(hex.substring(1, 3), 16),
		// Integer.valueOf(hex.substring(3, 5), 16),
		// Integer.valueOf(hex.substring(5, 7), 16));
		return new Color(rgb[0], rgb[1], rgb[2]);
	}

	/*
	 * Takes a screenshot of the monitor.
	 * 
	 * @Image
	 */
	public static BufferedImage takeSS(double x, double y) {
		BufferedImage image = null;
		try {
			Dimension dim = new Dimension();
			dim.setSize(x, y);
			image = new Robot().createScreenCapture(new Rectangle(dim));
		} catch (HeadlessException e) {
			e.printStackTrace();
		} catch (AWTException e) {
			e.printStackTrace();
		}
		/*
		 * File outputfile = new File("/home/manzey/Desktop/image.jpg"); try {
		 * ImageIO.write(image, "jpg", outputfile); } catch (IOException e) { // TODO
		 * Auto-generated catch block e.printStackTrace(); }
		 */

		return image;
	}

	/*
	 * Gets the color from selected pixel.
	 * 
	 * @Color
	 */
	public static Color getPixelValue(BufferedImage img, int x, int y) {
		Color color = new Color(img.getRGB(x, y), true);
		return color;
	}
	
	/*
	 * Gets the color hue from RGB
	 * 
	 * @Color
	 */
	public static String getColorHue(int r, int g, int b) {
		float[] hsv = new float[3];
		Color.RGBtoHSB(r,g,b, hsv);
		float hueDegree = hsv[0] * 360;
		float saturation = hsv[1];
		float brightness = hsv[2];
		if (brightness < 0.2) {
			// BLACK
			return "Black";
		}  else if (saturation < 0.2 && brightness < 0.5) {
			// GRAY
			return "Gray";
		} else if (saturation < 0.2 && brightness > 0.5) {
			// WHITE
			return "White";
		} else if (hueDegree >= 0 && hueDegree < 30) {
			// 0-30 degree
			return "Red";
		} else if (hueDegree >= 30 && hueDegree < 45) {
			// 30-45
			return "Orange";
		} else if (hueDegree >= 45 && hueDegree < 90) {
			// 45-90
			return "Yellow";
		} else if (hueDegree >= 90 && hueDegree < 150) {
			// 90-150
			return "Green";
		} else if (hueDegree >= 150 && hueDegree < 225) {
			// 150-225
			return "Cyan";
		} else if (hueDegree >= 225 && hueDegree < 270) {
			// 225-270
			return "Blue";
		} else if (hueDegree >= 270 && hueDegree < 285) {
			// 270-285
			return "Violet";
		} else if (hueDegree >= 285 && hueDegree < 330) {
			// 285-330
			return "Magenta";
		} else if (hueDegree >= 330 && hueDegree < 360) {
			// 330-360
			return "Red";
		}
			return null;
	}

	/*
	 * Translates RGB value to a color via checking distance between input and
	 * static data. SIMPLE VERSION (COLORS ALL CAN UNDERSTAND)
	 * 
	 * @ColorData
	 */
	public static ColorData rgbToSimpleColorName(int r, int g, int b) {
		float rgbDistance = 0;
		float prevClosest = 999;
		ColorData closestColor = null;
		for (ColorHue color : ColorHue.values()) {
			int[] colorRGB = color.rgb;
			//rgbDistance = Math.abs(r - colorRGB[0]) + Math.abs(g - colorRGB[1]) + Math.abs(b - colorRGB[1]);
			int diffRed   = Math.abs(r - colorRGB[0]);
			int diffGreen = Math.abs(g - colorRGB[1]);
			int diffBlue  = Math.abs(b - colorRGB[2]);
			float pctDiffRed   = (float)diffRed   / 255;
			float pctDiffGreen = (float)diffGreen / 255;
			float pctDiffBlue   = (float)diffBlue  / 255;
			rgbDistance = (pctDiffRed + pctDiffGreen + pctDiffBlue) / 3 * 100;
			if (prevClosest > rgbDistance) {
				closestColor = new ColorData(color.toString(), color.toString(),
						rgbToHex(colorRGB[0], colorRGB[1], colorRGB[2]),
						new ArrayList<Integer>(Arrays.asList(colorRGB[0], colorRGB[1], colorRGB[2])));
				prevClosest = rgbDistance;
			}
		}
		return closestColor;
	}

	/*
	 * Translates RGB value to a color via checking distance between input and
	 * static data.
	 * 
	 * @ColorData
	 */
	public static ColorData rgbToColorName(int r, int g, int b) {
		float rgbDistance = 0;
		float prevClosest = 999;
		ColorData closestColor = null;
		for (ColorData color : colors) {
			ArrayList<Integer> colorRGB = color.getRgb();
			int diffRed   = Math.abs(r - colorRGB.get(0));
			int diffGreen = Math.abs(g - colorRGB.get(1));
			int diffBlue  = Math.abs(b - colorRGB.get(2));
			float pctDiffRed   = (float)diffRed   / 255;
			float pctDiffGreen = (float)diffGreen / 255;
			float pctDiffBlue   = (float)diffBlue  / 255;
			rgbDistance = (pctDiffRed + pctDiffGreen + pctDiffBlue) / 3 * 100;
			rgbDistance = Math.abs(r - colorRGB.get(0)) + Math.abs(g - colorRGB.get(1)) + Math.abs(b - colorRGB.get(2));
			if (prevClosest > rgbDistance) {
				closestColor = color;
				prevClosest = rgbDistance;
			}
		}
		return closestColor;
	}

	public static Color generateScheme(ArrayList<Integer> rgb, int amount) {
		float[] hsv = new float[3];
		Color.RGBtoHSB(rgb.get(0), rgb.get(1), rgb.get(2), hsv);
		int rgb1 = Color.HSBtoRGB(hsv[0], hsv[1], (float) (hsv[2]));
		int red = (rgb1 >> 16) & 0xFF;
		int green = (rgb1 >> 8) & 0xFF;
		int blue = rgb1 & 0xFF;

		return new Color(red, green, blue);
	}

	/*
	 * Generates a color scheme based on adjusting the brightness of the color.
	 * Adjust brightness to generate a color scheme.
	 */
	public static ColorData[] generateBrightnessScheme(ArrayList<Integer> rgb, int amount) {
		ColorData[] array = new ColorData[amount];
		float[] hsv = new float[3];
		Color.RGBtoHSB(rgb.get(0), rgb.get(1), rgb.get(2), hsv);
		if (hsv[2] > 0.5) {
			float percentage = 0;
			for (int i = 0; i < amount; i++) {
				percentage += 0.1;
				int rgb1 = Color.HSBtoRGB(hsv[0], hsv[1], (float) (hsv[2] - percentage));
				int red = (rgb1 >> 16) & 0xFF;
				int green = (rgb1 >> 8) & 0xFF;
				int blue = rgb1 & 0xFF;
				array[i] = Colorify.rgbToColorName(red, green, blue);
			}
		} else {
			float percentage = 0;
			for (int i = 0; i < amount; i++) {
				percentage += 0.1;
				int rgb1 = Color.HSBtoRGB(hsv[0], hsv[1], (float) (hsv[2] + percentage));
				int red = (rgb1 >> 16) & 0xFF;
				int green = (rgb1 >> 8) & 0xFF;
				int blue = rgb1 & 0xFF;
				array[i] = Colorify.rgbToColorName(red, green, blue);
			}
		}
		return array;
	}

	/*
	 * Generates a color scheme based on adjusting the saturation of the color.
	 * Adjust saturation to generate a color scheme.
	 */
	public static ColorData[] generateSaturationScheme(ArrayList<Integer> rgb, int amount) {
		ColorData[] array = new ColorData[amount];
		float[] hsv = new float[3];
		Color.RGBtoHSB(rgb.get(0), rgb.get(1), rgb.get(2), hsv);
		if (hsv[2] > 0.5) {
			float percentage = 0;
			for (int i = 0; i < amount; i++) {
				percentage += 0.1;
				int rgb1 = Color.HSBtoRGB(hsv[0], (float) hsv[1] - percentage, hsv[2]);
				int red = (rgb1 >> 16) & 0xFF;
				int green = (rgb1 >> 8) & 0xFF;
				int blue = rgb1 & 0xFF;
				array[i] = Colorify.rgbToColorName(red, green, blue);
			}
		} else {
			float percentage = 0;
			for (int i = 0; i < amount; i++) {
				percentage += 0.1;
				int rgb1 = Color.HSBtoRGB(hsv[0], (float) hsv[1] + percentage, (hsv[2]));
				int red = (rgb1 >> 16) & 0xFF;
				int green = (rgb1 >> 8) & 0xFF;
				int blue = rgb1 & 0xFF;
				array[i] = Colorify.rgbToColorName(red, green, blue);
			}
		}
		return array;
	}

	/*
	 * Generates a color scheme based on adjusting the hue of the color. Three of
	 * the colors are on the same side of the circle as the original color, and two
	 * of the colors are on the opposite.
	 */
	public static ColorData[] generateHueScheme(ArrayList<Integer> rgb, int amount) {
		ColorData[] array = new ColorData[amount];
		float[] hsv = new float[3];
		Color.RGBtoHSB(rgb.get(0), rgb.get(1), rgb.get(2), hsv);
		float percentage = 0;
		for (int i = 0; i < amount; i++) {
			percentage += 0.08;
			if (i == 0) {
				int rgb1 = Color.HSBtoRGB(hsv[0], hsv[1], hsv[2]);
				int red = (rgb1 >> 16) & 0xFF;
				int green = (rgb1 >> 8) & 0xFF;
				int blue = rgb1 & 0xFF;
				array[i] = Colorify.rgbToColorName(red, green, blue);
			} else if (i <= 1) {
				int rgb1 = Color.HSBtoRGB((float) hsv[0] + percentage, hsv[1], hsv[2]);
				int red = (rgb1 >> 16) & 0xFF;
				int green = (rgb1 >> 8) & 0xFF;
				int blue = rgb1 & 0xFF;
				array[i] = Colorify.rgbToColorName(red, green, blue);
			} else if (i >= 2) {
				int rgb1 = Color.HSBtoRGB((float) (hsv[0] + 0.5 + percentage), hsv[1], hsv[2]);
				int red = (rgb1 >> 16) & 0xFF;
				int green = (rgb1 >> 8) & 0xFF;
				int blue = rgb1 & 0xFF;
				array[i] = Colorify.rgbToColorName(red, green, blue);
			}
		}

		return array;
	}
}
