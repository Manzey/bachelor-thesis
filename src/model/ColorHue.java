package model;

public enum ColorHue {
	White(new int[] {255,255,255}),
	Yellow(new int[] {255,255,0}),
	Fuchsia(new int[] {255,0,255}),
	Red(new int[] {255,0,0}),
	Silver(new int[] {192,192,192}),
	Gray(new int[] {128,128,128}),
	Olive(new int[] {128,128,0}),
	Purple(new int[] {128,0,128}),
	Maroon(new int[] {128,0,0}),
	Aqua(new int[] {0,255,255}),
	Lime(new int[] {0,255,255}),
	Teal(new int[] {0,128,128}),
	Green(new int[] {0,128,0}),
	Blue(new int[] {0,0,255}),
	Navy(new int[] {0,0,128}),
	Black(new int[] {0,0,0});
	
	
	public final int[] rgb;
	
    private ColorHue(int[] rgb) {
        this.rgb = rgb;
    }
}
