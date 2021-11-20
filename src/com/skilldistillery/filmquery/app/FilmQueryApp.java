package com.skilldistillery.filmquery.app;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import com.skilldistillery.filmquery.database.DatabaseAccessor;
import com.skilldistillery.filmquery.database.DatabaseAccessorObject;
import com.skilldistillery.filmquery.entities.Film;

public class FilmQueryApp {

	DatabaseAccessor db = new DatabaseAccessorObject();

	public static void main(String[] args) throws SQLException {
		FilmQueryApp app = new FilmQueryApp();
//    app.test();
		app.launch();
	}

	private void test() throws SQLException {
		Film film = db.findFilmById(1);
		System.out.println(film);
	}

	private void launch() {
		Scanner scanner = new Scanner(System.in);
		System.out.println("1. Look up a film by its id: ");
		System.out.println("2. Look up a film by a search keyword: ");
		System.out.println("3. Exit the application.");
		int userInput = scanner.nextInt();
		scanner.nextLine();
		switch (userInput) {
		case 1:
			System.out.println("Enter the id you would like to match to a film: ");
			int matcher = scanner.nextInt();
			try {
				Film film = db.findFilmById(matcher);
				if (film != null) {
					System.out.println(film);
				} else {
					System.out.println("No corresponding film id was found.");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			launch();
			break;
		case 2:
			System.out.println("Enter a keyword you would like to use to search for a film: ");
			String keyword = scanner.nextLine();
			System.out.println(keyword);
			List<Film> films;
			try {
				films = db.findFilmBySearch(keyword);
				if (films != null) {
					System.out.println(films);
				} else {
					System.out.println("No matching films were found within your search parameters.");
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			launch();
			break;

		case 3:
			System.exit(0);
			break;
		}
		startUserInterface(scanner);

		scanner.close();
	}

	private void startUserInterface(Scanner input) {

	}

}
