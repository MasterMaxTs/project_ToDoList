package ru.job4j.todo.persistence;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.entity.User;

import java.util.List;
import java.util.Optional;

@Repository
public class UserStoreImpl implements UserStore {

    private final SessionFactory sf;

    public UserStoreImpl(SessionFactory sf) {
        this.sf = sf;
    }

    @Override
    public User add(User user) {
        Session session = sf.openSession();
        session.beginTransaction();
        session.save(user);
        session.getTransaction().commit();
        session.close();
        return user;
    }

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
    public List<User> findAll() {
        Session session = sf.openSession();
        session.beginTransaction();
        List<User> rsl = session.createQuery(
                "from User", User.class
        ).list();
        session.getTransaction().commit();
        session.close();
        return rsl;
    }

    @Override
    public boolean update(User user) {
        Session session = sf.openSession();
        session.beginTransaction();
        boolean rsl = session.createQuery(
                        "update User set name = :fName, login = :fLogin"
                                + ", password = :fPwd where id = :fId "
                ).setParameter("fName", user.getName())
                .setParameter("fLogin", user.getLogin())
                .setParameter("fPwd", user.getPassword())
                .setParameter("fId", user.getId())
                .executeUpdate() > 0;
        session.getTransaction().commit();
        session.close();
        return rsl;
    }

    @Override
    public boolean delete(int id) {
        Session session = sf.openSession();
        session.beginTransaction();
        boolean rsl = session.createQuery(
                        "delete from User where id = :fId", User.class
                ).setParameter("fId", id)
                .executeUpdate() > 0;
        session.getTransaction().commit();
        session.close();
        return rsl;
    }

    @Override
    public User findById(int id) {
        Session session = sf.openSession();
        session.beginTransaction();
        Query<User> query = session.createQuery(
                "from User where id = :fId", User.class
        ).setParameter("fId", id);
        User user = query.uniqueResult();
        session.getTransaction().commit();
        session.close();
        return user;
    }

    @Override
    public void close() {
        sf.close();
    }
}
