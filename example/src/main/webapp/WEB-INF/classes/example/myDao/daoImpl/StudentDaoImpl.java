package example.myDao.daoImpl;
        import example.myDao.daoInterf.StudentDao;
        import example.myDao.daoObj.Student;
        import java.sql.*;
        import java.util.List;
        import java.util.ArrayList;
        import example.DaoException;
public class StudentDaoImpl implements StudentDao {
    private static Connection connection;
    PreparedStatement ps;
    PreparedStatement psUpdate;
    PreparedStatement psAdd;
    PreparedStatement psDel;
	PreparedStatement psSelStud;

    public StudentDaoImpl(Connection connection) throws DaoException{
        this.connection = connection;
        final String SQL = "SELECT id,FIRST_NAME,SECOND_NAME FROM student_db.student;";
        final String SQLUPDATE = "Update student_db.student SET FIRST_NAME=?,SECOND_NAME=? WHERE id=?";
        final String SQLADD = "INSERT INTO student_db.student(FIRST_NAME,SECOND_NAME) VALUES(?,?)";
        final String SQLDEL = "DELETE FROM student_db.student WHERE id=?";
		final String SQL_SELStudBYID="Select FIRST_NAME,SECOND_NAME FROM student_db.student WHERE id=?";
        try {
            this.ps = connection.prepareStatement(SQL);
            this.psUpdate = connection.prepareStatement(SQLUPDATE);
            this.psAdd = connection.prepareStatement(SQLADD);
            this.psDel = connection.prepareStatement(SQLDEL);
			this.psSelStud=connection.prepareStatement(SQL_SELStudBYID);
        }
        catch (SQLException e) {
            throw  new DaoException(e) ;
        }

    }

    @Override
    public List<Student> getAllStudents() throws  DaoException {


        List<Student> list = new ArrayList();

        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Student s = new Student();
                s.setId(rs.getInt("id"));

                s.setLastName(rs.getString("SECOND_NAME"));
                s.setFirstName(rs.getString("FIRST_NAME"));
                list.add(s);
            }
            try {
                rs.close();
            } catch (SQLException e) {
                throw  new DaoException(e);
            }
        }
        catch (SQLException e) {
            throw  new DaoException(e) ;
        }

        return list;
    }

    @Override
    public void update(Student student) throws DaoException {
        try {
            psUpdate.setString(1, student.getFirstName());
            psUpdate.setString(2, student.getLastName());
            psUpdate.setInt(3, student.getId());
			psUpdate.executeUpdate();
        }
        catch (SQLException e) {
            throw  new DaoException(e);
        }
           }

    @Override
    public void add(Student student) throws DaoException {
        try {
            psAdd.setString(1,student.getFirstName());
            psAdd.setString(2,student.getLastName());
            psAdd.executeUpdate();
        }
        catch (SQLException e) {
            throw  new DaoException(e);
        }
           }

       @Override
    public void delete(int id) throws DaoException {
        try {

            psDel.setInt(1, id);
			psDel.executeUpdate();
        }
        catch (SQLException e) {
            throw  new DaoException(e);
        }

    }
	 public Student selectByID(int id) throws DaoException {
        Student student=new Student();
        try{ ResultSet rs;
            psSelStud.setInt(1,id);
            rs = psSelStud.executeQuery();
            rs.next();
                    student.setId(id);
                    student.setLastName(rs.getString("SECOND_NAME"));
                    student.setFirstName(rs.getString("FIRST_NAME"));

                try {
                    rs.close();
                } catch (SQLException e) {
                    throw  new DaoException(e);
                }
            }
            catch (SQLException e) {
                throw  new DaoException(e) ;
                
            }
           
         return student;
    }
    public void close()throws DaoException{
        try {
            ps.close();
        }
        catch (SQLException e) {
            throw  new DaoException(e);
        }
         try {
            psUpdate.close();
         }
         catch (SQLException e) {
             throw  new DaoException(e);
         }

             try {
                 psAdd.close();
             }
             catch (SQLException e) {
                 throw  new DaoException(e);
             }
            try{
                 psDel.close();
        }
        catch (SQLException e) {
            throw  new DaoException(e);
        }
		 try {
                 psSelStud.close();
             }
             catch (SQLException e) {
                 throw  new DaoException(e);
             }
		 if (connection != null) {
            try {

                connection.close();
            } catch (Exception e) {
                throw  new DaoException(e) ;
            }

        }	 
			 
			 
    }
}

//удаляет по idIF NOT EXISTS ( SELECT 1 FROM Users WHERE FirstName = 'John' AND LastName = 'Smith' )
//BEGIN
//INSERT INTO Users (FirstName, LastName) VALUES ('John', 'Smith')
//END
