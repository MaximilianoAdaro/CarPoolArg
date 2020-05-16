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
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@WebServlet("/upload.do")
@MultipartConfig
public class UploadAvatar extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Optional<User> optionalUser = Users.findByEmail(req.getUserPrincipal().getName());
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            Long id = user.getUserId();
            ServletFileUpload sf = new ServletFileUpload(new DiskFileItemFactory());
            String oldPhoto;
            String path = "/project/images/";
            String maxiPath = "C:\\Users\\maxia\\images\\";
            if ((oldPhoto = user.getAvatarPath()) != null && !oldPhoto.equals("/project/images/defaultAvatar.png")) {
                String[] cutPath = oldPhoto.split("/");
                System.out.println(cutPath[cutPath.length - 1]);
                deletePhoto(maxiPath + cutPath[cutPath.length - 1]);
            }
            try {
                List<FileItem> multiFiles = sf.parseRequest(req);
                FileItem file = multiFiles.get(0);

                String[] partExt = file.getName().split("\\.");
                String ext = partExt[partExt.length - 1];
                String fileName = id + "." + ext;

                file.write(new File(maxiPath + fileName));
                user.setAvatarPath(path + fileName);
                Users.persist(user);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        resp.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        req.getRequestDispatcher("/secure/profile.jsp").forward(req, resp);
    }

    private void deletePhoto(String oldPhoto) {
        try {
            Files.delete(Paths.get(oldPhoto));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
