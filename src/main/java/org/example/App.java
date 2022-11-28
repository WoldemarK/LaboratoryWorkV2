package org.example;

import org.example.model.Director;

import org.example.model.Movie;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.ArrayList;
import java.util.Collections;

public class App {
    public static void main(String[] args) {

        Configuration configuration = new Configuration()
                .addAnnotatedClass(Director.class)
                .addAnnotatedClass(Movie.class);

        SessionFactory sessionFactory = configuration.buildSessionFactory();
        Session session = sessionFactory.getCurrentSession();


        try {
            Transaction transaction =session.beginTransaction();
            /**
             * Получите любой фильм, а затем получите его режиссера.
             */
            Movie someMovie = session.get(Movie.class, 2);
            Director someDirector = someMovie.getDirector();
            System.out.println(someDirector);

            System.out.println("____________________________________________");

            /**
             * Добавьте еще один фильм для любого режиссера.
             */
            Director woodyAllen = session.get(Director.class, 4);
            Movie newMovie = new Movie("Magic in the Moonlight", 2014, woodyAllen);

            woodyAllen.getMoves().add(newMovie);
            session.persist(woodyAllen);

            System.out.println("____________________________________________");

            /**
             *Создайте нового режиссера и новый фильм и свяжите эти сущности.
             */
            Director newDirector = new Director("Adam McKay", 66);
            Movie newMovies = new Movie("Big Swiss (TV Series) (producer) (announced)", 2011, newDirector);
            newDirector.setMoves(new ArrayList<>(Collections.singleton(newMovies)));
            session.save(newDirector);
            session.save(newMovie);

            System.out.println("____________________________________________");

            /**
             *Удалите фильм у любого режиссера.
             */
            Director delete = session.get(Director.class, 1);
            Movie list = session.get(Movie.class, 1);
            list.getDirector()
                    .getMoves()
                    .remove(list);

            list.setDirector(delete);
            delete.getMoves().add(newMovie);

            System.out.println("____________________________________________");


            transaction.commit();
            session.close();

        } finally {
            sessionFactory.close();


        }
    }
}
