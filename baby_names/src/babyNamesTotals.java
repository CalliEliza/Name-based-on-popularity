/**
 * Print out total number of babies born, as well as for each gender, in a given CSV file of baby name data.
 * 
 * @author Callie
 */
import java.io.File;
import edu.duke.*;

import org.apache.commons.csv.*;


public class babyNamesTotals {
	public void printNames () {
		FileResource fr = new FileResource();
		for (CSVRecord rec : fr.getCSVParser(false)) {
			int numBorn = Integer.parseInt(rec.get(2));
			if (numBorn <= 100) {
				System.out.println("Name " + rec.get(0) +
						   " Gender " + rec.get(1) +
						   " Num Born " + rec.get(2));
			}
		}
	}

	public void totalBirths (FileResource fr) {
		int totalBirths = 0;
		int totalBoys = 0;
		int totalGirls = 0;
		int totalDifferentGirls = 0;
		int totalDifferentBoys = 0; 
		for (CSVRecord rec : fr.getCSVParser(false)) {
			int numBorn = Integer.parseInt(rec.get(2));
			totalBirths += numBorn;
			if (rec.get(1).equals("M")) {
				totalBoys += numBorn;
				totalDifferentBoys += 1;
			}
			else {
				totalGirls += numBorn;
				totalDifferentGirls += 1;
			}
			
		}
		System.out.println("total births = " + totalBirths);
		System.out.println("female girls = " + totalGirls);
		System.out.println("male boys = " + totalBoys);
		System.out.println("Unique girl names = " + totalDifferentGirls);
		System.out.println("Unique girl names = " + totalDifferentBoys);
	}
	
	public int getRank(int year, String name, String gender) {
		// set variables up 
		int rank = 0;
		int femaleCounter = 0;
		int maleCounter = 0;
		// file resource
		String yrStr = Integer.toString(year);
		FileResource fr = new FileResource("data/babyData/yob"+yrStr+".csv");
		// loop through data, matching name
		for (CSVRecord rec : fr.getCSVParser(false)) {
			if (rec.get(1).equals("F")) {
				femaleCounter += 1;
				if (rec.get(0).equals(name)) {
					rank = femaleCounter;
				}
			}
			if (rec.get(1).equals("M")) {
				maleCounter += 1;
				if (rec.get(0).equals(name)) {
					rank = maleCounter;
				}
			}
		}
		return rank;
	}
	
	public String getName(int year, int rank, String gender) {
		// file resource
		String yrStr = Integer.toString(year);
		String name = "";
		int count = 1;
		int maleCounter = 0;
		FileResource fr = new FileResource("data/babyData/yob"+yrStr+".csv");
		for (CSVRecord rec : fr.getCSVParser(false)) {
			if (rec.get(1).equals(gender)) {
				if (rec.get(1).equals("M")) {
					maleCounter += 1;
					if (rank == maleCounter) {
					name = rec.get(0);
					}
				}
				else if (rank == count) {
					name = rec.get(0);
				}
			}
			count +=1;
		}
		return name;
	}
	
	public void whatIsNameInYear(String name, int year, int newYear, String gender) {
		int rank = getRank(year, name, gender);
		String newName = getName(newYear, rank, gender);
		System.out.println(name +" born in "+ year +" would be "+ newName +" if born in "+ newYear + ".");
	}
	
	public int yearOfHighestRank(String name, String gender) {
		DirectoryResource dr = new DirectoryResource();
		int rank1 = 0;
		int years = 0; 
		for (File f : dr.selectedFiles()) {
			int beg = f.getName().indexOf("b");
			int end = f.getName().indexOf("s", beg+1);
			int year = Integer.parseInt(f.getName().substring(beg+1,end-2));
			int rank = getRank(year, name, gender);
			if (rank1 == 0) {
				rank1 = rank;
				years = year;
			}
			else if (rank1 > rank) {
				rank1 = rank;
				years = year;
			}
		}
		return years;
	}
	
	public double getAverageRank(String name, String gender) {
		DirectoryResource dr = new DirectoryResource();
		double rankAvg = 0;
		double top = 0;
		double totalFiles = 0;
		for (File f : dr.selectedFiles()) {
			int beg = f.getName().indexOf("b");
			int end = f.getName().indexOf("s", beg+1);
			int year = Integer.parseInt(f.getName().substring(beg+1,end-2));
			int rank = getRank(year, name, gender);
			top += rank;
			totalFiles += 1;
		}
		rankAvg = top / totalFiles;
		return rankAvg;
	}
	
	public int getTotalBirthsRankedHigher(int year, String name, String gender) {
		FileResource fr = new FileResource("data/babyData/yob"+year+".csv");
		int totalBirths = 0;
		int count = 0;
		int countMale = 0;
		for (CSVRecord rec : fr.getCSVParser(false)) {
			if (rec.get(0).equals(name) && rec.get(1).equals(gender)) {
				for (CSVRecord rec2 : fr.getCSVParser(false)) {
					if (rec2.get(1).equals(gender) && countMale < count) {
						totalBirths += Integer.parseInt(rec2.get(2));
					}
					countMale += 1;
				}
			}
			count += 1;
		}
		return totalBirths;
	}

	public void testTotalBirths () {
		//FileResource fr = new FileResource();
		FileResource fr = new FileResource("data/babyData/yob1905.csv");
		totalBirths(fr);
	}
	
	public void testGetRank () {
		//FileResource fr = new FileResource();
		//FileResource fr = new FileResource("data/babyData/example-small.csv");
		System.out.println(getRank(1971, "Frank", "M" ));
	}
	
	public void testGetName () {
		//FileResource fr = new FileResource();
		//FileResource fr = new FileResource("data/babyData/example-small.csv");
		System.out.println(getName(1982, 450 , "M" ));
	}
	
	public void testWhatIsNameInYear () {
		//FileResource fr = new FileResource();
		//FileResource fr = new FileResource("data/babyData/example-small.csv");
		//whatIsNameInYear("Isabella", 2012, 2013 , "F" );
		whatIsNameInYear("Owen", 1974, 2014, "M");
	}
	
	public void testYearOfHighestRank () {
		//FileResource fr = new FileResource();
		//FileResource fr = new FileResource("data/babyData/example-small.csv");
		//whatIsNameInYear("Isabella", 2012, 2013 , "F" );
		System.out.println(yearOfHighestRank("Mich", "M"));
	}
	
	public void testGetAverageRank () {
		//FileResource fr = new FileResource();
		//FileResource fr = new FileResource("data/babyData/example-small.csv");
		//whatIsNameInYear("Isabella", 2012, 2013 , "F" );
		System.out.println(getAverageRank("Robert", "M"));
	}
	
	public void testGetTotalBirthsRankedHigher() {
		//FileResource fr = new FileResource();
		//FileResource fr = new FileResource("data/testData/yob2012short.csv");
		System.out.println(getTotalBirthsRankedHigher(1990, "Drew", "M"));
	}
}
