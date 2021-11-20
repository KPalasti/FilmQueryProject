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
			actor = new Actor();
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
				+ "JOIN language ON film.language_id = language.id " + "WHERE film.id = ?";
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setInt(1, filmId);
		ResultSet filmResult = stmt.executeQuery();
		if (filmResult.next()) {
			film = new Film();
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
			actor = new Actor();
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
				+ "JOIN language ON film.language_id = language.id " + "WHERE title LIKE ? OR description LIKE ?";
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setString(1, keyword);
		stmt.setString(2, keyword);
		ResultSet filmResult = stmt.executeQuery();
		while (filmResult.next()) {
			film = new Film();
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
