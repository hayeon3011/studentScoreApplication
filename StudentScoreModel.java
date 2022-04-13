package com.mire.sixclass;

import java.io.Serializable;
import java.util.Objects;

public class StudentScoreModel implements Comparable<Object>,Serializable{
   public static final int SUBJECT_COUNT = 3;

   //멤버변수 (id,name,age,gender,phoneNumber,kor,eng,mat,sum,avr,grade)
   private int id;
   private String name;
   private int age;
   private String gender;
   private String phoneNumber;
   private int kor;
   private int eng;
   private int mat;
   private int sum;
   private double avr;
   private String grade;
   
   //생성자 
   public StudentScoreModel(int id, String name, int age, String gender, String phoneNumber, int kor, int eng, int mat) {
      super();
      this.id = id;
      this.name = name;
      this.age = age;
      this.gender = gender;
      this.phoneNumber = phoneNumber;
      this.kor = kor;
      this.eng = eng;
      this.mat = mat;
      this.sum = 0;
      this.avr = 0.0;
      this.grade = null;
   }

   //오버라이딩 - hashCode :전화번호 , equals : 전화번호 , compareTo : 이름 , toString
   @Override
   public int hashCode() {
      return Objects.hash(phoneNumber);
   }

   @Override
   public boolean equals(Object obj) {
      if(!(obj instanceof StudentScoreModel)) {
         throw new IllegalArgumentException("no exist StudentScoreModel class");
      }
      StudentScoreModel studentScoreModel = (StudentScoreModel)obj;
      return this.phoneNumber.equals(studentScoreModel.phoneNumber);
   }

   @Override
   public int compareTo(Object obj) {
      if(!(obj instanceof StudentScoreModel)) {
         throw new IllegalArgumentException("no exist StudentScoreModel class");
      }
      StudentScoreModel studentScoreModel = (StudentScoreModel)obj;
      return this.name.compareTo(studentScoreModel.name);
   }

   // toString
   @Override
   public String toString() {
      return  id + "번\t" + name + "\t" + age + "세\t" + gender
            + "\t" + phoneNumber + "\t국어: " + kor + "점\t영어: " + eng + "점\t수학: " + mat + "점\t 총점: " + sum
            + "점\t 평균: " + avr + "점\t 등급: " + grade;
   }

   public void calSum() {
      this.sum = this.kor + this.eng + this.mat;
   }
   
   public void calAvr() {
      this.avr = this.sum / (double)SUBJECT_COUNT; 
   }
   
   public void calGrade() {
      if(this.avr>= 90.0) {
         this.grade = "A";
      }else if(this.avr >= 80.0) {
         this.grade = "B";
      }else if(this.avr >= 70.0) {
         this.grade = "C";
      }else if(this.avr >= 60.0) {
         this.grade = "D";
      }else {
         this.grade = "F";
      }
      
   }

   //getter,setter
   public int getId() {
      return id;
   }


   public void setId(int id) {
      this.id = id;
   }


   public String getName() {
      return name;
   }


   public void setName(String name) {
      this.name = name;
   }


   public int getAge() {
      return age;
   }


   public void setAge(int age) {
      this.age = age;
   }


   public String getGender() {
      return gender;
   }


   public void setGender(String gender) {
      this.gender = gender;
   }


   public String getPhoneNumber() {
      return phoneNumber;
   }


   public void setPhoneNumber(String phoneNumber) {
      this.phoneNumber = phoneNumber;
   }


   public int getKor() {
      return kor;
   }


   public void setKor(int kor) {
      this.kor = kor;
   }


   public int getEng() {
      return eng;
   }


   public void setEng(int eng) {
      this.eng = eng;
   }


   public int getMat() {
      return mat;
   }


   public void setMat(int mat) {
      this.mat = mat;
   }


   public int getSum() {
      return sum;
   }


   public void setSum(int sum) {
      this.sum = sum;
   }


   public double getAvr() {
      return avr;
   }


   public void setAvr(double avr) {
      this.avr = avr;
   }


   public String getGrade() {
      return grade;
   }


   public void setGrade(String grade) {
      this.grade = grade;
   }

}