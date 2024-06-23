import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Project {
    private JTextField txtArticle;
    private JTextField txtAuthor;
    private JTextArea txtContent;  // Assuming you have added a JTextArea for the content
    private JButton btnSearch;
    private JLabel lblTitle;
    private JLabel lblAuthor;
    private JButton btnAddArticle;
    private JButton deleteButton;
    private JButton btnRepository;
    private JPanel panel;
    private JTable tblResults;
    private JTextField txtName;

    public Project() {
        // Set up the JFrame
        JFrame frame = new JFrame("Article Manager");
        frame.setContentPane(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        btnSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String title = txtArticle.getText();
                if (title.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please enter an article title to search.", "Warning", JOptionPane.WARNING_MESSAGE);
                } else {
                    Connect.searchArticle(title, tblResults);
                }
            }
        });

        btnAddArticle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String articleID = txtName.getText();
                String author = txtAuthor.getText();
                if (articleID.isEmpty() || author.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Article ID and Author fields must be filled out to add an article.", "Warning", JOptionPane.WARNING_MESSAGE);
                } else {
                    boolean success = Connect.addAuthorToArticle(articleID, author);
                    if (success) {
                        JOptionPane.showMessageDialog(null, "Article added successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                        clearFields();
                    } else {
                        JOptionPane.showMessageDialog(null, "Failed to add article.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String articleID = txtName.getText();
                if (!articleID.isEmpty()) {
                    boolean success = Connect.deleteArticleByID(articleID);
                    if (success) {
                        JOptionPane.showMessageDialog(null, "Record deleted successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                        clearFields();
                    } else {
                        JOptionPane.showMessageDialog(null, "Failed to delete.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Please enter an article ID.", "Warning", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        btnRepository.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Connect.showRepository(tblResults);
            }
        });
    }

    private void clearFields() {
        txtArticle.setText("");
        txtAuthor.setText("");
        txtName.setText("");
    }

}
