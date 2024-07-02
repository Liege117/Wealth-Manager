package com.example.wealthmanager.service;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.title.LegendTitle;
import org.jfree.chart.ui.RectangleInsets;
import org.jfree.data.general.DefaultPieDataset;
import org.springframework.stereotype.Service;

@Service
public class ChartService {

    public byte[] savePieChart(double totalInvestment,double totalExpense,double totalIncome, double totalReturns)throws IOException{
        DefaultPieDataset dataset = new DefaultPieDataset();
        dataset.setValue("Total Investment", totalInvestment);
        dataset.setValue("Total Expense", totalExpense);
        dataset.setValue("Total Income", totalIncome);
        dataset.setValue("Total Returns", totalReturns);

        JFreeChart chart = ChartFactory.createPieChart("Financial Overview",dataset, true,true,false);

        PiePlot plot = (PiePlot) chart.getPlot();
        plot.setSectionPaint("Total Investment", new Color(0, 204, 102));
        plot.setSectionPaint("Total Expense", new Color(255, 51, 51));
        plot.setSectionPaint("Total Income", new Color(51, 102, 255));
        plot.setSectionPaint("Total Returns", new Color(255, 255, 0));
        plot.setBackgroundPaint(new Color(220,220,221));

        LegendTitle legend = chart.getLegend();
        legend.setItemFont(new java.awt.Font("SansSerif", java.awt.Font.PLAIN, 12));
        legend.setBackgroundPaint(new Color(220,220,221));
        legend.setFrame(new org.jfree.chart.block.LineBorder(Color.BLACK, new BasicStroke(), new RectangleInsets(2, 2, 2, 2)));
        
        BufferedImage chartImage = chart.createBufferedImage(500, 400);
        chart.setBackgroundPaint(new Color(220,220,221));
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(chartImage, "png", baos);
        baos.flush();
        byte[] imageInByte = baos.toByteArray();
        baos.close();

        return imageInByte;
    }
}