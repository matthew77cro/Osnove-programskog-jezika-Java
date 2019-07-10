package hr.fer.zemris.lsystems.demo;

import hr.fer.zemris.lsystems.LSystem;
import hr.fer.zemris.lsystems.LSystemBuilderProvider;
import hr.fer.zemris.lsystems.gui.LSystemViewer;
import hr.fer.zemris.lsystems.impl.LSystemBuilderImpl;

public class Glavni1 {

	public static void main(String[] args) {
		LSystemViewer.showLSystem(createPlant2(LSystemBuilderImpl::new));
	}
	
	@SuppressWarnings("unused")
	private static LSystem createKochCurve(LSystemBuilderProvider provider) {
		return provider.createLSystemBuilder()
		.registerCommand('F', "draw 1")
		.registerCommand('+', "rotate 60")
		.registerCommand('-', "rotate -60")
		.registerCommand('U', "color ff00ff")
		.registerCommand('I', "color 00ff00")
		.registerCommand('O', "color 000000")
		.registerCommand('P', "color ff0000")
		.setOrigin(0.05, 0.4)
		.setAngle(0)
		.setUnitLength(0.9)
		.setUnitLengthDegreeScaler(1.0/3.0)
		.registerProduction('F', "UF+IF--OF+PF")
		.setAxiom("F")
		.build();
	}
	
	@SuppressWarnings("unused")
	private static LSystem createKochCurve2(LSystemBuilderProvider provider) {
		String[] data = new String[] {
		"origin 0.05 0.4",
		"angle 0",
		"unitLength 0.9",
		"unitLengthDegreeScaler 1.0 / 3.0",
		"",
		"command F draw 1",
		"command + rotate 60",
		"command - rotate -60",
		"command U color ff00ff",
		"command I color 00ff00",
		"command O color 000000",
		"command P color ff0000",
		"",
		"axiom F",
		"",
		"production F UF+IF--OF+PF"
		};
		return provider.createLSystemBuilder().configureFromText(data).build();
	}
	
	@SuppressWarnings("unused")
	private static LSystem createKoch2Curve(LSystemBuilderProvider provider) {
		String[] data = new String[] {
		"origin 0.3 0.75",
		"angle 0",
		"unitLength 0.45",
		"unitLengthDegreeScaler 1.0 / 3.0",
		"",
		"command F draw 1",
		"command + rotate 90",
		"command - rotate -90",
		"",
		"axiom F-F-F-F",
		"",
		"production F FF-F-F-F-FF"
		};
		return provider.createLSystemBuilder().configureFromText(data).build();
	}
	
	@SuppressWarnings("unused")
	private static LSystem createHilbertCurve(LSystemBuilderProvider provider) {
		String[] data = new String[] {
		"origin                 0.05 0.05",
		"angle                  0",
		"unitLength             0.9", 
		"unitLengthDegreeScaler 1.0 / 2.0",
		"",
		"command F draw 1" , 
		"command + rotate 90" , 
		"command - rotate -90" ,
		"" ,
		"axiom L" ,
		"" ,
		"production L +RF-LFL-FR+" ,
		"production R -LF+RFR+FL-", 
		""
		};
		return provider.createLSystemBuilder().configureFromText(data).build();
	}
	
	@SuppressWarnings("unused")
	private static LSystem createSierpinskiGasket(LSystemBuilderProvider provider) {
		String[] data = new String[] {
		"origin                 0.15 0.6",
		"angle                  0",
		"unitLength             0.5", 
		"unitLengthDegreeScaler 1.0 / 2.2",
		"",
		"command R draw 1" , 
		"command L draw 1" ,
		"command + rotate 60" , 
		"command - rotate -60" ,
		"" ,
		"axiom R" ,
		"" ,
		"production R L-R-L" ,
		"production L R+L+R", 
		""
		};
		return provider.createLSystemBuilder().configureFromText(data).build();
	}
	
	@SuppressWarnings("unused")
	private static LSystem createPlant1(LSystemBuilderProvider provider) {
		String[] data = new String[] {
		"origin                 0.5 0.0",
		"angle                  90", 
		"unitLength             0.1",
		"unitLengthDegreeScaler 1.0 /2.05" ,
		"",
		"command F draw 1",
		"command + rotate 25.7" ,
		"command - rotate -25.7" , 
		"command [ push" ,
		"command ] pop" , 
		"command G color 00FF00" ,
		"" ,
		"axiom GF" , 
		"" , 
		"production F F[+F]F[-F]F", 
		"" , 
		""
		};
		return provider.createLSystemBuilder().configureFromText(data).build();
	}
	
	private static LSystem createPlant2(LSystemBuilderProvider provider) {
		String[] data = new String[] {
		"origin                 0.5 0.0",
		"angle                  90", 
		"unitLength             0.3",
		"unitLengthDegreeScaler 1.0 /2.05" ,
		"",
		"command F draw 1",
		"command + rotate 25" ,
		"command - rotate -25" , 
		"command [ push" ,
		"command ] pop" , 
		"command G color 00FF00" ,
		"" ,
		"axiom GF" , 
		"" , 
		"production F FF+[+F-F-F]-[-F+F+F]", 
		"" , 
		""
		};
		return provider.createLSystemBuilder().configureFromText(data).build();
	}
	
	@SuppressWarnings("unused")
	private static LSystem createPlant3(LSystemBuilderProvider provider) {
		String[] data = new String[] {
		"origin                 0.5 0.0",
		"angle                  90", 
		"unitLength             0.5",
		"unitLengthDegreeScaler 1.0 /2.05" ,
		"",
		"command F draw 1",
		"command + rotate 20" ,
		"command - rotate -20" , 
		"command [ push" ,
		"command ] pop" , 
		"command G color 00FF00" ,
		"" ,
		"axiom GB" , 
		"" , 
		"production B F[+B]F[-B]+B",
		"production F FF", 
		"" , 
		""
		};
		return provider.createLSystemBuilder().configureFromText(data).build();
	}
	
	@SuppressWarnings("unused")
	private static LSystem createKochIsland(LSystemBuilderProvider provider) {
		String[] data = new String[] {
		"origin                 0.3 0.75\r\n",
		"angle                  0\r\n",
		"unitLength             0.45\r\n",
		"unitLengthDegreeScaler 1.0 / 4.0\r\n",
		"\r\n",
		"command F draw 1\r\n", 
		"command + rotate 90\r\n" ,
		"command - rotate -90\r\n" , 
		"\r\n" ,
		"axiom F-F-F-F\r\n", 
		"\r\n",
		"production F F-F+F+FF-F-F+F\r\n",
		"\r\n",
		"\r\n" ,
		""
		};
		return provider.createLSystemBuilder().configureFromText(data).build();
	}

}
