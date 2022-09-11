package ru.job4j.todo.persistence.userstore;

import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.entity.User;

import java.util.Optional;

@Repository
@AllArgsConstructor
public class UserDbStore implements UserStore {

    private final SessionFactory sf;

    @Override
    public User add(User user) {
        Session session = sf.openSession();
        session.beginTransaction();
        session.save(user);
        session.getTransaction().commit();
        session.close();
        return user;
    }

    @Override
    public boolean findUserByLogin(String login) {
        Session session = sf.openSession();
        session.beginTransaction();
        Query<User> query = session.createQuery(
                "from User where login = :fLogin", User.class
        ).setParameter("fLogin", login);
        Optional<User> userInDb = query.uniqueResultOptional();
        session.getTransaction().commit();
        session.close();
        return userInDb.isPresent();
    }

    @Override
    public Optional<User> findUserByLoginAndPwd(String login, String password) {
        Session session = sf.openSession();
        session.beginTransaction();
        Query<User> query = session.createQuery(
                        "from User where login = :fLogin AND password = :fPwd", User.class
                ).setParameter("fLogin", login)
                .setParameter("fPwd", password);
        Optional<User> userInDb = query.uniqueResultOptional();
        session.getTransaction().commit();
        session.close();
        return userInDb;
    }

    @Override
    public User update(User user) {
        Session session = sf.openSession();
        session.beginTransaction();
        session.createQuery(
                        "update User set name = :fName, login = :fLogin"
                                + ", password = :fPwd where id = :fId "
                ).setParameter("fName", user.getName())
                .setParameter("fLogin", user.getLogin())
                .setParameter("fPwd", user.getPassword())
                .setParameter("fId", user.getId())
                .executeUpdate();
        session.getTransaction().commit();
        session.close();
        return user;
    }

    @Override
    public void close() {
        sf.close();
    }
}