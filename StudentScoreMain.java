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
         //ÀÔ·Â, °Ë»ö,»èÁ¦,¼öÁ¤,Ãâ·Â,Á¤·Ä,Á¾·á
         case INSERT : StudentScoreInsert(); break;
         case SEARCH : StudentScoreSearch(); break;
         case DELETE : StudentScoreDelete(); break;
         case UPDATE : StudentScoreUpdate(); break;
         case PRINT : StudentScorePrint(); break;
         case SORT : StudentScoreSort(); break;
         case EXIT : flag = true; 
            System.out.println("½Ã½ºÅÛÁ¾·áÇÕ´Ï´Ù."); break;
         default: System.out.println("´Ù½ÃÀÔ·Â¿ä¸Á!"); break;
         }//end of switch   
      }//end of while
   
   }
   

   //ÀÔ·Â (id,name,age,gender,phoneNumber,kor,eng,mat,sum,avr,grade)
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
      
      //ÇĞ¹ø
      while(true) {
         System.out.println("ÇĞ¹ø(1~100) ÀÔ·Â >>");
         id = scan.nextInt();
         str = scan.nextLine();
         if(id >= 1 && id <= 100 ) {
            break;
         }else {
            System.out.println("´Ù½Ã ÀÔ·ÂÇØÁÖ¼¼¿ä");
         }
      }
      
      //ÀÌ¸§
      while(true) {
         System.out.println("ÀÌ¸§(È«±æµ¿) ÀÔ·Â >>");
         name = scan.nextLine();
         
         if(patternCheck(name, NAME)) {
             break;
         } else {            
             System.out.println("´Ù½Ã ÀÔ·ÂÇØÁÖ¼¼¿ä");
         }
      }
      
      //³ªÀÌ
      while(true) {
         System.out.println("³ªÀÌ(20~60) ÀÔ·Â >>");
         age = scan.nextInt();
         str = scan.nextLine();
         if(age >= 20 && age <= 60 ) {
            break;
         }else {
            System.out.println("´Ù½Ã ÀÔ·ÂÇØÁÖ¼¼¿ä");
         }
      }   
      
      //¼ºº°
      while(true) {
         System.out.println("¼ºº°(³²,¿©) ÀÔ·Â >>");
         gender = scan.nextLine();
         
         if(gender.equals("³²") || gender.equals("¿©")) {
            break;
         }else {
            System.out.println("´Ù½Ã ÀÔ·ÂÇØÁÖ¼¼¿ä");
         }
      }   
      
      //ÀüÈ­¹øÈ£
      while(true) {
         System.out.println("010-0000-0000 ÀÔ·Â >>");
         phoneNumber = scan.nextLine();

         if(patternCheck(phoneNumber, PHONE)) {
             break;
         } else {            
             System.out.println("´Ù½Ã ÀÔ·ÂÇØÁÖ¼¼¿ä");
         }
      }   
      
      //Á¡¼ö
      while(true) {
         System.out.println("±¹¾îÁ¡¼ö(0~100) ÀÔ·Â >>");
         kor = scan.nextInt();
         
         if(kor >= 0 && kor <= 100 ) {
            break;
         }else {
            System.out.println("´Ù½Ã ÀÔ·ÂÇØÁÖ¼¼¿ä");
         }
      }
      while(true) {
         System.out.println("¿µ¾îÁ¡¼ö(0~100) ÀÔ·Â >>");
         eng = scan.nextInt();
         
         if(eng >= 0 && eng <= 100 ) {
            break;
         }else {
            System.out.println("´Ù½Ã ÀÔ·ÂÇØÁÖ¼¼¿ä");
         }
      }      
      while(true) {
         System.out.println("¼öÇĞÁ¡¼ö(0~100) ÀÔ·Â >>");
         mat = scan.nextInt();
         
         if(mat >= 0 && mat <= 100 ) {
            break;
         }else {
            System.out.println("´Ù½Ã ÀÔ·ÂÇØÁÖ¼¼¿ä");
         }
      }      
      
   

      
      
      //»ğÀÔÇÒ °´Ã¼¸ğµ¨ »ı¼º
      StudentScoreModel studentScoreModel = new StudentScoreModel(id,name,age,gender,phoneNumber,kor,eng,mat);
      studentScoreModel.calSum();
      studentScoreModel.calAvr();
      studentScoreModel.calGrade();
      int returnValue = DBController.StudentScoreInsert(studentScoreModel);
      if(returnValue != 0) {
         System.out.println(studentScoreModel.getName() + "´Ô »ğÀÔ ¼º°øÇÏ¿´½À´Ï´Ù.");
      }else {
         System.out.println(studentScoreModel.getName() + "´Ô »ğÀÔ ½ÇÆĞÇÏ¿´½À´Ï´Ù.");
      }
   }
   
   //ÆĞÅÏ¸ÅÄ¡ Ã³¸®ÇÔ¼ö
   private static boolean patternCheck(String patternData, int patternType) {
      String filter = null;
      
      switch(patternType) {
      case NAME : filter = "^[°¡-ÆR]{2,5}$"; break;
      case PHONE : filter = "\\d{3}-\\d{4}-\\d{4}"; break;
      }
      Pattern pattern = Pattern.compile(filter);
      Matcher matcher = pattern.matcher(patternData);
      return matcher.matches();
   }

   //°Ë»ö (ÀÌ¸§,¼ºº°,ÀüÈ­¹øÈ£ °Ë»ö)
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
      //ÀÌ¸§
      case NAME_NUM :
         while(true) {
            System.out.print("ÀÌ¸§(È«±æµ¿) ÀÔ·Â>> ");
            name = scan.nextLine();
            if(patternCheck(name, NAME)) {
               break;
            }else {
               System.out.println("´Ù½Ã ÀÔ·ÂÇØÁÖ¼¼¿ä.");
            }
         }
         searchData = name;
         number = NAME_NUM;
         break;
      //¼ºº°
      case GENDER_NUM : 
         while(true) {
            System.out.print("¼ºº°(³²,¿©) ÀÔ·Â>>");
            gender = scan.nextLine();
            if(gender.equals("³²") || gender.equals("¿©")) {
               break;
            }else {
               System.out.println("´Ù½Ã ÀÔ·ÂÇØÁÖ¼¼¿ä.");
            }
         }
         searchData = gender;
         number = GENDER_NUM;
         break;
         //ÀüÈ­¹øÈ£
      case PHONE_NUM : 
         while(true) {
         System.out.println("°Ë»öÇÒ ÀüÈ­¹øÈ£ ÀÔ·Â>>");
         phoneNumber = scan.nextLine();
         if(patternCheck(phoneNumber, PHONE)) {
            break;
         }else {
            System.out.println("´Ù½Ã ÀÔ·ÂÇØÁÖ¼¼¿ä.");
            }

         }
         searchData = phoneNumber;
         number = PHONE_NUM;
         break;
      case EXIT : 
         System.out.println("°Ë»öÀÌ Ãë¼ÒµÇ¾ú½À´Ï´Ù.");
         flag = true;
         break;            
      }//end of switch
      if(flag) {
         return;
      }
      list = DBController.StudentScoreSearch(searchData,number);
      if(list.size() <= 0) {
         System.out.println(searchData + "Ã£Áö ¸øÇß½À´Ï´Ù.");
         return;
      }
      for(StudentScoreModel data : list) {
         System.out.println(data);
      }
   }


   //°Ë»öÇÒ ³»¿ëÀ» ¼±ÅÃ¿äÃ»(ÀÌ¸§,ÀüÈ­¹øÈ£)
   private static int searchMenu() {
      boolean flag = false;
      int no = 0;
      while(!flag) {
         System.out.println("****************************");
         System.out.println("1.ÀÌ¸§ 2.¼ºº° 3.ÀüÈ­¹øÈ£ 4.Á¾·á");
         System.out.println("****************************");
         System.out.print("¹øÈ£ÀÔ·Â >> ");
      try {
         no = Integer.parseInt(scan.nextLine());
      }catch(InputMismatchException e) {
         System.out.println("°æ°í: ¼ıÀÚ ÀÔ·ÂÇØÁÖ¼¼¿ä!");
         continue;
      }catch(Exception e) {
         System.out.println("°æ°í: ¼ıÀÚ ÀÔ·ÂÇØÁÖ¼¼¿ä!");
         continue;
         }
      if(no >=1 && no <= 4) {
         flag = true;
      }else{
         System.out.println("°æ°í: 1~4±îÁö ¼ıÀÚ¸¦ ÀÔ·ÂÇØÁÖ¼¼¿ä!");
         }
      }// end of while
      return no;
   }

   //»èÁ¦(ÀüÈ­¹øÈ£)
   private static void StudentScoreDelete() {
      final int PHONE_NUM = 1;
      String phoneNumber = null;
      String deleteData = null;
      int number = 0;
      int resultNumber = 0;
      
      //ÀüÈ­¹øÈ£
      while(true) {
      System.out.println("°Ë»öÇÒ ÀüÈ­¹øÈ£ ÀÔ·Â>>");
      phoneNumber = scan.nextLine();
      if(patternCheck(phoneNumber, PHONE)) {
         break;
      }else {
         System.out.println("´Ù½Ã ÀÔ·ÂÇØÁÖ¼¼¿ä.");
         }

      }   
      deleteData = phoneNumber;
      number = PHONE_NUM;
      resultNumber = DBController.StudentScoreDelete(deleteData, number);
      
      if(resultNumber != 0) {
         System.out.println(phoneNumber+"¹øÈ£ »èÁ¦¿Ï·áÇß½À´Ï´Ù.");
      }else {
         System.out.println(phoneNumber+"¹øÈ£ »èÁ¦½ÇÆĞÇß½À´Ï´Ù.");
      }
      return;
   }

   // ¼öÁ¤(ÀüÈ­¹øÈ£ºÎ)³»¿ë¼öÁ¤(id)
   private static void StudentScoreUpdate() {
      //1. ÀüÈ­¹øÈ£ºÎ °Ë»ö
      final int PHONE_NUM = 3;
      List<StudentScoreModel> list = new ArrayList<StudentScoreModel>();
      //°Ë»öÇÒ ³»¿ë ¼±ÅÃ 
      String phoneNumber = null;
      String searchData = null;
      int number = 0;
      boolean flag= false;
      int resultNumber= 0;
      int id = 0;
      
      //ÀüÈ­¹øÈ£
      while(true) {
         System.out.println("°Ë»öÇÒ ÀüÈ­¹øÈ£ ÀÔ·Â >>");
         phoneNumber = scan.nextLine();
         if(patternCheck(phoneNumber, PHONE)) {
             break;
         } else {            
             System.out.println("´Ù½Ã ÀÔ·ÂÇØÁÖ¼¼¿ä");
         }
      }
      searchData = phoneNumber;
      number = PHONE_NUM;
      
      list = DBController.StudentScoreSearch(searchData, number);
      
      if(list.size() <= 0) {
         System.out.println(searchData + "Ã£Áö ¸øÇß½À´Ï´Ù.");
         return;
      }
      
      StudentScoreModel data_buffer = null;
      for(StudentScoreModel data:list) {
         System.out.println(data);
         data_buffer = data;
      }
      
      //2. ÀüÈ­¹øÈ£°¡ ÀÖÀ¸¸é ¼öÁ¤ÇÒ ÀÛ¾÷À» ¹Ş¾Æ¼­ ¼öÁ¤¿äÃ»
      while(true) {
         System.out.println("["+data_buffer.getId() +"] \n¼öÁ¤ÇÒ ÇĞ¹øÀÔ·Â >>");
         id = scan.nextInt();
         
         if(id>=1 && id<=100) {
            break;
         }else {
            System.out.println("´Ù½Ã ÀÔ·Â¹Ù¶ø´Ï´Ù.");
         }
      }
      //3. °á°ú°ª È®ÀÎ
      resultNumber = DBController.StudentScoreUpdate(phoneNumber, id);
      
      if(resultNumber != 0) {
         System.out.println(phoneNumber +"¹øÈ£ ¼öÁ¤¿Ï·áÇß½À´Ï´Ù.");
      }else {
      System.out.println(phoneNumber +"¹øÈ£ ¼öÁ¤¿Ï·áÇß½À´Ï´Ù.");
      }
      return;
   }
   
   //Ãâ·Â
   private static void StudentScorePrint() {
      List<StudentScoreModel> list = new ArrayList<StudentScoreModel>();
      list = DBController.studentScoreSelect();
      if(list.size() <= 0 ) {
         System.out.println("Ãâ·ÂÇÒ ³»¿ëÀÌ ¾ø½À´Ï´Ù.");
         return;
      }
      
      for(StudentScoreModel data:list) {
         System.out.println(data);
      }
   }

   //Á¤·Ä
   private static void StudentScoreSort() {
      List<StudentScoreModel> list = new ArrayList<StudentScoreModel>();
      int no = 0;
      boolean flag = false;
      
      //1. Á¤·Ä¹æ½Ä (ÀÌ¸§: ¿À¸§Â÷¼ø, ³»¸²Â÷¼ø)
      while(!flag) {
         System.out.println("1.ÀÌ¸§ ¿À¸§Â÷¼ø 2. ÀÌ¸§ ³»¸²Â÷¼ø");
         System.out.println("Á¤·Ä¹æ½Ä ¼±ÅÃ>>");
         
         try {
            no = Integer.parseInt(scan.nextLine());
         }catch(InputMismatchException e){
            System.out.println("°æ°í : ¼ıÀÚ ÀÔ·ÂÇØÁÖ¼¼¿ä!");
            continue;
         }catch(Exception e) {
            System.out.println("°æ°í : ¼ıÀÚ ÀÔ·ÂÇØÁÖ¼¼¿ä!");
            continue;
         }
         
         if(no >= 1 && no <= 2 ) {
            flag = true;
         }else {
            System.out.println("°æ°í : 1~2±îÁö ¼ıÀÚ¸¦ ÀÔ·ÂÇØÁÖ¼¼¿ä!");
         }
      } // end of while
      
      //2. Ãâ·Â¹®À» °¡Á®¿Â´Ù.
      
      list = DBController.StudentScoreSort(no);
      
      if(list.size() <= 0 ) {
         System.out.println("Á¤·ÄÇÒ ³»¿ëÀÌ ¾ø½À´Ï´Ù.");
         return;
      }
      
      for(StudentScoreModel data:list) {
         System.out.println(data);
      }
      return;
   }

   //¸Ş´º¼±ÅÃ
   private static int selectMenu() {
      boolean flag = false;
      int no = 0;
      
      while(!flag) {
         System.out.println("******************************************");
         System.out.println("1.ÀÔ·Â 2.°Ë»ö 3.»èÁ¦ 4.¼öÁ¤ 5.Ãâ·Â 6.Á¤·Ä 7.Á¾·á ");
         System.out.println("******************************************");
         System.out.print("¹øÈ£ÀÔ·Â >> ");   
         
         try {
            no = Integer.parseInt(scan.nextLine());
         }catch(InputMismatchException e){
            System.out.println("°æ°í : ¼ıÀÚ ÀÔ·ÂÇØÁÖ¼¼¿ä!");
            continue;
         }catch(Exception e) {
            System.out.println("°æ°í : ¼ıÀÚ ÀÔ·ÂÇØÁÖ¼¼¿ä!");
            continue;
         }
         if(no>=1 && no<=7) {
            flag = true;
         }else {
            System.out.println("°æ°í: 1~7 »çÀÌÀÇ ¼ıÀÚ¸¦ ÀÔ·ÂÇØÁÖ¼¼¿ä!");
         }
      }//end of while
      return no;
   }

}