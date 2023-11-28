package musicPlayer;
import java.util.Scanner;
import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.StringTokenizer;
public class Main {

	public static void main(String[] args) {
		// Command line scanner and file scanner
		ArrayList<Song> trackList = new ArrayList<>();
		Scanner input = new Scanner(System.in);
		File songFile = null;
		Scanner sc = null;
		FileWriter output = null;
		
		try {
			String filePath = System.getProperty("user.dir") + "\\bin\\MusicPlayer\\songs.txt";
		    songFile = new File(filePath);
		    if (songFile.createNewFile()) {
		    	System.out.println("Empty file created.");
		    }
		    sc = new Scanner(songFile);
		} 
		catch (IOException e) {
		      System.out.println("Error occurred.");
		      e.printStackTrace();
		}
		
		
		
		
		while (sc.hasNextLine()) {
			// read line and split to tokens
			String line = sc.nextLine();
			StringTokenizer lineToken = new StringTokenizer(line, ",");
			int tokenCount = 0;
			String title = null, artist = null;
			Genres genre = null;
			//Parse each line's tokens in Song attributes
			while(lineToken.hasMoreTokens()) {
				String token = lineToken.nextToken();
				if(tokenCount == 0) {
					title = token;
				}
				else if(tokenCount == 1) {
					artist = token;
				}
				else{
					int index = Integer.parseInt(token);
					genre = Genres.values()[index];
				}
				tokenCount++;
			}
			Song newSong = new Song(title,artist,genre);
			trackList.add(newSong);
		}
		
		//Close scanner
		sc.close();
		parseInput(trackList, input);
		
		
		//Open FileWriter
		try {
			output = new FileWriter(songFile);
			for(Song song : trackList) {
				String newLine = String.format("%s,%s,%s\n", song.title, song.artist, song.genre.ordinal());
				output.write(newLine);
			}
			//close output writer
			output.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		input.close();
	}
	
	
	public static void parseInput(ArrayList<Song> trackList, Scanner input){
		int userInput = 0;
		while(userInput != 5) {
			printMenu();
			System.out.println("[SELECT AN OPTION:]");
			userInput = input.nextInt();
			switch(userInput) {
				case 0: 
					displaySongs(trackList);
					break;
				case 1:
					chooseSong(trackList, input);
					break;
				case 2:
					playGenre(trackList, input);
					break;
				case 3:
					addSong(trackList, input);
					break;
				case 4:
					deleteSong(trackList, input);
					break;
				case 5:
					System.out.println("[ EXITING. ]");
					break;
				default:
					continue;
			}
		}
	}
	public static void printMenu() {
		System.out.println("[0] List All Songs");
		System.out.println("[1] Play A Song");
		System.out.println("[2] Play A Genre");
		System.out.println("[3] Add A Song");
		System.out.println("[4] Delete A Song");
		System.out.println("[5] Exit");
	}
	
	public static void chooseSong(ArrayList<Song> trackList,Scanner input) {
		int i = 0;
		for(Song song: trackList) {
			System.out.printf("\t<%d> %s - %s\n",i,song.title,song.artist);
			i++;
		}
		System.out.println("[SELECT SONG:]");
		int userInput = input.nextInt();
		if( userInput >= 0 && userInput <= i-1) {
			trackList.get(userInput).printself();
		}
		else {
			System.out.println("[ERROR: INVALID INDEX]");
		}
	}
	
	public static void playGenre(ArrayList<Song> trackList, Scanner input) {
		int i = 0;
		for(Genres genre: Genres.values()) {
			System.out.printf("\t<%d> - %s\n", i, genre);
			i++;
		}
		System.out.println("[SELECT GENRE:]");
		int userInput = input.nextInt();
		if( userInput >= 0 && userInput <= i-1) {
			System.out.println(Genres.values()[userInput]+" SELECTED");
			for(Song song: trackList) {
				if(song.genre == Genres.values()[userInput]) {
					song.printself();
				}
			}
		}
		else {
			System.out.println("[ERROR: INVALID INDEX]");
		}
		
	}
	public static void displaySongs(ArrayList<Song> arr) {
		int i = 0;
		for(Song song: arr) {
			System.out.printf("\t<%d> %s - %s\n",i,song.title,song.artist);
			i++;
		}
	}
	
	public static void addSong(ArrayList<Song> trackList, Scanner input) {
		//function to let user input a new song to end of list
		Song userSong = new Song();
		//display the genres available, have them choose a valid index
		int i = 0;
		for(Genres genre: Genres.values()) {
			System.out.printf("\t<%d> - %s\n", i, genre);
			i++;
		}
		System.out.println("[SELECT GENRE:]");
		int userInput = input.nextInt();
		input.nextLine();
		if( userInput >= 0 && userInput <= i-1) {
			userSong.genre = Genres.values()[userInput];
		}
		else {
			System.out.println("[ERROR: INVALID INDEX]");
		}
		System.out.println("Enter song title");
		String title = input.nextLine();
		userSong.title = title;
		System.out.println("Enter artist name");
		String artist = input.nextLine();
		userSong.artist = artist;
		trackList.add(userSong);
		System.out.printf("%s - %s added.\n", userSong.title, userSong.artist);
	}

	public static void deleteSong(ArrayList<Song> trackList, Scanner input) {
		int i = 0;
		for(Song song: trackList) {
			System.out.printf("\t<%d> %s - %s\n",i,song.title,song.artist);
			i++;
		}
		System.out.println("[SELECT SONG TO DELETE:]");
		int userInput = input.nextInt();
		if( userInput >= 0 && userInput <= i-1) {
			trackList.remove(userInput);
			System.out.println("Song removed.");
		}
		else {
			System.out.println("[ERROR: INVALID INDEX]");
		}	
	}
}
