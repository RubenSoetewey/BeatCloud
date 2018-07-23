/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Application.Util;

import java.net.URLClassLoader;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.net.URL;
import java.util.Enumeration;
import java.io.IOException;
import java.io.File;
import java.lang.reflect.*;
import java.util.Scanner;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
 
import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
 import java.util.*;

/**
 *
 * @author Max
 */
public class JarLoader {

    /**
     * @param args the command line arguments
     */
    public static final String apiUrl = "https://beatcloud.herokuapp.com";
    public Hashtable<String,Method> methods = new Hashtable<String,Method>();
    public List<String> pluginsKeys = new ArrayList<String>();

    private void loadPlugin(String pathToJar) {
        try {

            JarFile jarFile = new JarFile(pathToJar);
            Enumeration<JarEntry> e = jarFile.entries();

            URL[] urls = {new URL("jar:file:" + pathToJar + "!/")};
            URLClassLoader cl = URLClassLoader.newInstance(urls);

            while (e.hasMoreElements()) {
                JarEntry je = e.nextElement();
                if (je.isDirectory() || !je.getName().endsWith(".class")) {
                    continue;
                }
                // -6 because of .class
                String className = je.getName().substring(0, je.getName().length() - 6);
                className = className.replace('/', '.');
                Class c = cl.loadClass(className);
                Method method = c.getMethod("exec", ArrayList.class);
                this.methods.put(className, method);
                this.pluginsKeys.add(className);
                System.out.println(className);
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public void downloadPlugin(String urlPlugin, String fileName){
        try{
            URL url = new URL(urlPlugin);
            ReadableByteChannel rbc = Channels.newChannel(url.openStream());
            FileOutputStream fos = new FileOutputStream(fileName);
            fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
            fos.close();
            rbc.close();

        }catch(Exception ex){
            System.out.println(ex);
        }
    }
    private void delete(File file) throws IOException {
        for (File childFile : file.listFiles()) {
            System.out.println(childFile.toString());
            if (childFile.isDirectory()) {
                delete(childFile);
            } else {
                if (!childFile.delete()) {
                    throw new IOException();
                }
            }
        }
        if (!file.delete()) {
            throw new IOException();
        }
    }

    public void downloadPlugins(){
        String pluginUrl = apiUrl + "/public/plugins";
        try{
            String plugins = new Scanner(new URL(pluginUrl).openStream(), "UTF-8").useDelimiter("\\A").next();
            JSONArray jsonarray = new JSONArray(plugins);

            File pluginDirectory = new File("./plugins");
            if(pluginDirectory.isDirectory() && pluginDirectory.exists()){
                this.delete(pluginDirectory);
            }

            pluginDirectory.mkdir();
            for (int i = 0; i < jsonarray.length(); i++) {
                JSONObject jsonobject = jsonarray.getJSONObject(i);
                String downloadUrl = jsonobject.getString("downloadUrl");
                String pluginName = jsonobject.getString("name");

                System.out.println(downloadUrl);
                downloadPlugin(downloadUrl, "./plugins/" + pluginName);
            }
        } catch(Exception ex){
            System.out.println(ex);
        }

    }
    public void loadPlugins() {
        JarLoader jarLoader = new JarLoader();
        jarLoader.downloadPlugins();
        File folder = new File("./plugins");
        File[] listOfFiles = folder.listFiles();
        for (File file : listOfFiles) {
            if (file.isFile()) {
                this.loadPlugin(file.getPath());
            }
        }
    }

    public Object execPlugin(String pluginName, ArrayList<String> params){
        try{
            Method method = this.methods.get(pluginName);
            return method.invoke(null, new Object[]{params});
        } catch(Exception ex){
            System.out.println(ex);
            return null;
        }
    }

    public static void main(String[] args) {
        JarLoader jarLoader = new JarLoader();
        jarLoader.downloadPlugins();
        jarLoader.loadPlugins();
        String param = "test";

        ArrayList<String> params = new ArrayList<String>();
        params.add("token");
        params.add("filename");

        for (String key : jarLoader.pluginsKeys) {
            System.out.println(key);
            param = jarLoader.execPlugin(key, params).toString();
            System.out.println(param);
        }
    }

}