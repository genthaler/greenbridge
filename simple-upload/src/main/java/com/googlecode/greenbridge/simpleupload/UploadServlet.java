/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.googlecode.greenbridge.simpleupload;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 *
 * @author ryan
 */
public class UploadServlet extends HttpServlet {


    public static final String UPLOAD_ROOT_DIR = "UPLOAD_ROOT_DIR";
    public static final String STREAM_URL = "STREAM_URL";
    public static final String STREAM_URL_PROTOCOL = "rtmp://";
    public static final String STREAM_URL_PORT = "1935";
    public static final String STREAM_URL_PATH = "/oflaDemo";
    private File red5StreamDir;
    private String stream_url;
    private DiskFileItemFactory factory;
   
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        try {

            String id = findID(request);
            

            if (isValidID(id)) {
                response.setContentType("text/html;charset=UTF-8");
                /* TODO output your page here **/
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Servlet UploadServlet</title>");
                out.println("</head>");
                out.println("<body>");

                out.println("<h1>You want " + id + "</h1>");
                out.println("</body>");
                out.println("</html>");
            }
        } catch(Exception e) {
        }
        finally {
            out.close();
        }
    } 

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {

        
        // Create a new file upload handler
        ServletFileUpload upload = new ServletFileUpload(factory);
        PrintWriter out = response.getWriter();
        try {
            String id = findID(request);
            if (id == null) id = UUID.randomUUID().toString();
            if (isValidID(id)) {
                // Parse the request
                List<FileItem> items = upload.parseRequest(request);
                for (FileItem item : items) {
                    String suffix = item.getName().substring(item.getName().lastIndexOf("."), item.getName().length());
                    File outputfile = getFileForID(id, suffix);
                    if (!outputfile.exists()) {
                        item.write(outputfile);
                    }
                    response.setContentType("application/json");

                    /* TODO output your page here **/
                    out.print("{");
                    out.print("\"url\":\"" + findStreamUrl(request) + "\",");
                    out.print("\"media\":\"" + getURLforID(id, suffix) + "\"");
                    out.println("}");
                }


            }
        } catch(Exception e) {
        }
        finally {
            out.close();
        }
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    protected String findID(HttpServletRequest request) {
        String url = request.getRequestURI();
        return url.substring(url.lastIndexOf("/") + 1, url.length());

    }


    protected boolean isValidID(String id) {
        return id.matches("[0-9a-zA-Z_-]+");
    }


    protected File getFileForID(String uuid, String suffix) {
        String prefix = uuid.substring(0, 2);        
        File destFolder = new File(red5StreamDir, prefix);
        destFolder.mkdirs();
        return new File(destFolder, uuid +  suffix);
    }

    protected String getURLforID(String uuid, String suffix) {
        String prefix = uuid.substring(0, 2);
        return prefix + "/" + uuid +  suffix;
    }


    @Override
    public void init() throws ServletException {
        red5StreamDir = findRed5StreamsDir();
        factory = new DiskFileItemFactory();
        System.out.println("--------------------------------------");
        System.out.println("Red5 DIR: " + red5StreamDir.getAbsolutePath());
    }



    protected String findStreamUrl(HttpServletRequest request) {
        String strm_url = getServletConfig().getInitParameter(STREAM_URL);
        if (strm_url != null && !"".equals(strm_url)) return strm_url;
        return STREAM_URL_PROTOCOL + request.getServerName() + ":" + STREAM_URL_PORT + STREAM_URL_PATH;

    }


    protected File findRed5StreamsDir() {
        String upload_dir = getServletConfig().getInitParameter(UPLOAD_ROOT_DIR);
        if (upload_dir != null && !"".equals(upload_dir)) {
            return new File(upload_dir);
        }
        File webapp_root = findFirstDirectory("./work","./webapps", "./target");
        if (webapp_root == null) return new File(".");
        File red5_root =  findRed5UnpackedWebapp(webapp_root);
        if (red5_root == null) return webapp_root;
        File streams =  new File(red5_root, "webapp/streams");
        if (streams != null) return streams;
        streams = new File(".");
        return streams;
    }

    protected File findRed5UnpackedWebapp(File webapp_root) {
        File[] children = webapp_root.listFiles();
        for (int i = 0; i < children.length; i++) {
            File child = children[i];
            if (child.isDirectory() && child.getName().contains("red5")) {
                return child;
            }

        }
        return null;
    }


    protected File findFirstDirectory(String ... list) {
        for (String item : list) {
            File f = new File(item);
            if (f.exists() && f.isDirectory()) return f;
        }
        return null;
    }






}
