package example.myDao.daoInterf;

import example.myDao.daoObj.Mark;
import example.DaoException;
/**
 Получить все предметы одного студента вместе с их оценками
 */
public interface MarkDao extends BaseDao<Mark> {
    public void getAllMarks();
}
