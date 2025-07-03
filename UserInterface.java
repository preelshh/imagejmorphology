/*     */ import MPD_Quantification.UserInterface;
/*     */ import MPD_Quantification.combineResults;
/*     */ import MPD_Quantification.processImage;
/*     */ import MPD_Quantification.processResults;
/*     */ import fiji.util.gui.GenericDialogPlus;
/*     */ import ij.IJ;
/*     */ import java.io.File;
/*     */ import java.util.Arrays;
/*     */ 
/*     */ public class UserInterface {
/*  17 */   public String inputFolder = "";
/*     */   
/*  18 */   public String outputFolder = "";
/*     */   
/*  19 */   public String fileSuffix = "";
/*     */   
/*  21 */   public double fileResolution = 0.0D;
/*     */   
/*     */   public boolean analyzeClumps = false;
/*     */   
/*     */   public boolean analyzePellets = false;
/*     */   
/*     */   public void FirstUserInterface() {
/*  28 */     GenericDialogPlus gui = new GenericDialogPlus("Morphology of Pellet and Dispersed mycelia (MPD) Quantification");
/*  29 */     gui.addDirectoryField("Input directory", this.inputFolder);
/*  30 */     gui.addDirectoryField("Output directory", this.outputFolder);
/*  32 */     gui.addStringField("File Suffix", this.fileSuffix);
/*  34 */     gui.addNumericField("Resolution (µm/px)", this.fileResolution, 0);
/*  36 */     gui.addCheckbox("Dispersed", this.analyzeClumps);
/*  37 */     gui.addCheckbox("Pellets", this.analyzePellets);
/*  39 */     gui.setOKLabel("Next");
/*  41 */     gui.showDialog();
/*  45 */     this.inputFolder = gui.getNextString();
/*  46 */     this.outputFolder = gui.getNextString();
/*  47 */     this.fileSuffix = gui.getNextString();
/*  49 */     this.fileResolution = gui.getNextNumber();
/*  51 */     this.analyzeClumps = gui.getNextBoolean();
/*  52 */     this.analyzePellets = gui.getNextBoolean();
/*  54 */     if (gui.wasCanceled()) {
/*  56 */       GenericDialogPlus exitDialog = new GenericDialogPlus("Watch out!");
/*  57 */       exitDialog.addMessage("Do you really want to exit?");
/*  58 */       exitDialog.setCancelLabel("Yes");
/*  59 */       exitDialog.setOKLabel("No");
/*  60 */       exitDialog.showDialog();
/*  62 */       if (exitDialog.wasOKed()) {
/*  63 */         FirstUserInterface();
/*     */       } else {
/*     */         return;
/*     */       } 
/*  71 */     } else if ((!this.analyzeClumps && !this.analyzePellets) || this.inputFolder.equals("") || this.outputFolder.equals("") || this.fileSuffix.equals("") || this.fileResolution == 0.0D) {
/*  73 */       GenericDialogPlus nothing = new GenericDialogPlus("Watch out!");
/*  74 */       nothing.addMessage("Please enter all required data.");
/*  75 */       nothing.hideCancelButton();
/*  76 */       nothing.setOKLabel("Back");
/*  77 */       nothing.showDialog();
/*  79 */       if (nothing.wasOKed())
/*  80 */         FirstUserInterface(); 
/*     */     } else {
/*  84 */       SecondUserInterface(this.analyzeClumps, this.analyzePellets, this.inputFolder, this.outputFolder, this.fileSuffix, this.fileResolution);
/*     */     } 
/*     */   }
/*     */   
/*  88 */   public double minClumpSize = 0.0D;
/*     */   
/*  89 */   public double maxClumpSize = 0.0D;
/*     */   
/*  90 */   public double minPelletSize = 0.0D;
/*     */   
/*  91 */   public double maxPelletSize = 0.0D;
/*     */   
/*     */   public boolean defaultSize = false;
/*     */   
/*     */   public void SecondUserInterface(boolean analyzeClumps, boolean analyzePellets, String inputFolder, String outputFolder, String fileSuffix, double fileResolution) {
/*  98 */     if (analyzeClumps && analyzePellets) {
/*  99 */       GenericDialogPlus secondgui = new GenericDialogPlus("Size ranges");
/* 100 */       secondgui.addNumericField("Minimal dispersed mycelia size (µm^2)", this.minClumpSize, 0);
/* 101 */       secondgui.addNumericField("Maximal dispersed mycelia/minimal pellet size (µm^2)", this.maxClumpSize, 0);
/* 102 */       secondgui.addNumericField("Maximal pellet size (µm^2)", this.maxPelletSize, 0);
/* 103 */       secondgui.addCheckbox("Default size parameters", this.defaultSize);
/* 104 */       secondgui.setOKLabel("Let's do it!");
/* 105 */       secondgui.showDialog();
/* 107 */       this.defaultSize = secondgui.getNextBoolean();
/* 109 */       this.minClumpSize = secondgui.getNextNumber();
/* 110 */       this.maxClumpSize = secondgui.getNextNumber();
/* 111 */       this.maxPelletSize = secondgui.getNextNumber();
/* 113 */       if (secondgui.wasCanceled()) {
/* 114 */         GenericDialogPlus secondExitDialog = new GenericDialogPlus("Watch out!");
/* 115 */         secondExitDialog.addMessage("Do you really want to exit?");
/* 116 */         secondExitDialog.setCancelLabel("Yes");
/* 117 */         secondExitDialog.setOKLabel("No");
/* 118 */         secondExitDialog.showDialog();
/* 120 */         if (secondExitDialog.wasOKed()) {
/* 121 */           SecondUserInterface(analyzeClumps, analyzePellets, inputFolder, outputFolder, fileSuffix, fileResolution);
/*     */         } else {
/*     */           return;
/*     */         } 
/* 129 */       } else if ((this.minClumpSize == 0.0D || this.maxClumpSize == 0.0D || this.maxPelletSize == 0.0D) && !this.defaultSize) {
/* 131 */         GenericDialogPlus noSizeEntered = new GenericDialogPlus("Watch out!");
/* 132 */         noSizeEntered.addMessage("Please enter all required sizes or choose default size parameters.");
/* 133 */         noSizeEntered.setOKLabel("Back");
/* 134 */         noSizeEntered.hideCancelButton();
/* 135 */         noSizeEntered.showDialog();
/* 137 */         if (noSizeEntered.wasOKed())
/* 138 */           SecondUserInterface(analyzeClumps, analyzePellets, inputFolder, outputFolder, fileSuffix, fileResolution); 
/* 142 */       } else if (this.defaultSize) {
/* 143 */         this.minClumpSize = 95.0D;
/* 144 */         this.maxClumpSize = 499.9D;
/* 145 */         this.minPelletSize = this.maxClumpSize + 0.1D;
/* 146 */         this.maxPelletSize = 1.0E9D;
/* 148 */         fileOrDirectory(inputFolder, outputFolder, this.level, fileSuffix, analyzeClumps, analyzePellets);
/* 149 */         combineAllResults(inputFolder, outputFolder, analyzeClumps, analyzePellets, fileSuffix);
/* 150 */         theEnd();
/*     */       } else {
/* 154 */         this.minPelletSize = this.maxClumpSize + 0.1D;
/* 156 */         fileOrDirectory(inputFolder, outputFolder, this.level, fileSuffix, analyzeClumps, analyzePellets);
/* 157 */         combineAllResults(inputFolder, outputFolder, analyzeClumps, analyzePellets, fileSuffix);
/* 158 */         theEnd();
/*     */       } 
/* 162 */     } else if (analyzeClumps) {
/* 163 */       GenericDialogPlus secondgui = new GenericDialogPlus("Size ranges");
/* 164 */       secondgui.addNumericField("Minimal dispersed mycelia size (µm^2)", this.minClumpSize, 0);
/* 165 */       secondgui.addNumericField("Maximal dispersed mycelia size (µm^2)", this.maxClumpSize, 0);
/* 166 */       secondgui.addCheckbox("Default size parameters", this.defaultSize);
/* 167 */       secondgui.setOKLabel("Let's do it!");
/* 168 */       secondgui.showDialog();
/* 170 */       this.defaultSize = secondgui.getNextBoolean();
/* 172 */       this.minClumpSize = secondgui.getNextNumber();
/* 173 */       this.maxClumpSize = secondgui.getNextNumber();
/* 175 */       if (secondgui.wasCanceled()) {
/* 176 */         GenericDialogPlus secondExitDialog = new GenericDialogPlus("Watch out!");
/* 177 */         secondExitDialog.addMessage("Do you really want to exit?");
/* 178 */         secondExitDialog.setCancelLabel("Yes");
/* 179 */         secondExitDialog.setOKLabel("No");
/* 180 */         secondExitDialog.showDialog();
/* 182 */         if (secondExitDialog.wasOKed()) {
/* 183 */           SecondUserInterface(analyzeClumps, analyzePellets, inputFolder, outputFolder, fileSuffix, fileResolution);
/*     */         } else {
/*     */           return;
/*     */         } 
/* 191 */       } else if (!this.defaultSize && (this.minClumpSize == 0.0D || this.maxClumpSize == 0.0D)) {
/* 193 */         GenericDialogPlus noSizeEntered = new GenericDialogPlus("Watch out!");
/* 194 */         noSizeEntered.addMessage("Please enter all required sizes or choose default size parameters.");
/* 195 */         noSizeEntered.setOKLabel("Back");
/* 196 */         noSizeEntered.hideCancelButton();
/* 197 */         noSizeEntered.showDialog();
/* 199 */         if (noSizeEntered.wasOKed())
/* 200 */           SecondUserInterface(analyzeClumps, analyzePellets, inputFolder, outputFolder, fileSuffix, fileResolution); 
/* 204 */       } else if (this.defaultSize) {
/* 205 */         this.minClumpSize = 95.0D;
/* 206 */         this.maxClumpSize = 499.9D;
/* 208 */         fileOrDirectory(inputFolder, outputFolder, this.level, fileSuffix, analyzeClumps, analyzePellets);
/* 209 */         combineAllResults(inputFolder, outputFolder, analyzeClumps, analyzePellets, fileSuffix);
/* 210 */         theEnd();
/*     */       } else {
/* 214 */         fileOrDirectory(inputFolder, outputFolder, this.level, fileSuffix, analyzeClumps, analyzePellets);
/* 215 */         combineAllResults(inputFolder, outputFolder, analyzeClumps, analyzePellets, fileSuffix);
/* 216 */         theEnd();
/*     */       } 
/* 221 */     } else if (analyzePellets) {
/* 222 */       GenericDialogPlus secondgui = new GenericDialogPlus("Size ranges");
/* 223 */       secondgui.addNumericField("Minimal pellet size (µm^2)", this.minPelletSize, 0);
/* 224 */       secondgui.addNumericField("Maximal pellet size (µm^2)", this.maxPelletSize, 0);
/* 225 */       secondgui.addCheckbox("Default size parameters", this.defaultSize);
/* 226 */       secondgui.setOKLabel("Let's do it!");
/* 227 */       secondgui.showDialog();
/* 229 */       this.defaultSize = secondgui.getNextBoolean();
/* 231 */       this.minPelletSize = secondgui.getNextNumber();
/* 232 */       this.maxPelletSize = secondgui.getNextNumber();
/* 234 */       if (secondgui.wasCanceled()) {
/* 235 */         GenericDialogPlus secondExitDialog = new GenericDialogPlus("Watch out!");
/* 236 */         secondExitDialog.addMessage("Do you really want to exit?");
/* 237 */         secondExitDialog.setCancelLabel("Yes");
/* 238 */         secondExitDialog.setOKLabel("No");
/* 239 */         secondExitDialog.showDialog();
/* 241 */         if (secondExitDialog.wasOKed()) {
/* 242 */           SecondUserInterface(analyzeClumps, analyzePellets, inputFolder, outputFolder, fileSuffix, fileResolution);
/*     */         } else {
/*     */           return;
/*     */         } 
/* 250 */       } else if (!this.defaultSize && (this.minPelletSize == 0.0D || this.maxPelletSize == 0.0D)) {
/* 252 */         GenericDialogPlus noSizeEntered = new GenericDialogPlus("Watch out!");
/* 253 */         noSizeEntered.addMessage("Please enter all required sizes or choose default size parameters.");
/* 254 */         noSizeEntered.setOKLabel("Back");
/* 255 */         noSizeEntered.hideCancelButton();
/* 256 */         noSizeEntered.showDialog();
/* 258 */         if (noSizeEntered.wasOKed())
/* 259 */           SecondUserInterface(analyzeClumps, analyzePellets, inputFolder, outputFolder, fileSuffix, fileResolution); 
/* 263 */       } else if (this.defaultSize) {
/* 264 */         this.minPelletSize = 500.0D;
/* 265 */         this.maxPelletSize = 1.0E9D;
/* 267 */         fileOrDirectory(inputFolder, outputFolder, this.level, fileSuffix, analyzeClumps, analyzePellets);
/* 268 */         combineAllResults(inputFolder, outputFolder, analyzeClumps, analyzePellets, fileSuffix);
/* 269 */         theEnd();
/*     */       } else {
/* 273 */         fileOrDirectory(inputFolder, outputFolder, this.level, fileSuffix, analyzeClumps, analyzePellets);
/* 274 */         combineAllResults(inputFolder, outputFolder, analyzeClumps, analyzePellets, fileSuffix);
/* 275 */         theEnd();
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/* 280 */   public int level = 0;
/*     */   
/*     */   public void fileOrDirectory(String inputFolder, String outputFolder, int level, String fileSuffix, boolean analyzeClumps, boolean analyzePellets) {
/* 284 */     File maindir = new File(inputFolder);
/* 285 */     File[] listOfFiles = maindir.listFiles();
/* 286 */     Arrays.sort((Object[])listOfFiles);
/* 288 */     if (analyzeClumps) {
/* 290 */       for (int j = 0; j < listOfFiles.length; j++) {
/* 292 */         String filePath = listOfFiles[j].getAbsolutePath();
/* 294 */         if (listOfFiles[j].isFile() && filePath.endsWith(fileSuffix)) {
/* 296 */           processImage pi = new processImage();
/* 297 */           pi.imageProcessingClumps(filePath, this.minClumpSize, this.maxClumpSize, this.fileResolution);
/*     */         } 
/*     */       } 
/* 302 */       if (IJ.isResultsWindow()) {
/* 304 */         String resultsPath = inputFolder;
/* 306 */         String[] parentFolder = resultsPath.split("/");
/* 307 */         String parentFolderName = parentFolder[parentFolder.length - 1];
/* 309 */         processResults pr = new processResults();
/* 310 */         pr.resultsProcessing(resultsPath, this.fileResolution);
/* 312 */         IJ.saveAs("Results", resultsPath + "/Raw_Data_Results_Dispersed.csv");
/* 313 */         IJ.selectWindow("Results");
/* 314 */         IJ.run("Close");
/*     */       } 
/*     */     } 
/* 320 */     if (analyzePellets) {
/* 322 */       for (int j = 0; j < listOfFiles.length; j++) {
/* 324 */         String filePath = listOfFiles[j].getAbsolutePath();
/* 326 */         if (listOfFiles[j].isFile() && filePath.endsWith(fileSuffix)) {
/* 328 */           processImage pi = new processImage();
/* 329 */           pi.imageProcessingPellets(filePath, this.minPelletSize, this.maxPelletSize, this.fileResolution);
/*     */         } 
/*     */       } 
/* 334 */       if (IJ.isResultsWindow()) {
/* 336 */         String resultsPath = inputFolder;
/* 338 */         String[] parentFolder = resultsPath.split("/");
/* 339 */         String parentFolderName = parentFolder[parentFolder.length - 1];
/* 341 */         processResults pr = new processResults();
/* 342 */         pr.resultsProcessing(resultsPath, this.fileResolution);
/* 344 */         IJ.saveAs("Results", resultsPath + "/Raw_Data_Results_Pellets.csv");
/* 345 */         IJ.selectWindow("Results");
/* 346 */         IJ.run("Close");
/*     */       } 
/*     */     } 
/* 353 */     for (int i = 0; i < listOfFiles.length; i++) {
/* 355 */       if (listOfFiles[i].isDirectory()) {
/* 357 */         String directoryPath = listOfFiles[i].getAbsolutePath();
/* 358 */         fileOrDirectory(directoryPath, outputFolder, level + 1, fileSuffix, analyzeClumps, analyzePellets);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void combineAllResults(String inputFolder, String outputFolder, boolean analyzeClumps, boolean analyzePellets, String fileSuffix) {
/* 367 */     combineResults cr = new combineResults();
/* 368 */     cr.combiningResults(inputFolder, this.level, analyzeClumps, analyzePellets, fileSuffix);
/* 369 */     cr.printCombinedResults(outputFolder, analyzeClumps, analyzePellets);
/* 370 */     cr.ratioArea(outputFolder, analyzeClumps, analyzePellets);
/*     */   }
/*     */   
/*     */   public void theEnd() {
/* 375 */     IJ.showMessage("Image Processing completed");
/*     */   }
/*     */ }


/* Location:              C:\Users\priya\OneDrive\Desktop\!\UserInterface.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */