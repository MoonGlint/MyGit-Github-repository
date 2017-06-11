package example.myDao.daoInterf;

import example.myDao.daoObj.Student;
import java.util.List;
import example.DaoException;
/**Прочитать всех студентов
 */
public interface StudentDao extends BaseDao<Student> {

  List<Student>  getAllStudents() throws DaoException;
  void update(Student student)throws DaoException;
  void close() throws DaoException;

  Student selectByID(int studentId)throws DaoException;
}

