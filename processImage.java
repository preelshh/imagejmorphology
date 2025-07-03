/*    */ import MPD_Quantification.UserInterface;
/*    */ import ij.IJ;
/*    */ import ij.ImagePlus;
/*    */ import ij.Prefs;
/*    */ import ij.measure.Measurements;
/*    */ import ij.plugin.filter.ParticleAnalyzer;
/*    */ 
/*    */ public class processImage extends UserInterface implements Measurements {
/*    */   public void imageProcessingClumps(String filePath, double minClumpSize, double maxClumpSize, double fileResolution) {
/* 12 */     Double lowerClumpSize = Double.valueOf(minClumpSize * minClumpSize / fileResolution * fileResolution);
/* 13 */     Double upperClumpSize = Double.valueOf(maxClumpSize * maxClumpSize / fileResolution * fileResolution);
/* 15 */     IJ.run("Set Measurements...", "area shape feret's display redirect=None decimal=2");
/* 17 */     ImagePlus imp = IJ.openImage(filePath);
/* 18 */     IJ.run(imp, "Enhance Contrast", "saturated=5.0");
/* 19 */     IJ.run(imp, "RGB Stack", "");
/* 20 */     IJ.run(imp, "Next Slice [>]", "");
/* 21 */     IJ.run(imp, "Delete Slice", "");
/* 22 */     IJ.run(imp, "Next Slice [>]", "");
/* 23 */     IJ.run(imp, "Delete Slice", "");
/* 24 */     IJ.setAutoThreshold(imp, "Default dark");
/* 25 */     IJ.setRawThreshold(imp, 0.0D, 172.0D, null);
/* 26 */     Prefs.blackBackground = false;
/* 27 */     IJ.run(imp, "Convert to Mask", "method=Default background=Dark only");
/* 28 */     IJ.run(imp, "Invert", "");
/* 29 */     IJ.run(imp, "Fill Holes", "");
/* 30 */     ParticleAnalyzer.setFontSize(60);
/* 31 */     IJ.run(imp, "Analyze Particles...", "size=" + lowerClumpSize + "-" + upperClumpSize + " show=Outlines display exclude include");
/* 32 */     IJ.saveAs("Jpeg", filePath + "_Results_Dispersed.jpg");
/* 33 */     IJ.run("Close");
/* 34 */     imp.show();
/* 35 */     IJ.run("Close");
/*    */   }
/*    */   
/*    */   public void imageProcessingPellets(String filePath, double minPelletSize, double maxPelletSize, double fileResolution) {
/* 41 */     Double lowerPelletSize = Double.valueOf(minPelletSize * minPelletSize / fileResolution * fileResolution);
/* 42 */     Double upperPelletSize = Double.valueOf(maxPelletSize * maxPelletSize / fileResolution * fileResolution);
/* 44 */     IJ.run("Set Measurements...", "area shape feret's display redirect=None decimal=2");
/* 46 */     ImagePlus imp = IJ.openImage(filePath);
/* 47 */     IJ.run(imp, "Enhance Contrast", "saturated=5.0");
/* 48 */     IJ.run(imp, "RGB Stack", "");
/* 49 */     IJ.run(imp, "Next Slice [>]", "");
/* 50 */     IJ.run(imp, "Delete Slice", "");
/* 51 */     IJ.run(imp, "Next Slice [>]", "");
/* 52 */     IJ.run(imp, "Delete Slice", "");
/* 53 */     IJ.setAutoThreshold(imp, "Default dark");
/* 54 */     IJ.setRawThreshold(imp, 0.0D, 172.0D, null);
/* 55 */     Prefs.blackBackground = false;
/* 56 */     IJ.run(imp, "Convert to Mask", "method=Default background=Dark only");
/* 57 */     IJ.run(imp, "Invert", "");
/* 58 */     IJ.run(imp, "Fill Holes", "");
/* 59 */     ParticleAnalyzer.setFontSize(60);
/* 60 */     IJ.run(imp, "Analyze Particles...", "size=" + lowerPelletSize + "-" + upperPelletSize + " show=Outlines display exclude include");
/* 61 */     IJ.saveAs("Jpeg", filePath + "_Results_Pellets.jpg");
/* 62 */     IJ.run("Close");
/* 63 */     imp.show();
/* 64 */     IJ.run("Close");
/*    */   }
/*    */ }


/* Location:              C:\Users\priya\OneDrive\Desktop\!\processImage.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */