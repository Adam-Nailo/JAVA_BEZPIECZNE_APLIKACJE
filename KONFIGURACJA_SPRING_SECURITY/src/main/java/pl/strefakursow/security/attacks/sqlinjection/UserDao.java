package pl.strefakursow.security.attacks.sqlinjection;

import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Collection;

public class UserDao {

    private JdbcTemplate jdbcTemplate;

    public UserDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Collection<User> unsafeFindByName(String name){
       return jdbcTemplate.query("SELECT * FROM user_sql_injection WHERE username ='"+name+"'"
                , (resultSet, i) -> new User(resultSet.getString("username")));
    }

    public void save(User... users) {
jdbcTemplate.update("INSERT INTO user")
    }
}
