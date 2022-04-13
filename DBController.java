package com.mire.sixclass;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;




public class DBController {
   //멤버함수 :정적멤버함수(db삽입)
   public static int StudentScoreInsert(StudentScoreModel studentScoreModel) {
      //데이터베이스 명령문 리턴값
      int returnValue = 0;
      
      //1. 데이터베이스 접속
      Connection con = DBUtility.getConnection();
      
      if(con == null) {
         System.out.println("Mysql Connection Fail");
         return 0;
      }
      
      //(id,name,age,gender,phoneNumber,kor,eng,mat,sum,avr,grade)
      //2. 명령문 하달(삽입명령문 하달: 쿼리문으로 명령문 하달 :curd)
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
         
         //3. 리턴값받는다.
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
      
      
      //4. 결과값을 통보한다.
      return returnValue;
   }

   
   //멤버함수 : 정적멤버함수 (테이블 모델객체 가져오기(보여주기))
   public static List<StudentScoreModel> studentScoreSelect(){
      //테이블에 있는 레코드 셋을 가젿오기 위한  ArrayList<StudentScoreModel>
      List<StudentScoreModel> list = new ArrayList<StudentScoreModel>();
      //1.데이터베이스 접속요청
      Connection con = (Connection)DBUtility.getConnection();
      
      if(con == null) {
         System.out.println("Mysql ConnectionFail");
         return null;
      }
      
      //2.(select 쿼리문 명령문하달: 쿼리문으로 명령문하달 : SELECT * FROM studentscoredb.studentscoretbl;)
      String selectQuery = "SELECT * FROM studentscoredb.studentscoretbl";
      PreparedStatement ps = null;
      ResultSet resultSet = null;
      
      try {
         //2.1 select query binding
         ps = (PreparedStatement)con.prepareStatement(selectQuery);
         //2.2 명령문 실행(select 쿼리문) 
         //3.리턴값(레코드셋=ResultSet 리턴한다) 구별 : executeUpdate() 와 executeQuery() 구별할 수 있어야한다. 
         resultSet = ps.executeQuery();
         
         //레코드셋을 List로 가져온다.
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
      //3. 리턴값(삽입한 갯수를 리턴한다) 받음.
      //4. 결과값을 통보한다.
      return list;
   }

   // 정적멤버함수 (테이블 검색하기: 이름(이름내용,1) , 성별(성별내용,2) 전화번호(전화번호내용,3)
   public static List<StudentScoreModel> StudentScoreSearch(String searchData, int number) {
      final int NAME_NUM = 1, GENDER_NUM = 2,PHONE_NUM = 3, EXIT = 4;
      //레코드 셋을 저장을 위한 List Collection
      List<StudentScoreModel> list = new ArrayList<StudentScoreModel>();
      //1. 데이터베이스 접속요청
      Connection con = DBUtility.getConnection();
      
      //2. 검색명령문 하달 (검색명령문하달: 쿼리문으로 명령문하달 : select *from studentscoredb.studentscoretbl where name like '홍길동';)
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
         //2.2 명령문 실행과 동시에
         //3. 리턴값 (레코드셋 = ResultSet 리턴한다) 구별 : executeUpdate() 와 executeQuery() 구별할 수 있어야한다.
         resultSet = ps.executeQuery();
         //3.1 리턴값 ResultSet을 ArrayList<StudentScoreModel>변환한다.
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
      //3. 리턴값(삽입한 갯수를 리턴한다) 받음.
      //4. 결과값을 통보한다.
      return list;
   }


   public static int StudentScoreDelete(String deleteData, int number) {
      final int PHONE_NUM = 1;
      //1. 데이터베이스 접속
      Connection con = DBUtility.getConnection();
      
      if(con == null) {
         System.out.println("Mysql Connection Fail");
         return 0;
      }      
      //2.(delete 쿼리문 명령문하달: 쿼리문으로 명령문하달 : delete from studentscoredb.studentscoretbl where phoneNumber like ?;)
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
         //2.2 명령문 실행과 동시에
         //3. 리턴값 (레코드셋 = ResultSet 리턴한다) 구별 : executeUpdate() 와 executeQuery() 구별할 수 있어야한다.
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
      //4. 결과값을 통보한다.
      
      return resultNumber;
   }


   public static int StudentScoreUpdate(String phoneNumber, int id) {
      final int PHONE_NUM = 1;
      //1. 데이터베이스 접속
      Connection con = DBUtility.getConnection();
      
      if(con == null) {
         System.out.println("Mysql Connection Fail");
         return 0;
      }      
      //2.(update 수정명령문하달: 쿼리문으로 명령문하달 :update studentscoredb.studentscoretbl set id = '33' where phoneNumber = '333-3333-3333';)
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
         //2.2 명령문 실행과 동시에
         //3. 리턴값 (레코드셋 = ResultSet 리턴한다) 구별 : executeUpdate() 와 executeQuery() 구별할 수 있어야한다.
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
      //4. 결과값을 통보한다.
      
      return resultNumber;
   }

   //정적멤버함수 (테이블 정렬하기: 이름으로 정렬(오름,내림))
   public static List<StudentScoreModel> StudentScoreSort(int no) {
      final int ASC = 1 , DESC = 2;
      //테이블에 있는 레코드 셋을 가져오기 위한 ArrayList<StudentScoreModel>
      List<StudentScoreModel> list = new ArrayList<StudentScoreModel>();
      String sortQuery = null;
      
      //1. 데이터베이스 접속요청
      Connection con = DBUtility.getConnection();
      
      if(con == null) {
         System.out.println("Mysql Connection Fail");
         return null;
      }
      //2. 정렬쿼리문 명령문 하달 : 쿼리문으로 명령문 하달 : (select *from studentscoredb.studentscoretbl order by name asc;)
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
         //2.2 명령문 실행
         //3.리턴값(레코드셋=ResulrSet 리턴한다)
         resultSet = ps.executeQuery();
         //레코드셋을 List로 가져온다.
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
      //3. 리턴값(삽입한 갯수를 리턴한다) 받음.
      //4. 결과값을 통보한다.
      return list;
   }      
         
}   
      
      