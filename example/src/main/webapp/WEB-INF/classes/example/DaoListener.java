package example;
import example.myDao.daoImpl.DaoFactory;
import example.myDao.daoInterf.StudentDao;
import example.DaoException;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class DaoListener implements HttpSessionListener {

    public void sessionCreated(HttpSessionEvent e) {
        
		try {
			
            e.getSession().setAttribute("studentDaoI",new DaoFactory().getStudentDao());
            e.getSession().setAttribute("cssClass","tdinv");
           
        } catch (DaoException d) {
            d.printStackTrace();
        }
    }

    public void sessionDestroyed(HttpSessionEvent e) {
        StudentDao studentDaoI;
		
        try{
            if ((studentDaoI= (StudentDao) e.getSession().getAttribute("studentDaoI")) != null)
                studentDaoI.close();
       }


       catch (DaoException d) {
            d.printStackTrace();
        }

       
    }
}
