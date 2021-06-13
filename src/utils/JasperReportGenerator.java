package utils;
import controller.Controller;
import javafx.util.Pair;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JRDesignParameter;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import org.apache.xmlbeans.impl.jam.JParameter;

import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class JasperReportGenerator
{
    public static void main( String[] args ) throws FileNotFoundException, JRException {
       JasperReport jr = JasperCompileManager.compileReport("src/reports/sales.jrxml");
       ArrayList <Pair<String, Integer>> array = new ArrayList<Pair<String, Integer>>();
       //JRDataSource jrDataSource = new JRBeanCollectionDataSource(array);
        JRDataSource jrDataSource = new JREmptyDataSource();
       JasperPrint jp = JasperFillManager.fillReport(jr, null, jrDataSource);
       JasperViewer.viewReport(jp);
    }
}
