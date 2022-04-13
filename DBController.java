package com.mire.sixclass;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;




public class DBController {
   //����Լ� :��������Լ�(db����)
   public static int StudentScoreInsert(StudentScoreModel studentScoreModel) {
      //�����ͺ��̽� ��ɹ� ���ϰ�
      int returnValue = 0;
      
      //1. �����ͺ��̽� ����
      Connection con = DBUtility.getConnection();
      
      if(con == null) {
         System.out.println("Mysql Connection Fail");
         return 0;
      }
      
      //(id,name,age,gender,phoneNumber,kor,eng,mat,sum,avr,grade)
      //2. ��ɹ� �ϴ�(���Ը�ɹ� �ϴ�: ���������� ��ɹ� �ϴ� :curd)
      String insertQuery = "insert into studentscoretbl values(?,?,?,?,?,?,?,?,?,?,?)";
      PreparedStatement ps = null;
      
      try {
         //2.1 insert query binding
         ps = (PreparedStatement)con.prepareStatement(insertQuery);
         ps.setInt(1, studentScoreModel.getId());
         ps.setString(2, studentScoreModel.getName());
         ps.setInt(3, studentScoreModel.getAge());
         ps.setString(4, studentScoreModel.getGender());
         ps.setString(5, studentScoreModel.getPhoneNumber());
         ps.setInt(6, studentScoreModel.getKor());
         ps.setInt(7, studentScoreModel.getEng());
         ps.setInt(8, studentScoreModel.getMat());
         ps.setInt(9, studentScoreModel.getSum());
         ps.setDouble(10, studentScoreModel.getAvr());
         ps.setString(11, studentScoreModel.getGrade());
         
         //3. ���ϰ��޴´�.
         returnValue = ps.executeUpdate();
         
      } catch (SQLException e) {
         e.printStackTrace();
      }finally {
         try {
            if(ps != null && !ps.isClosed()) {
               ps.close();
            }
            if(con != null && !con.isClosed()) {
               con.close();
            }
         } catch (SQLException e) {
            e.printStackTrace();
         }
      }
      
      
      //4. ������� �뺸�Ѵ�.
      return returnValue;
   }

   
   //����Լ� : ��������Լ� (���̺� �𵨰�ü ��������(�����ֱ�))
   public static List<StudentScoreModel> studentScoreSelect(){
      //���̺� �ִ� ���ڵ� ���� �������� ����  ArrayList<StudentScoreModel>
      List<StudentScoreModel> list = new ArrayList<StudentScoreModel>();
      //1.�����ͺ��̽� ���ӿ�û
      Connection con = (Connection)DBUtility.getConnection();
      
      if(con == null) {
         System.out.println("Mysql ConnectionFail");
         return null;
      }
      
      //2.(select ������ ��ɹ��ϴ�: ���������� ��ɹ��ϴ� : SELECT * FROM studentscoredb.studentscoretbl;)
      String selectQuery = "SELECT * FROM studentscoredb.studentscoretbl";
      PreparedStatement ps = null;
      ResultSet resultSet = null;
      
      try {
         //2.1 select query binding
         ps = (PreparedStatement)con.prepareStatement(selectQuery);
         //2.2 ��ɹ� ����(select ������) 
         //3.���ϰ�(���ڵ��=ResultSet �����Ѵ�) ���� : executeUpdate() �� executeQuery() ������ �� �־���Ѵ�. 
         resultSet = ps.executeQuery();
         
         //���ڵ���� List�� �����´�.
         while(resultSet.next()) {
            int id = resultSet.getInt(1);
            String name = resultSet.getString(2);
            int age = resultSet.getInt(3);
            String gender = resultSet.getString(4);
            String phoneNumber = resultSet.getString(5);
            int kor = resultSet.getInt(6);
            int eng = resultSet.getInt(7);
            int mat = resultSet.getInt(8);
            int sum = resultSet.getInt(9);
            double avr = resultSet.getDouble(10);
            String grade = resultSet.getString(11);
            
                                                            //(id,name,age,gender,phoneNumber,kor,eng,mat,sum,avr,grade)
            StudentScoreModel studentScoreModel = new StudentScoreModel(id, name, age, gender, phoneNumber, kor, eng, mat);
            studentScoreModel.calSum();
            studentScoreModel.calAvr();
            studentScoreModel.calGrade();
            list.add(studentScoreModel);
         }
         
      } catch (SQLException e) {
         e.printStackTrace();
      } finally {
         try {
         if(ps != null && !ps.isClosed()) {
               ps.close();
         }
         if(con != null && !con.isClosed())
            con.close();
      }   
      catch (SQLException e) {
         e.printStackTrace();
         }
      }
      //3. ���ϰ�(������ ������ �����Ѵ�) ����.
      //4. ������� �뺸�Ѵ�.
      return list;
   }

   // ��������Լ� (���̺� �˻��ϱ�: �̸�(�̸�����,1) , ����(��������,2) ��ȭ��ȣ(��ȭ��ȣ����,3)
   public static List<StudentScoreModel> StudentScoreSearch(String searchData, int number) {
      final int NAME_NUM = 1, GENDER_NUM = 2,PHONE_NUM = 3, EXIT = 4;
      //���ڵ� ���� ������ ���� List Collection
      List<StudentScoreModel> list = new ArrayList<StudentScoreModel>();
      //1. �����ͺ��̽� ���ӿ�û
      Connection con = DBUtility.getConnection();
      
      //2. �˻���ɹ� �ϴ� (�˻���ɹ��ϴ�: ���������� ��ɹ��ϴ� : select *from studentscoredb.studentscoretbl where name like 'ȫ�浿';)
      String searchQueary = null;
      PreparedStatement ps = null;
      ResultSet resultSet = null;
      switch(number) {
      case NAME_NUM :
         searchQueary = "select *from studentscoredb.studentscoretbl where name like ?";
         break;
      case GENDER_NUM :
         searchQueary = "select *from studentscoredb.studentscoretbl where gender like ?";
         break;
      case PHONE_NUM :
         searchQueary = "select *from studentscoredb.studentscoretbl where phoneNumber like ?";
         break;
      }
      
      //2.1 select queary binding
      try {
         ps = (PreparedStatement)con.prepareStatement(searchQueary);
         searchData = "%" + searchData + "%";
         ps.setString(1, searchData);
         //2.2 ��ɹ� ����� ���ÿ�
         //3. ���ϰ� (���ڵ�� = ResultSet �����Ѵ�) ���� : executeUpdate() �� executeQuery() ������ �� �־���Ѵ�.
         resultSet = ps.executeQuery();
         //3.1 ���ϰ� ResultSet�� ArrayList<StudentScoreModel>��ȯ�Ѵ�.
         while(resultSet.next()) {
            int id = resultSet.getInt(1);
            String name = resultSet.getString(2);
            int age = resultSet.getInt(3);
            String gender = resultSet.getString(4);
            String phoneNumber = resultSet.getString(5);
            int kor = resultSet.getInt(6);
            int eng = resultSet.getInt(7);
            int mat = resultSet.getInt(8);
            int sum = resultSet.getInt(9);
            double avr = resultSet.getDouble(10);
            String grade = resultSet.getString(11);
            
            StudentScoreModel studentScoreModel = new StudentScoreModel(id, name, age, gender, phoneNumber, kor, eng, mat);
            studentScoreModel.calSum();
            studentScoreModel.calAvr();
            studentScoreModel.calGrade();
            list.add(studentScoreModel);
            
         }
      } catch (SQLException e) {
         e.printStackTrace();
      } finally {
         try {
         if(ps != null && !ps.isClosed()) {
               ps.close();
         }
         if(con != null && !con.isClosed())
            con.close();
      }   
      catch (SQLException e) {
         e.printStackTrace();
         }
      }
      //3. ���ϰ�(������ ������ �����Ѵ�) ����.
      //4. ������� �뺸�Ѵ�.
      return list;
   }


   public static int StudentScoreDelete(String deleteData, int number) {
      final int PHONE_NUM = 1;
      //1. �����ͺ��̽� ����
      Connection con = DBUtility.getConnection();
      
      if(con == null) {
         System.out.println("Mysql Connection Fail");
         return 0;
      }      
      //2.(delete ������ ��ɹ��ϴ�: ���������� ��ɹ��ϴ� : delete from studentscoredb.studentscoretbl where phoneNumber like ?;)
      String deleteQueary = null;
      PreparedStatement ps = null;
      int resultNumber = 0;
      
      switch(number) {
      case PHONE_NUM :
         deleteQueary ="delete from studentscoredb.studentscoretbl where phoneNumber like ?";
         break;
      }
      
      //2.1 delete query binding
      try {
         ps = (PreparedStatement)con.prepareStatement(deleteQueary);
         deleteData = "%" + deleteData + "%";
         ps.setString(1, deleteData);
         //2.2 ��ɹ� ����� ���ÿ�
         //3. ���ϰ� (���ڵ�� = ResultSet �����Ѵ�) ���� : executeUpdate() �� executeQuery() ������ �� �־���Ѵ�.
         resultNumber = ps.executeUpdate();
      } catch (SQLException e) {
         e.printStackTrace();
      } finally {
         try {
         if(ps != null && !ps.isClosed()) {
               ps.close();
         }
         if(con != null && !con.isClosed())
            con.close();
      }   
      catch (SQLException e) {
         e.printStackTrace();
         }
      }
      //4. ������� �뺸�Ѵ�.
      
      return resultNumber;
   }


   public static int StudentScoreUpdate(String phoneNumber, int id) {
      final int PHONE_NUM = 1;
      //1. �����ͺ��̽� ����
      Connection con = DBUtility.getConnection();
      
      if(con == null) {
         System.out.println("Mysql Connection Fail");
         return 0;
      }      
      //2.(update ������ɹ��ϴ�: ���������� ��ɹ��ϴ� :update studentscoredb.studentscoretbl set id = '33' where phoneNumber = '333-3333-3333';)
      String updateQueary = null;
      PreparedStatement ps = null;
      int resultNumber = 0;
      
      switch(PHONE_NUM) {
      case PHONE_NUM :
         updateQueary ="update studentscoredb.studentscoretbl set id = ? where phoneNumber = ?";
         break;
      }
      
      //2.1 delete query binding
      try {
         ps = (PreparedStatement)con.prepareStatement(updateQueary);
         ps.setInt(1, id);
         ps.setString(2, phoneNumber);
         //2.2 ��ɹ� ����� ���ÿ�
         //3. ���ϰ� (���ڵ�� = ResultSet �����Ѵ�) ���� : executeUpdate() �� executeQuery() ������ �� �־���Ѵ�.
         resultNumber = ps.executeUpdate();
      } catch (SQLException e) {
         e.printStackTrace();
      } finally {
         try {
         if(ps != null && !ps.isClosed()) {
               ps.close();
         }
         if(con != null && !con.isClosed())
            con.close();
      }   
      catch (SQLException e) {
         e.printStackTrace();
         }
      }
      //4. ������� �뺸�Ѵ�.
      
      return resultNumber;
   }

   //��������Լ� (���̺� �����ϱ�: �̸����� ����(����,����))
   public static List<StudentScoreModel> StudentScoreSort(int no) {
      final int ASC = 1 , DESC = 2;
      //���̺� �ִ� ���ڵ� ���� �������� ���� ArrayList<StudentScoreModel>
      List<StudentScoreModel> list = new ArrayList<StudentScoreModel>();
      String sortQuery = null;
      
      //1. �����ͺ��̽� ���ӿ�û
      Connection con = DBUtility.getConnection();
      
      if(con == null) {
         System.out.println("Mysql Connection Fail");
         return null;
      }
      //2. ���������� ��ɹ� �ϴ� : ���������� ��ɹ� �ϴ� : (select *from studentscoredb.studentscoretbl order by name asc;)
      switch(no) {
      case ASC :
         sortQuery = "select *from studentscoredb.studentscoretbl order by name asc";
         break;
      case DESC:
         sortQuery = "select *from studentscoredb.studentscoretbl order by name desc";
         break;
      }
      
      PreparedStatement ps = null;
      ResultSet resultSet = null;
      try {
         //2.1 sort query binding
         ps = (PreparedStatement) con.prepareStatement(sortQuery);
         //2.2 ��ɹ� ����
         //3.���ϰ�(���ڵ��=ResulrSet �����Ѵ�)
         resultSet = ps.executeQuery();
         //���ڵ���� List�� �����´�.
         while(resultSet.next()) {
            int id = resultSet.getInt(1);
            String name = resultSet.getString(2);
            int age = resultSet.getInt(3);
            String gender = resultSet.getString(4);
            String phoneNumber = resultSet.getString(5);
            int kor = resultSet.getInt(6);
            int eng = resultSet.getInt(7);
            int mat = resultSet.getInt(8);
            int sum = resultSet.getInt(9);
            double avr = resultSet.getDouble(10);
            String grade = resultSet.getString(11);
            
                                                            //(id,name,age,gender,phoneNumber,kor,eng,mat,sum,avr,grade)
            StudentScoreModel studentScoreModel = new StudentScoreModel(id, name, age, gender, phoneNumber, kor, eng, mat);
            studentScoreModel.calSum();
            studentScoreModel.calAvr();
            studentScoreModel.calGrade();
            list.add(studentScoreModel);
         }
      
      } catch (SQLException e) {
         e.printStackTrace();
      } finally {
         try {
         if(ps != null && !ps.isClosed()) {
               ps.close();
         }
         if(con != null && !con.isClosed())
            con.close();
      }   
      catch (SQLException e) {
         e.printStackTrace();
         }
      }
      //3. ���ϰ�(������ ������ �����Ѵ�) ����.
      //4. ������� �뺸�Ѵ�.
      return list;
   }      
         
}   
      
      