/*    */ import MPD_Quantification.UserInterface;
/*    */ import ij.measure.ResultsTable;
/*    */ 
/*    */ public class processResults extends UserInterface {
/*    */   public void resultsProcessing(String resultsPath, double fileResolution) {
/* 11 */     ResultsTable resultTable = ResultsTable.getResultsTable();
/* 13 */     int nResults = resultTable.size();
/* 15 */     for (int i = 0; i < nResults; i++) {
/* 17 */       Double Area = Double.valueOf(resultTable.getValue("Area", i));
/* 18 */       Double Areaum = Double.valueOf(Area.doubleValue() * fileResolution * fileResolution);
/* 19 */       resultTable.setValue("Area [um^2]", i, Areaum.doubleValue());
/* 21 */       Double Feret = Double.valueOf(resultTable.getValue("Feret", i));
/* 22 */       Double Feretum = Double.valueOf(Feret.doubleValue() * fileResolution);
/* 23 */       resultTable.setValue("Feret [um]", i, Feretum.doubleValue());
/* 25 */       Double AR = Double.valueOf(resultTable.getValue("AR", i));
/* 26 */       Double Solidity = Double.valueOf(resultTable.getValue("Solidity", i));
/* 27 */       Double MN = Double.valueOf(2.0D * Math.sqrt(Area.doubleValue()) * Solidity.doubleValue() / Math.sqrt(3.14D) * Feret.doubleValue() * AR.doubleValue());
/* 28 */       resultTable.setValue("MN", i, MN.doubleValue());
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\priya\OneDrive\Desktop\!\processResults.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */