package austral.ing.lab1.service;

import austral.ing.lab1.entity.Users;
import austral.ing.lab1.model.User;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@WebServlet("/upload")
@MultipartConfig
public class UploadServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Optional<User> optionalUser = Users.findByEmail(req.getUserPrincipal().getName());
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            ServletFileUpload sf = new ServletFileUpload(new DiskFileItemFactory());
            String oldPhoto;
            String maxiPath = "C:\\Users\\maxia\\projects\\lab1\\Carpoolarg\\src\\main\\webapp\\images\\";
            if((oldPhoto = user.getAvatarPath()) != null){
                deletePhoto(maxiPath + oldPhoto);
            }
            try {
                List<FileItem> multiFiles = sf.parseRequest(req);
                FileItem file = multiFiles.get(0);
                file.write(new File(maxiPath + file.getName()));
                user.setAvatarPath(file.getName());
                Users.persist(user);

                req.getSession().setAttribute("hasPath", user.getAvatarPath() != null);
                req.getSession().setAttribute("avatarPath", user.getAvatarPath());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        req.getRequestDispatcher("/secure/profile.jsp").forward(req, resp);
    }

    private void deletePhoto(String oldPhoto) {
        Path fileToDeletePath = Paths.get(oldPhoto);
        try {
            Files.delete(fileToDeletePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
