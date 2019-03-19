package ca.ryerson.ee.coe848.dal;

import ca.ryerson.ee.coe848.cmn.User;
import ca.ryerson.ee.coe848.cmn.Message;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class MessageManager {

    public void add(Message newMessage) throws ClassNotFoundException, Exception {
        Connection db = SQLiteDatabaseManager.getConnection();
        ResultSet rs = null;
        PreparedStatement ps = null;
        try {
        
            //sql insert command
            String sqlMessage = "INSERT INTO Message(Title) VALUES(?)";

            //to add readability to code and avoid sql injection in command we use "prepareStatement()"
            ps = db.prepareStatement(sqlMessage, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, newMessage.getTitle());

            //execute sql insert command
            int rowsAffected = ps.executeUpdate();

            //get Message id
            rs = ps.getGeneratedKeys();
            int MessageId = 0;
            if (rs.next()) {
                MessageId = rs.getInt(1);
            }

            ArrayList<Integer> userIds = new ArrayList<>();
            for (User d : newMessage.getUsers()) {
                //sql insert command for each User
                String sqlUser = "INSERT INTO User(Name) VALUES(?)";

                ps = db.prepareStatement(sqlUser, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, d.getName());

                rowsAffected = ps.executeUpdate();

                //get User id
                rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    userIds.add(rs.getInt(1));
                }
            }

            //we could move this part inside User loop above
            for (Integer UserId : userIds) {
                String sqlMessageUser = "INSERT INTO MessageUser(MessageId, UserId) VALUES(?, ?)";
                ps = db.prepareStatement(sqlMessageUser);
                ps.setInt(1, MessageId);
                ps.setInt(2, UserId);
                rowsAffected = ps.executeUpdate();
            }

            //commit work
            db.commit();
            
            newMessage.setId(MessageId);
            for(int i = 0; i < userIds.size(); ++i) {
                newMessage.getUsers().get(i).setId(userIds.get(i));
            }

        } catch (SQLException e) {
            if (db != null) {
                db.rollback();
            }
            throw e;
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (db != null) {
                    db.close();
                }
            } catch (SQLException e) {
                throw e;
            }
        }
    }

    public Message getById(Integer id) throws SQLException, ClassNotFoundException, Exception {
        Message m = null;
        Connection db = SQLiteDatabaseManager.getConnection();
        ResultSet rs = null;
        PreparedStatement ps = null;
        try {
        
            String sqlMessage = "SELECT Id, Title FROM Message WHERE Id = ?";

            ps = db.prepareStatement(sqlMessage);
            ps.setInt(1, id);

            rs = ps.executeQuery();
            
            //since we expect only one Message, we do not use "while"
            if (rs.next()) {
                m = new Message();
                m.setId(id);
                m.setTitle(rs.getString("Title"));
            }else
                return m;
            
            m.setUsers(new ArrayList<>());
            String sqlUsers = "SELECT Id, Name FROM User WHERE Id IN (SELECT UserId FROM MessageUser WHERE MessageId = ?)";

            ps = db.prepareStatement(sqlUsers);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            //here we expect more than one Users, so we use "while"
            while(rs.next()) {
                m.getUsers().add(new User(rs.getInt("Id"), rs.getString("Name")));
            }
            
            //no need for commit command
            //db.commit();
            return m;
            
            

        } catch (SQLException e) {
            throw e;
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (db != null) {
                    db.close();
                }
            } catch (SQLException e) {
                throw e;
            }
        }
    }

    public void deleteById(Integer id) throws Exception {
        Connection db = SQLiteDatabaseManager.getConnection();
        ResultSet rs = null;
        PreparedStatement ps = null;
        try {
            //be careful when deleting a Message. 
            //first delete the Message's relationship with Users or other entities if any
            String sqlMessageUsers = "DELETE FROM MessageUser WHERE MessageId = ?";
            ps = db.prepareStatement(sqlMessageUsers);
            ps.setInt(1, id);
            int rowsAffected = ps.executeUpdate();
            
            //then delete the Message itself
            String sqlMessage = "DELETE FROM Message WHERE Id = ?";
            ps = db.prepareStatement(sqlMessage);
            ps.setInt(1, id);
            rowsAffected = ps.executeUpdate();
            
            if(rowsAffected < 0)
                throw new Exception("Message not found in database!", null);
            
            db.commit();

        } catch (SQLException e) {
            throw e;
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (db != null) {
                    db.close();
                }
            } catch (SQLException e) {
                throw e;
            }
        }
    }

    public int getMessageCountByDb() throws ClassNotFoundException, Exception {
        int messageCount = 0;
        Connection db = SQLiteDatabaseManager.getConnection();
        ResultSet rs = null;
        PreparedStatement ps = null;
        String sqlMessageCount = "SELECT COUNT(*) AS MessageCount FROM Message";
        try{
            ps = db.prepareStatement(sqlMessageCount);
            rs = ps.executeQuery();
            if(rs.next()){
                messageCount = rs.getInt("MessageCount");
            }
        }catch(SQLException e){
            throw e;
        }
        finally{
            if(rs != null)
                rs.close();
            if(ps != null)
                ps.close();
            if(db != null)
                db.close();
        }
        return messageCount;
    }
    
    public int getMessageCountByApp() throws ClassNotFoundException, Exception {
        Connection db = SQLiteDatabaseManager.getConnection();
        ResultSet rs = null;
        PreparedStatement ps = null;
        ArrayList<Integer> messageIds = new ArrayList<>();
        String sqlMessageCount = "SELECT Id FROM Message";
        try{
            ps = db.prepareStatement(sqlMessageCount);
            rs = ps.executeQuery();            
            while(rs.next()){
                messageIds.add(rs.getInt("Id"));
            }
        }catch(SQLException e){
            throw e;
        }
        finally{
            if(rs != null)
                rs.close();
            if(ps != null)
                ps.close();
            if(db != null)
                db.close();
        }
        return messageIds.size();
    }

}
