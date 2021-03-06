package com.mire.sixclass;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StudentScoreMain {
   public static Scanner scan = new Scanner(System.in);
   public static final int INSERT = 1, SEARCH = 2, DELETE = 3,UPDATE = 4, PRINT = 5, SORT = 6, EXIT = 7;
   public static final int NAME = 1, GENDER = 2, PHONE = 3;

   
   public static void main(String[] args) {
      boolean flag = false;
      
      while(!flag) {
         int no = selectMenu();
         switch(no) {
         //입력, 검색,삭제,수정,출력,정렬,종료
         case INSERT : StudentScoreInsert(); break;
         case SEARCH : StudentScoreSearch(); break;
         case DELETE : StudentScoreDelete(); break;
         case UPDATE : StudentScoreUpdate(); break;
         case PRINT : StudentScorePrint(); break;
         case SORT : StudentScoreSort(); break;
         case EXIT : flag = true; 
            System.out.println("시스템종료합니다."); break;
         default: System.out.println("다시입력요망!"); break;
         }//end of switch   
      }//end of while
   
   }
   

   //입력 (id,name,age,gender,phoneNumber,kor,eng,mat,sum,avr,grade)
   private static void StudentScoreInsert() {
      int id = 0;
      String name = null;
      int age = 0;
      String gender = null;
      String phoneNumber = null;
      int kor = 0;
      int eng = 0;
      int mat = 0;
      int sum = 0;
      double avr = 0.0;
      String grade = null;
      String str = null;
      
      //학번
      while(true) {
         System.out.println("학번(1~100) 입력 >>");
         id = scan.nextInt();
         str = scan.nextLine();
         if(id >= 1 && id <= 100 ) {
            break;
         }else {
            System.out.println("다시 입력해주세요");
         }
      }
      
      //이름
      while(true) {
         System.out.println("이름(홍길동) 입력 >>");
         name = scan.nextLine();
         
         if(patternCheck(name, NAME)) {
             break;
         } else {            
             System.out.println("다시 입력해주세요");
         }
      }
      
      //나이
      while(true) {
         System.out.println("나이(20~60) 입력 >>");
         age = scan.nextInt();
         str = scan.nextLine();
         if(age >= 20 && age <= 60 ) {
            break;
         }else {
            System.out.println("다시 입력해주세요");
         }
      }   
      
      //성별
      while(true) {
         System.out.println("성별(남,여) 입력 >>");
         gender = scan.nextLine();
         
         if(gender.equals("남") || gender.equals("여")) {
            break;
         }else {
            System.out.println("다시 입력해주세요");
         }
      }   
      
      //전화번호
      while(true) {
         System.out.println("010-0000-0000 입력 >>");
         phoneNumber = scan.nextLine();

         if(patternCheck(phoneNumber, PHONE)) {
             break;
         } else {            
             System.out.println("다시 입력해주세요");
         }
      }   
      
      //점수
      while(true) {
         System.out.println("국어점수(0~100) 입력 >>");
         kor = scan.nextInt();
         
         if(kor >= 0 && kor <= 100 ) {
            break;
         }else {
            System.out.println("다시 입력해주세요");
         }
      }
      while(true) {
         System.out.println("영어점수(0~100) 입력 >>");
         eng = scan.nextInt();
         
         if(eng >= 0 && eng <= 100 ) {
            break;
         }else {
            System.out.println("다시 입력해주세요");
         }
      }      
      while(true) {
         System.out.println("수학점수(0~100) 입력 >>");
         mat = scan.nextInt();
         
         if(mat >= 0 && mat <= 100 ) {
            break;
         }else {
            System.out.println("다시 입력해주세요");
         }
      }      
      
   

      
      
      //삽입할 객체모델 생성
      StudentScoreModel studentScoreModel = new StudentScoreModel(id,name,age,gender,phoneNumber,kor,eng,mat);
      studentScoreModel.calSum();
      studentScoreModel.calAvr();
      studentScoreModel.calGrade();
      int returnValue = DBController.StudentScoreInsert(studentScoreModel);
      if(returnValue != 0) {
         System.out.println(studentScoreModel.getName() + "님 삽입 성공하였습니다.");
      }else {
         System.out.println(studentScoreModel.getName() + "님 삽입 실패하였습니다.");
      }
   }
   
   //패턴매치 처리함수
   private static boolean patternCheck(String patternData, int patternType) {
      String filter = null;
      
      switch(patternType) {
      case NAME : filter = "^[가-힣]{2,5}$"; break;
      case PHONE : filter = "\\d{3}-\\d{4}-\\d{4}"; break;
      }
      Pattern pattern = Pattern.compile(filter);
      Matcher matcher = pattern.matcher(patternData);
      return matcher.matches();
   }

   //검색 (이름,성별,전화번호 검색)
   private static void StudentScoreSearch() {
      final int NAME_NUM = 1, GENDER_NUM = 2,PHONE_NUM = 3,  EXIT= 4;
      List<StudentScoreModel> list = new ArrayList<StudentScoreModel>();
      String name = null;
      String gender = null;
      String phoneNumber = null;
      String searchData = null;
      int number = 0;
      int no = 0;
      boolean flag = false;
      
      no = searchMenu();
      switch(no) {
      //이름
      case NAME_NUM :
         while(true) {
            System.out.print("이름(홍길동) 입력>> ");
            name = scan.nextLine();
            if(patternCheck(name, NAME)) {
               break;
            }else {
               System.out.println("다시 입력해주세요.");
            }
         }
         searchData = name;
         number = NAME_NUM;
         break;
      //성별
      case GENDER_NUM : 
         while(true) {
            System.out.print("성별(남,여) 입력>>");
            gender = scan.nextLine();
            if(gender.equals("남") || gender.equals("여")) {
               break;
            }else {
               System.out.println("다시 입력해주세요.");
            }
         }
         searchData = gender;
         number = GENDER_NUM;
         break;
         //전화번호
      case PHONE_NUM : 
         while(true) {
         System.out.println("검색할 전화번호 입력>>");
         phoneNumber = scan.nextLine();
         if(patternCheck(phoneNumber, PHONE)) {
            break;
         }else {
            System.out.println("다시 입력해주세요.");
            }

         }
         searchData = phoneNumber;
         number = PHONE_NUM;
         break;
      case EXIT : 
         System.out.println("검색이 취소되었습니다.");
         flag = true;
         break;            
      }//end of switch
      if(flag) {
         return;
      }
      list = DBController.StudentScoreSearch(searchData,number);
      if(list.size() <= 0) {
         System.out.println(searchData + "찾지 못했습니다.");
         return;
      }
      for(StudentScoreModel data : list) {
         System.out.println(data);
      }
   }


   //검색할 내용을 선택요청(이름,전화번호)
   private static int searchMenu() {
      boolean flag = false;
      int no = 0;
      while(!flag) {
         System.out.println("****************************");
         System.out.println("1.이름 2.성별 3.전화번호 4.종료");
         System.out.println("****************************");
         System.out.print("번호입력 >> ");
      try {
         no = Integer.parseInt(scan.nextLine());
      }catch(InputMismatchException e) {
         System.out.println("경고: 숫자 입력해주세요!");
         continue;
      }catch(Exception e) {
         System.out.println("경고: 숫자 입력해주세요!");
         continue;
         }
      if(no >=1 && no <= 4) {
         flag = true;
      }else{
         System.out.println("경고: 1~4까지 숫자를 입력해주세요!");
         }
      }// end of while
      return no;
   }

   //삭제(전화번호)
   private static void StudentScoreDelete() {
      final int PHONE_NUM = 1;
      String phoneNumber = null;
      String deleteData = null;
      int number = 0;
      int resultNumber = 0;
      
      //전화번호
      while(true) {
      System.out.println("검색할 전화번호 입력>>");
      phoneNumber = scan.nextLine();
      if(patternCheck(phoneNumber, PHONE)) {
         break;
      }else {
         System.out.println("다시 입력해주세요.");
         }

      }   
      deleteData = phoneNumber;
      number = PHONE_NUM;
      resultNumber = DBController.StudentScoreDelete(deleteData, number);
      
      if(resultNumber != 0) {
         System.out.println(phoneNumber+"번호 삭제완료했습니다.");
      }else {
         System.out.println(phoneNumber+"번호 삭제실패했습니다.");
      }
      return;
   }

   // 수정(전화번호부)내용수정(id)
   private static void StudentScoreUpdate() {
      //1. 전화번호부 검색
      final int PHONE_NUM = 3;
      List<StudentScoreModel> list = new ArrayList<StudentScoreModel>();
      //검색할 내용 선택 
      String phoneNumber = null;
      String searchData = null;
      int number = 0;
      boolean flag= false;
      int resultNumber= 0;
      int id = 0;
      
      //전화번호
      while(true) {
         System.out.println("검색할 전화번호 입력 >>");
         phoneNumber = scan.nextLine();
         if(patternCheck(phoneNumber, PHONE)) {
             break;
         } else {            
             System.out.println("다시 입력해주세요");
         }
      }
      searchData = phoneNumber;
      number = PHONE_NUM;
      
      list = DBController.StudentScoreSearch(searchData, number);
      
      if(list.size() <= 0) {
         System.out.println(searchData + "찾지 못했습니다.");
         return;
      }
      
      StudentScoreModel data_buffer = null;
      for(StudentScoreModel data:list) {
         System.out.println(data);
         data_buffer = data;
      }
      
      //2. 전화번호가 있으면 수정할 작업을 받아서 수정요청
      while(true) {
         System.out.println("["+data_buffer.getId() +"] \n수정할 학번입력 >>");
         id = scan.nextInt();
         
         if(id>=1 && id<=100) {
            break;
         }else {
            System.out.println("다시 입력바랍니다.");
         }
      }
      //3. 결과값 확인
      resultNumber = DBController.StudentScoreUpdate(phoneNumber, id);
      
      if(resultNumber != 0) {
         System.out.println(phoneNumber +"번호 수정완료했습니다.");
      }else {
      System.out.println(phoneNumber +"번호 수정완료했습니다.");
      }
      return;
   }
   
   //출력
   private static void StudentScorePrint() {
      List<StudentScoreModel> list = new ArrayList<StudentScoreModel>();
      list = DBController.studentScoreSelect();
      if(list.size() <= 0 ) {
         System.out.println("출력할 내용이 없습니다.");
         return;
      }
      
      for(StudentScoreModel data:list) {
         System.out.println(data);
      }
   }

   //정렬
   private static void StudentScoreSort() {
      List<StudentScoreModel> list = new ArrayList<StudentScoreModel>();
      int no = 0;
      boolean flag = false;
      
      //1. 정렬방식 (이름: 오름차순, 내림차순)
      while(!flag) {
         System.out.println("1.이름 오름차순 2. 이름 내림차순");
         System.out.println("정렬방식 선택>>");
         
         try {
            no = Integer.parseInt(scan.nextLine());
         }catch(InputMismatchException e){
            System.out.println("경고 : 숫자 입력해주세요!");
            continue;
         }catch(Exception e) {
            System.out.println("경고 : 숫자 입력해주세요!");
            continue;
         }
         
         if(no >= 1 && no <= 2 ) {
            flag = true;
         }else {
            System.out.println("경고 : 1~2까지 숫자를 입력해주세요!");
         }
      } // end of while
      
      //2. 출력문을 가져온다.
      
      list = DBController.StudentScoreSort(no);
      
      if(list.size() <= 0 ) {
         System.out.println("정렬할 내용이 없습니다.");
         return;
      }
      
      for(StudentScoreModel data:list) {
         System.out.println(data);
      }
      return;
   }

   //메뉴선택
   private static int selectMenu() {
      boolean flag = false;
      int no = 0;
      
      while(!flag) {
         System.out.println("******************************************");
         System.out.println("1.입력 2.검색 3.삭제 4.수정 5.출력 6.정렬 7.종료 ");
         System.out.println("******************************************");
         System.out.print("번호입력 >> ");   
         
         try {
            no = Integer.parseInt(scan.nextLine());
         }catch(InputMismatchException e){
            System.out.println("경고 : 숫자 입력해주세요!");
            continue;
         }catch(Exception e) {
            System.out.println("경고 : 숫자 입력해주세요!");
            continue;
         }
         if(no>=1 && no<=7) {
            flag = true;
         }else {
            System.out.println("경고: 1~7 사이의 숫자를 입력해주세요!");
         }
      }//end of while
      return no;
   }

}