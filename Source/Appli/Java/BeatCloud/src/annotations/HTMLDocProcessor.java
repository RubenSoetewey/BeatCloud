/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package annotations;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;

/**
 *
 * @author rsoetewey
 */    
@SupportedAnnotationTypes(value = { 
               "annotations.HTMLDoc"
                }
)
public class HTMLDocProcessor extends AbstractProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        
        for (TypeElement te : annotations) {
 
            for (Element element : roundEnv.getElementsAnnotatedWith(te)) {
        
                HTMLDoc htmlDoc = element.getAnnotation(HTMLDoc.class);
                generateHTML(htmlDoc);
            }
        }
        return true;
    }
   public void generateHTML(HTMLDoc htmlDoc){
       StringBuilder html = new StringBuilder();
        html.append("<html>");     
        html.append("<body>");
        String htmlFileName = htmlDoc.getClass().getName();
        File htmlFile = new File(htmlFileName+".html");
        FileOutputStream fw = null;
         try {
            fw = new FileOutputStream(htmlFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Field[] fields = htmlDoc.getClass().getFields();
        Method[] methods = htmlDoc.getClass().getMethods();
        html.append("<h1>"+htmlFileName+"<h1>");
        for(Field field : fields){
            html.append("<h2>"+field.getName()+" : "+field.getType()+"<h2>");
        } 
        for( Method met : methods){
            html.append("<h2>"+met.getName()+"<h2>");
            for(Parameter param : met.getParameters())
                html.append("<h3>"+param.getName()+":"+param.getType()+" <h3>");
        }
        try {
            fw.write(html.toString().getBytes());
        } catch (IOException e) { 
      e.printStackTrace();
     }finally{
        try{
           fw.close();
        } catch (IOException ex) { 
           ex.printStackTrace();
           
        }
     }
   }


}
