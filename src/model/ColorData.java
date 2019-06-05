package model;

import java.util.ArrayList;

public class ColorData {

	String rawName = "";
	String prettyName = "";
	String hex = "";
	ArrayList<Integer> rgb;

	public ColorData(String x_rawName, String x_prettyName, String x_hex, ArrayList<Integer> rgbArr) {
		rawName = x_rawName;
		prettyName = x_prettyName;
		hex = x_hex;
		rgb = rgbArr;
	}

	public String getRawName() {
		return rawName;
	}

	public void setRawName(String rawName) {
		this.rawName = rawName;
	}

	public String getPrettyName() {
		return prettyName;
	}

	public void setPrettyName(String prettyName) {
		this.prettyName = prettyName;
	}

	public String getHex() {
		return hex;
	}

	public void setHex(String hex) {
		this.hex = hex;
	}

	public ArrayList<Integer> getRgb() {
		return rgb;
	}

	public void setRgb(ArrayList<Integer> rgb) {
		this.rgb = rgb;
	}

}
