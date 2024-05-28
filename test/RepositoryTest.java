import org.junit.jupiter.api.Test;
import repositories.UserRepository;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class RepositoryTest {
    @Test
    public void testDatabaseConnection(){
        try(Connection connection = UserRepository.connect()){
            assertNotNull(connection);
            System.out.println("Database connection->" + connection);
        }catch (SQLException e){
            assertNull(e);
            e.printStackTrace();
        }
    }
}
