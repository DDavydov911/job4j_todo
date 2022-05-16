package ru.job4j.todo.store;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.User;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Repository
public class UserStore {

    private final SessionFactory sf;

    public UserStore(SessionFactory sf) {
        this.sf = sf;
    }

    private <T> T tx(final Function<Session, T> command) {
        final Session session = sf.openSession();
        final Transaction tx = session.beginTransaction();
        try {
            T rsl = command.apply(session);
            tx.commit();
            return rsl;
        } catch (final Exception e) {
            session.getTransaction().rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    public Optional<User> add(User user) {
        return this.tx(
                session -> {
                    List users = session.createQuery(
                                    "from ru.job4j.todo.model.User where email = :email"
                            )
                            .setParameter("email", user.getEmail())
                            .list();
                    if (users.isEmpty()) {
                        session.save(user);
                        return Optional.of(user);
                    }
                    return Optional.empty();
                }
        );
    }

    /**
     * public Optional<User> add(User user) {
     * return this.tx(
     * session -> {
     * try {
     * session.save(user);
     * return Optional.of(user);
     * } catch (ConstraintViolationException ex) {
     * return Optional.empty();
     * }
     * }
     * );
     * }
     */

    public Optional<User> findUserByEmailAndPassword(String email, String password) {
        return this.tx(
                session -> {
                    List users = session.createQuery(
                                    "from ru.job4j.todo.model.User where email = :email"
                            )
                            .setParameter("email", email)
                            .list();
                    if (!users.isEmpty()) {
                        User user = (User) users.get(0);
                        if (user.getPassword().equals(password)) {
                            return Optional.of(user);
                        }
                    }
                    return Optional.empty();
                }
        );
    }
}