package org.example;

import org.example.model.Director;

import org.example.model.Movie;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class App {
    public static void main(String[] args) {

        Configuration configuration = new Configuration()
                .addAnnotatedClass(Director.class)
                .addAnnotatedClass(Movie.class);

        SessionFactory sessionFactory = configuration.buildSessionFactory();
        Session session = sessionFactory.getCurrentSession();

        try {
            session.beginTransaction();

            Movie movie = session.get(Movie.class,2);
            System.out.println(movie.toString());

            Director director = movie.getDirector();
            System.out.println(director.toString());


            session.beginTransaction().commit();

        } finally {
            sessionFactory.close();
            session.close();


        }
    }
}
