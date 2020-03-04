package io.gitee.zhousiwei;

import io.netty.util.internal.StringUtil;
import org.apache.commons.lang.StringUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Created by 試毅-思伟 on 2018/10/15
 */
public class _Main {

    private static Connection getConn() {
        String driver = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/vcms?useSSL=false";
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
        //String str = "<p style=\"text-align: center;\"><iframe scrolling=\"no\" height=\"430\" width=\"630\" src=\"api-huoqusp-1-uploadfile/14/video/20180917井冈山新闻（成品）.mp4.html\" frameborder=\"0 allowfullscreen\"></iframe>\u200B</p><p><br></p>";

        Connection conn = getConn();
        //43993
        String sql = "select * from dr_14_news_data_0";
        PreparedStatement pstmt;
        String insert = "insert into flag(id,catid,content) values(?,?,?)";
        try {
            pstmt = (PreparedStatement) conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                String content = rs.getString("content");
                if (StringUtil.isNullOrEmpty(content)) {
                    continue;
                }
                int id = rs.getInt("id");
                int catid = rs.getInt("catid");

                List<String> srcList = getSrcContent(content, SRC_REGEX);
                List<String> hrefList = getSrcContent(content, HREF_REGEX);
                for (String str : srcList) {
                    if (StringUtils.isEmpty(str)) {
                        continue;
                    }
                    if (str.toLowerCase().contains(".js") || str.toLowerCase().contains(".swf")
                            || str.toLowerCase().contains(".jpg") || str.toLowerCase().contains(".jpeg")
                            || str.toLowerCase().contains(".png") || str.toLowerCase().contains(".gif")
                            || str.toLowerCase().contains(".bmp")) {
                        //continue;
                    } else {
                        pstmt = (PreparedStatement) conn.prepareStatement(insert);
                        pstmt.setInt(1, id);
                        pstmt.setInt(2, catid);
                        pstmt.setString(3, str);
                        pstmt.executeUpdate();
                    }
                }
                for (String href : hrefList) {
                    if (StringUtils.isEmpty(href)) {
                        continue;
                    }
                    if (href.toLowerCase().contains(".xls") || href.toLowerCase().contains(".xlsx")
                            || href.toLowerCase().contains(".doc") || href.toLowerCase().contains(".docx")
                            || href.toLowerCase().contains(".ppt") || href.toLowerCase().contains(".pdf")) {
                        pstmt = conn.prepareStatement(insert);
                        pstmt.setInt(1, id);
                        pstmt.setInt(2, catid);
                        pstmt.setString(3, href);
                        pstmt.executeUpdate();
                    }
                }
            }
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("----------Finished----------");
    }

    /**
     * 获取src正则
     */
    protected final static String SRC_REGEX = "(?i)src=\"([^\"]*)\"";
    /**
     * 获取href正则
     */
    protected final static String HREF_REGEX = "(?i)href=\"([^\"]*)\"";

    /**
     * 获取src或href里面的内容
     */
    public static List<String> getSrcContent(String str, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);

        List<String> srcList = new ArrayList<>();

        while (matcher.find()) {
            String group = matcher.group();
            srcList.add(group.replaceFirst(regex, "$1"));
        }
        return srcList;
    }


}
