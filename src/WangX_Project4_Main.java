import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

class property{
	protected int label;
	protected  int nPixel;
	protected int nR;
	protected  int nC;
	protected  int mR;
	protected  int mC;
	
	public property(int l, int np, int nr, int mr, int nc, int mc) {
		label =l;
		nPixel = np;
		nR=nr;
		mR = mr;
		nC = nc;
		mC = mc;
	}
}
public class WangX_Project4_Main {
	protected static int numR;
	protected static int numC;
	protected static int minV;
	protected static int maxV;
	protected static int newLabel;
	protected static int trueNumCC;
	protected static int newMin;
	protected static int newMax;
	protected static int[][] zeroFramedAry;
	protected static int[] nznAry;
	protected static int [] EQAry;
	protected static char option;
	protected static property[] CCproperty;
	
	public WangX_Project4_Main() {
		
	}
	
	public static void negative1D(int[] a) {
		for(int i = 0; i<a.length;i++) {
			a[i] = -1;
		}
	}
	
	public static void loadImage(Scanner s) throws FileNotFoundException {
		for(int i =1; i<numR+1; i++) {
			for(int j =1; j<numC+1; j++) {
				zeroFramedAry[i][j] = s.nextInt();
			}
		}
	}
	 public static void printImg(int[][] a, FileWriter o) throws IOException {
		 o.write(numR +" " + numC+" " + minV+" " + maxV+"\n");
		 String s = ""+maxV;
		 int w = s.length()+1;
		    int r = 1;
		    while (r < numR+1 ) {
		      int c = 1;
		      while (c < numC+1 ) {
		    	  s = ""+a[r][c];
		    	  o.write(s);
		    	  
		    	  
		        int ww = s.length();
		        while (ww <= w) {
		        	o.write(" ");
		        	ww++;
		        }
		        c++;
		      }
		      o.write("\n");
		      r++;
		    }
	 }
	 public static void reformatPrettyPrint(int[][] a, FileWriter o) throws IOException {
		 o.write(numR +" " + numC+" " + minV+" " + maxV+"\n");
		 String s = ""+maxV;
		 int w = s.length()+1;
		    int r = 1;
		    while (r < numR+1 ) {
		      int c = 1;
		      while (c < numC+1 ) {
		    	  if(a[r][c] ==0) {
		    		  o.write(".");
		    		  s = ".";
		    	  }
		    	  else {
		    		  s = ""+a[r][c];
		    		  o.write(s);
		    	  }
		      
		        int ww = s.length();
		        while (ww <= w) {
		        	o.write(" ");
		        	ww++;
		        }
		        c++;
		      }
		      o.write("\n");
		      r++;
		    }
	 }
	
	 public static void connected4(int[][] a, int nl, int[] eq, FileWriter o, FileWriter debug) throws IOException {
		 debug.write("entering connected4 method \n");
		 connect4Pass1(a,eq);
		 debug.write("After connected4 pass1, newLabel = " + newLabel+"\n");
		 o.write("Result of: Pass 1\n");
		 reformatPrettyPrint(a,o);
		 o.write("\nEquivalency Table after: Pass 1  ");
		 printEQAry(newLabel, o);
		 
		 connect4Pass2(a,eq);
		 debug.write("After connected4 pass2, newLabel = " + newLabel+"\n");
		 o.write("\nResult of: Pass 2\n");
		 reformatPrettyPrint(a,o);
		 o.write("\nEquivalency Table after: Pass 2  ");
		 printEQAry(newLabel, o);
		 
		 trueNumCC = manageEQAry(eq, nl);
		 o.write("\nEquivalency Table after: EQ Table Management  ");
		 printEQAry(nl, o);
		 newMin = 0;
		 newMax = trueNumCC;
		 CCproperty = new property[trueNumCC+1];
		 debug.write("In connected4, after manage EQAry, trueNumCC = " + trueNumCC +"\n");
		 
		 connectPass3(a, eq, CCproperty, trueNumCC, debug);
		 o.write("\nResult of: Pass 3\n");
		 reformatPrettyPrint(a,o);
		 o.write("\nEquivalency Table after: Pass 3  ");
		 printEQAry(nl, o);
		 debug.write("Leaving connected4 method \n");
	 }
	 
	 public static void connected8(int[][] a, int nl, int[] eq, FileWriter o, FileWriter debug) throws IOException {
		 debug.write("entering connected8 method \n");
		 connect8Pass1(zeroFramedAry,eq);
		 debug.write("After connected8 pass1, newLabel = " + newLabel+"\n");
		 o.write("Result of: Pass 1\n");
		 reformatPrettyPrint(zeroFramedAry,o);
		 o.write("\nEquivalency Table after: Pass 1  ");
		 printEQAry(newLabel, o);
		 
		 connect8Pass2(zeroFramedAry,eq);
		 debug.write("After connected8 pass2, newLabel = " + newLabel+"\n");
		 o.write("\nResult of: Pass 2\n");
		 reformatPrettyPrint(zeroFramedAry,o);
		 o.write("\nEquivalency Table after: Pass 2  ");
		 printEQAry(newLabel, o);
		 
		 trueNumCC = manageEQAry(eq, newLabel);
		 o.write("\nEquivalency Table after: EQ Table Management  ");
		 printEQAry(newLabel, o);
		 newMin = 0;
		 newMax = trueNumCC;
		 CCproperty = new property[trueNumCC+1];
		 debug.write("In connected8, after manage EQAry, trueNumCC = " + trueNumCC +"\n");
		 
		 connectPass3(zeroFramedAry, EQAry, CCproperty, trueNumCC, debug);
		 o.write("\nResult of: Pass 3\n");
		 reformatPrettyPrint(zeroFramedAry,o);
		 o.write("\nEquivalency Table after: Pass 3  ");
		 printEQAry(newLabel, o);
		 debug.write("Leaving connected8 method \n");
	 }
	 
	 public static void connectPass3(int[][] a, int[] eq, property[] cp, int tc, FileWriter debug) throws IOException {
		 debug.write("entering connectPass3 method \n");
		 for(int i =1; i< trueNumCC+1;i++) {
			 CCproperty[i]= new property(i,0,numR,0,numC,0);
		 }
		 
		 for(int r =0; r<numR+1;r++) {
			 for(int c=0; c<numC+1;c++) {
				 if(zeroFramedAry[r][c] >0) {
					 zeroFramedAry[r][c] = EQAry[zeroFramedAry[r][c]]; 
					 int k = zeroFramedAry[r][c];
					 CCproperty[k].nPixel++;
					 if(r<CCproperty[k].nR)
						 CCproperty[k].nR=r-1;
					 if(r>CCproperty[k].mR)
						 CCproperty[k].mR=r-1;
					 if(c<CCproperty[k].nC)
						 CCproperty[k].nC=c-1;
					 if(c>CCproperty[k].mC)
						 CCproperty[k].mC=c-1;
				 }
			 }
		 }
		 debug.write("leaving connectPass3 method \n");
	 }
	 
	 public static void drawBoxes(int[][] a, property[] cp, int tc, FileWriter debug) throws IOException {
		 debug.write("entering drawBoxes method \n");
		 int index = 1;
		 
		 while(index<=trueNumCC) {
		 int minR = CCproperty[index].nR+1;
		 int minC =  CCproperty[index].nC+1;
		 int maxR =  CCproperty[index].mR+1;
		 int maxC =  CCproperty[index].mC+1;
		 int label =  CCproperty[index].label;
		 
		 for(int i = minC; i<=maxC;i++) {
			 zeroFramedAry[minR][i] = label;
			 zeroFramedAry[maxR][i] = label;
		 }
		 for(int i = minR; i<=maxR;i++) {
			 if(zeroFramedAry[i][minC-1]==label ) {
				 minC--;
				 break;
			 }
		 } 
		 for(int i = minR; i<=maxR;i++) {
			 zeroFramedAry[i][minC] = label;
			 zeroFramedAry[i][maxC] = label;
		 }
		 index++;
		 }
		 debug.write("leaving drawBoxes method \n");
	 }
	 
	 public static void connect4Pass1(int[][] a, int[] eq) {
		 int minL=0;
		 for(int r =1; r<numR+1;r++) {
			 for(int c=1; c<numC+1;c++) {
				 if(zeroFramedAry[r][c] >0) {
					int d =findNeighbor(zeroFramedAry,r,c,4,1);
					 if(nznAry[1]==0&&nznAry[0]==0) {
						 newLabel++;
						 zeroFramedAry[r][c]=newLabel;
						EQAry[newLabel]=newLabel;
					 }
					 else if(d==1|| (d==0 &&(nznAry[0]==0||nznAry[1]==0))) {
						 if(nznAry[0]!=0)
							 zeroFramedAry[r][c]=nznAry[0];
						 else
							 zeroFramedAry[r][c]=nznAry[1];
					 }
					 else if(d==0&&nznAry[0]>0&&nznAry[1]>0) {
						 if(nznAry[0]!=0)
							 minL =nznAry[0];
						 else
							 minL =nznAry[1];
						 zeroFramedAry[r][c]=minL;
						 updateEQ(newLabel,minL,2);
					 }
				 }
			 }
		 }
	 }
	 
	 public static void connect4Pass2(int[][] a, int[] eq) {
		 int minL;
		 for(int r =numR; r>0;r--) {
			 for(int c=numC; c>0;c--) {
				 if(zeroFramedAry[r][c] >0) {
					int d =findNeighbor(zeroFramedAry,r,c,4,2);
						if(d==1&&nznAry[0]==0) {
							}
					 else if((d==1&& nznAry[0]== zeroFramedAry[r][c])|| (d==0 &&(nznAry[0]== a[r][c]||nznAry[1]== a[r][c])&&(nznAry[0]==0||nznAry[1]==0))) {
						 
					 }
					 else if((nznAry[0]>0 && nznAry[0] !=zeroFramedAry[r][c])||(nznAry[1]>0 && nznAry[1] !=zeroFramedAry[r][c])) {
						 if(nznAry[0]!=0)
							 minL =nznAry[0];
						 else
							 minL =nznAry[1];
						 if(zeroFramedAry[r][c]>minL) {
							 EQAry[zeroFramedAry[r][c]] = minL;
							 zeroFramedAry[r][c] = minL;
						 }
					 }
					 zeroFramedAry[r][c] = EQAry[zeroFramedAry[r][c]];
				 }
				 
			 }
			 
		 }
	 }
	 public static void connect8Pass1(int[][] a,  int[] eq) {
		 int ml=0;
		 for(int r =1; r<numR+1;r++) {
			 for(int c=1; c<numC+1;c++) {
				 if(a[r][c] >0) {
					 int b = findNeighbor(zeroFramedAry,r,c,8,1);
					 if(nznAry[4]==0&&nznAry[0]==0) {
						 newLabel++;
						 zeroFramedAry[r][c]=newLabel;
						 EQAry[newLabel]=newLabel;
					 }
					 else if(nznAry[4]==1) {
						 zeroFramedAry[r][c]=nznAry[0];
					 }
					 else if(nznAry[4]>1) {
						 ml= nznAry[0];
						 zeroFramedAry[r][c]=ml;
						 updateEQ(newLabel,ml,4);
					 }
				 }
			 }
		 }
	 }
	 
	 public static void connect8Pass2(int[][] a, int[] eq) {
		 for(int r =numR; r>0;r--) {
			 for(int c=numC; c>0;c--) {
				 if(zeroFramedAry[r][c] >0) {
					 int b = findNeighbor(zeroFramedAry,r,c,8,2);
					 if(nznAry[4]==0&&nznAry[0]==0) {
					 }
					 else if(nznAry[4]==1&&nznAry[0]==zeroFramedAry[r][c]) {
					 }
					 else if(nznAry[4]>1|| (nznAry[4]==1&&nznAry[0]!=zeroFramedAry[r][c])) {
						 int ml = nznAry[0];
						 if(zeroFramedAry[r][c]>ml) {
							 EQAry[zeroFramedAry[r][c]] = ml;
							 zeroFramedAry[r][c] = ml;
						 }
					 }
					 else
					 zeroFramedAry[r][c] = EQAry[zeroFramedAry[r][c]];
					 
				 }
			 }
		 }
	 }
	 public static int findNeighbor(int[][] a, int r, int c, int cc, int p) {
		int s = 0;
		int d=0;
		int f=0;
		 if(cc==4) {
			 if(p==1) {
				 nznAry[0]=Math.min(zeroFramedAry[r-1][c],zeroFramedAry[r][c-1]);
				 nznAry[1]=Math.max(zeroFramedAry[r-1][c],zeroFramedAry[r][c-1]);
			 }
			 else {
				 nznAry[0]=Math.min(zeroFramedAry[r+1][c],zeroFramedAry[r][c+1]);
				 nznAry[1]=Math.max(zeroFramedAry[r+1][c],zeroFramedAry[r][c+1]);
			 }
			 if(nznAry[0]== nznAry[1]) {
					 return 1;
				 }
		 }
		 else {
			 int i,j;
			 if(p ==1) 
			  i = r-1;
			 else 
				 i = r;
			 int n = 0;
			 while(i<=r+1) {
					 j= c-1;
					 if(i==r && p==2)
						 j=c+1;
				 while(j <=c+1) {
					 if(zeroFramedAry[i][j] !=0) {
						 nznAry[n] = zeroFramedAry[i][j];
						 n++;
					 }
					 if(i==r && j==c&&p==1) {
						 break;
					 }
				 }
			 }
			/* if(p==1) {
				 nznAry[0]=zeroFramedAry[r-1][c];
			 nznAry[1]=zeroFramedAry[r-1][c-1];
			 nznAry[2]=zeroFramedAry[r][c-1];
			 nznAry[3]=zeroFramedAry[r-1][c+1];
			 }
			 else {
				 nznAry[0]=zeroFramedAry[r+1][c];
				 nznAry[1]=zeroFramedAry[r+1][c-1];
				 nznAry[2]=zeroFramedAry[r][c+1];
				 nznAry[3]=zeroFramedAry[r+1][c+1];
			 }
			 for(int i = 1; i<4;i++) {
				 int min = nznAry[i];
				 int j = i-1;
				if(min==0||nznAry[j]==0)
					s++;
				 while(j>=0 && min<nznAry[j]) {
					 nznAry[j+1]=nznAry[j];
					 j--;
				 }
				 nznAry[j+1]=min;
			 }
			 
			 for(int i = 0; i<4-s;i++) {
				 nznAry[i]=nznAry[i+s];
		        }
		        for (int i = 0; i < s; i++) {
		        	nznAry[4 - 1 - i] = 0;
		        }
		        for(int i =0; i<3;i++) {
		        	 if(nznAry[i+1]==nznAry[i])
							d++;
		        	 else if(nznAry[i+1]!=nznAry[i]&&(nznAry[i+1]!=0||nznAry[i]!=0))
		        		 f++;
		        }
		        nznAry[4]=f;*/
		 }
		 return d;
	 }
	 
	 public static void updateEQ(int l, int ml,int c) {
		 for(int i =0;i<c;i++) {
			 if(nznAry[i]!=0)
				EQAry[nznAry[i]]=ml;
		 }
	 }
	 
	 public static int manageEQAry(int[] eq, int nl) {
		 int rl= 0;
		 int i =1;
		 while(i<=newLabel) {
			  if(i!=EQAry[i]) {
				  EQAry[i] = EQAry[EQAry[i]];
			  }
			  else {
				  rl++;
				  EQAry[i]=rl;
			  }
			  i++;
		 }
		return rl;
	 }
	 
	 public static void printCCproperty(FileWriter o) throws IOException {
		 o.write(numR + " " + numC+ " "+minV+ " "+maxV+ "\n");
		 o.write(trueNumCC+"\n");
		 for(int i =1; i<trueNumCC+1;i++) { 
			 o.write(CCproperty[i].label+"\n");
			 o.write(CCproperty[i].nPixel+"\n");
			 o.write(CCproperty[i].nR+"\t"+CCproperty[i].nC+"\n");
			 o.write(CCproperty[i].mR+"\t"+CCproperty[i].mC+"\n");
		 }
	 }
	 
	 public static void printEQAry(int nl, FileWriter o) throws IOException {
		 o.write("(indexing starts from 1)\n");
		 for(int i = 1; i<=newLabel;i++) {
			 o.write(EQAry[i]+" ");
		 }
		 o.write("\n");
	 }
	public static void main(String[] args) throws IOException {
		File inFile = new File(args[0]);
		String connectness = args[1];
		FileWriter RFprettyPrintFile = new FileWriter(args[2]);
		FileWriter labelFile = new FileWriter(args[3]);
		FileWriter propertyFile = new FileWriter(args[4]);
		FileWriter debug = new FileWriter(args[5]);
		Scanner s = new Scanner(inFile);
		numR = s.nextInt();
		numC= s.nextInt();
		minV= s.nextInt();
		maxV= s.nextInt();
		newLabel = 0;
		zeroFramedAry = new int[numR+2][numC+2];
		nznAry = new int[5];
		EQAry = new int[(numR*numC)/4];
		
		loadImage(s);
		if(connectness.equals("4")) {
			connected4(zeroFramedAry, newLabel, EQAry,RFprettyPrintFile,debug);
		}
		if(connectness.equals("8")) {
			connected8(zeroFramedAry, newLabel, EQAry,RFprettyPrintFile,debug);
		}
		
		printImg(zeroFramedAry, labelFile);
		printCCproperty(propertyFile);
		drawBoxes(zeroFramedAry, CCproperty, trueNumCC,debug);
		RFprettyPrintFile.write("\n Drawing bounding boxes of CC\n");
		reformatPrettyPrint(zeroFramedAry,RFprettyPrintFile);
		RFprettyPrintFile.write("\nNumber of Connected Components: "+trueNumCC+"\n");
		
		RFprettyPrintFile.close();
		labelFile.close();
		propertyFile.close();
		debug.close();
		s.close();
	}
}
