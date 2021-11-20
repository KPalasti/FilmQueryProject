package com.skilldistillery.filmquery.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.skilldistillery.filmquery.entities.Actor;
import com.skilldistillery.filmquery.entities.Film;

public class DatabaseAccessorObject implements DatabaseAccessor {

	private static final String URL = "jdbc:mysql://localhost:3306/sdvid?useSSL=false";

//	public DatabaseAccessorObject() {
//		try {
//			Class.forName("com.mysql.jdbc.Driver");
//		} catch (ClassNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}

//	public static void main(String[] args) throws SQLException, ClassNotFoundException {
//		System.out.println("DataAcessorObject started.");
//		DatabaseAccessorObject tester = new DatabaseAccessorObject();
//		// tester.showStaff();
//		Scanner input = new Scanner(System.in);
//		int storeId = 0;
//		do {
//			System.out.print("Enter a store ID (0 to quit): ");
//			storeId = input.nextInt();
////			tester.findFilmsByActorId(storeId);
//		} while (storeId != 0);
//	}
//
//	public List<Film> findFilmsByActorId(int actorId) throws SQLException {
//		List<Film> films = new ArrayList<>();
//		try {
//			String user = "student";
//			String pass = "student";
//			Connection conn = DriverManager.getConnection(URL, user, pass);
//			String sql = "SELECT id, title, description, release_year, language_id, rental_duration, ";
//			sql += " rental_rate, length, replacement_cost, rating, special_features "
//					+ " FROM film JOIN film_actor ON film.id = film_actor.film_id " + " WHERE actor_id = ?";
//			PreparedStatement stmt = conn.prepareStatement(sql);
//			stmt.setInt(1, actorId);
//			ResultSet rs = stmt.executeQuery();
//			while (rs.next()) {
//				int filmId = rs.getInt(1);
//				String title = rs.getString(2);
//				String desc = rs.getString(3);
//				short releaseYear = rs.getShort(4);
//				int langId = rs.getInt(5);
//				int rentDur = rs.getInt(6);
//				double rate = rs.getDouble(7);
//				int length = rs.getInt(8);
//				double repCost = rs.getDouble(9);
//				String rating = rs.getString(10);
//				String features = rs.getString(11);
//				Film film = new Film(filmId, title, desc, releaseYear, langId, rentDur, rate, length, repCost, rating,
//						features);
//				films.add(film);
//			}
//			rs.close();
//			stmt.close();
//			conn.close();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return films;
//	}

	public Actor findActorById(int actorId) throws SQLException {
		Actor actor = null;
		String user = "student";
		String pass = "student";
		Connection conn = DriverManager.getConnection(URL, user, pass);
		String sql = "SELECT id, first_name, last_name FROM actor WHERE id = ?";
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setInt(1, actorId);
		ResultSet actorResult = stmt.executeQuery();
		if (actorResult.next()) {
			actor = new Actor(); // Create the object
			// Here is our mapping of query columns to our object fields:
			actor.setId(actorResult.getInt("id"));
			actor.setFirstName(actorResult.getString("firstName"));
			actor.setLastName(actorResult.getString("lastName"));
//			actor.setFilms(findFilmsByActorId(actorId)); // An Actor has Films
		}
		actorResult.close();
		stmt.close();
		conn.close();
		return actor;
	}

	public Film findFilmById(int filmId) throws SQLException {
		Film film = null;
		String user = "student";
		String pass = "student";
		Connection conn = DriverManager.getConnection(URL, user, pass);
		String sql = " SELECT title, release_year, rating, description, language.name, film.id FROM film "
				+ "JOIN language ON film.language_id = language.id "
				+ "WHERE film.id = ?";
		PreparedStatement stmt = conn.prepareStatement(sql);
		// join language table for langauge name
		stmt.setInt(1, filmId);
		ResultSet filmResult = stmt.executeQuery();
		if (filmResult.next()) {
			film = new Film(); // Create the object
			// Here is our mapping of query columns to our object fields:
			film.setId(filmResult.getInt("id"));
			film.setTitle(filmResult.getString("title"));
			film.setDescription(filmResult.getString("description"));
			film.setReleaseYear(filmResult.getShort("release_year"));
//			film.setLanguageId(filmResult.getInt("language_id"));
			film.setRating(filmResult.getString("rating"));
			film.setLanguageName(filmResult.getString("name"));
			film.setActors(findActorsByFilmId(filmId));
//			film.setRentalDuration(filmResult.getInt("rental_duration"));
//			film.setRentalRate(filmResult.getDouble("rental_rate"));
//			film.setLength(filmResult.getInt("length"));
//			film.setReplacementCost(filmResult.getDouble("replacement_cost"));
//			film.setSpecialFeatures(filmResult.getString("special_features"));
		}
		filmResult.close();
		stmt.close();
		conn.close();
		return film;
	}

	@Override
	public List<Actor> findActorsByFilmId(int filmId) throws SQLException {
		List<Actor> actors = new ArrayList<>();
		Actor actor = null;
		String user = "student";
		String pass = "student";
		Connection conn = DriverManager.getConnection(URL, user, pass);
		String sql = "SELECT id, first_name, last_name FROM actor JOIN film_actor ON actor.id = film_actor.actor_id WHERE film_id = ?";
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setInt(1, filmId);
		ResultSet actorResult = stmt.executeQuery();
		while (actorResult.next()) {
			actor = new Actor(); // Create the object
			// Here is our mapping of query columns to our object fields:
			actor.setId(actorResult.getInt("id"));
			actor.setFirstName(actorResult.getString("first_name"));
			actor.setLastName(actorResult.getString("last_name"));
			actors.add(actor);
		}
		actorResult.close();
		stmt.close();
		conn.close();
		return actors;
	}

	public List<Film> findFilmBySearch(String keyword) throws SQLException {
		List<Film> films = new ArrayList<>();
		Film film = null;
		keyword = "%" + keyword + "%";
		String user = "student";
		String pass = "student";
		Connection conn = DriverManager.getConnection(URL, user, pass);
		String sql = "SELECT title, release_year, rating, description, film.id, language_id, language.name FROM film "
				+ "JOIN language ON film.language_id = language.id "
				+ "WHERE title LIKE ? OR description LIKE ?";
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setString(1, keyword);
		stmt.setString(2, keyword);
		ResultSet filmResult = stmt.executeQuery();
		while (filmResult.next()) {
			film = new Film(); // Create the object
			// Here is our mapping of query columns to our object fields:
			film.setId(filmResult.getInt("id"));
			film.setTitle(filmResult.getString("title"));
			film.setDescription(filmResult.getString("description"));
			film.setReleaseYear(filmResult.getShort("release_year"));
			film.setLanguageName(filmResult.getString("name"));
//			film.setLanguageId(filmResult.getInt("language_id")); set full language
//			film.setRentalDuration(filmResult.getInt("rental_duration"));
//			film.setRentalRate(filmResult.getDouble("rental_rate"));
//			film.setLength(filmResult.getInt("length"));
//			film.setReplacementCost(filmResult.getDouble("replacement_cost"));
			film.setRating(filmResult.getString("rating"));
//			film.setSpecialFeatures(filmResult.getString("special_features"));
			film.setActors(findActorsByFilmId(filmResult.getInt("id")));
			films.add(film);
		}
		filmResult.close();
		stmt.close();
		conn.close();
		return films;
	}
}
// Reformat to string, use language joins on each method
