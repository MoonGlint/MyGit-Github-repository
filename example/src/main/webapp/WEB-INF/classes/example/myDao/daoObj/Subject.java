package example.myDao.daoObj;

/**
 * Created by Admin on 20.12.2016.
 */
public class Subject extends DbObject{
    Subject(){
        super();
    }
    private String subject;
    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }


}
