package DAO;

import models.Lesson;
import util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LessonDao {
    
    public void addLesson(Lesson lesson){
        String sql = "INSERT INTO lessons(title) VALUES(?)";
        try (
            Connection con = DBUtil.getConnection();
            PreparedStatement stmt = con.prepareStatement(sql)
        ){

            stmt.setString(1, lesson.getTitle());
            stmt.executeUpdate();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Lesson> getAllLessons(){
        String sql = "SELECT * FROM lessons";
        List<Lesson> lessons = new ArrayList<>();
        try (
            Connection con = DBUtil.getConnection();
            PreparedStatement stmt = con.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()
        ){

            while (rs.next()) {
                Lesson lesson = new Lesson();
                lesson.setId(rs.getLong("id"));
                lesson.setTitle(rs.getString("title"));
                lessons.add(lesson);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lessons;
    }

    public Lesson getLessonById(int id){
        String sql = "SELECT * FROM lessons WHERE id = ?";
        try (
            Connection con = DBUtil.getConnection();
            PreparedStatement stmt = con.prepareStatement(sql)
        ){

            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Lesson lesson = new Lesson(rs.getString("title"));
                lesson.setId(rs.getLong("id"));
                return lesson;
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public void deleteLesson(long id){
        String sql = "DELETE FROM lessons WHERE id = ?";
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
}
