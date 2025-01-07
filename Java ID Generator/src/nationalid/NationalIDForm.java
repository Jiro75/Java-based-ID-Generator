package nationalid;

import com.toedter.calendar.JDateChooser;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.DocumentFilter;
import javax.swing.text.AbstractDocument;
import javax.swing.text.BadLocationException;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class NationalIDForm extends JFrame {
    private JTextField nameField;
    private JComboBox<Governate> governateCombo;
    private JDateChooser dateChooser;
    private JComboBox<Gender> genderCombo;
    private JComboBox<Socialstatus> statusCombo;
    private JTextField addressField;
    private JComboBox<Religion> religionCombo;
    private JLabel nameErrorLabel;

    public NationalIDForm() {
        setTitle("National ID Generator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 400);

        // Create main panel with padding
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Create form panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Initialize components
        initComponents();

        // Add components to form panel
        addComponents(formPanel, gbc);

        // Add form panel to main panel
        mainPanel.add(formPanel);

        // Add main panel to frame
        add(mainPanel);

        // Center the window
        setLocationRelativeTo(null);
        pack();
    }

    private void initComponents() {
        // Initialize name field with validation
        nameField = new JTextField(20);
        setupNameValidation();

        // Initialize error label
        nameErrorLabel = new JLabel("");
        nameErrorLabel.setForeground(Color.RED);
        nameErrorLabel.setFont(new Font(nameErrorLabel.getFont().getName(), Font.PLAIN, 12));

        // Initialize address field
        addressField = new JTextField(20);

        // Initialize combo boxes
        governateCombo = new JComboBox<>(Governate.values());
        genderCombo = new JComboBox<>(Gender.values());
        statusCombo = new JComboBox<>(Socialstatus.values());
        religionCombo = new JComboBox<>(Religion.values());

        // Initialize date chooser
        dateChooser = new JDateChooser();
        dateChooser.setDateFormatString("dd-MM-yyyy");
        dateChooser.setPreferredSize(new Dimension(200, 25));

        // Set date range
        Date currentDate = new Date();
        dateChooser.setMaxSelectableDate(currentDate);

        Calendar minDate = Calendar.getInstance();
        minDate.add(Calendar.YEAR, -100);
        dateChooser.setMinSelectableDate(minDate.getTime());

        // Set preferred size for combo boxes
        Dimension comboSize = new Dimension(200, 25);
        governateCombo.setPreferredSize(comboSize);
        genderCombo.setPreferredSize(comboSize);
        statusCombo.setPreferredSize(comboSize);
        religionCombo.setPreferredSize(comboSize);
    }

    private void setupNameValidation() {
        ((AbstractDocument) nameField.getDocument()).setDocumentFilter(new DocumentFilter() {
            @Override
            public void insertString(FilterBypass fb, int offset, String string, javax.swing.text.AttributeSet attr)
                    throws BadLocationException {
                if (isValidNameInput(string)) {
                    super.insertString(fb, offset, string, attr);
                    validateName(fb.getDocument().getText(0, fb.getDocument().getLength()));
                }
            }

            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, javax.swing.text.AttributeSet attrs)
                    throws BadLocationException {
                if (isValidNameInput(text)) {
                    super.replace(fb, offset, length, text, attrs);
                    validateName(fb.getDocument().getText(0, fb.getDocument().getLength()));
                }
            }
        });

        nameField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                try {
                    validateName(nameField.getText());
                } catch (BadLocationException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private boolean isValidNameInput(String text) {
        return text.matches("[a-zA-Z\\s\\-']*");
    }

    private void validateName(String name) throws BadLocationException {
        if (name.trim().isEmpty()) {
            nameErrorLabel.setText("Name is required");
            nameField.setBackground(new Color(255, 232, 232));
        } else if (name.length() < 2) {
            nameErrorLabel.setText("Name must be at least 2 characters");
            nameField.setBackground(new Color(255, 232, 232));
        } else if (!name.matches("^[a-zA-Z\\s\\-']+$")) {
            nameErrorLabel.setText("Name can only contain letters, spaces, hyphens and apostrophes");
            nameField.setBackground(new Color(255, 232, 232));
        } else {
            nameErrorLabel.setText("");
            nameField.setBackground(Color.WHITE);
        }
    }

    private void addComponents(JPanel panel, GridBagConstraints gbc) {
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.3;
        gbc.anchor = GridBagConstraints.WEST;

        // Add form components
        addFormRowWithError(panel, "Name:", nameField, nameErrorLabel, gbc);
        addFormRow(panel, "Governate:", governateCombo, gbc);
        addFormRow(panel, "Birthdate:", dateChooser, gbc);
        addFormRow(panel, "Gender:", genderCombo, gbc);
        addFormRow(panel, "Social Status:", statusCombo, gbc);
        addFormRow(panel, "Address:", addressField, gbc);
        addFormRow(panel, "Religion:", religionCombo, gbc);

        // Add submit button
        gbc.gridy++;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(20, 5, 5, 5);

        JButton submitButton = new JButton("Generate ID");
        submitButton.setPreferredSize(new Dimension(150, 30));
        submitButton.addActionListener(e -> generateID());
        panel.add(submitButton, gbc);
    }

    private void addFormRow(JPanel panel, String label, JComponent component, GridBagConstraints gbc) {
        panel.add(new JLabel(label), gbc);
        gbc.gridx = 1;
        gbc.weightx = 0.7;
        panel.add(component, gbc);
        gbc.gridx = 0;
        gbc.gridy++;
    }

    private void addFormRowWithError(JPanel panel, String label, JComponent component, JLabel errorLabel, GridBagConstraints gbc) {
        panel.add(new JLabel(label), gbc);
        gbc.gridx = 1;
        gbc.weightx = 0.7;
        panel.add(component, gbc);

        gbc.gridy++;
        gbc.gridx = 1;
        gbc.insets = new Insets(0, 5, 5, 5);
        panel.add(errorLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.insets = new Insets(5, 5, 5, 5);
    }

    private void generateID() {
        try {
            // Validate all fields
            String name = nameField.getText().trim();
            if (!name.matches("^[a-zA-Z\\s\\-']+$") || name.length() < 2) {
                throw new IllegalArgumentException("Please enter a valid name");
            }

            if (addressField.getText().trim().isEmpty()) {
                throw new IllegalArgumentException("Please enter an address");
            }

            if (dateChooser.getDate() == null) {
                throw new IllegalArgumentException("Please select a birth date");
            }

            // Format the date
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
            String birthdate = formatter.format(dateChooser.getDate());

            // Create new NationalID instance
            NationalID id = new NationalID(
                    name,
                    (Governate) governateCombo.getSelectedItem(),
                    birthdate,
                    (Gender) genderCombo.getSelectedItem(),
                    (Socialstatus) statusCombo.getSelectedItem(),
                    addressField.getText().trim(),
                    (Religion) religionCombo.getSelectedItem()
            );

            // Show the results dialog
            showResultsDialog(id);

        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this,
                    ex.getMessage(),
                    "Invalid Input",
                    JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "An error occurred while generating the ID",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void showResultsDialog(NationalID id) {
        // Create a new dialog
        JDialog resultDialog = new JDialog(this, "Generated ID Information", true);
        resultDialog.setLayout(new BorderLayout());

        // Create main panel with padding
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Create the text area for information
        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        textArea.setBackground(new Color(245, 245, 245));

        // Build the information string
        StringBuilder info = new StringBuilder();
        info.append("═══════════════════════════════════════════\n");
        info.append("             PERSONAL IDENTIFICATION\n");
        info.append("═══════════════════════════════════════════\n\n");

        // Personal Information Section
        info.append("Personal Information\n");
        info.append("───────────────────────────────────────────\n");
        appendField(info, "Name", id.getName());
        appendField(info, "Gender", id.getGender().toString());
        appendField(info, "Religion", id.getReligion().toString());
        appendField(info, "Social Status", id.getSocialstatus().toString());
        info.append("\n");

        // Location Information
        info.append("Location Details\n");
        info.append("───────────────────────────────────────────\n");
        appendField(info, "Governate", id.getGovernate().toString());
        appendField(info, "Address", id.getAddress());
        info.append("\n");

        // Document Information
        info.append("Document Details\n");
        info.append("───────────────────────────────────────────\n");
        appendField(info, "ID Number", id.getID());
        appendField(info, "Date of Birth", id.getBirthDate());
        appendField(info, "Date Created", id.getdateofcreation());
        appendField(info, "Expiration Date", id.getexpiration());

        info.append("\n═══════════════════════════════════════════");

        textArea.setText(info.toString());

        // Add components to dialog
        JScrollPane scrollPane = new JScrollPane(textArea);
        mainPanel.add(scrollPane);

        // Add close button
        JButton closeButton = new JButton("Close");
        closeButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        closeButton.addActionListener(e -> resultDialog.dispose());

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBorder(new EmptyBorder(10, 0, 0, 0));
        buttonPanel.add(closeButton);
        mainPanel.add(buttonPanel);

        // Configure and show dialog
        resultDialog.add(mainPanel);
        resultDialog.setSize(400, 600);
        resultDialog.setLocationRelativeTo(this);
        resultDialog.setVisible(true);
    }

    private void appendField(StringBuilder sb, String label, String value) {
        sb.append(String.format("%-15s : %s%n", label, value));
    }
}