package main;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartRenderingInfo;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.entity.StandardEntityCollection;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import com.fazecast.jSerialComm.SerialPort;

public class Graph {
    static SerialPort chosenPort;
    static int x = 0;
    public Graph() {
     // create and configure the window
        JFrame window = new JFrame();
        window.setTitle("Sensor Graph");
        window.setSize(600, 400);
        window.setLayout(new BorderLayout());
        window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        // create a drop-down box and connect button, then place them at the top of the window
        JComboBox<String> portList = new JComboBox<String>();
        JButton connectButton = new JButton("Connect");
        JPanel topPanel = new JPanel();
        topPanel.add(portList);
        topPanel.add(connectButton);
        window.add(topPanel, BorderLayout.NORTH);
        
        // populate the drop-down box
        SerialPort[] portNames = SerialPort.getCommPorts();
        if(portNames.length == 0) {
            JOptionPane.showMessageDialog(null, "Cannot connect to device. Please check your connection");
            window.setVisible(false);
            window.dispose();
        }
        else {
        for(int i = 0; i < portNames.length; i++)
            portList.addItem(portNames[i].getSystemPortName());
        
        // create the line graph
        XYSeries series = new XYSeries("Sensor Readings");
        XYSeriesCollection dataset = new XYSeriesCollection(series);
        JFreeChart chart = ChartFactory.createXYLineChart("Squeeze Sensor Readings", "Time (milliseconds)", "Pressure", dataset);
        window.add(new ChartPanel(chart), BorderLayout.CENTER);
        
        // configure the connect button and use another thread to listen for data
        connectButton.addActionListener(new ActionListener(){
            @Override public void actionPerformed(ActionEvent arg0) {
                if(connectButton.getText().equals("Connect")) {
                    // attempt to connect to the serial port
                    try {
                    chosenPort = SerialPort.getCommPort(portList.getSelectedItem().toString());
                    chosenPort.setComPortTimeouts(SerialPort.TIMEOUT_SCANNER, 0, 0);
                    if(chosenPort.openPort()) {
                       connectButton.setText("Disconnect");
                       portList.setEnabled(false);
                    }
                       
                   
                    
                    // create a new thread that listens for incoming text and populates the graph
                    Thread thread = new Thread(){
                        @Override public void run() {
                            
                       try{
                            Scanner scanner = new Scanner(chosenPort.getInputStream());
                            while(scanner.hasNextLine()) {
                                
                                    String line = scanner.nextLine();
                                    int number = Integer.parseInt(line);
                                    series.add(x++, number);
                                    window.repaint();
                            }
                            scanner.close();
                        }catch(Exception e) {
                            JOptionPane.showMessageDialog(null,  "Device Disconnected");
                        }
                    }
                    };
                    thread.start();
                    } catch(Exception e) {
                        JOptionPane.showMessageDialog(null, "Cannot connect to device");
                    }
                    
                } 
                    
                    else {
                    // disconnect from the serial port
                        try{
                            final ChartRenderingInfo info = new ChartRenderingInfo(new StandardEntityCollection());
                            File file1 = new File("~Desktop/Chart1.png"); 
                            ChartUtilities.saveChartAsJPEG(file1, chart, 600, 400, info);
                        } catch(Exception e) {
                            JOptionPane.showMessageDialog(null, "Cannot save chart as image");
                        }
                    chosenPort.closePort();
                    portList.setEnabled(true);
                    connectButton.setText("Connect");
                   
                    series.clear();
                    x = 0;
                }
            }
        });
        
        // show the window
        window.setVisible(true);
        }
    }
}

