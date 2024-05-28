package repositories;

import org.bobbysStore.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SuppressWarnings("all")
public class UserRepository {

    public static Connection connect() {
        String url = "jdbc:postgresql://localhost:5432/postgres";
        String username="postgres";
        String password="Bobbyjay1@2&3";
        try {
            return DriverManager.getConnection(url,username,password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public User saveUser(User user) {
        String sql = "insert into users (id,wallet_id)values (?, ?)";
        try(Connection connection = connect()){
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            long currentId = generateId();
            preparedStatement.setLong(1,currentId+1);
            preparedStatement.setObject(2,user.getWalletId());
            preparedStatement.execute();
            return getUserBy(currentId+1);
        }catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException("Failed to connect to database");
        }

    }
    public User getUserBy(Long id){
        String sql = "select * from users where id=?";

        try(Connection conn = connect()){
          var preparedStatement = conn.prepareStatement(sql);

          preparedStatement.setLong(1,id);
          var resultSet = preparedStatement.executeQuery();
          resultSet.next();
          long userId = resultSet.getLong(1);
          long walletId = resultSet.getLong(2);
          User user = new User();
          user.setId(userId);
          user.setWalletId(walletId);
          return user;
        }catch(SQLException e){
            System.err.println(e.getMessage());
        }
        return null;
    }
    public User updateUser(Long userId, Long walletId){
        try(Connection connection = connect()){
            String sql = "UPDATE users SET wallet_id = ? WHERE id = ? ";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1,walletId);
            preparedStatement.setLong(2,userId);
            preparedStatement.executeUpdate();
            return getUserBy(userId);

        }catch(SQLException exception){
            throw new RuntimeException(exception.getMessage());
        }
    }


    public void deleteUser(Long userId) {
        try(Connection connection = connect()){
            String sql = "DELETE FROM users WHERE id =?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1,userId);
            preparedStatement.execute();
        }catch(SQLException exception){
            throw new RuntimeException(exception.getMessage());
        }
    }

    public long count() {
        String getIdSqlStatement = "select  count(*) from users";
        String sql = "insert into users (id,wallet_id)values (?, ?)";
        try(Connection connection = connect()) {
            PreparedStatement preparedStatement = connection.prepareStatement(getIdSqlStatement);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return resultSet.getLong(1);
        }catch (SQLException exception){
            throw new RuntimeException(exception.getMessage());
        }
    }
    private long generateId(){
        try(Connection conn = connect()){
            String sql = "SELECT max(id) FROM users";
            var statement = conn.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            Long lastIdGenerated = resultSet.getLong(1);
            return lastIdGenerated + 1;
        }catch (SQLException exception){
            throw new RuntimeException(exception.getMessage());
        }
    }

    public Optional<User> findUserBy(Long userId) {
        User user = getUserBy(userId);
        if(user == null) throw new RuntimeException("User not found");
        return Optional.of(user);
    }
    public List<User> findAll(){
        try(Connection connection = connect()){
            String sql = "SELECT * FROM users";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            return extractUsersFrom(resultSet);
        }catch (SQLException exception){
            return null;
        }
    }
    private List<User> extractUsersFrom(ResultSet resultSet)throws  SQLException{
        List<User> users = new ArrayList<>();
        while(resultSet.next()){
            User user = new User();
            user.setId(resultSet.getLong("id"));
            user.setWalletId(resultSet.getLong("wallet_id"));
            users.add(user);
        }
        return users;
    }
}
