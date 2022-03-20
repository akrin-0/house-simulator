/** @author Muhammet Fatih Ulu
 *  @brief A Java program for simulating the distribution of student houses by using Java Collections Framework.
 */
 
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.ArrayList;
import java.lang.StringBuilder;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.io.File;

public class house-simulator {

	public static void main(String[] args) throws IOException {
		double t1 = (double) System.nanoTime();

		// Check if there is an issue with arguments.
		if (args.length != 2) {
			System.out.println("Error: Given arguments are not valid.");
			System.out.println("Usage: java house-simulator <inputfile> <outputfile>");
			System.exit(0);
		} else {
			
			// Declare array lists (array lists are used to improve performance).
			
			// To read lines and sort. 
			ArrayList<String> allLines = new ArrayList<String>();
			ArrayList<String> allSorted = new ArrayList<String>();
			// Remaining house and student duration.
			ArrayList<Integer> houseDuration = new ArrayList<Integer>();
			ArrayList<Integer> studentDuration = new ArrayList<Integer>();
			// Rating of house and rating expectation of student.
			ArrayList<Double> houseRating = new ArrayList<Double>();
			ArrayList<Double> studentRating = new ArrayList<Double>();
			// Name and ID of student, ID of house.
			ArrayList<String> studentName = new ArrayList<String>();
			ArrayList<Integer> studentID = new ArrayList<Integer>();
			ArrayList<Integer> houseID = new ArrayList<Integer>();
			
			// Try if given text file could be read, if not catch error.
			try {
				BufferedReader reader = new BufferedReader(new FileReader(args[0]));
				String line = reader.readLine();
				while (line != null) {
					allLines.add(line);
					line = reader.readLine();
				}				
				reader.close();
			} catch (FileNotFoundException err) {
				System.out.println("An error occured while reading the file.");
				err.printStackTrace();
			}
			
			/**
			 *  Collections.sort() is used, which sorts all lines that are string.
			 *  Therefore strings "0" are added to the beginnings of IDs which lack
			 *  digit. Maximum length of and ID is found by parsing lines.
			 */
			int maxLengthId = 1;
			for (String line : allLines) {
				String[] wordsArr = line.split(" ");
				if (wordsArr[1].length() > maxLengthId)
					maxLengthId = wordsArr[1].length();
			}


			/**
			 * The digit differences are obtained and changes are 
			 * made by for loop. The derived lines are added to 
			 * another array list and then sorted.
			 */
			for (String line : allLines) {
				String[] wordsArr = line.split(" ");
				if (wordsArr[1].length() < maxLengthId) {
					int diff = maxLengthId - wordsArr[1].length();
					for (int i = 0; i < diff; i++)
						line = new StringBuilder(line).insert(2, "0").toString(); // Addition of 0's.
				}
				allSorted.add(line);
			}
			Collections.sort(allSorted);


			for (String line : allSorted) {
				String[] wordsArr = line.split(" ");
				int currentID = Integer.parseInt(wordsArr[1]);
				
				if (wordsArr[0].equals("h")) { // Detect whether line belongs to a house or a student.
					// 3. and 4. columns of input file for houses are duration and rating respectively.
					int currentDuration = Integer.parseInt(wordsArr[2]);
					double currentRating = Integer.parseInt(wordsArr[3]);
					houseID.add(currentID);
					houseDuration.add(currentDuration); 
					houseRating.add(currentRating);
				} else if (wordsArr[0].equals("s")) {
					// 4., 5. and 3. columns of input file for students are duration, rating and name respectively.
					int currentDuration = Integer.parseInt(wordsArr[3]);
					double currentRating = Integer.parseInt(wordsArr[4]);
					studentID.add(currentID);
					studentDuration.add(currentDuration);
					studentRating.add(currentRating);
					studentName.add(wordsArr[2]);
				} else {
					System.out.println("Error: The first chars of lines have to be either 'h' or 's'.");
					System.exit(0);
				}
			}


			// Check for limits.
			int maxSemester = Collections.max(studentDuration); // Cannot be greater than 8.
			int maxSemesterHouse = Collections.max(houseDuration); // Same with above.
			double maxRating = Collections.max(studentRating);  // cannot be greater than 10.0 or lower than 0.0 
			double maxRatingHouse = Collections.max(houseRating); // Same with above.
			
			
			/**
			 *  ID array lists are created to check if there exists 
			 *  two ID with the same value for both students and houses.
			 *  They are sorted, and IDs are checked.
			 */
			Collections.sort(studentID);
			Collections.sort(houseID);
			
			// Check if IDs are valid.
			for (int i = 0; i < (studentID.size() - 1); i++)
				if (studentID.get(i) == studentID.get(i + 1)) {
					System.out.println("No two student can have the same ID.");
					System.exit(0);
				}
			for (int i = 0; i < (houseID.size() - 1); i++)
				if (houseID.get(i) == houseID.get(i + 1)) {
					System.out.println("No two house can have the same ID.");
					System.exit(0);
				}
				
			

			// Check if there is a non-realistic input data.
			if (maxSemester > 8 && maxSemesterHouse > 8 && maxRating < 0.0 
					&& maxRating > 10.0 && maxRatingHouse < 0.0 && maxRatingHouse > 10.0 ) {
				System.out.println("There is unexpected data in given input.");
				System.exit(0);
			} else {

				/**
				 * Till the very young person finishes the university, loop 
				 * proceeds. Students and houses are already queued. Starts 
				 * with the student at index 0, tries to find the first suitable 
				 * house. By suitability, the if condition searches for a house 
				 * is available, whose rating is sufficient, that student never 
				 * stayed in any house, and student isn't graduated.
				 */
				while (maxSemester > 0) {
					for (int j = 0; j < studentDuration.size(); j++) {
						for (int i = 0; i < houseDuration.size(); i++ ) {
							// If circumstances are met, student finds a house.
							if ((houseDuration.get(i).equals(0)) && 
									(studentRating.get(j) <= houseRating.get(i)) && studentDuration.get(j) != 0) {
								houseDuration.set(i, studentDuration.get(j)); // House is held till student graduates.
								studentRating.remove(j);   // Remove student from the all lists.
								studentDuration.remove(j);
								studentName.remove(j);
								j--; // Element is removed, index is slipped.
								
								// If house will not be available at all, remove it from the house lists.
								if (houseDuration.get(i) == maxSemester) {
									houseDuration.remove(i); 
									houseRating.remove(i);
									i--;
								}
								// If student finds house, stop and move to the next student.
								break; 
							}
						}
					}

					// Decrement part (end of the semester).
					if (studentDuration.size() > houseDuration.size()) {
						int k = 0;
						while (k < houseDuration.size()) {
							if (studentDuration.get(k) > 0)
								studentDuration.set(k, (studentDuration.get(k) - 1));
							if (houseDuration.get(k) > 0)
								houseDuration.set(k, (houseDuration.get(k) - 1));
							k++;
						}
						while (k < studentDuration.size()) {
							if (studentDuration.get(k) > 0)
								studentDuration.set(k, (studentDuration.get(k) - 1));
							k++;
						}
					} else {
						int k = 0;
						while (k < studentDuration.size()) {
							if (studentDuration.get(k) > 0)
								studentDuration.set(k, (studentDuration.get(k) - 1));
							if (houseDuration.get(k) > 0)
								houseDuration.set(k, (houseDuration.get(k) - 1));
							k++;
						}
						while (k < houseDuration.size()) {
							if (houseDuration.get(k) > 0)
								houseDuration.set(k, (houseDuration.get(k) - 1));
							k++;
						}
					}
					maxSemester--;
				}
				
				// Create unlucky students' array list.
				ArrayList<String> unluckyOnes = new ArrayList<String>();
				for (int i = 0; i < studentName.size(); i++) {
					unluckyOnes.add(studentName.get(i));
				}
				
				// Create output file and check if that name exists. If not, write.
				String outputPath = args[1];
				File output = new File(outputPath);
				try {					
					if (output.createNewFile())
						System.out.println("File is created.");
					else {
						System.out.println("File already exists.");
						return;
					}
					FileWriter writer = new FileWriter(outputPath);
					for (String unlucky : unluckyOnes) {
						writer.write(unlucky);
						writer.write("\n");
					}
					writer.close();
				} catch (Exception err) {
					System.out.println("An error occured while writing to the file.");
					err.getStackTrace();
				}
			}			
		}
		// Print the total seconds passed.
		double t2 = (double) System.nanoTime();
		double diff = (t2 - t1)/1000000000;
		System.out.println(diff + " seconds");
	}
}
