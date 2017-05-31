package example.myDao.daoInterf;


import example.myDao.daoObj.DbObject;
import example.DaoException;



/**Обновить студента, предмет, оценка
 Добавить студента, предмет, оценку
 Удалить студента, предмет, оценку
 */
public interface BaseDao<T extends DbObject> {

        //обновляет обьект
        public void update(T t) throws DaoException;

        //добавляет обьект
        public void add(T t) throws DaoException;

        //удаляет обьект из базы данных
        public void delete(int id) throws DaoException;
      // public void selectAll();

 }
