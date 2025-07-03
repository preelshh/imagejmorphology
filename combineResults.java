/*     */ import MPD_Quantification.UserInterface;
/*     */ import MPD_Quantification.combineResults;
/*     */ import ij.IJ;
/*     */ import ij.measure.ResultsTable;
/*     */ import java.io.File;
/*     */ import java.io.FileWriter;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintWriter;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ 
/*     */ public class combineResults extends UserInterface {
/*  13 */   public String resultFileSuffixClumps = "Raw_Data_Results_Dispersed.csv";
/*     */   
/*  14 */   public String resultFileSuffixPellets = "Raw_Data_Results_Pellets.csv";
/*     */   
/*  16 */   ArrayList<ArrayList<Double>> allAreaClumps = new ArrayList<>();
/*     */   
/*  17 */   ArrayList<ArrayList<Double>> allSolidityClumps = new ArrayList<>();
/*     */   
/*  18 */   ArrayList<ArrayList<Double>> allARClumps = new ArrayList<>();
/*     */   
/*  19 */   ArrayList<ArrayList<Double>> allFeretDClumps = new ArrayList<>();
/*     */   
/*  20 */   ArrayList<ArrayList<Double>> allMNClumps = new ArrayList<>();
/*     */   
/*  22 */   ArrayList<ArrayList<Double>> allAreaPellets = new ArrayList<>();
/*     */   
/*  23 */   ArrayList<ArrayList<Double>> allSolidityPellets = new ArrayList<>();
/*     */   
/*  24 */   ArrayList<ArrayList<Double>> allARPellets = new ArrayList<>();
/*     */   
/*  25 */   ArrayList<ArrayList<Double>> allFeretDPellets = new ArrayList<>();
/*     */   
/*  26 */   ArrayList<ArrayList<Double>> allMNPellets = new ArrayList<>();
/*     */   
/*  28 */   ArrayList<Integer> biggestArrayClumps = new ArrayList<>();
/*     */   
/*  29 */   ArrayList<Integer> biggestArrayPellets = new ArrayList<>();
/*     */   
/*  31 */   ArrayList<String> headers = new ArrayList<>();
/*     */   
/*     */   ArrayList<Double> areaClumps;
/*     */   
/*     */   ArrayList<Double> solidityClumps;
/*     */   
/*     */   ArrayList<Double> ARClumps;
/*     */   
/*     */   ArrayList<Double> FeretDClumps;
/*     */   
/*     */   ArrayList<Double> MNClumps;
/*     */   
/*     */   ArrayList<Double> areaPellets;
/*     */   
/*     */   ArrayList<Double> solidityPellets;
/*     */   
/*     */   ArrayList<Double> ARPellets;
/*     */   
/*     */   ArrayList<Double> FeretDPellets;
/*     */   
/*     */   ArrayList<Double> MNPellets;
/*     */   
/*     */   ArrayList<Double> noValueArray;
/*     */   
/*  47 */   ArrayList<String> totAreaClumps = new ArrayList<>();
/*     */   
/*  48 */   ArrayList<String> totAreaPellets = new ArrayList<>();
/*     */   
/*  49 */   ArrayList<String> totArea = new ArrayList<>();
/*     */   
/*  50 */   ArrayList<String> areaRatioPellet = new ArrayList<>();
/*     */   
/*     */   String headerName;
/*     */   
/*  54 */   double noValue = 0.0D;
/*     */   
/*     */   double xClumps;
/*     */   
/*     */   double xPellets;
/*     */   
/*     */   public void combiningResults(String inputFolder, int level, boolean analyzeClumps, boolean analyzePellets, String fileSuffix) {
/*  58 */     File maindir = new File(inputFolder);
/*  59 */     File[] listOfResultFiles = maindir.listFiles();
/*  60 */     Arrays.sort((Object[])listOfResultFiles);
/*  62 */     double totalAreaClumps = 0.0D;
/*  63 */     double totalAreaPellets = 0.0D;
/*  65 */     int headerCounter = 0;
/*  66 */     int counterClumpsCvs = 0;
/*  67 */     int counterPelletCvs = 0;
/*     */     int i;
/*  69 */     for (i = 0; i < listOfResultFiles.length; i++) {
/*  71 */       String resultFilePath = listOfResultFiles[i].getAbsolutePath();
/*  73 */       if (listOfResultFiles[i].isFile() && resultFilePath.endsWith(fileSuffix)) {
/*  74 */         headerCounter++;
/*  75 */         String fileSep = resultFilePath.replace("\\", "/");
/*  76 */         String[] parentFolder = fileSep.split("/");
/*  77 */         this.headerName = parentFolder[parentFolder.length - 2];
/*     */       } 
/*     */     } 
/*  81 */     if (headerCounter > 0)
/*  82 */       this.headers.add(this.headerName); 
/*  86 */     if (analyzeClumps == true) {
/*  87 */       for (i = 0; i < listOfResultFiles.length; i++) {
/*  89 */         String resultFilePath = listOfResultFiles[i].getAbsolutePath();
/*  91 */         if (listOfResultFiles[i].isFile() && resultFilePath.endsWith(this.resultFileSuffixClumps)) {
/*  93 */           counterClumpsCvs++;
/*  95 */           IJ.open(resultFilePath);
/*  96 */           ResultsTable resultTable = ResultsTable.getResultsTable();
/*  98 */           int nResults = resultTable.size();
/*  99 */           this.areaClumps = new ArrayList<>(nResults);
/* 100 */           this.solidityClumps = new ArrayList<>(nResults);
/* 101 */           this.ARClumps = new ArrayList<>(nResults);
/* 102 */           this.FeretDClumps = new ArrayList<>(nResults);
/* 103 */           this.MNClumps = new ArrayList<>(nResults);
/* 105 */           for (int j = 0; j < nResults; j++) {
/* 107 */             totalAreaClumps += resultTable.getValue("Area [um^2]", j);
/* 109 */             this.areaClumps.add(Double.valueOf(resultTable.getValue("Area [um^2]", j)));
/* 110 */             this.solidityClumps.add(Double.valueOf(resultTable.getValue("Solidity", j)));
/* 111 */             this.ARClumps.add(Double.valueOf(resultTable.getValue("AR", j)));
/* 112 */             this.FeretDClumps.add(Double.valueOf(resultTable.getValue("Feret [um]", j)));
/* 113 */             this.MNClumps.add(Double.valueOf(resultTable.getValue("MN", j)));
/*     */           } 
/* 117 */           int bigArrayClumps = this.areaClumps.size();
/* 118 */           this.biggestArrayClumps.add(Integer.valueOf(bigArrayClumps));
/* 120 */           this.totAreaClumps.add(Double.toString(totalAreaClumps));
/* 122 */           this.allAreaClumps.add(this.areaClumps);
/* 123 */           this.allSolidityClumps.add(this.solidityClumps);
/* 124 */           this.allARClumps.add(this.ARClumps);
/* 125 */           this.allFeretDClumps.add(this.FeretDClumps);
/* 126 */           this.allMNClumps.add(this.MNClumps);
/* 128 */           IJ.selectWindow("Results");
/* 129 */           IJ.run("Close");
/*     */         } 
/*     */       } 
/* 134 */       if (headerCounter > 0 && counterClumpsCvs < 1) {
/* 136 */         totalAreaClumps += 0.0D;
/* 137 */         this.totAreaClumps.add(Double.toString(totalAreaClumps));
/* 139 */         this.noValueArray = new ArrayList<>(10);
/* 141 */         this.noValueArray.add(Double.valueOf(this.noValue));
/* 143 */         this.allAreaClumps.add(this.noValueArray);
/* 144 */         this.allSolidityClumps.add(this.noValueArray);
/* 145 */         this.allARClumps.add(this.noValueArray);
/* 146 */         this.allFeretDClumps.add(this.noValueArray);
/* 147 */         this.allMNClumps.add(this.noValueArray);
/*     */       } 
/*     */     } 
/* 151 */     if (analyzePellets == true) {
/* 152 */       for (i = 0; i < listOfResultFiles.length; i++) {
/* 154 */         String resultFilePath = listOfResultFiles[i].getAbsolutePath();
/* 156 */         if (listOfResultFiles[i].isFile() && resultFilePath.endsWith(this.resultFileSuffixPellets)) {
                    IJ.log("---- Processing Clump File: " + resultFilePath);

                    ResultsTable resultTable = ResultsTable.getResultsTable();
                    int nResults = resultTable.size();
                    IJ.log("Number of rows in ResultsTable: " + nResults);

                    String[] headings = resultTable.getHeadings();
                    IJ.log("Columns in ResultsTable: " + java.util.Arrays.toString(headings));


/* 158 */           counterPelletCvs++;
/* 160 */           IJ.open(resultFilePath);
/* 161 */           ResultsTable resultTablePellets = ResultsTable.getResultsTable();
/* 163 */           int nResults = resultTablePellets.size();
/* 164 */           this.areaPellets = new ArrayList<>(nResults);
/* 165 */           this.solidityPellets = new ArrayList<>(nResults);
/* 166 */           this.ARPellets = new ArrayList<>(nResults);
/* 167 */           this.FeretDPellets = new ArrayList<>(nResults);
/* 168 */           this.MNPellets = new ArrayList<>(nResults);
/* 170 */           for (int j = 0; j < nResults; j++) {
/* 172 */             totalAreaPellets += resultTablePellets.getValue("Area [um^2]", j);
/* 174 */             this.areaPellets.add(Double.valueOf(resultTablePellets.getValue("Area [um^2]", j)));
/* 175 */             this.solidityPellets.add(Double.valueOf(resultTablePellets.getValue("Solidity", j)));
/* 176 */             this.ARPellets.add(Double.valueOf(resultTablePellets.getValue("AR", j)));
/* 177 */             this.FeretDPellets.add(Double.valueOf(resultTablePellets.getValue("Feret [um]", j)));
/* 178 */             this.MNPellets.add(Double.valueOf(resultTablePellets.getValue("MN", j)));
/*     */           } 
/* 182 */           int bigArrayPellets = this.areaPellets.size();
/* 183 */           this.biggestArrayPellets.add(Integer.valueOf(bigArrayPellets));
                    IJ.log("bigArrayPellets added: " + bigArrayPellets);

/* 185 */           this.totAreaPellets.add(Double.toString(totalAreaPellets));
/* 187 */           this.allAreaPellets.add(this.areaPellets);
/* 188 */           this.allSolidityPellets.add(this.solidityPellets);
/* 189 */           this.allARPellets.add(this.ARPellets);
/* 190 */           this.allFeretDPellets.add(this.FeretDPellets);
/* 191 */           this.allMNPellets.add(this.MNPellets);
/* 193 */           IJ.selectWindow("Results");
/* 194 */           IJ.run("Close");
/*     */         } 
/*     */       } 
/* 199 */       if (headerCounter > 0 && counterPelletCvs < 1) {
/* 201 */         totalAreaPellets += 0.0D;
/* 202 */         this.totAreaPellets.add(Double.toString(totalAreaPellets));
/* 204 */         this.noValueArray = new ArrayList<>(10);
/* 206 */         this.noValueArray.add(Double.valueOf(this.noValue));
/* 208 */         this.allAreaPellets.add(this.noValueArray);
/* 209 */         this.allSolidityPellets.add(this.noValueArray);
/* 210 */         this.allARPellets.add(this.noValueArray);
/* 211 */         this.allFeretDPellets.add(this.noValueArray);
/* 212 */         this.allMNPellets.add(this.noValueArray);
/*     */       } 
/*     */     } 
/* 217 */     for (i = 0; i < listOfResultFiles.length; i++) {
/* 218 */       if (listOfResultFiles[i].isDirectory()) {
/* 219 */         String directoryPath = listOfResultFiles[i].getAbsolutePath();
/* 220 */         combiningResults(directoryPath, level + 1, analyzeClumps, analyzePellets, fileSuffix);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void printCombinedResults(String outputFolder, boolean analyzeClumps, boolean analyzePellets) {
/* 227 */     if (analyzeClumps == true) {
/* 229 */       int nArrayClumps = ((Integer)Collections.<Integer>max(this.biggestArrayClumps)).intValue();
/* 231 */       ArrayList<ArrayList<String>> combinedAreaClumps = new ArrayList<>();
/* 232 */       ArrayList<ArrayList<String>> combinedSolidityClumps = new ArrayList<>();
/* 233 */       ArrayList<ArrayList<String>> combinedARClumps = new ArrayList<>();
/* 234 */       ArrayList<ArrayList<String>> combinedFeretDClumps = new ArrayList<>();
/* 235 */       ArrayList<ArrayList<String>> combinedMNClumps = new ArrayList<>();
/* 237 */       for (int k = 0; k < nArrayClumps; k++) {
/* 239 */         ArrayList<String> emptyListAreaClumps = new ArrayList<>();
/* 240 */         ArrayList<String> emptyListSolidityClumps = new ArrayList<>();
/* 241 */         ArrayList<String> emptyListARClumps = new ArrayList<>();
/* 242 */         ArrayList<String> emptyListFeretDClumps = new ArrayList<>();
/* 243 */         ArrayList<String> emptyListMNClumps = new ArrayList<>();
/* 245 */         for (List<Double> array : this.allAreaClumps) {
/*     */           try {
/* 249 */             String arrayElement = Double.toString(((Double)array.get(k)).doubleValue());
/* 250 */             emptyListAreaClumps.add(arrayElement);
/* 251 */           } catch (Exception e) {
/* 252 */             emptyListAreaClumps.add("");
/*     */           } 
/*     */         } 
/* 256 */         for (List<Double> array : this.allSolidityClumps) {
/*     */           try {
/* 260 */             String arrayElement = Double.toString(((Double)array.get(k)).doubleValue());
/* 261 */             emptyListSolidityClumps.add(arrayElement);
/* 262 */           } catch (Exception e) {
/* 263 */             emptyListSolidityClumps.add("");
/*     */           } 
/*     */         } 
/* 267 */         for (List<Double> array : this.allARClumps) {
/*     */           try {
/* 271 */             String arrayElement = Double.toString(((Double)array.get(k)).doubleValue());
/* 272 */             emptyListARClumps.add(arrayElement);
/* 273 */           } catch (Exception e) {
/* 274 */             emptyListARClumps.add("");
/*     */           } 
/*     */         } 
/* 278 */         for (List<Double> array : this.allFeretDClumps) {
/*     */           try {
/* 282 */             String arrayElement = Double.toString(((Double)array.get(k)).doubleValue());
/* 283 */             emptyListFeretDClumps.add(arrayElement);
/* 284 */           } catch (Exception e) {
/* 285 */             emptyListFeretDClumps.add("");
/*     */           } 
/*     */         } 
/* 289 */         for (List<Double> array : this.allMNClumps) {
/*     */           try {
/* 293 */             String arrayElement = Double.toString(((Double)array.get(k)).doubleValue());
/* 294 */             emptyListMNClumps.add(arrayElement);
/* 295 */           } catch (Exception e) {
/* 296 */             emptyListMNClumps.add("");
/*     */           } 
/*     */         } 
/* 301 */         combinedAreaClumps.add(emptyListAreaClumps);
/* 302 */         combinedSolidityClumps.add(emptyListSolidityClumps);
/* 303 */         combinedARClumps.add(emptyListARClumps);
/* 304 */         combinedFeretDClumps.add(emptyListFeretDClumps);
/* 305 */         combinedMNClumps.add(emptyListMNClumps);
/*     */       } 
/* 308 */       File csvFile = new File(outputFolder + "/All_Area_Results_Dispersed.csv");
/* 309 */       try (PrintWriter csvWriter = new PrintWriter(new FileWriter(csvFile))) {
/* 310 */         csvWriter.println(Arrays.toString(this.headers.toArray()).replace("[", "").replace("]", ""));
/* 311 */         for (List<String> anEmptyList : combinedAreaClumps) {
/* 312 */           String untiedResults = Arrays.toString(anEmptyList.toArray()).replace("[", "").replace("]", "");
/* 313 */           csvWriter.println(untiedResults);
/*     */         } 
/* 315 */       } catch (IOException e) {
/* 316 */         e.printStackTrace();
/*     */       } 
/* 318 */       File csvFileone = new File(outputFolder + "/All_Solidity_Results_Dispersed.csv");
/* 319 */       try (PrintWriter csvWriter = new PrintWriter(new FileWriter(csvFileone))) {
/* 320 */         csvWriter.println(Arrays.toString(this.headers.toArray()).replace("[", "").replace("]", ""));
/* 321 */         for (List<String> anEmptyList : combinedSolidityClumps) {
/* 322 */           String untiedResults = Arrays.toString(anEmptyList.toArray()).replace("[", "").replace("]", "");
/* 323 */           csvWriter.println(untiedResults);
/*     */         } 
/* 325 */       } catch (IOException e) {
/* 326 */         e.printStackTrace();
/*     */       } 
/* 328 */       File csvFiletwo = new File(outputFolder + "/All_AR_Results_Dispersed.csv");
/* 329 */       try (PrintWriter csvWriter = new PrintWriter(new FileWriter(csvFiletwo))) {
/* 330 */         csvWriter.println(Arrays.toString(this.headers.toArray()).replace("[", "").replace("]", ""));
/* 331 */         for (List<String> anEmptyList : combinedARClumps) {
/* 332 */           String untiedResults = Arrays.toString(anEmptyList.toArray()).replace("[", "").replace("]", "");
/* 333 */           csvWriter.println(untiedResults);
/*     */         } 
/* 335 */       } catch (IOException e) {
/* 336 */         e.printStackTrace();
/*     */       } 
/* 338 */       File csvFilethree = new File(outputFolder + "/All_FeretDiamter_Results_Dispersed.csv");
/* 339 */       try (PrintWriter csvWriter = new PrintWriter(new FileWriter(csvFilethree))) {
/* 340 */         csvWriter.println(Arrays.toString(this.headers.toArray()).replace("[", "").replace("]", ""));
/* 341 */         for (List<String> anEmptyList : combinedFeretDClumps) {
/* 342 */           String untiedResults = Arrays.toString(anEmptyList.toArray()).replace("[", "").replace("]", "");
/* 343 */           csvWriter.println(untiedResults);
/*     */         } 
/* 345 */       } catch (IOException e) {
/* 346 */         e.printStackTrace();
/*     */       } 
/* 348 */       File csvFilefour = new File(outputFolder + "/All_MN_Results_Dispersed.csv");
/* 349 */       try (PrintWriter csvWriter = new PrintWriter(new FileWriter(csvFilefour))) {
/* 350 */         csvWriter.println(Arrays.toString(this.headers.toArray()).replace("[", "").replace("]", ""));
/* 351 */         for (List<String> anEmptyList : combinedMNClumps) {
/* 352 */           String untiedResults = Arrays.toString(anEmptyList.toArray()).replace("[", "").replace("]", "");
/* 353 */           csvWriter.println(untiedResults);
/*     */         } 
/* 355 */       } catch (IOException e) {
/* 356 */         e.printStackTrace();
/*     */       } 
/*     */     } 
/* 361 */     if (analyzePellets == true) {
/* 362 */       int nArrayPellets = ((Integer)Collections.<Integer>max(this.biggestArrayPellets)).intValue();
/* 364 */       ArrayList<ArrayList<String>> combinedAreaPellets = new ArrayList<>();
/* 365 */       ArrayList<ArrayList<String>> combinedSolidityPellets = new ArrayList<>();
/* 366 */       ArrayList<ArrayList<String>> combinedARPellets = new ArrayList<>();
/* 367 */       ArrayList<ArrayList<String>> combinedFeretDPellets = new ArrayList<>();
/* 368 */       ArrayList<ArrayList<String>> combinedMNPellets = new ArrayList<>();
/* 370 */       for (int k = 0; k < nArrayPellets; k++) {
/* 372 */         ArrayList<String> emptyListAreaPellets = new ArrayList<>();
/* 373 */         ArrayList<String> emptyListSolidityPellets = new ArrayList<>();
/* 374 */         ArrayList<String> emptyListARPellets = new ArrayList<>();
/* 375 */         ArrayList<String> emptyListFeretDPellets = new ArrayList<>();
/* 376 */         ArrayList<String> emptyListMNPellets = new ArrayList<>();
/* 378 */         for (List<Double> array : this.allAreaPellets) {
/*     */           try {
/* 381 */             String arrayElement = Double.toString(((Double)array.get(k)).doubleValue());
/* 382 */             emptyListAreaPellets.add(arrayElement);
/* 383 */           } catch (Exception e) {
/* 384 */             emptyListAreaPellets.add("");
/*     */           } 
/*     */         } 
/* 388 */         for (List<Double> array : this.allSolidityPellets) {
/*     */           try {
/* 391 */             String arrayElement = Double.toString(((Double)array.get(k)).doubleValue());
/* 392 */             emptyListSolidityPellets.add(arrayElement);
/* 393 */           } catch (Exception e) {
/* 394 */             emptyListSolidityPellets.add("");
/*     */           } 
/*     */         } 
/* 398 */         for (List<Double> array : this.allARPellets) {
/*     */           try {
/* 401 */             String arrayElement = Double.toString(((Double)array.get(k)).doubleValue());
/* 402 */             emptyListARPellets.add(arrayElement);
/* 403 */           } catch (Exception e) {
/* 404 */             emptyListARPellets.add("");
/*     */           } 
/*     */         } 
/* 408 */         for (List<Double> array : this.allFeretDPellets) {
/*     */           try {
/* 411 */             String arrayElement = Double.toString(((Double)array.get(k)).doubleValue());
/* 412 */             emptyListFeretDPellets.add(arrayElement);
/* 413 */           } catch (Exception e) {
/* 414 */             emptyListFeretDPellets.add("");
/*     */           } 
/*     */         } 
/* 418 */         for (List<Double> array : this.allMNPellets) {
/*     */           try {
/* 421 */             String arrayElement = Double.toString(((Double)array.get(k)).doubleValue());
/* 422 */             emptyListMNPellets.add(arrayElement);
/* 423 */           } catch (Exception e) {
/* 424 */             emptyListMNPellets.add("");
/*     */           } 
/*     */         } 
/* 429 */         combinedAreaPellets.add(emptyListAreaPellets);
/* 430 */         combinedSolidityPellets.add(emptyListSolidityPellets);
/* 431 */         combinedARPellets.add(emptyListARPellets);
/* 432 */         combinedFeretDPellets.add(emptyListFeretDPellets);
/* 433 */         combinedMNPellets.add(emptyListMNPellets);
/*     */       } 
/* 437 */       File csvFile = new File(outputFolder + "/All_Area_Results_Pellets.csv");
/* 438 */       try (PrintWriter csvWriter = new PrintWriter(new FileWriter(csvFile))) {
/* 439 */         csvWriter.println(Arrays.toString(this.headers.toArray()).replace("[", "").replace("]", ""));
/* 440 */         for (List<String> anEmptyList : combinedAreaPellets) {
/* 441 */           String untiedResults = Arrays.toString(anEmptyList.toArray()).replace("[", "").replace("]", "");
/* 442 */           csvWriter.println(untiedResults);
/*     */         } 
/* 444 */       } catch (IOException e) {
/* 445 */         e.printStackTrace();
/*     */       } 
/* 447 */       File csvFileone = new File(outputFolder + "/All_Solidity_Results_Pellets.csv");
/* 448 */       try (PrintWriter csvWriter = new PrintWriter(new FileWriter(csvFileone))) {
/* 449 */         csvWriter.println(Arrays.toString(this.headers.toArray()).replace("[", "").replace("]", ""));
/* 450 */         for (List<String> anEmptyList : combinedSolidityPellets) {
/* 451 */           String untiedResults = Arrays.toString(anEmptyList.toArray()).replace("[", "").replace("]", "");
/* 452 */           csvWriter.println(untiedResults);
/*     */         } 
/* 454 */       } catch (IOException e) {
/* 455 */         e.printStackTrace();
/*     */       } 
/* 457 */       File csvFiletwo = new File(outputFolder + "/All_AR_Results_Pellets.csv");
/* 458 */       try (PrintWriter csvWriter = new PrintWriter(new FileWriter(csvFiletwo))) {
/* 459 */         csvWriter.println(Arrays.toString(this.headers.toArray()).replace("[", "").replace("]", ""));
/* 460 */         for (List<String> anEmptyList : combinedARPellets) {
/* 461 */           String untiedResults = Arrays.toString(anEmptyList.toArray()).replace("[", "").replace("]", "");
/* 462 */           csvWriter.println(untiedResults);
/*     */         } 
/* 464 */       } catch (IOException e) {
/* 465 */         e.printStackTrace();
/*     */       } 
/* 467 */       File csvFilethree = new File(outputFolder + "/All_FeretDiamter_Results_Pellets.csv");
/* 468 */       try (PrintWriter csvWriter = new PrintWriter(new FileWriter(csvFilethree))) {
/* 469 */         csvWriter.println(Arrays.toString(this.headers.toArray()).replace("[", "").replace("]", ""));
/* 470 */         for (List<String> anEmptyList : combinedFeretDPellets) {
/* 471 */           String untiedResults = Arrays.toString(anEmptyList.toArray()).replace("[", "").replace("]", "");
/* 472 */           csvWriter.println(untiedResults);
/*     */         } 
/* 474 */       } catch (IOException e) {
/* 475 */         e.printStackTrace();
/*     */       } 
/* 477 */       File csvFilefour = new File(outputFolder + "/All_MN_Results_Pellets.csv");
/* 478 */       try (PrintWriter csvWriter = new PrintWriter(new FileWriter(csvFilefour))) {
/* 479 */         csvWriter.println(Arrays.toString(this.headers.toArray()).replace("[", "").replace("]", ""));
/* 480 */         for (List<String> anEmptyList : combinedMNPellets) {
/* 481 */           String untiedResults = Arrays.toString(anEmptyList.toArray()).replace("[", "").replace("]", "");
/* 482 */           csvWriter.println(untiedResults);
/*     */         } 
/* 484 */       } catch (IOException e) {
/* 485 */         e.printStackTrace();
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void ratioArea(String outputFolder, boolean analyzeClumps, boolean analyzePellets) {
/* 494 */     if (analyzeClumps == true && analyzePellets == true) {
/* 495 */       int length = this.totAreaClumps.size();
/* 498 */       for (int m = 0; m < length; m++) {
/*     */         try {
/* 501 */           this.xClumps = Double.parseDouble(this.totAreaClumps.get(m));
/* 502 */         } catch (Exception e) {
/* 503 */           this.xClumps = 0.0D;
/*     */         } 
/*     */         try {
/* 507 */           this.xPellets = Double.parseDouble(this.totAreaPellets.get(m));
/* 508 */         } catch (Exception e) {
/* 509 */           this.xPellets = 0.0D;
/*     */         } 
/* 512 */         double totalFungalArea = this.xClumps + this.xPellets;
/* 513 */         double ratioPelletFungal = this.xPellets / totalFungalArea;
/* 515 */         this.totArea.add(Double.toString(totalFungalArea));
/* 516 */         this.areaRatioPellet.add(Double.toString(ratioPelletFungal));
/*     */       } 
/* 520 */       String headersratio = Arrays.toString(this.headers.toArray()).replace("[", "").replace("]", "");
/* 521 */       String strAreaClumps = Arrays.toString(this.totAreaClumps.toArray()).replace("[", "").replace("]", "");
/* 522 */       String strAreaPellet = Arrays.toString(this.totAreaPellets.toArray()).replace("[", "").replace("]", "");
/* 523 */       String strAreaFungal = Arrays.toString(this.totArea.toArray()).replace("[", "").replace("]", "");
/* 524 */       String strAreaRatio = Arrays.toString(this.areaRatioPellet.toArray()).replace("[", "").replace("]", "");
/* 526 */       File csvFile = new File(outputFolder + "/Total_Area_Ratio.csv");
/* 527 */       try (PrintWriter csvWriter = new PrintWriter(new FileWriter(csvFile))) {
/* 528 */         csvWriter.println(" ," + headersratio);
/* 529 */         csvWriter.println("Total area of dispersed mycelia [um^2]," + strAreaClumps);
/* 530 */         csvWriter.println("Total area of pellets [um^2]," + strAreaPellet);
/* 531 */         csvWriter.println("Total fungal area [um^2]," + strAreaFungal);
/* 532 */         csvWriter.println("ratio pellet area / fungal area," + strAreaRatio);
/* 533 */       } catch (IOException e) {
/* 535 */         e.printStackTrace();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\priya\OneDrive\Desktop\!\combineResults.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
