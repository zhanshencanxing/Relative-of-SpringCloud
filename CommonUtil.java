package com.qhit.utils;

import java.io.FileOutputStream;
import java.sql.*;
import java.util.Date;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings({"deprecation"})
public class CommonUtil {

    static Connection getConnection() {
        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            //new oracle.jdbc.driver.OracleDriver();
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ems?useUnicode=true&characterEncoding=utf-8&useSSL=true&serverTimezone=UTC", "root", "123456");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }

    static void exportExcelToPath(String title, String[] name, ResultSet rs, String path) throws Exception {
        HSSFWorkbook workBook = new HSSFWorkbook(); //创建excel文件
        HSSFSheet sheet = workBook.createSheet(); //创建工作表

        for (int i = 0; i < name.length; i++) {
            sheet.setColumnWidth(i, 20 * 256);
        }

        //标题样式
        HSSFCellStyle titleStyle = workBook.createCellStyle();
        titleStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); //水平居中
        titleStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        HSSFFont titleFont = workBook.createFont(); //字体
        titleFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); //加粗
        titleFont.setFontHeightInPoints((short) 14);  //字号
        titleStyle.setFont(titleFont);               //标题使用字体样式
        //正文样式
        HSSFCellStyle contentStyle = workBook.createCellStyle();
        HSSFFont contentFont = workBook.createFont(); //字体
        contentFont.setFontHeightInPoints((short) 12);
        contentStyle.setFont(contentFont);
        contentStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);//边框
        contentStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        contentStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        contentStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        contentStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); //居中
        contentStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

        HSSFRow titleRow = sheet.createRow(0);    //创建标题行
        HSSFCell titleCell = titleRow.createCell(0);
        titleCell.setCellValue(title);
        titleCell.setCellStyle(titleStyle);     //单元格样式
        CellRangeAddress address = new CellRangeAddress(0, 0, 0, name.length - 1);
        sheet.addMergedRegion(address);
        titleRow.setHeightInPoints(30); //行高


        HSSFRow row2 = sheet.createRow(1);
        row2.setHeightInPoints(20);
        for (int i = 0; i < name.length; i++) {
            HSSFCell cell = row2.createCell(i);
            cell.setCellValue(name[i]);
            cell.setCellStyle(contentStyle);
        }

        int flag = 2;
        while (rs.next()) {
            HSSFRow datarow = sheet.createRow(flag);
            datarow.setHeightInPoints(20);
            ResultSetMetaData metaData = rs.getMetaData();
            for (int i = 0; i < metaData.getColumnCount(); i++) {
                HSSFCell datacell = datarow.createCell(i);
                datacell.setCellValue(rs.getString(i + 1));
                datacell.setCellStyle(contentStyle);
            }

            flag++;
        }
        rs.close();
        path = path + "\\" + title + "_" + CustomDate.ToDateTimeString(new Date()).replaceAll(":", "_") + ".xls";
        FileOutputStream out = new FileOutputStream(path);
        workBook.write(out);    //保存单元格
        out.close();

    }

    private static void exportExcelToDowload(String title, String[] name, ResultSet rs, HttpServletResponse response) throws Exception {
        HSSFWorkbook workBook = new HSSFWorkbook(); //创建excel文件
        HSSFSheet sheet = workBook.createSheet(); //创建工作表

        //设置列宽
        for (int i = 0; i < name.length; i++) {
            sheet.setColumnWidth(i, 20 * 256);
        }

        //标题样式
        HSSFCellStyle titleStyle = workBook.createCellStyle();
        titleStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); //水平居中
        titleStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        HSSFFont titleFont = workBook.createFont(); //字体
        titleFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); //加粗
        titleFont.setFontHeightInPoints((short) 14);  //字号
        titleStyle.setFont(titleFont); //标题使用字体样式

        //正文样式
        HSSFCellStyle contentStyle = workBook.createCellStyle();
        HSSFFont contentFont = workBook.createFont(); //字体
        contentFont.setFontHeightInPoints((short) 12);
        contentStyle.setFont(contentFont);
        contentStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);//边框
        contentStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        contentStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        contentStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        contentStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); //居中
        contentStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

        HSSFRow titleRow = sheet.createRow(0);  //创建标题行
        HSSFCell titleCell = titleRow.createCell(0);
        titleCell.setCellValue(title);
        titleCell.setCellStyle(titleStyle);  //单元格样式
        CellRangeAddress address = new CellRangeAddress(0, 0, 0, name.length - 1);
        sheet.addMergedRegion(address);
        titleRow.setHeightInPoints(30); //行高

        HSSFRow row2 = sheet.createRow(1);
        row2.setHeightInPoints(20);

        //创建单元格
        for (int i = 0; i < name.length; i++) {
            HSSFCell cell = row2.createCell(i);
            cell.setCellValue(name[i]);
            cell.setCellStyle(contentStyle);
        }

        int flag = 2;
        while (rs.next()) {
            HSSFRow datarow = sheet.createRow(flag);
            datarow.setHeightInPoints(20);
            ResultSetMetaData metaData = rs.getMetaData();
            for (int i = 0; i < metaData.getColumnCount(); i++) {
                HSSFCell datacell = datarow.createCell(i);
                datacell.setCellValue(rs.getString(i + 1));
                datacell.setCellStyle(contentStyle);
            }

            flag++;
        }
        rs.close();
        //调用setResponseHeader()方法，响应头部信息
        setResponseHeader(response, title + "_" + CustomDate.ToDateTimeString(new Date()) + ".xls");

        //获取SeverletResponse输出流
        ServletOutputStream outputStream = response.getOutputStream();
        workBook.write(outputStream);    //保存单元格

        //刷新缓冲区
        outputStream.flush();
        //关流
        outputStream.close();

    }

    //发送响应流方法，响应头部信息
    private static void setResponseHeader(HttpServletResponse response, String fileName) {
        try {
            fileName = new String(fileName.getBytes(), "ISO8859-1");
            response.setContentType("application/octet-stream;charset=ISO8859-1");
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
            response.addHeader("Pargam", "no-cache");
            response.addHeader("Cache-Control", "no-cache");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    //响应下载
    public static void getDowload(String title, String[] name, String sql, HttpServletResponse response) {
        //获取数据
        Connection connection = getConnection();
        Statement statement = null;
        ResultSet rs;
        try {
            //创建statement对象
            statement = connection.createStatement();
            //执行SQL语句
            rs = statement.executeQuery(sql);

            //调用exportExcelToDowload()方法
            exportExcelToDowload(title, name, rs, response);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                //关闭所有桥
                if (statement != null) statement.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
