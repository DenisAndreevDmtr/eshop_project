package by.teachmeskills.eshop.repositories.impl;

import by.teachmeskills.eshop.entities.User;
import by.teachmeskills.eshop.repositories.UserRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Repository
public class UserRepositoryImpl implements UserRepository {
    private final JdbcTemplate jdbcTemplate;
    private static final String GET_USER_BY_ID = "SELECT * FROM user WHERE id=?";
    private static final String GET_USER_BY_LOGIN_AND_PASSWORD = "SELECT * FROM user WHERE login=? AND password=?";
    private static final String GET_USER_BY_LOGIN = "SELECT * FROM user WHERE login=?";
    private static final String INSERT_NEW_USER = "INSERT INTO user (name, surname, email, password, login, birth_date, balance) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String GET_ALL_USERS = "SELECT * FROM user";
    private static final String UPDATE_USER = "UPDATE user SET name=?, surname=?, email=?, password=?, login=?, birth_date=?, balance=? WHERE login=?";
    private static final String DELETE_USER = "DELETE FROM user WHERE id=?";

    public UserRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public User getUserById(int id) {
        return jdbcTemplate.queryForObject(GET_USER_BY_ID, (RowMapper<User>) (rs, rowNum) -> User.builder()
                .id(rs.getInt("id"))
                .login(rs.getString("login"))
                .password(rs.getString("password"))
                .name(rs.getString("name"))
                .surname(rs.getString("surname"))
                .dateBorn(rs.getDate("birth_date").toLocalDate())
                .eMail(rs.getString("email"))
                .balance(rs.getBigDecimal("balance"))
                .build(), id);
    }

    @Override
    public Optional<User> getUserByLoginAndPassword(String login, String password) {
        User user = jdbcTemplate.queryForObject(GET_USER_BY_LOGIN_AND_PASSWORD, (RowMapper<User>) (rs, rowNum) -> User.builder()
                .id(rs.getInt("id"))
                .login(rs.getString("login"))
                .password(rs.getString("password"))
                .name(rs.getString("name"))
                .surname(rs.getString("surname"))
                .dateBorn(rs.getDate("birth_date").toLocalDate())
                .eMail(rs.getString("email"))
                .balance(rs.getBigDecimal("balance"))
                .build(), login, password);
        return Optional.ofNullable(user);
    }

    @Override
    public Optional<User> getUserByLogin(String login) {
        User user = null;
        try {
            user = jdbcTemplate.queryForObject(GET_USER_BY_LOGIN, (RowMapper<User>) (rs, rowNum) -> User.builder()
                    .id(rs.getInt("id"))
                    .login(rs.getString("login"))
                    .password(rs.getString("password"))
                    .name(rs.getString("name"))
                    .surname(rs.getString("surname"))
                    .dateBorn(rs.getDate("birth_date").toLocalDate())
                    .eMail(rs.getString("email"))
                    .balance(rs.getBigDecimal("balance"))
                    .build(), login);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.ofNullable(user);
    }

    @Override
    public User create(User entity) {
        jdbcTemplate.update(INSERT_NEW_USER, entity.getName(), entity.getSurname(), entity.getEMail(), entity.getPassword(),
                entity.getLogin(), Date.valueOf(entity.getDateBorn()), entity.getBalance());
        return getUserByLogin(entity.getLogin()).get();
    }

    //method should be updated
    @Override
    public List<User> read() {
        return jdbcTemplate.query(GET_ALL_USERS, (rs, rowNum) -> User.builder()
                .id(rs.getInt("id"))
                .login(rs.getString("login"))
                .password(rs.getString("password"))
                .name(rs.getString("name"))
                .surname(rs.getString("surname"))
                .dateBorn(rs.getDate("birth_date").toLocalDate())
                .eMail(rs.getString("email"))
                .balance(rs.getBigDecimal("balance"))
                .build());
    }

    //method should be updated
    @Override
    public User update(User entity) {
        jdbcTemplate.update(UPDATE_USER, entity.getName(), entity.getSurname(), entity.getEMail(), entity.getPassword(),
                entity.getLogin(), Date.valueOf(entity.getDateBorn()), entity.getBalance(), entity.getLogin());
        return getUserByLogin(entity.getLogin()).get();
    }

    //method should be updated
    @Override
    public void delete(int id) {
        jdbcTemplate.update(DELETE_USER, id);
    }
}