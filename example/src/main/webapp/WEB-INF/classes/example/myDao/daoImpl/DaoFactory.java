package example.myDao.daoImpl;
import example.DaoException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class DaoFactory  {

    static Connection connection ;
    static String userName = "root";
    static String password = "MoonGlint1982";
    static String url = "jdbc:mysql://localhost:3306/student_db?autoReconnect=true&useSSL=false";
    String driver = "com.mysql.jdbc.Driver";

    public DaoFactory() {
             try {
                Class.forName(driver);
                connection = DriverManager.getConnection(url, userName, password);
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
          }
    public void close() throws DaoException{
            if (connection != null) {
            try {

                connection.close();
            } catch (Exception e) {
                throw  new DaoException(e) ;
            }

        }
    }

    public MarkDaoImpl getMarkDAO(){
        return new MarkDaoImpl();
    }
    public StudentDaoImpl getStudentDao() throws DaoException {
        return new StudentDaoImpl(connection);
    }
    public SubjectDaoImpl getSubjectDao(){
        return new SubjectDaoImpl();
    }
}
