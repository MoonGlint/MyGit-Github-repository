
package example;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import example.DaoException;
import example.myDao.daoImpl.DaoFactory;
import example.myDao.daoInterf.StudentDao;
import example.myDao.daoObj.Student;
import javax.servlet.http.HttpSession;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class HelloWorldServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session=request.getSession();
            try {
            String action = request.getParameter("action");

            if (action.equalsIgnoreCase("StudentsList")){
                drawListOfStudents(request,response);
            }
            else{
                if (action.equalsIgnoreCase("Add")) {
                    if (checkName(request.getParameter("firstName"))&&(checkName(request.getParameter("lastName")))){
                        newStudentAppending(request);
                    }
                    else{
                        request.getSession().setAttribute( "cssClass","td");
                    }
                    drawListOfStudents(request,response);
                }
                else {
                    if (action.equalsIgnoreCase("Delete")) {
                        studentRemoving(request);
                        drawListOfStudents(request,response);

                    }
                    else {
                        if (action.equalsIgnoreCase("Update")) {
                            if (checkNameForUpdate(request.getParameter("firstName"))&&(checkNameForUpdate(request.getParameter("lastName")))){
                                studentUpdate(request);
                            }
                            else{
                                request.getSession().setAttribute( "cssClass","td");
                            }

                            drawListOfStudents(request,response);
                        }
                        else {
                            drawListOfStudents(request,response);

                        }
                    }
                }
            }
        }
        catch (IOException|DaoException e) {
            session.setAttribute("exception", e);
        }
      
    }


    protected boolean checkName(String s){
        Pattern p = Pattern.compile("^[A-z]+");
        Matcher m=p.matcher(s);
        return m.matches();
    }
    protected boolean checkNumber(String s){
        Pattern p = Pattern.compile("^[0-9]+");
        Matcher m=p.matcher(s);
        return m.matches();
    }
    protected boolean checkNameForUpdate(String s){
        Pattern p = Pattern.compile("^[A-z ]+|");
        Matcher m=p.matcher(s);
        return m.matches();
    }
    protected boolean checkNumberForUpdate(String s){
        Pattern p = Pattern.compile("^[0-9 ]+|");
        Matcher m=p.matcher(s);
        return m.matches();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }

    private void drawListOfStudents(HttpServletRequest request, HttpServletResponse response)throws DaoException,IOException{
        int n=0;
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        PrintWriter pw = response.getWriter();
        StudentDao studentDaoI=(StudentDao)(request.getSession().getAttribute("studentDaoI"));
        String cssClass= (String) request.getSession().getAttribute("cssClass");
        try{
            ArrayList allStudentFN = (ArrayList) studentDaoI.getAllStudents();
            Iterator iterator=allStudentFN.iterator();
            Object iteratorNext;
            pw.print(
                    "<html>"+
                            "<head>"+
                            "<meta http-equiv=\"Content-Typ\" Content=\"text/html; Charset=utf-8\">"+
                            "<link rel='stylesheet' href=\"css\\style.css\" type='text/css'>"+
                            "</head>"+
                            "<body>"+
                            "<div>Students list</div>"+
                            "<div>"+
                            "<div class=\"th\">"+
                            "<div class=\"tdN\">&Nscr;</div>"+
                            "<div class=\"td\">First Name</div>"+
                            "<div class=\"td\">Last Name</div>"+
                            "<div class=\"td\">Birth Date</div>"+
                            "<div class=\"td\">Enter Year</div>"+
                            "<div class=\"td\">Action</div>"+
                            "</div>"
            );

            while (iterator.hasNext()){
                iteratorNext=iterator.next();
                n++;

                pw.print(
                        "<div class=\"tr\">"+
                                "<form  method=\"post\" action=\"http://localhost:8080/myApp/hello\" class=\"f\">"+
                                "<div class=\"tdNum\">"+n+"</div>"+
                                "<div class=\"td\"><input type=\"text\" placeholder=\" "+
                                ((Student)(iteratorNext)).getFirstName()+"\" name=\"firstName\"> </div>"+
                                "<div class=\"td\"><input type=\"text\" placeholder=\""+
                                ((Student)(iteratorNext)).getLastName()+"\" name=\"lastName\">"+
                                "</div>"+
                                "<div class=\"td\"><input type=\"text\" placeholder=\" Insert Birth Date\" name=\"birthDate\">"+
                                "</div>"+
                                "<div class=\"td\"><input type=\"text\" placeholder=\" Insert Enter Year\" name=\"enterYear\">"+
                                "</div>"+
                                "<div class=\"td\"><input type=\"submit\" name=\"action\" value=\"Update\"><input type=\"submit\" name=\"action\" value=\"Delete\">"+
                                "   <input type=\"hidden\" name=\"studentId\" value=\""+((Student)(iteratorNext)).getId()+"\">"+
                                "</div>"+
                                "<div class=\""+cssClass+"\">Invalid value</div>"+
                                "</form>"+
                                "</div>"
                );
            }
            pw.print(
                    "<div class=\"tfoot\">"+
                            "<form name\"first\" method=\"post\" action=\"http://localhost:8080/myApp/hello\" class=\"f\">"+
                            "<div class=\"tdNum\">&Nscr;</div>"+
                            "<div class=\"td\"><input type=\"text\" placeholder=\" Insert First Name\" name=\"firstName\"></div>"+
                            "<div class=\"td\"><input type=\"text\" placeholder=\" Insert Last Name\" name=\"lastName\"></div>"+
                            "<div class=\"td\"><input type=\"text\" placeholder=\" Insert Birth Date\" name=\"birthDate\"></div>"+
                            "<div class=\"td\"><input type=\"text\" placeholder=\" Insert Enter Year\" name=\"enterYear\"></div>"+
                            "<div class=\"tdfoot\"><input type=\"submit\" name=\"action\" value=\"Add\"></div>"+
                            "</form>"+
                            "</div>"+
                            "</div>");
			pw.print(			
							
							//"<div class=\"exc\""+
							"<div>"+
							
							
                             ((Exception)(request.getSession().getAttribute("exception"))).getMessage()+
                            "</div>"+
                            "</body>"+
                            "</html>"
            );
        }
        catch (DaoException e) {
            //throw new DaoException(e);
			 request.getSession().setAttribute("exception", e);
        }
    }

    private void newStudentAppending(HttpServletRequest request)throws DaoException{
        if (((request.getParameter("firstName")).trim().length()!=0)&&((request.getParameter("lastName")).trim().length()!=0)){

            String firstName = request.getParameter("firstName");
            String lastName = request.getParameter("lastName");
            String birthDate = request.getParameter("birthDate");
            String enterYear = request.getParameter("enterYear");
            Student newStudent = new Student();
            newStudent.setFirstName(firstName);
            newStudent.setLastName(lastName);

            try {
                ((StudentDao)(request.getSession().getAttribute("studentDaoI"))).add(newStudent);
                            }
            catch (DaoException e) {
               // throw new DaoException(e);
			   request.getSession().setAttribute("exception", e);
            }

        }

    }

    private void studentRemoving(HttpServletRequest request)throws DaoException {
        try {
            ((StudentDao)(request.getSession().getAttribute("studentDaoI"))).delete(Integer.parseInt((request.getParameter("studentId"))));
        }
        catch (DaoException e) {
           // throw new DaoException(e);
		   request.getSession().setAttribute("exception", e);
        }
    }
    private void studentUpdate(HttpServletRequest request)throws DaoException{
        Student student;
        StudentDao studentDaoI=(StudentDao)(request.getSession().getAttribute("studentDaoI"));    
        try{
            student=studentDaoI.selectByID(Integer.parseInt(request.getParameter("studentId")));
            if ((request.getParameter("firstName")).trim().length()!=0){
                student.setFirstName(request.getParameter("firstName"));
            }
            else{
                student.setFirstName(student.getFirstName());
            }
            if ((request.getParameter("lastName")).trim().length()!=0){
                student.setLastName(request.getParameter("lastName"));
            }
            else{
                student.setLastName(student.getLastName());
            }
            studentDaoI.update(student);

        }
        catch (DaoException e) {
            //throw new DaoException(e);
			 request.getSession().setAttribute("exception", e);
        }
    }
    
}




/* 

 public class HelloWorldServlet extends HttpServlet { 
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
      HttpSession session=request.getSession();
	  PrintWriter pw=response.getWriter();
        StudentDao studentDaoI;
		String cssClass="tdinv";
             
            studentDaoI=(StudentDao)session.getAttribute("studentDaoI");
        
            try {
                String action = request.getParameter("action");
				
                if (action.equalsIgnoreCase("StudentsList")){
                    drawListOfStudents(studentDaoI,response,cssClass);
                }
                else{
                    if (action.equalsIgnoreCase("Add")) {
						if (checkName(request.getParameter("firstName"))&&(checkName(request.getParameter("lastName")))){  
                        newStudentAppending(studentDaoI,request);
                        
						}
						else{
							cssClass="td";
						}
						drawListOfStudents(studentDaoI,response,cssClass);
                    }
                    else {
                        if (action.equalsIgnoreCase("Delete")) {
                            studentRemoving(studentDaoI,request);
                            drawListOfStudents(studentDaoI,response,cssClass);

                        }
                        else {
                            if (action.equalsIgnoreCase("Update")) {
								if (checkNameForUpdate(request.getParameter("firstName"))&&(checkNameForUpdate(request.getParameter("lastName")))){  
                         studentUpdate(studentDaoI,request);
                        
						}
						else{
							cssClass="td";
						}
							                  
								 drawListOfStudents(studentDaoI,response,cssClass);
                            }
                            else {
                                drawListOfStudents(studentDaoI,response,cssClass);
                            }
                        }
                    }
                }
            }
             catch (IOException|DaoException e){
             pw.print(
						
                    "<html>"+
                            "<head>"+
                            "<meta http-equiv=\"Content-Typ\" Content=\"text/html; Charset=utf-8\">"+
                            "<link rel='stylesheet' href=\"css\\style.css\" type='text/css'>"+
                            "</head>"+
                            "<body>"+
                            "<div class=td>"+
                            e.getMessage()+
                            "</div>"+
                            "</body>"+
                            "</html>");
							
             }
        }
    
	
	protected boolean checkName(String s){
		Pattern p = Pattern.compile("^[A-z]+");  
	     Matcher m=p.matcher(s);
		 return m.matches();
	}
	protected boolean checkNumber(String s){
		Pattern p = Pattern.compile("^[0-9]+");  
	     Matcher m=p.matcher(s);
		 return m.matches();
	}
	protected boolean checkNameForUpdate(String s){
		Pattern p = Pattern.compile("^[A-z ]+|");  
	     Matcher m=p.matcher(s);
		 return m.matches();
	}
	protected boolean checkNumberForUpdate(String s){
		Pattern p = Pattern.compile("^[0-9 ]+|");  
	     Matcher m=p.matcher(s);
		 return m.matches();
	}
	
	
	

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }


    private void drawListOfStudents(StudentDao studentDaoI, HttpServletResponse response,String cssClass)throws DaoException,IOException{
        int n=0;
		response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        PrintWriter pw = response.getWriter();
        try{
            ArrayList allStudentFN = (ArrayList) studentDaoI.getAllStudents();
            Iterator iterator=allStudentFN.iterator();
            Object iteratorNext;
            pw.print(
                    "<html>"+
                            "<head>"+
                            "<meta http-equiv=\"Content-Typ\" Content=\"text/html; Charset=utf-8\">"+
                            "<link rel='stylesheet' href=\"css\\style.css\" type='text/css'>"+
                            "</head>"+
                            "<body>"+
                            "<div>Students list</div>"+
                            "<div>"+
                            "<div class=\"th\">"+
                            "<div class=\"tdN\">&Nscr;</div>"+
                            "<div class=\"td\">First Name</div>"+
                            "<div class=\"td\">Last Name</div>"+
                            "<div class=\"td\">Birth Date</div>"+
                            "<div class=\"td\">Enter Year</div>"+
                            "<div class=\"td\">Action</div>"+
                            "</div>"
            );

            while (iterator.hasNext()){
                iteratorNext=iterator.next();
                n++;

                pw.print(
                        "<div class=\"tr\">"+
                                "<form  method=\"post\" action=\"http://localhost:8080/myApp/hello\" class=\"f\">"+
                                "<div class=\"tdNum\">"+n+"</div>"+
                                "<div class=\"td\"><input type=\"text\" placeholder=\" "+
                                ((Student)(iteratorNext)).getFirstName()+"\" name=\"firstName\"> </div>"+
                                "<div class=\"td\"><input type=\"text\" placeholder=\""+
                                ((Student)(iteratorNext)).getLastName()+"\" name=\"lastName\">"+
                                "</div>"+
                                "<div class=\"td\"><input type=\"text\" placeholder=\" Insert Birth Date\" name=\"birthDate\">"+
                                "</div>"+
                                "<div class=\"td\"><input type=\"text\" placeholder=\" Insert Enter Year\" name=\"enterYear\">"+
                                "</div>"+
                                "<div class=\"td\"><input type=\"submit\" name=\"action\" value=\"Update\"><input type=\"submit\" name=\"action\" value=\"Delete\">"+
                                "   <input type=\"hidden\" name=\"studentId\" value=\""+((Student)(iteratorNext)).getId()+"\">"+
                                "</div>"+
								"<div class=\""+cssClass+"\">Invalid value</div>"+
                                "</form>"+
                                "</div>"
                );
            }
            pw.print(
                    "<div class=\"tfoot\">"+
                            "<form name\"first\" method=\"post\" action=\"http://localhost:8080/myApp/hello\" class=\"f\">"+
                            "<div class=\"tdNum\">&Nscr;</div>"+
                            "<div class=\"td\"><input type=\"text\" placeholder=\" Insert First Name\" name=\"firstName\"></div>"+
                            "<div class=\"td\"><input type=\"text\" placeholder=\" Insert Last Name\" name=\"lastName\"></div>"+
                            "<div class=\"td\"><input type=\"text\" placeholder=\" Insert Birth Date\" name=\"birthDate\"></div>"+
                            "<div class=\"td\"><input type=\"text\" placeholder=\" Insert Enter Year\" name=\"enterYear\"></div>"+
                            "<div class=\"tdfoot\"><input type=\"submit\" name=\"action\" value=\"Add\"></div>"+
							"</form>"+
                            "</div>"+
                            "</div>"+
                            "</body>"+
                            "</html>"
            );
        }
        catch (DaoException e) {
            throw new DaoException(e);
        }
    }
  
    private void newStudentAppending(StudentDao studentDaoI,HttpServletRequest request)throws DaoException{
        if (((request.getParameter("firstName")).trim().length()!=0)&&((request.getParameter("lastName")).trim().length()!=0)){
        		  
			String firstName = request.getParameter("firstName");
            String lastName = request.getParameter("lastName");
            String birthDate = request.getParameter("birthDate");
            String enterYear = request.getParameter("enterYear");
            Student newStudent = new Student();
            newStudent.setFirstName(firstName);
            newStudent.setLastName(lastName);

			 try {            
                studentDaoI.add(newStudent);
				}
            catch (DaoException e) {
                throw new DaoException(e);
            }
       
        }
		
    }

    private void studentRemoving(StudentDao studentDaoI,HttpServletRequest request)throws DaoException {
        try {
            studentDaoI.delete(Integer.parseInt((request.getParameter("studentId"))));
        }
        catch (DaoException e) {
          throw new DaoException(e);
        }
    }
    private void studentUpdate(StudentDao studentDaoI,HttpServletRequest request)throws DaoException{
        Student student;
        try{
            student=studentDaoI.selectByID(Integer.parseInt(request.getParameter("studentId")));
            if ((request.getParameter("firstName")).trim().length()!=0){
                student.setFirstName(request.getParameter("firstName"));
            }
			else{
				student.setFirstName(student.getFirstName());
			}
            if ((request.getParameter("lastName")).trim().length()!=0){
                student.setLastName(request.getParameter("lastName"));
            }
			else{
				student.setLastName(student.getLastName());
			}
            studentDaoI.update(student);

        }
        catch (DaoException e) {
           throw new DaoException(e);
        }
    }
}




 */
























/*

package example;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import example.DaoException;
import example.myDao.daoImpl.DaoFactory;
import example.myDao.daoInterf.StudentDao;
import example.myDao.daoObj.Student;

public class HelloWorldServlet extends HttpServlet {
    DaoFactory daoFactory;
    StudentDao studentDaoI;
   	      {
        try{
        if (studentDaoI != null)
        studentDaoI.close();}

        catch (DaoException e) {
        e.printStackTrace();
        }

        try {
        if (daoFactory != null)
        daoFactory.close();
        }
        catch (DaoException e) {
        e.printStackTrace();
        }
        }


public HelloWorldServlet() throws DaoException {
       // super();
		
        daoFactory = new DaoFactory();
        try {
        studentDaoI = daoFactory.getStudentDao();
        }
		catch (DaoException e) {
        throw new DaoException(e);
        }
		}

		
		protected void doPost(HttpServletRequest request, HttpServletResponse response)  {
       
		//HttpSession session = request.getSession(true);
		//if (session.isNew()){ request.setSession(daoF);}
	   try {
			
			
			
			
			
			String action = request.getParameter("action");
            if (action.equalsIgnoreCase("StudentsList")){
			drawListOfStudents(request,response);
			}
			else{
				if (action.equalsIgnoreCase("Add")) {
             newStudentAppending(request);
             drawListOfStudents(request,response);
				}
             else {
                if (action.equalsIgnoreCase("Delete")) {
                studentRemoving(request);
                drawListOfStudents(request,response);

                }
                else {
                    if (action.equalsIgnoreCase("Update")) {
                      studentUpdate(request);
                      drawListOfStudents(request,response);
                    }
                    else {
                      drawListOfStudents(request,response);
                    }
                }
			 }
		   }
		}
           // catch (IOException e){
               e.printStackTrace();
		  }
    }
  
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	doPost(request,response);
	}
	
	
	private void drawListOfStudents(HttpServletRequest request, HttpServletResponse response)throws IOException{
        int n=0;
		response.setContentType("text/html");
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String text = request.getParameter("text");
        PrintWriter pw = response.getWriter();
                
        try{
        ArrayList allStudentFN = (ArrayList) studentDaoI.getAllStudents();
        Iterator iterator=allStudentFN.iterator();
        Object iteratorNext;

        pw.print(
        "<html>"+
        "<head>"+
        "<meta http-equiv=\"Content-Typ\" Content=\"text/html; Charset=utf-8\">"+
        "<link rel='stylesheet' href=\"css\\style.css\" type='text/css'>"+
        "</head>"+
        "<body>"+
        "<div>Students list</div>"+
        "<div>"+
        "<div class=\"th\">"+
        "<div class=\"tdN\">&Nscr;</div>"+
        "<div class=\"td\">First Name</div>"+
        "<div class=\"td\">Last Name</div>"+
        "<div class=\"td\">Birth Date</div>"+
        "<div class=\"td\">Enter Year</div>"+
        "<div class=\"td\">Action</div>"+
        "</div>"
        );

        while (iterator.hasNext()){
        iteratorNext=iterator.next();
        n++;
		response.setContentType("text/html");
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        		
        pw.print(
		"<div class=\"tr\">"+
        "<form  method=\"post\" action=\"http://localhost:8080/myApp/hello\" class=\"f\">"+
        "<div class=\"tdNum\">"+n+"</div>"+
        "<div class=\"td\"><input type=\"text\" placeholder=\" "+
        ((Student)(iteratorNext)).getFirstName()+"\" name=\"firstName\"> </div>"+
        "<div class=\"td\"><input type=\"text\" placeholder=\""+
        ((Student)(iteratorNext)).getLastName()+"\" name=\"lastName\">"+
        "</div>"+
        "<div class=\"td\"><input type=\"text\" placeholder=\" Insert Birth Date\" name=\"birthDate\">"+
        "</div>"+
        "<div class=\"td\"><input type=\"text\" placeholder=\" Insert Enter Year\" name=\"enterYear\">"+
        "</div>"+
        "<div class=\"td\"><input type=\"submit\" name=\"action\" value=\"Update\"><input type=\"submit\" name=\"action\" value=\"Delete\">"+
        "   <input type=\"hidden\" name=\"studentId\" value=\""+((Student)(iteratorNext)).getId()+"\">"+
        "</div>"+
        "</form>"+
        "</div>"
        );
        }
        pw.print(
        "<div class=\"tfoot\">"+
        "<form name\"first\" method=\"post\" action=\"http://localhost:8080/myApp/hello\" class=\"f\">"+
        "<div class=\"tdNum\">&Nscr;</div>"+
        "<div class=\"td\"><input type=\"text\" placeholder=\" Insert First Name\" name=\"firstName\"></div>"+
        "<div class=\"td\"><input type=\"text\" placeholder=\" Insert Last Name\" name=\"lastName\"></div>"+
        "<div class=\"td\"><input type=\"text\" placeholder=\" Insert Birth Date\" name=\"birthDate\"></div>"+
        "<div class=\"td\"><input type=\"text\" placeholder=\" Insert Enter Year\" name=\"enterYear\"></div>"+
        "<div class=\"tdfoot\"><input type=\"submit\" name=\"action\" value=\"Add\"></div>"+
        "</form>"+
        "</div>"+
        "</div>"+
        "</body>"+
        "</html>"
        );
        }
        catch (DaoException e) {
        e.printStackTrace();
        }
	}
        
//
		private void newStudentAppending(HttpServletRequest request){
			if (((request.getParameter("firstName")).trim().length()!=0)&&((request.getParameter("lastName")).trim().length()!=0)){
			String firstName = request.getParameter("firstName");
			String lastName = request.getParameter("lastName");
			String birthDate = request.getParameter("birthDate");
			String enterYear = request.getParameter("enterYear");
			Student newStudent = new Student();
			newStudent.setFirstName(firstName);
			newStudent.setLastName(lastName);
			

        try {
            studentDaoI.add(newStudent);
        }
		catch (DaoException e) {
            e.printStackTrace();
        }
			}
    }
	
		private void studentRemoving(HttpServletRequest request) {
            	try {	
				studentDaoI.delete(Integer.parseInt((request.getParameter("studentId"))));
				}				
				catch (DaoException e) {
				e.printStackTrace();
				}
       }
	   private void studentUpdate(HttpServletRequest request){
        Student student;
		try{
		    student=studentDaoI.selectByID(Integer.parseInt(request.getParameter("studentId")));
            if ((request.getParameter("firstName")).trim().length()!=0){
			student.setFirstName(request.getParameter("firstName"));
            }
			if ((request.getParameter("lastName")).trim().length()!=0){
				student.setLastName(request.getParameter("lastName"));
            }
			studentDaoI.update(student);
					
		}
         catch (DaoException e) {
           e.printStackTrace();
        }
}
}
	
	
	
	
	
	
/*try {
                PrintWriter pw = null;
                
                    pw = response.getWriter();

					pw.print(
                        "<Html><head>"+
                        "<meta http-equiv=\"Content-Typ\" Content=\"text/html; Charset=utf-8\">"+
                        "</head>"+
                        "<body>"+
                        "<br>"+request.getParameter("studentId")+
                        "<br>"+Integer.parseInt((request.getParameter("studentId")))+
                        "</body>"+
                        "</html>"
					);
                } 
				catch (IOException e) {
                    e.printStackTrace();
                }*/






