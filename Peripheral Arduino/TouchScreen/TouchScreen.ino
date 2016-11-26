#include <SPI.h>
#include <Wire.h>

#include "Adafruit_STMPE610.h"

// I believe these coordinates were taken experimentally
// (found to be used in the old LED_maxtrix code)
#define MIN_X 108
#define MAX_X 4006
#define MIN_Y 189
#define MAX_Y 3989

// Use the default constructor with empty () to use I2C
Adafruit_STMPE610 touch = Adafruit_STMPE610();

void setup() { 
  Serial.begin(9600);
  Serial.println("Adafruit STMPE610 example");
  Serial.flush();

  pinMode(10, OUTPUT);

  // If using I2C you can select the I2C address (there are two options) by calling
  // touch.begin(0x41), the default, or touch.begin(0x44) if A0 is tied to 3.3V
  // If no address is passed, 0x41 is used
  
  // Loop until STMPE is initialized properly
  while (! touch.begin()) {
    Serial.println("STMPE not found!");
    delay(100);
  }
  Serial.println("Waiting for touch sense");
}

void loop() {
  uint8_t z;
  uint16_t x, y;
  // Quadrant information
  uint16_t xQ, yQ;
  unsigned char quadrant;
  
  
  if (touch.touched()) {
    // read x & y & z;
    while (! touch.bufferEmpty()) {
      touch.readData(&x, &y, &z);
      // This maps the coordinates to 0 or 1 quadrant info
      xQ = map(x, MIN_X, MAX_X, 0, 2);
      yQ = map(y, MIN_Y, MAX_Y, 0, 2);
      
      // 0,0 is quadrant 1
      if((xQ == 0) && (yQ == 0)) {quadrant = 1;}
      
      // 1,0 is quadrant 2
      else if((xQ == 1) && (yQ == 0)) {quadrant = 2;}
      
      // 0,1 is quadrant 3
      else if((xQ == 0) && (yQ == 1)) {quadrant = 3;}
      
      // 1,1 is quadrant 4
      else {quadrant = 4;}
      Serial.print("Quadrant ");
      Serial.println(quadrant); 
      
      /*
      Serial.print(touch.bufferSize());
      Serial.print("->("); 
      Serial.print(x); Serial.print(", "); 
      Serial.print(y); Serial.print(", "); 
      Serial.print(z);
      Serial.println(")");
      */
    }
    touch.writeRegister8(STMPE_INT_STA, 0xFF); // reset all ints
  }
  delay(10);
}

