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

    private <T> Optional<T> tx(final Function<Session, T> command) {
        final Session session = sf.openSession();
        final Transaction tx = session.beginTransaction();
        try {
            T rsl = command.apply(session);
            tx.commit();
            return Optional.of(rsl);
        } catch (final Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
            return Optional.empty();
        } finally {
            session.close();
        }
    }

    public Optional<User> add(User user) {
        return this.tx(
                session -> {
                    session.save(user);
                    return user;
                }
        );
    }

    public Optional<User> findUserByEmailAndPassword(String email, String password) {
        return this.tx(
                session -> {
                    var users = session.createQuery(
                                    "from ru.job4j.todo.model.User where email = :email"
                            )
                            .setParameter("email", email)
                            .list();
                    if (!users.isEmpty()) {
                        User user = (User) users.get(0);
                        if (user.getPassword().equals(password)) {
                            return user;
                        }
                    }
                    return null;
                }
        );
    }
}