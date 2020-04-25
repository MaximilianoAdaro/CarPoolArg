package austral.ing.lab1.service;

import austral.ing.lab1.entity.Users;
import austral.ing.lab1.model.User;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.IOException;
import java.util.List;

@WebServlet("/signup.do")
public class Signup extends HttpServlet {
  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    final User user = new User();

    user.setFirstName(req.getParameter("firstname"));
    user.setLastName(req.getParameter("lastname"));
    user.setEmail(req.getParameter("email"));
    user.setPassword(req.getParameter("password"));
    try {
      ServletFileUpload sf = new ServletFileUpload(new DiskFileItemFactory());
      List<FileItem> multifiles = sf.parseRequest(req);

      for(FileItem item : multifiles){
        item.write(new File("/Users/Numa Leone/Documents/Lab1/Avatar" +"hola"));
      }
    } catch (Exception e) {
      System.out.println(e);
    }

    user.setActive(true);

    Users.persist(user);

    final RequestDispatcher view = req.getRequestDispatcher("login.html");

    view.forward(req, resp);
  }
}
