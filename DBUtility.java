package com.mire.sixclass;


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBUtility {
   // ����Լ� : �����ͺ��̽� ���ӿ�û
   // Connection : �����ͺ��̽� �����ϴ� �ڵ鰪
   public static Connection getConnection() {
      Connection con = null;
      FileReader fr = null;
      

         try {
            fr = new FileReader("src\\com\\mire\\sixclass\\db.properties");
            Properties properties = new Properties();
            properties.load(fr);
            String DRIVER = properties.getProperty("DRIVER");
            String URL = properties.getProperty("URL");
            String userID = properties.getProperty("userID");
            String userPassword = properties.getProperty("usePassword");
            
            //����̹� ����
            Class.forName(DRIVER);
            //���̤����̽� ����
            con = (Connection) DriverManager.getConnection(URL,userID,userPassword);
         } catch (ClassNotFoundException e) {
            System.out.println("mysql database connection fail");
            e.printStackTrace();
         } catch (SQLException e) {
            System.out.println("mysql database connection fail");
            e.printStackTrace();
         } catch (FileNotFoundException e) {
            System.out.println("file not found db.properties");
            e.printStackTrace();
         } catch (IOException e) {
            System.out.println("file db.properties connection fail");
            e.printStackTrace();
         }finally {
            
         try {
            fr.close();
            }catch(IOException e) {
               e.printStackTrace();
            }
         }
         return con;   
      
   }

}