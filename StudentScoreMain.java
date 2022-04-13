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
         //�Է�, �˻�,����,����,���,����,����
         case INSERT : StudentScoreInsert(); break;
         case SEARCH : StudentScoreSearch(); break;
         case DELETE : StudentScoreDelete(); break;
         case UPDATE : StudentScoreUpdate(); break;
         case PRINT : StudentScorePrint(); break;
         case SORT : StudentScoreSort(); break;
         case EXIT : flag = true; 
            System.out.println("�ý��������մϴ�."); break;
         default: System.out.println("�ٽ��Է¿��!"); break;
         }//end of switch   
      }//end of while
   
   }
   

   //�Է� (id,name,age,gender,phoneNumber,kor,eng,mat,sum,avr,grade)
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
      
      //�й�
      while(true) {
         System.out.println("�й�(1~100) �Է� >>");
         id = scan.nextInt();
         str = scan.nextLine();
         if(id >= 1 && id <= 100 ) {
            break;
         }else {
            System.out.println("�ٽ� �Է����ּ���");
         }
      }
      
      //�̸�
      while(true) {
         System.out.println("�̸�(ȫ�浿) �Է� >>");
         name = scan.nextLine();
         
         if(patternCheck(name, NAME)) {
             break;
         } else {            
             System.out.println("�ٽ� �Է����ּ���");
         }
      }
      
      //����
      while(true) {
         System.out.println("����(20~60) �Է� >>");
         age = scan.nextInt();
         str = scan.nextLine();
         if(age >= 20 && age <= 60 ) {
            break;
         }else {
            System.out.println("�ٽ� �Է����ּ���");
         }
      }   
      
      //����
      while(true) {
         System.out.println("����(��,��) �Է� >>");
         gender = scan.nextLine();
         
         if(gender.equals("��") || gender.equals("��")) {
            break;
         }else {
            System.out.println("�ٽ� �Է����ּ���");
         }
      }   
      
      //��ȭ��ȣ
      while(true) {
         System.out.println("010-0000-0000 �Է� >>");
         phoneNumber = scan.nextLine();

         if(patternCheck(phoneNumber, PHONE)) {
             break;
         } else {            
             System.out.println("�ٽ� �Է����ּ���");
         }
      }   
      
      //����
      while(true) {
         System.out.println("��������(0~100) �Է� >>");
         kor = scan.nextInt();
         
         if(kor >= 0 && kor <= 100 ) {
            break;
         }else {
            System.out.println("�ٽ� �Է����ּ���");
         }
      }
      while(true) {
         System.out.println("��������(0~100) �Է� >>");
         eng = scan.nextInt();
         
         if(eng >= 0 && eng <= 100 ) {
            break;
         }else {
            System.out.println("�ٽ� �Է����ּ���");
         }
      }      
      while(true) {
         System.out.println("��������(0~100) �Է� >>");
         mat = scan.nextInt();
         
         if(mat >= 0 && mat <= 100 ) {
            break;
         }else {
            System.out.println("�ٽ� �Է����ּ���");
         }
      }      
      
   

      
      
      //������ ��ü�� ����
      StudentScoreModel studentScoreModel = new StudentScoreModel(id,name,age,gender,phoneNumber,kor,eng,mat);
      studentScoreModel.calSum();
      studentScoreModel.calAvr();
      studentScoreModel.calGrade();
      int returnValue = DBController.StudentScoreInsert(studentScoreModel);
      if(returnValue != 0) {
         System.out.println(studentScoreModel.getName() + "�� ���� �����Ͽ����ϴ�.");
      }else {
         System.out.println(studentScoreModel.getName() + "�� ���� �����Ͽ����ϴ�.");
      }
   }
   
   //���ϸ�ġ ó���Լ�
   private static boolean patternCheck(String patternData, int patternType) {
      String filter = null;
      
      switch(patternType) {
      case NAME : filter = "^[��-�R]{2,5}$"; break;
      case PHONE : filter = "\\d{3}-\\d{4}-\\d{4}"; break;
      }
      Pattern pattern = Pattern.compile(filter);
      Matcher matcher = pattern.matcher(patternData);
      return matcher.matches();
   }

   //�˻� (�̸�,����,��ȭ��ȣ �˻�)
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
      //�̸�
      case NAME_NUM :
         while(true) {
            System.out.print("�̸�(ȫ�浿) �Է�>> ");
            name = scan.nextLine();
            if(patternCheck(name, NAME)) {
               break;
            }else {
               System.out.println("�ٽ� �Է����ּ���.");
            }
         }
         searchData = name;
         number = NAME_NUM;
         break;
      //����
      case GENDER_NUM : 
         while(true) {
            System.out.print("����(��,��) �Է�>>");
            gender = scan.nextLine();
            if(gender.equals("��") || gender.equals("��")) {
               break;
            }else {
               System.out.println("�ٽ� �Է����ּ���.");
            }
         }
         searchData = gender;
         number = GENDER_NUM;
         break;
         //��ȭ��ȣ
      case PHONE_NUM : 
         while(true) {
         System.out.println("�˻��� ��ȭ��ȣ �Է�>>");
         phoneNumber = scan.nextLine();
         if(patternCheck(phoneNumber, PHONE)) {
            break;
         }else {
            System.out.println("�ٽ� �Է����ּ���.");
            }

         }
         searchData = phoneNumber;
         number = PHONE_NUM;
         break;
      case EXIT : 
         System.out.println("�˻��� ��ҵǾ����ϴ�.");
         flag = true;
         break;            
      }//end of switch
      if(flag) {
         return;
      }
      list = DBController.StudentScoreSearch(searchData,number);
      if(list.size() <= 0) {
         System.out.println(searchData + "ã�� ���߽��ϴ�.");
         return;
      }
      for(StudentScoreModel data : list) {
         System.out.println(data);
      }
   }


   //�˻��� ������ ���ÿ�û(�̸�,��ȭ��ȣ)
   private static int searchMenu() {
      boolean flag = false;
      int no = 0;
      while(!flag) {
         System.out.println("****************************");
         System.out.println("1.�̸� 2.���� 3.��ȭ��ȣ 4.����");
         System.out.println("****************************");
         System.out.print("��ȣ�Է� >> ");
      try {
         no = Integer.parseInt(scan.nextLine());
      }catch(InputMismatchException e) {
         System.out.println("���: ���� �Է����ּ���!");
         continue;
      }catch(Exception e) {
         System.out.println("���: ���� �Է����ּ���!");
         continue;
         }
      if(no >=1 && no <= 4) {
         flag = true;
      }else{
         System.out.println("���: 1~4���� ���ڸ� �Է����ּ���!");
         }
      }// end of while
      return no;
   }

   //����(��ȭ��ȣ)
   private static void StudentScoreDelete() {
      final int PHONE_NUM = 1;
      String phoneNumber = null;
      String deleteData = null;
      int number = 0;
      int resultNumber = 0;
      
      //��ȭ��ȣ
      while(true) {
      System.out.println("�˻��� ��ȭ��ȣ �Է�>>");
      phoneNumber = scan.nextLine();
      if(patternCheck(phoneNumber, PHONE)) {
         break;
      }else {
         System.out.println("�ٽ� �Է����ּ���.");
         }

      }   
      deleteData = phoneNumber;
      number = PHONE_NUM;
      resultNumber = DBController.StudentScoreDelete(deleteData, number);
      
      if(resultNumber != 0) {
         System.out.println(phoneNumber+"��ȣ �����Ϸ��߽��ϴ�.");
      }else {
         System.out.println(phoneNumber+"��ȣ ���������߽��ϴ�.");
      }
      return;
   }

   // ����(��ȭ��ȣ��)�������(id)
   private static void StudentScoreUpdate() {
      //1. ��ȭ��ȣ�� �˻�
      final int PHONE_NUM = 3;
      List<StudentScoreModel> list = new ArrayList<StudentScoreModel>();
      //�˻��� ���� ���� 
      String phoneNumber = null;
      String searchData = null;
      int number = 0;
      boolean flag= false;
      int resultNumber= 0;
      int id = 0;
      
      //��ȭ��ȣ
      while(true) {
         System.out.println("�˻��� ��ȭ��ȣ �Է� >>");
         phoneNumber = scan.nextLine();
         if(patternCheck(phoneNumber, PHONE)) {
             break;
         } else {            
             System.out.println("�ٽ� �Է����ּ���");
         }
      }
      searchData = phoneNumber;
      number = PHONE_NUM;
      
      list = DBController.StudentScoreSearch(searchData, number);
      
      if(list.size() <= 0) {
         System.out.println(searchData + "ã�� ���߽��ϴ�.");
         return;
      }
      
      StudentScoreModel data_buffer = null;
      for(StudentScoreModel data:list) {
         System.out.println(data);
         data_buffer = data;
      }
      
      //2. ��ȭ��ȣ�� ������ ������ �۾��� �޾Ƽ� ������û
      while(true) {
         System.out.println("["+data_buffer.getId() +"] \n������ �й��Է� >>");
         id = scan.nextInt();
         
         if(id>=1 && id<=100) {
            break;
         }else {
            System.out.println("�ٽ� �Է¹ٶ��ϴ�.");
         }
      }
      //3. ����� Ȯ��
      resultNumber = DBController.StudentScoreUpdate(phoneNumber, id);
      
      if(resultNumber != 0) {
         System.out.println(phoneNumber +"��ȣ �����Ϸ��߽��ϴ�.");
      }else {
      System.out.println(phoneNumber +"��ȣ �����Ϸ��߽��ϴ�.");
      }
      return;
   }
   
   //���
   private static void StudentScorePrint() {
      List<StudentScoreModel> list = new ArrayList<StudentScoreModel>();
      list = DBController.studentScoreSelect();
      if(list.size() <= 0 ) {
         System.out.println("����� ������ �����ϴ�.");
         return;
      }
      
      for(StudentScoreModel data:list) {
         System.out.println(data);
      }
   }

   //����
   private static void StudentScoreSort() {
      List<StudentScoreModel> list = new ArrayList<StudentScoreModel>();
      int no = 0;
      boolean flag = false;
      
      //1. ���Ĺ�� (�̸�: ��������, ��������)
      while(!flag) {
         System.out.println("1.�̸� �������� 2. �̸� ��������");
         System.out.println("���Ĺ�� ����>>");
         
         try {
            no = Integer.parseInt(scan.nextLine());
         }catch(InputMismatchException e){
            System.out.println("��� : ���� �Է����ּ���!");
            continue;
         }catch(Exception e) {
            System.out.println("��� : ���� �Է����ּ���!");
            continue;
         }
         
         if(no >= 1 && no <= 2 ) {
            flag = true;
         }else {
            System.out.println("��� : 1~2���� ���ڸ� �Է����ּ���!");
         }
      } // end of while
      
      //2. ��¹��� �����´�.
      
      list = DBController.StudentScoreSort(no);
      
      if(list.size() <= 0 ) {
         System.out.println("������ ������ �����ϴ�.");
         return;
      }
      
      for(StudentScoreModel data:list) {
         System.out.println(data);
      }
      return;
   }

   //�޴�����
   private static int selectMenu() {
      boolean flag = false;
      int no = 0;
      
      while(!flag) {
         System.out.println("******************************************");
         System.out.println("1.�Է� 2.�˻� 3.���� 4.���� 5.��� 6.���� 7.���� ");
         System.out.println("******************************************");
         System.out.print("��ȣ�Է� >> ");   
         
         try {
            no = Integer.parseInt(scan.nextLine());
         }catch(InputMismatchException e){
            System.out.println("��� : ���� �Է����ּ���!");
            continue;
         }catch(Exception e) {
            System.out.println("��� : ���� �Է����ּ���!");
            continue;
         }
         if(no>=1 && no<=7) {
            flag = true;
         }else {
            System.out.println("���: 1~7 ������ ���ڸ� �Է����ּ���!");
         }
      }//end of while
      return no;
   }

}