import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class GetData extends SS_OAS
{
	
	static double[] r = new double[DataSize];
	static double[] p = new double[DataSize];
	static double[] e = new double[DataSize];
	static double[] d = new double[DataSize];
	static double[] b = new double[DataSize];
	static double[] w = new double[DataSize];
	
	public static void WhichData() throws IOException 
	{
		FileInputStream fis = new FileInputStream(new File(".\\project_data\\"+DataSize+"Orders.xlsx"));
		XSSFWorkbook wb = new XSSFWorkbook(fis);
		XSSFSheet sheet = wb.getSheetAt(0);
		Row row = sheet.getRow(rown);
		for(int i=0;i<4;i++)
		{
	    	Cell cell = row.getCell(i);
	    	Data[i]=(int) cell.getNumericCellValue();
		}
		Cell cell = row.getCell(4);
		opt=cell.getNumericCellValue();
		wb.close();
		fis.close();
	}
	
	public static void Get_Data() throws FileNotFoundException
	{
		ArrayList<String> DataS = new ArrayList<>();
		ArrayList<double[]> adata = new ArrayList<>();
		File file = new File(".\\project_data\\Dataslack_"+Data[0]+"orders_Tao"+Data[1]+"R"+Data[2]+"_"+Data[3]+"_without_setup.dat");
		
		Scanner scnr = new Scanner(file);
		
		while(scnr.hasNextLine())
		{
			String line = scnr.nextLine();
			DataS.add(line);
		}
		
		int[] index = {1,4,7,10,13,16};
		for(int i=0;i<6;i++)
		{
			String[] k = DataS.get(index[i]).split(",");
			double[] ki = new double[k.length]; 
			for(int j=0;j<k.length;j++)
			{
				ki[j] = Double.parseDouble(k[j]);
			}
			adata.add(ki);
		}
		
		for(int j=1;j<=DataSize;j++)
		{
			r[j-1]=adata.get(0)[j];
			p[j-1]=adata.get(1)[j];
			e[j-1]=adata.get(2)[j];
			d[j-1]=adata.get(3)[j];
			b[j-1]=adata.get(4)[j];
			w[j-1]=adata.get(5)[j];
		}
		
		scnr.close();
	}
	
	public static void WriteData() throws EncryptedDocumentException, IOException
	{
		try 
		{
			FileInputStream inputStream = new FileInputStream(new File(".\\project_data\\"+DataSize+"Orders.xlsx"));
			Workbook workbook = WorkbookFactory.create(inputStream);
            Sheet sheet = workbook.getSheetAt(0);
			Row row = sheet.createRow(rown);
			for(int i=0;i<4;i++)
			{
		    	Cell cell = row.createCell(i);
		    	cell.setCellValue(Data[i]);
			}
			Cell cell = row.createCell(4);
	    	cell.setCellValue(opt);
	    	cell = row.createCell(5);
	    	cell.setCellValue(Incumbent.obj);
	    	cell = row.createCell(6);
	    	cell.setCellValue((opt-Incumbent.obj)/opt);
	    	cell = row.createCell(7);
	    	cell.setCellValue(System.currentTimeMillis()-cts);
	    	
	    	FileOutputStream outputStream = new FileOutputStream(".\\project_data\\"+DataSize+"Orders.xlsx");
            workbook.write(outputStream);
            workbook.close();
            outputStream.close();
		}
		catch (IOException | EncryptedDocumentException ex)
		{
            ex.printStackTrace();
		}
	}
}
