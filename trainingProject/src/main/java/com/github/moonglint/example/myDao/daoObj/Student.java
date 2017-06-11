

/**
 * Created by AЗадание 2: Cделать DAO и DTO объекты для чтени и записи студентов, дисциплин и оценок.
 Какие должны быть DTO:
 Студент
 Предмет
 Оценка

 Какие должны быть действия:
 Прочитать всех студентов
 Получить все предметы одного студента вместе с их оценками
 Получить все предметы

 Обновить студента, предмет, оценка
 Добавить студента, предмет, оценку
 Удалить студента, предмет, оценку
 dmin on 20.12.2016.
 */
package example.myDao.daoObj;

import java.util.Date;

public class Student extends DbObject {
    private String firstName;
    private String lastName;

   /* public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        String input = "03.01.2015";
        DateTimeFormatter formatter = DateTimeFormat.forPattern("dd.MM.yyyy");
        DateTime dt = DateTime.parse(input, formatter);
        DateTime now = new DateTime();
        this.birthDate = birthDate;
    }

    private Date birthDate;*/
   // private enterYear;

    public Student() {
        super();
    }
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {

        return lastName;
    }
     public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
