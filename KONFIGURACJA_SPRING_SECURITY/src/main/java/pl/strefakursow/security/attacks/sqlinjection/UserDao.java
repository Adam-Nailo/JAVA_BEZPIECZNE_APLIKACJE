package pl.strefakursow.security.attacks.sqlinjection;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.Collection;

@Component
public class UserDao {

    private JdbcTemplate jdbcTemplate;
    private TransactionTemplate transactionTemplate;

    public UserDao(JdbcTemplate jdbcTemplate, TransactionTemplate transactionTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.transactionTemplate = transactionTemplate;
    }

    public Collection<User> unsafeFindByName(String name) {
        return jdbcTemplate.query("SELECT * FROM user_sql_injection "+"WHERE "+"username ='" + name + "'"
                , (resultSet, i) -> new User(resultSet.getString("username")));
    }

    public void save(User... users) {
        transactionTemplate.execute(status ->{
        for (User user: users){
            jdbcTemplate.update("INSERT INTO user_sql_injection (username) VALUES (?)",
                    new Object[]{user.getUsername()});
        }
        return null;
        });
    }

    public void deleteAll() {
        jdbcTemplate.update("DELETE FROM user_sql_injection");
    }
    public Collection<User> safeFindByName(String name){
        return jdbcTemplate.query("SELECT * FROM user_sql_injection WHERE "+"username =?", new Object[]{name}
                , (resultSet, i) -> new User(resultSet.getString("username = ?")));
    }
}
