package com.servease.ui.dashboard;

import com.servease.controller.BookingController;
import com.servease.model.User;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ProviderBookingsFrame extends JPanel {

    private User user;
    private JPanel listPanel;

    public ProviderBookingsFrame(User user){

        this.user = user;

        setLayout(new BorderLayout());
        setBackground(new Color(245,245,245));

        JLabel title = new JLabel("My Bookings");
        title.setFont(new Font("Arial",Font.BOLD,18));
        title.setBorder(BorderFactory.createEmptyBorder(15,20,15,20));

        listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel,BoxLayout.Y_AXIS));

        JScrollPane scroll = new JScrollPane(listPanel);

        add(title,BorderLayout.NORTH);
        add(scroll,BorderLayout.CENTER);

        load();
    }

    private void load(){

        listPanel.removeAll();

        BookingController bc = new BookingController();
        List<Object[]> list = bc.getBookingsByProvider(user.getId());

        for(Object[] row: list){

            int id = (int)row[0];

            JPanel card = new JPanel(new BorderLayout());
            card.setBackground(Color.WHITE);
            card.setBorder(BorderFactory.createEmptyBorder(15,15,15,15));

            JLabel info = new JLabel(
                    row[1]+" | "+row[2]+" | "+row[3]+" | "+row[4]);

            JPanel btns = new JPanel();

            JButton a = new JButton("Accept");
            JButton r = new JButton("Reject");
            JButton c = new JButton("Complete");

            a.addActionListener(e -> {bc.updateBookingStatus(id,"Accepted"); load();});
            r.addActionListener(e -> {bc.updateBookingStatus(id,"Rejected"); load();});
            c.addActionListener(e -> {bc.updateBookingStatus(id,"Completed"); load();});

            btns.add(a); btns.add(r); btns.add(c);

            card.add(info,BorderLayout.CENTER);
            card.add(btns,BorderLayout.EAST);

            listPanel.add(card);
            listPanel.add(Box.createVerticalStrut(10));
        }

        revalidate();
        repaint();
    }
}