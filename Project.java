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
                String title = txtArticle.getText();
                String author = txtAuthor.getText();
                String content = txtContent.getText();
                if (title.isEmpty() || author.isEmpty() || content.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "All fields must be filled out to add an article.", "Warning", JOptionPane.WARNING_MESSAGE);
                } else {
                    boolean success = Connect.insertArticle(title, author);
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
                String title = txtArticle.getText();
                if (title.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please enter an article title to delete.", "Warning", JOptionPane.WARNING_MESSAGE);
                } else {
                    boolean success = Connect.deleteArticle(title);
                    if (success) {
                        JOptionPane.showMessageDialog(null, "Article deleted successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                        clearFields();
                    } else {
                        JOptionPane.showMessageDialog(null, "Article deleted successfully.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
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
        txtContent.setText("");  // Assuming you have added a JTextArea for the content
    }


}

