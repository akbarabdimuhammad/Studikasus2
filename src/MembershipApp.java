package src;

import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class MembershipApp extends JFrame {
    private final JTable table;
    private final DefaultTableModel tableModel;
    private final MembershipDAO membershipDAO;

    public MembershipApp() {
        membershipDAO = new MembershipDAO();
        setTitle("Membership Management");
        setLayout(new BorderLayout());
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        tableModel = new DefaultTableModel(new String[]{"ID", "Name", "Email", "Phone", "Membership Type"}, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Add");
        JButton updateButton = new JButton("Update");
        JButton deleteButton = new JButton("Delete");
        updateButton.setEnabled(false);
        deleteButton.setEnabled(false);
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        add(buttonPanel, BorderLayout.SOUTH);

        addButton.addActionListener(_ -> addMembership());
        updateButton.addActionListener(_ -> updateMembership());
        deleteButton.addActionListener(_ -> deleteMembership());

        table.getSelectionModel().addListSelectionListener(_ -> {
            boolean isRowSelected = table.getSelectedRow() != -1;
            updateButton.setEnabled(isRowSelected);
            deleteButton.setEnabled(isRowSelected);
        });

        loadMembershipData();
    }

    private void loadMembershipData() {
        List<Membership> memberships = membershipDAO.getAllMemberships();
        tableModel.setRowCount(0);
        for (Membership membership : memberships) {
            tableModel.addRow(new Object[]{
                membership.getId(), membership.getName(), membership.getEmail(),
                membership.getPhone(), membership.getMembershipType()
            });
        }
    }

    private void addMembership() {
        String name = showInputDialog("Enter Name:");
        String email = showInputDialog("Enter Email:");
        String phone = showInputDialog("Enter Phone:");
        String membershipType = showInputDialog("Enter Membership Type:");

        Membership newMembership = new Membership(0, name, email, phone, membershipType);
        if (membershipDAO.addMembership(newMembership)) {
            loadMembershipData();
        }
    }

    private void updateMembership() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            int id = (int) tableModel.getValueAt(selectedRow, 0);
            String name = showInputDialog("Enter Name:");
            String email = showInputDialog("Enter Email:");
            String phone = showInputDialog("Enter Phone:");
            String membershipType = showInputDialog("Enter Membership Type:");

            Membership updatedMembership = new Membership(id, name, email, phone, membershipType);
            if (membershipDAO.updateMembership(updatedMembership)) {
                loadMembershipData();
            }
        }
    }

    private void deleteMembership() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            int id = (int) tableModel.getValueAt(selectedRow, 0);
            if (membershipDAO.deleteMembership(id)) {
                loadMembershipData();
            }
        }
    }

    private String showInputDialog(String message) {
        String input = JOptionPane.showInputDialog(this, message);
        return (input != null && !input.trim().isEmpty()) ? input.trim() : null;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MembershipApp app = new MembershipApp();
            app.setVisible(true);
        });
    }
}
