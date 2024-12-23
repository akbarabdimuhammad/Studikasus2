package src;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MembershipDAO {
    private static final Logger logger = Logger.getLogger(MembershipDAO.class.getName());
    private final Connection connection;

    public MembershipDAO() {
        this.connection = DatabaseConnection.connect();
        if (this.connection == null) {
            logger.log(Level.SEVERE, "Failed to establish database connection");
        }
    }

    public List<Membership> getAllMemberships() {
        List<Membership> memberships = new ArrayList<>();
        String query = "SELECT * FROM Membership";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                memberships.add(new Membership(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getString("membership_type")
                ));
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error fetching all memberships", e);
        }
        return memberships;
    }

    public boolean addMembership(Membership membership) {
        String query = "INSERT INTO Membership (name, email, phone, membership_type) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, membership.getName());
            pstmt.setString(2, membership.getEmail());
            pstmt.setString(3, membership.getPhone());
            pstmt.setString(4, membership.getMembershipType());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error adding membership", e);
            return false;
        }
    }

    public boolean updateMembership(Membership membership) {
        String query = "UPDATE Membership SET name = ?, email = ?, phone = ?, membership_type = ? WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, membership.getName());
            pstmt.setString(2, membership.getEmail());
            pstmt.setString(3, membership.getPhone());
            pstmt.setString(4, membership.getMembershipType());
            pstmt.setInt(5, membership.getId());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error updating membership", e);
            return false;
        }
    }

    public boolean deleteMembership(int id) {
        String query = "DELETE FROM Membership WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error deleting membership", e);
            return false;
        }
    }
}
