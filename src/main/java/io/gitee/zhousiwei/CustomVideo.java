package io.gitee.zhousiwei;

import io.netty.util.internal.StringUtil;

import java.sql.*;

/**
 * @author Created by 試毅-思伟 on 2018/12/03
 */
public class CustomVideo {

    private static Connection getConn() {
        String driver = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/test?useSSL=false";
        String username = "root";
        String password = "root";
        Connection conn = null;
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    public static void main(String[] args) {
        one();
        two();
        three();
        System.out.println("----------Finished----------");
    }

    private static void one() {
        Connection conn = getConn();
        String sql = "SELECT\n" +
                "\tf_info_id,\n" +
                "\tSUBSTR(\n" +
                "\t\tREPLACE (f_value, ' ', ''),\n" +
                "\t\tLOCATE(\n" +
                "\t\t\t'vMp4url',\n" +
                "\t\t\tREPLACE (f_value, ' ', '')\n" +
                "\t\t) + 9,\n" +
                "\t\tLOCATE(\n" +
                "\t\t\t'.flv',\n" +
                "\t\t\tREPLACE (f_value, ' ', '')\n" +
                "\t\t) - LOCATE(\n" +
                "\t\t\t'vMp4url=',\n" +
                "\t\t\tREPLACE (f_value, ' ', '')\n" +
                "\t\t) - 5\n" +
                "\t) AS content,\n" +
                "\tREVERSE(substring_index(REVERSE(SUBSTR(\n" +
                "\t\tREPLACE (f_value, ' ', ''),\n" +
                "\t\tLOCATE(\n" +
                "\t\t\t'vMp4url',\n" +
                "\t\t\tREPLACE (f_value, ' ', '')\n" +
                "\t\t) + 9,\n" +
                "\t\tLOCATE(\n" +
                "\t\t\t'.flv',\n" +
                "\t\t\tREPLACE (f_value, ' ', '')\n" +
                "\t\t) - LOCATE(\n" +
                "\t\t\t'vMp4url=',\n" +
                "\t\t\tREPLACE (f_value, ' ', '')\n" +
                "\t\t) - 5\n" +
                "\t)),'/',1)) AS file_name\n" +
                "FROM\n" +
                "\tcms_info_custom\n" +
                "WHERE\n" +
                "\tf_value LIKE '%.flv%'\n" +
                "AND f_value LIKE '<div%';\n";
        PreparedStatement pstmt;
        String insert = "insert into cms_info_custom(f_info_id,f_key,f_value) values(?,?,?)";
        try {
            pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                String content = rs.getString("content");
                if (StringUtil.isNullOrEmpty(content)) {
                    continue;
                }
                int id = rs.getInt("f_info_id");
                String fileName = rs.getString("file_name");

                System.out.println(fileName);
                pstmt = (PreparedStatement) conn.prepareStatement(insert);
                pstmt.setInt(1, id);
                pstmt.setString(2, "videoName");
                pstmt.setString(3, fileName);
                pstmt.executeUpdate();
                pstmt = (PreparedStatement) conn.prepareStatement(insert);
                pstmt.setInt(1, id);
                pstmt.setString(2, "video");
                pstmt.setString(3, content.replaceAll("/_mediafile/www/video/","/uploads/1/video/public/"));
                pstmt.executeUpdate();
                pstmt = (PreparedStatement) conn.prepareStatement(insert);
                pstmt.setInt(1, id);
                pstmt.setString(2, "videoLength");
                pstmt.setString(3, "0");
                pstmt.executeUpdate();
            }
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void two() {
        Connection conn = getConn();
        String sql = "SELECT\n" +
                "\tf_info_id,SUBSTR(\n" +
                "SUBSTR(f_value,LOCATE('vcastr_file=',f_value)+12,LOCATE('.flv',f_value)-LOCATE('vcastr_file=',f_value)-8),\n" +
                "LOCATE('rm/',SUBSTR(f_value,LOCATE('vcastr_file=',f_value)+12,LOCATE('.flv',f_value)-LOCATE('vcastr_file=',f_value)-8))+3,\n" +
                "LOCATE('.flv',SUBSTR(f_value,LOCATE('vcastr_file=',f_value)+12,LOCATE('.flv',f_value)-LOCATE('vcastr_file=',f_value)-8))-LOCATE('rm/',SUBSTR(f_value,LOCATE('vcastr_file=',f_value)+12,LOCATE('.flv',f_value)-LOCATE('vcastr_file=',f_value)-8))+1\n" +
                ") as file_name,\n" +
                "\n" +
                "SUBSTR(f_value,LOCATE('vcastr_file=',f_value)+12,LOCATE('.flv',f_value)-LOCATE('vcastr_file=',f_value)-8) as content\n" +
                "FROM\n" +
                "\tcms_info_custom\n" +
                "WHERE\n" +
                "\tf_value LIKE '%.flv%'\n" +
                "and f_value  Like '%param%'\n";
        PreparedStatement pstmt;
        String insert = "insert into cms_info_custom(f_info_id,f_key,f_value) values(?,?,?)";
        try {
            pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                String content = rs.getString("content");
                if (StringUtil.isNullOrEmpty(content)) {
                    continue;
                }
                int id = rs.getInt("f_info_id");
                String fileName = rs.getString("file_name");

                System.out.println(fileName);
                pstmt = (PreparedStatement) conn.prepareStatement(insert);
                pstmt.setInt(1, id);
                pstmt.setString(2, "videoName");
                pstmt.setString(3, fileName);
                pstmt.executeUpdate();
                pstmt = (PreparedStatement) conn.prepareStatement(insert);
                pstmt.setInt(1, id);
                pstmt.setString(2, "video");
                pstmt.setString(3, content.replaceAll("/article/rm/","/uploads/1/video/public/"));
                pstmt.executeUpdate();
                pstmt = (PreparedStatement) conn.prepareStatement(insert);
                pstmt.setInt(1, id);
                pstmt.setString(2, "videoLength");
                pstmt.setString(3, "0");
                pstmt.executeUpdate();
            }
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void three() {
        Connection conn = getConn();
        String sql = "SELECT\n" +
                "\tf_info_id,\n" +
                "SUBSTR(\n" +
                "SUBSTR(f_value,LOCATE('src=',f_value)+5,LOCATE('.mp4.html',f_value)-LOCATE('src=',f_value)+4),\n" +
                "LOCATE('public/',SUBSTR(f_value,LOCATE('src=',f_value)+5,LOCATE('.mp4.html',f_value)-LOCATE('src=',f_value)+4))+7,\n" +
                "LOCATE('.mp4.html',SUBSTR(f_value,LOCATE('src=',f_value)+5,LOCATE('.mp4.html',f_value)-LOCATE('src=',f_value)+4))-LOCATE('public/',SUBSTR(f_value,LOCATE('src=',f_value)+5,LOCATE('.mp4.html',f_value)-LOCATE('src=',f_value)+4))+3\n" +
                ") as file_name,\n" +
                "\n" +
                "SUBSTR(f_value,LOCATE('src=',f_value)+5,LOCATE('.mp4.html',f_value)-LOCATE('src=',f_value)-1) as content\n" +
                "FROM\n" +
                "\tcms_info_custom\n" +
                "WHERE\n" +
                "\tf_value LIKE '%.mp4.html%';\n";
        PreparedStatement pstmt;
        String insert = "insert into cms_info_custom(f_info_id,f_key,f_value) values(?,?,?)";
        try {
            pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                String content = rs.getString("content");
                if (StringUtil.isNullOrEmpty(content)) {
                    continue;
                }
                int id = rs.getInt("f_info_id");
                String fileName = rs.getString("file_name");

                System.out.println(fileName);
                pstmt = (PreparedStatement) conn.prepareStatement(insert);
                pstmt.setInt(1, id);
                pstmt.setString(2, "videoName");
                pstmt.setString(3, fileName);
                pstmt.executeUpdate();
                pstmt = (PreparedStatement) conn.prepareStatement(insert);
                pstmt.setInt(1, id);
                pstmt.setString(2, "video");
                pstmt.setString(3, content);
                pstmt.executeUpdate();
                pstmt = (PreparedStatement) conn.prepareStatement(insert);
                pstmt.setInt(1, id);
                pstmt.setString(2, "videoLength");
                pstmt.setString(3, "0");
                pstmt.executeUpdate();
            }
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
