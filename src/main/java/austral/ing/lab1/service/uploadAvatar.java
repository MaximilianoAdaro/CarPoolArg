package austral.ing.lab1.service;

import austral.ing.lab1.model.User;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.List;



public class uploadAvatar extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response, User user) throws ServletException, IOException {

        try {
            ServletFileUpload sf = new ServletFileUpload(new DiskFileItemFactory());
            List<FileItem> multifiles = sf.parseRequest(request);
            for (FileItem item : multifiles){
                item.write(new File("/Users/Numa Leone/Documents/Lab1/Avatar/" + user.getFirstName()));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
