import net.proteanit.sql.DbUtils;
import javax.swing.*;
import java.sql.*;
import java.util.Random;


public class Connect {
    // Replace these with your database details
    static final String connectionUrl =
            "jdbc:sqlserver://sql.bsite.net\\MSSQL2016;databaseName=chieccabongne_123;" +
                    "user=chieccabongne_123;password=123123;encrypt=true;trustServerCertificate=true;";

    public static void closeConnect(Connection con) {
        if (con != null) {
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println("Error closing connection");
            }
        }
    }

    public static void showQuery(String sqlQuery, JTable resultTable) {
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs;
        try {
            con = DriverManager.getConnection(connectionUrl);
            stmt = con.prepareStatement(sqlQuery);
            rs = stmt.executeQuery();
            if (!rs.next()) {
                JOptionPane.showMessageDialog(null, "No results found", "Query Result", JOptionPane.WARNING_MESSAGE);
            } else {
                resultTable.setModel(DbUtils.resultSetToTableModel(rs));
                JOptionPane.showMessageDialog(null, "Query executed successfully", "Query Result", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error executing query: " + e.getMessage(), "Query Error", JOptionPane.WARNING_MESSAGE);
        } finally {
            try {
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                System.out.println("Error closing statement: " + e.getMessage());
            }
            closeConnect(con);
        }
    }

    public static boolean addAuthorToArticle(String articleID, String authorName) {
        boolean isUpdated = false;

        String selectAuthorString = "SELECT AuthorID FROM Author WHERE FullName = ?";
        String insertArticleAuthorString = "INSERT INTO Article_Author (ArticleID, AuthorID) VALUES (?, ?)";

        try (Connection con = DriverManager.getConnection(connectionUrl);
             PreparedStatement selectAuthorStmt = con.prepareStatement(selectAuthorString);
             PreparedStatement articleAuthorStmt = con.prepareStatement(insertArticleAuthorString)) {
            con.setAutoCommit(false);

            selectAuthorStmt.setString(1, authorName);
            ResultSet rs = selectAuthorStmt.executeQuery();

            if (rs.next()) {
                String authorID = rs.getString("AuthorID");

                articleAuthorStmt.setString(1, articleID);
                articleAuthorStmt.setString(2, authorID);

                if (articleAuthorStmt.executeUpdate() > 0) {
                    isUpdated = true;
                    con.commit();
                } else {
                    con.rollback();
                }
            } else {
                JOptionPane.showMessageDialog(null, "Author not found", "Error", JOptionPane.ERROR_MESSAGE);
                con.rollback();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isUpdated;
    }


    public static void searchArticle(String keyword, JTable resultTable) {
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs;
        try {
            con = DriverManager.getConnection(connectionUrl);
            String searchQuery = "SELECT a.*, au.* " +
                    "FROM Article a " +
                    "JOIN Article_Author aa ON a.ArticleID = aa.ArticleID " +
                    "JOIN Author au ON aa.AuthorID = au.AuthorID " +
                    "WHERE a.Title LIKE ? OR au.FullName LIKE ?";
            stmt = con.prepareStatement(searchQuery);
            stmt.setString(1, "%" + keyword + "%");
            stmt.setString(2, "%" + keyword + "%");
            rs = stmt.executeQuery();
            if (!rs.next()) {
                JOptionPane.showMessageDialog(null, "No results found", "Search Result", JOptionPane.WARNING_MESSAGE);
            } else {
                resultTable.setModel(DbUtils.resultSetToTableModel(rs));
                JOptionPane.showMessageDialog(null, "Search executed successfully", "Search Result", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error executing search: " + e.getMessage(), "Search Error", JOptionPane.WARNING_MESSAGE);
        } finally {
            try {
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                System.out.println("Error closing statement: " + e.getMessage());
            }
            closeConnect(con);
        }
    }

    public static boolean deleteArticleByID(String articleID) {
        Connection con = null;
        PreparedStatement stmt = null;
        boolean isDeleted = false;
        try {
            con = DriverManager.getConnection(connectionUrl);
            con.setAutoCommit(false);

            String deleteArticleQuery = "DELETE FROM [Articles] WHERE ID = ?";
            stmt = con.prepareStatement(deleteArticleQuery);
            stmt.setString(1, articleID);
            int result = stmt.executeUpdate();
            if (result > 0) {
                isDeleted = true;
            }
            con.commit();
        } catch (SQLException e) {
            try {
                if (con != null) con.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                System.out.println("Error closing statement: " + e.getMessage());
            }
            closeConnect(con);
        }
        return isDeleted;
    }



    public static void showRepository(JTable resultTable) {
        Connection con = null;
        Statement stmt = null;
        ResultSet rs;
        try {
            con = DriverManager.getConnection(connectionUrl);
            String query = "SELECT a.Title, a.PublicationDate, au.FullName " +
                    "FROM Article a " +
                    "JOIN Article_Author aa ON a.ArticleID = aa.ArticleID " +
                    "JOIN Author au ON aa.AuthorID = au.AuthorID;";
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);
            resultTable.setModel(DbUtils.resultSetToTableModel(rs));
            JOptionPane.showMessageDialog(null, "Repository loaded successfully", "Repository", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error loading repository: " + e.getMessage(), "Repository Error", JOptionPane.WARNING_MESSAGE);
        } finally {
            try {
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                System.out.println("Error closing statement: " + e.getMessage());
            }
            closeConnect(con);
        }
    }
}
