//package com.tianfang.admin.servlet;
//
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.OutputStream;
//import java.io.PrintWriter;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.apache.commons.fileupload.FileItem;
//import org.apache.commons.fileupload.disk.DiskFileItemFactory;
//import org.apache.commons.fileupload.servlet.ServletFileUpload;
//
//import com.google.gson.Gson;
//import com.tianfang.common.ffmpeg.FFmpegUtil;
//import com.tianfang.common.util.PropertiesUtils;
//import com.tianfang.common.util.StringUtils;
//import com.tianfang.common.util.UUIDGenerator;
//
//public class FileServlet extends HttpServlet {
//
//    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        Map map = new HashMap();
//        request.setCharacterEncoding("utf-8");
//        DiskFileItemFactory factory = new DiskFileItemFactory();
//        String path = PropertiesUtils.getProperty("upload.url");
//        factory.setRepository(new File(path));
//        factory.setSizeThreshold(100*1024*1024) ;
//        ServletFileUpload upload = new ServletFileUpload(factory);
//        
//		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
//		String context = "upload" + File.separator + format.format(new Date());
////        String fileName = FileUtils.getUploadFileNameBybase64(baseImg)+"."+prefix;
//        
//        StringBuffer rootPath = new StringBuffer(PropertiesUtils.getProperty("upload.url"));
//        rootPath.append(File.separator).append(context);
//        File file =new File(rootPath.toString());        
//        //如果文件夹不存在则创建    
//        if(!file.exists() && !file.isDirectory()) {
//            file.mkdir();
//        }
//        try {
//            //可以上传多个文件
//            List<FileItem> list = (List<FileItem>)upload.parseRequest(request);
//            for(FileItem item : list){
//                if(!item.isFormField()){
//                    String name = item.getName() ;
//                    String fileSuffix  = name.substring(name.lastIndexOf(".")+1,name.length());
////                    String oldName = name.replaceAll("." + fileSuffix,"");
////                    String fileName = String.valueOf(new Date().getTimezoneOffset());
//                    String newName = UUIDGenerator.getUUID32Bit() + "." + fileSuffix;
//
//                    String filePath = rootPath.append(File.separator).append(newName).toString();
//                    OutputStream out = new FileOutputStream(filePath);
//                    InputStream in = item.getInputStream() ;
//                    int length = 0 ;
//                    byte [] buf = new byte[1024] ;
//                    while( (length = in.read(buf) ) != -1){
//                        out.write(buf, 0, length);
//                    }
//                    in.close();
//                    out.close();
//                    /**将上传处理后的数据返回**/
//                    String fileStaticPath = File.separator+context + File.separator + newName;
//                    map.put("filePath",fileStaticPath);
//                    if(StringUtils.isVideo(fileSuffix)){//视频文件需要截图视频缩略图
//                    	String imageSavePath = File.separator+context + File.separator + UUIDGenerator.getUUID32Bit() + ".jpg";
//                    	map.put("imageSavePath",imageSavePath);
//                    	new FFmpegUtil(PropertiesUtils.getProperty("upload.url")+File.separator+fileStaticPath).makeScreenCut(PropertiesUtils.getProperty("upload.url")+imageSavePath, 318, 290);
//                    }
//                    break;
//                }
//            }
//        }catch (Exception e) {
//            System.out.println("出错了：" + e.getMessage());
//        }
//        response.setContentType("text/xml; charset=UTF-8");
//        response.setHeader("Cache-Control", "no-cache");
//        response.setHeader("Pragma", "no-cache");
//        PrintWriter out = response.getWriter();
//        Gson gson = new Gson();
//        String msg = gson.toJson(map);
////        JSONObject jsonObject = JSONObject.fromObject(map);
////        String msg =  jsonObject.toString();
//        out.print(msg);
//        out.close();
//    }
//
//    public void doGet(HttpServletRequest req, HttpServletResponse resp)
//            throws ServletException, IOException {
//        doPost(req, resp);
//    }
//}