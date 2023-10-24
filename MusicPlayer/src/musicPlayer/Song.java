package musicPlayer;
public class Song {
	String title;
	String artist;
	Genres genre;
	Song(String title, String artist, Genres genre){
		this.title = title;
		this.artist = artist;
		this.genre = genre;
	}
	
	void printself() {
		System.out.printf("[ NOW PLAYING ]: %s - %s",this.title, this.artist);
		for(int i = 0; i < 10; i++) {
			System.out.printf(".");
			try {
				Thread.sleep(400);
			} catch (InterruptedException e) {
				System.out.println("Thread interrupted.");
			}
		}
		System.out.printf("\n");
	}


}
