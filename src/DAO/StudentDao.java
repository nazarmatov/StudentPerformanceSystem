package DAO;

import models.Student;
import util.DBUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDao {
    
    public void addStudent(Student student){
        String sql = "INSERT INTO students(name,group_name) VALUES(?,?)";
        try (
            Connection con = DBUtil.getConnection();
            PreparedStatement stmt = con.prepareStatement(sql)
        ){

            stmt.setString(1, student.getName());
            stmt.setString(2, student.getGroup());

            stmt.executeUpdate();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Student> getAllStudents(){
        List<Student> students = new ArrayList<>();
        String sql = "SELECT * FROM students";
        try (
            Connection con = DBUtil.getConnection();
            PreparedStatement stmt = con.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()
        ){
            
            while (rs.next()) {
                Student s = new Student(rs.getString("name"),rs.getString("group_name"));
                s.setId(rs.getLong("id"));
                students.add(s);
            }

        } catch (Exception e) {
            e.fillInStackTrace();
        }
        return students;
    }

    public Student getStudentByID(int id){
        String sql = "SELECT * FROM students where id = ?";
        try (
            Connection con = DBUtil.getConnection();
            PreparedStatement stmt = con.prepareStatement(sql);
        ){

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Student student = new Student(
                    rs.getString("name"),
                    rs.getString("group_name")
                );
                student.setId(rs.getLong("id"));
                return student;
            }
            
        } catch (Exception e) {
            e.getStackTrace();
        }
        return null;
    }

    public void deleteStudent(int id){
        String sql = "DELETE FROM students WHERE id = ?";
        try (
            Connection con = DBUtil.getConnection();
            PreparedStatement stmt = con.prepareStatement(sql);
        ){

            stmt.setInt(1, id);
            stmt.executeUpdate();
            
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    public void updateStudent(Student student) {
        String sql = "UPDATE students SET name = ?, group_name = ? WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, student.getName());
            stmt.setString(2, student.getGroup());
            stmt.setLong(3, student.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}