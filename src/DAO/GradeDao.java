package DAO;

import models.Grade;
import util.DBUtil;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;

public class GradeDao {
    public void addGrade(Grade grade){
        String sql = "INSERT INTO grades(student_id,lesson_id,grade_value) VALUES(?,?,?)";
        try (
            Connection con = DBUtil.getConnection();
            PreparedStatement stmt = con.prepareStatement(sql);
        ){
            
            stmt.setLong(1, grade.getStudentId());
            stmt.setLong(2, grade.getLessonId());
            stmt.setInt(3, grade.getValue());
            stmt.executeUpdate();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Grade> getAllGrades(){
        String sql = "SELECT * FROM grades";
        List<Grade> grades = new ArrayList<>();
        try (
            Connection con = DBUtil.getConnection();
            PreparedStatement stmt = con.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()
        ){

            while (rs.next()) {
                Grade grade = new Grade(
                    rs.getLong("student_id"),
                    rs.getLong("lesson_id"),
                    rs.getInt("grade_value")
                );
                grade.setId(rs.getLong("id"));
                grades.add(grade);
            }
            
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return grades;
    }

    public Grade getGradeById(long id){
        String sql = "SELECT * FROM grades WHERE id = ?";
        try (
            Connection con = DBUtil.getConnection();
            PreparedStatement stmt = con.prepareStatement(sql)
        ){

            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Grade grade = new Grade();
                grade.setId(rs.getLong("id"));
                grade.setStudentId(rs.getLong("student_id"));
                grade.setLessonId(rs.getLong("lesson_id"));
                grade.setValue(rs.getInt("grade_value"));
                return grade;
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void deleteGrade(long id){
        String sql = "DELETE FROM grades WHERE id = ?";
        try (
            Connection con = DBUtil.getConnection();
            PreparedStatement stmt = con.prepareStatement(sql)
        ){
            
            stmt.setLong(1, id);
            stmt.executeUpdate();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Grade> getGradesByLessonId(long Id) {
        String sql = "SELECT * FROM grades WHERE lesson_id = ?";
        List<Grade> grades = new ArrayList<>();
        try (
            Connection con = DBUtil.getConnection();
            PreparedStatement stmt = con.prepareStatement(sql)
        ) {
            stmt.setLong(1, Id);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Grade grade = new Grade(
                    rs.getLong("student_id"),
                    rs.getLong("lesson_id"),
                    rs.getInt("grade_value")
                );
                grade.setId(rs.getLong("id"));
                grades.add(grade);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return grades;
    }
    
}
