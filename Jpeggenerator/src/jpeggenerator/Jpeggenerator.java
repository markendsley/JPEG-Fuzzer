
package jpeggenerator;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.sql.Time;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import javax.imageio.ImageIO;

/**
 *
 * @author Mark Endsley 
 */
public class Jpeggenerator {


    public static void main(String[] args) throws IOException, InterruptedException {
        
    int width = 963;    //width of the image
    int height = 640;   //height of the image
    BufferedImage image = null; //random JPEG image
    File f = null;
    
    Color color = new Color(0,0,0);   //For color selection
    int rgb = color.getRGB();    

    //Outer loop for running program multiple times until it crashes
    for(boolean checker = false;checker == false;){
            
    //read image
        try{
            f = new File("C:\\Code\\image.jpg"); //image file path
            image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            image = ImageIO.read(f);
            System.out.println("Reading complete.");
        }catch(IOException e){
            System.out.println("Error: "+e);
        }

        int x = 0;
        int y = 0;
    
        //random ints are assigned for size
        Random rand = new Random();
    
        int xSize = rand.nextInt(6550);
        int ySize = rand.nextInt(6550);
    
        //resize image with random values
        image = resizeImage(image,xSize,ySize);
    
        //give it a random filename
        String fileName = generateRandomString();
    
        //loop that decides color of random image
        while (y<ySize){
       
            //in loop for x dimension
            while(x<xSize){
        
            color = new Color(rand.nextInt(255),rand.nextInt(255),rand.nextInt(255));
            rgb = color.getRGB();    
        
            image.setRGB(x, y, rgb);
            x++;
        
        
            }//End inner loop for X dimension
            x=0;
            y++;
        }//End outer loop that decides color of random image

   
    //write image
        try{
            f = new File("C:\\Code\\" + fileName + ".jpg");  //output file path
            ImageIO.write(image, "jpg", f);
            System.out.println("Writing complete.");
        }catch(IOException e){
            System.out.println("Error: "+e);
        }
    
        TimeUnit.SECONDS.sleep(2);
    
        checker = runProgramTwo(fileName);
    
    }//End Outer loop for running program
    
  }
    
    //Function to resize the template image to given size
    public static BufferedImage resizeImage(BufferedImage image, int width, int height){
        
        Image temp = image.getScaledInstance(width,height,Image.SCALE_SMOOTH);
        BufferedImage dimg = new BufferedImage(width,height,BufferedImage.TYPE_INT_ARGB);
        
        Graphics2D g2d = dimg.createGraphics();
        g2d.drawImage(temp,0,0,null);
        g2d.dispose();
        
        return dimg;
        
    }
    
    //Function that generates Random String that follows Windows filename rules
    public static String generateRandomString(){
        
        String candidateChars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        String temp = new String();
        
        Random rand = new Random();
        
        int value = ((rand.nextInt(250)) +1);
        
        for(int i=0;i<value;i++){
            temp = temp + candidateChars.charAt(rand.nextInt(candidateChars.length()));
        }
        return temp;
    }
    
    //First runProgram function runs the program through command line, does not trace process
    public static void runProgram(String fileName) throws IOException{
        
        //adds .jpg to filename
        String path = fileName + ".jpg";
        
        //opens command line and executes commands
        Runtime rt = Runtime.getRuntime();
        rt.exec("cmd.exe /c start C:/Code/a.exe C:/Code/" + path);
             
        System.out.println("Program run");
    }
    
    //Second runProgram function traces process and returns true if program exited abnormally
    public static boolean runProgramTwo(String fileName) throws IOException, InterruptedException{
        
        
        //adds .jpg to filename
        String path = fileName + ".jpg";
        
        //processbuilder for process
        ProcessBuilder process = new ProcessBuilder("C:\\Code\\a.exe","C:\\Code\\" + path);
        
        //prints output path
        System.out.println(process.redirectError());
        
        //starts process
        Process go = process.start();
        
        //waits for process to end
        go.waitFor();
        
        //prints exit value
        System.out.println(go.exitValue());
        
        //returns boolean value based on exit condition
        if(go.exitValue() == 0){
            return false;
        }
        else{
            //Write error log with name of file that crashed it
            PrintWriter writer = new PrintWriter("errorlog.txt", "UTF-8");
            writer.println(path);
            writer.close();
            
            return true;
        }
        
    }
}
    
