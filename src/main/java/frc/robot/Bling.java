package frc.robot;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;

public class Bling {
    

    // Reuse buffer
    // Default to a length of 60, / Length is expensive to set, so only set it once, then just update data
 
    public static AddressableLED m_led = new AddressableLED(2); //port 2
    public static AddressableLEDBuffer m_ledBuffer = new AddressableLEDBuffer(60);
    

public static void rainbow() {
    // For every pixel
    int m_rainbowFirstPixelHue = 100;
    for (int i = 0; i < m_ledBuffer.getLength(); i++) {
      // Calculate the hue - hue is easier for rainbows because the color
      // shape is a circle so only one value needs to precess
   
       final int hue = (m_rainbowFirstPixelHue + (i * 180 / m_ledBuffer.getLength())) % 180;
      // Set the value
      m_ledBuffer.setHSV(i, hue, 255, 128);
    }
    // Increase by to make the rainbow "move"
    m_rainbowFirstPixelHue += 3;
    // Check bounds
    m_rainbowFirstPixelHue %= 180;
  }

  public static void red(){
      for (var i = 0; i < m_ledBuffer.getLength(); i++) 
        m_ledBuffer.setRGB(i, 255, 0, 0);
    m_led.setData(m_ledBuffer);
  }
  
  public static void blue(){
      for (int i = 0; i < m_ledBuffer.getLength(); i++) 
        m_ledBuffer.setRGB(i, 0, 0, 255);
    m_led.setData(m_ledBuffer);
  }
  
 public static void green(){
     for (int i = 0; i < m_ledBuffer.getLength(); i++) 
        m_ledBuffer.setRGB(i, 0, 255, 0);
  m_led.setData(m_ledBuffer);
  }

  public static void redBlue(){
    for (int i = 0; i < m_ledBuffer.getLength(); i++) {
        if (i % 8 <= 3)
            m_ledBuffer.setRGB(i, 0, 0, 255);
        else
            m_ledBuffer.setRGB(i, 255, 0, 0);
    
    }
    m_led.setData(m_ledBuffer);
  }
}
