/*
 * @author alex acevedo
 * 
 * CS 321
 * Instructor Yeh
 * 
 * This program is a first and second level based cache that will store more commonly used words within a files to
 * quickly be accessed. It will run off of command line arguments the user will input and run the file input text to 
 * determine hit or miss ratios, word references, for either global, 1st level or 2nd level. This is the main as well
 * 
 * 
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.Timer;

public class Test {
	
	//private global variables that will store user inputs or values
	
	private static int hitLevelOne;
	private static int hitLevelTwo;
	private static int missLevelOne;
	private static int missLevelTwo;
	private static int refsLevelOne;
	private static int refsLevelTwo;
	private static int refsTotal;
	private static int hitsTotal;
	private static double ratioLevelOne;
	private static double ratioLevelTwo;
	private static double globalRatio;
	private static Cache cacheLevelOne;
	private static Cache cacheLevelTwo;
	private static Scanner scan;

	//Main method for the program
	public static void main(String[] args) {
		
		//Creates a string value for the file that will be inputed by the user
		String fileInput;
		Timer timer = new Timer();
		
		//Checks if there are arguments inputed into the command line. Prints correct usage if not
		if (args.length < 1) {
			System.out.println("Error - There were no arguments in the Command Line");
			System.out.println("Correct Usage: ");
			System.out.println('\t' + "For Level One Cache:");
			System.out.println('\t' + "java Test 1 <cache size> <input textfile name>");

			System.out.println("\n" + '\t' + "For Level Two cache:");
			System.out.println('\t' + "java Test 2 <1st-level cache size> <2nd-level cache size> <input textfile name>");
			System.exit(1);
		}
		
		//Checks the first argument in the command line to determine if it is correct, if not throw correct usage format
		if (!args[0].equals("1") && !args[0].equals("2")) {
			System.out.println("Error - Incorrect input for Cache Level: ");
			System.out.println("\"1\" for Level One Cache" + "\n\"2\" for Level Two Cache");
			System.exit(1);
		}

		//If first argument is 1, check if the command line is in correct format length then run operations.
		if (args[0].equals("1")) {
			if (args.length < 3 || args.length > 3) {
				System.out.println("Error - Incorrect number of arguments for First Level.");
				System.out.println("Usage: ");
				System.out.println("\njava Test 1 <cache size> <input textfile name>");
				System.exit(1);
			}

			else {

				//try catch block to check if the file is correctly inputed or exists.
				try {
					File file = new File(args[2]);
					if (!file.isFile()) {
						System.out.println("Incorrect or not a Valid File Name: \nPlease enter a correct file.");
						System.exit(1);
					}

					//scans the file and creates a new generic for level 1 cache
					scan = new Scanner(file);
					cacheLevelOne = new Cache<String>(Integer.parseInt(args[1]));

					//Prints opening statement with the cache size on the console
					System.out.println("First level cache with " + cacheLevelOne.cacheSize() + " entries has been created.");
					System.out.println("Loading, Please Wait..................................");
					
					//While loop to scan each word within the text
					while (scan.hasNextLine()) {

						fileInput = scan.nextLine();
						
						//Token used to store each word
						StringTokenizer stringToken = new StringTokenizer(fileInput);

						//Nested While loop used to store and update hits and refs. Adds them to inputText String token
						while (stringToken.hasMoreTokens()) {
							
							String inputText = stringToken.nextToken();

							//can use equals instead of contains as well
							if (cacheLevelOne.contains(inputText)) {
								hitLevelOne++;
								refsLevelOne++;
								cacheLevelOne.add(inputText);

							}
							
							//else updates miss and referances
							else {
								refsLevelOne++;
								missLevelOne++;
								cacheLevelOne.add(inputText);

							}
						}
					}

					//The calculation for the rations and hits on Level 1 and prints
					globalRatio = (((double) (hitLevelOne) / refsLevelOne));
					hitsTotal = hitLevelOne;
					refsTotal = refsLevelOne;

					System.out.println("\nThe number of global references:   " + refsTotal);
					System.out.println("The number of global cache hits:   " + hitsTotal);
					System.out.println("The global hit ratio           :   " + globalRatio);

				}

				//if the file is not valid or misspelled, execute this catch
				catch (FileNotFoundException e) {
					System.out.println("File could not be found. Please provide a valid file or check for spelling.");
					System.exit(1);
				} 
				//Execute if an unknown error occurs
				catch (Exception e) {
					System.out.println("An unknown error occured. Please input usage again in the following format:");
					System.out.println("Correct Usage: ");
					System.out.println('\t' + "For Level One Cache:");
					System.out.println('\t' + "java Test 1 <cache size> <input textfile name>");
					System.out.println("\n" + '\t' + "For Level Two cache:");
					System.out.println('\t' + "java Test 2 <1st-level cache size> <2nd-level cache size> <input textfile name>");
					System.exit(1);

				}
			}
		}

		//Checks if the argument length is correct for Level 2 cache
		if (args[0].equals("2")) {
			if (args.length < 4 || args.length > 4) {
				System.out.println("Error - Incorrect number of arguments for Level Two Cache.");
				System.out.println("Usage: ");
				System.out.println("\njava Test 2 <1st-level cache size> <2nd-level cache size> <input textfile name>");
				System.exit(1);

			}

			//Compares the Cache sizes requested to make sure its in correct format
			if (Integer.parseInt(args[2]) <= Integer.parseInt(args[1])) {
				System.out.println("Error - Level Two cache must be smaller than Level One cache.");
				System.exit(1);

			}

			//Checks if the filename exists misspelled
			try {
				File file = new File(args[3]);
				if (!file.isFile()) {
					System.out.println("Incorrect filename. Please enter a valid file or check for spelling.");
					System.exit(1); 
				}

				//creates a new scanner
				scan = new Scanner(file);

				//creates two new strings generics for both levels
				cacheLevelOne = new Cache<String>(Integer.parseInt(args[1]));
				cacheLevelTwo = new Cache<String>(Integer.parseInt(args[2]));

				System.out.println("First level cache with " + cacheLevelOne.cacheSize() + " entries has been created.");
				System.out.println("Second level cache with " + cacheLevelTwo.cacheSize() + " entries has been created.");
				System.out.println("Loading, Please Wait..................................");
				

				//While loop that will tokenize all strings and input them into each level accordingly
				while (scan.hasNextLine()) {
					
					fileInput = scan.nextLine();
					StringTokenizer stringToken = new StringTokenizer(fileInput);

					while (stringToken.hasMoreTokens()) {

						String inputText = stringToken.nextToken();

						//checks and updates for level 1 cache
						if (cacheLevelOne.contains(inputText)) {
							refsLevelOne++;
							hitLevelOne++;
							cacheLevelOne.add(inputText);
							cacheLevelTwo.add(inputText);

						} 
						//if missed, update missed value
						else {
							refsLevelOne++;
							refsLevelTwo++;
							missLevelOne++;
							
							//checks for level two and updates values
							if (cacheLevelTwo.contains(inputText)) {
								hitLevelTwo++;
								cacheLevelTwo.add(inputText);
								cacheLevelOne.add(inputText);
							}
							//updates level two miss if found
							else {
								missLevelTwo++;
								cacheLevelOne.add(inputText);
								cacheLevelTwo.add(inputText);
							}

						}
					}
				}

				//calculations for ratios and refs for both levels then prints out contents
				hitsTotal = (hitLevelOne + hitLevelTwo);
				refsTotal = refsLevelOne;
				globalRatio = (((double) (hitLevelOne + hitLevelTwo) / refsLevelOne));
				ratioLevelOne = ((double) ((double) hitLevelOne / (double) refsLevelOne));
				ratioLevelTwo = ((double) ((double) hitLevelTwo / (double) refsLevelTwo));

				System.out.println("\nThe number of global references:   " + refsTotal);
				System.out.println("The number of global cache hits:   " + hitsTotal);
				System.out.println("The global hit ratio           :   " + globalRatio + "\n");

				System.out.println("The number of First-level references:   " + refsLevelOne);
				System.out.println("The number of First-level cache hits:   " + hitLevelOne);
				System.out.println("First-level Cache hit ratio         :   " + ratioLevelOne + "\n");

				System.out.println();

				System.out.println("The number of Second-level cache references:   " + refsLevelTwo);
				System.out.println("The number of Second-level cache hits      :   " + hitLevelTwo);
				System.out.println("Second-level Cache hit ratio               :   " + ratioLevelTwo + "\n");

			} 
			//Catch if file is misspelled or incorrect
			catch (FileNotFoundException e) {
				
				System.out.println("File not Found. Please input a valid file or check for spelling.");
				
			} 
			//Catch if unknown error occurs
			catch (Exception e) {
				System.out.println("An unknown error occured. Please input usage again in the following format:");
				System.out.println("Correct Usage: ");
				System.out.println('\t' + "For Level One Cache:");
				System.out.println('\t' + "java Test 1 <cache size> <input textfile name>");
				System.out.println("\n" + '\t' + "For Level Two cache:");
				System.out.println('\t' + "java Test 2 <1st-level cache size> <2nd-level cache size> <input textfile name>");
				System.exit(1);

			}
		}
	}

}
