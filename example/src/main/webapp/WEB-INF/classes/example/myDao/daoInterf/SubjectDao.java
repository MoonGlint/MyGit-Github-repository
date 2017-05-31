package example.myDao.daoInterf;

import example.myDao.daoObj.Subject;
import example.DaoException;
import java.util.List;

/** Получить все предметы
  */
public interface SubjectDao extends BaseDao<Subject> {
    public List<Subject> readAllSubject();
}
