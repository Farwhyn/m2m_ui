package main;

import java.awt.BorderLayout;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
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
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.entity.StandardEntityCollection;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import com.fazecast.jSerialComm.SerialPort;



public class Graph {
    static SerialPort chosenPort;
    static int x = 0;
    static int irange = 0;
    public volatile static boolean flag;
    public static int frange = 100;
    public static boolean autoRange = false;
    
    
    public static void create() {
     // create and configure the window
        
        JFrame window = new JFrame();
        window.setTitle("Sensor Graph");
        //window.setSize(600, 400);
        window.setLayout(new BorderLayout());
        window.setExtendedState(JFrame.MAXIMIZED_BOTH);
        window.setSize( (int) (GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().width * 0.6), (int) (GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().height * 0.6) );
        window.setLocationRelativeTo(null);
        
        window.setMinimumSize(window.getPreferredSize());
        
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
       
              
            XYSeries series = new XYSeries("Squeeze Readings"); //create a list of xy points dedicated for squeeze
            XYSeries oseries = new XYSeries("Tap Readings"); //create a list of xy points dedicated for tap
            XYSeriesCollection dataset = new XYSeriesCollection(series); //create an XY collection used to store previous the data series
            dataset.addSeries(oseries); //use this if you want to add more series to be plotted in the overall dataset
       
            JFreeChart chart = ChartFactory.createLineChart("Free Play Mode Readings", "Time (milliseconds)", "Pressure", (CategoryDataset) dataset.getSeries(0));
            XYPlot xyPlot = (XYPlot) chart.getPlot();
            NumberAxis yAxis = (NumberAxis) xyPlot.getRangeAxis();
            NumberAxis xAxis = (NumberAxis) xyPlot.getDomainAxis();
            yAxis.setRange(0, 30);
            xAxis.setRange(irange, frange);
            //xAxis.setTickUnit(new NumberTickUnit(1));
            
            
            window.add(new ChartPanel(chart), BorderLayout.CENTER);
            //boolean flag;
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
                           
                            flag = true;
                            // create a new thread that listens for incoming text and populates the graph
                            Thread thread = new Thread(){
                                @Override public void run() {
                               while(flag) {     
                               try{
                                    Scanner scanner = new Scanner(chosenPort.getInputStream());
                                    while(scanner.hasNextLine()) {
                                        
                                            String line = scanner.nextLine();
                                            int number = Integer.parseInt(line);
                                            series.add(x++, number);
                                            if(x == 100) {
                                                autoRange = true;
                                            }
                                            if(autoRange) {
                                                
                                                xAxis.setRange(++irange, ++frange);
                                            }
                                                
                                                
                                                
                                            window.repaint();
                                    }
                                    scanner.close();
                                }catch(Exception e) {
                                    JOptionPane.showMessageDialog(null,  "Connection Occupied: Please Close Running Graph");
                                    flag = false;
                                    break;
                                }
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
                        //thread.stop();
                        
                        autoRange = false;
                        flag = false;
                        chosenPort.closePort();
                        try{
                            //final ChartRenderingInfo info = new ChartRenderingInfo(new StandardEntityCollection());
                            File file1 = new File("Chart1.png"); 
                            ChartUtilities.saveChartAsJPEG(file1, chart, 600, 400);
                        } catch(Exception e) {
                            JOptionPane.showMessageDialog(null, "Cannot save chart as image");
                        }
                    
                    portList.setEnabled(true);
                    connectButton.setText("Connect");
                   
                    series.clear();
                    x = 0;
                    irange = 0;
                    frange = 100;
                    xAxis.setRange(irange, frange);
                }
            }
        });
        
        // show the window
        window.setVisible(true);
        }
    }
   
}

