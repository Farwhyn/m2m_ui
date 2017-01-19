#include "Adafruit_NeoPixel.h"

//define LED strip variables
#define SqueezePin    8
#define TapPin 9
#define N_LEDS 30 // 5 meter reel @ 30 LEDs/m
#define SIZE 10

//set up LED strips
Adafruit_NeoPixel SqueezeStrip = Adafruit_NeoPixel(N_LEDS, SqueezePin, NEO_GRB + NEO_KHZ800);
Adafruit_NeoPixel TapStrip = Adafruit_NeoPixel(N_LEDS, TapPin, NEO_GRB + NEO_KHZ800);


//define number to send to UI
#define tap_num 1
#define squeeze_num 2
#define spin_num 3


int serial_val; // Data received from the serial port

//Define sensor pins
int SqueezeSensorPin = A3; 
int TapSensorPin = A4;
int SpinSensorPin = A5;

//define variables to hold sensor values
int TapReading;
int SqueezeReading;
int SpinReading;

void setup() {
  // put your setup code here, to run once:
  Serial.begin(9600);

  SqueezeStrip.begin();
  TapStrip.begin();

  for (int i = 0; i < N_LEDS; i++) {
    SqueezeStrip.setPixelColor(i, SqueezeStrip.Color(0, 0, 0)); //Red
  }
  SqueezeStrip.show();
}

void loop() {
  // put your main code here, to run repeatedly:

  SqueezeReading = analogRead(SqueezeSensorPin);
  Serial.println(SqueezeReading);

  
  if (Serial.available())
  { // If data is available to read,
    serial_val = Serial.read(); // read it and store it in val
  }


  if (serial_val == squeeze_num)
  { // If 1 was received
    for (int i = 0; i < N_LEDS; i++) {
      SqueezeStrip.setPixelColor(i, SqueezeStrip.Color(255, 0, 0)); //Red
    }
    SqueezeStrip.show();
    serial_val=0;
  }
  else if (SqueezeReading > 370) {
    for (int i = 0; i < N_LEDS; i++) {
      SqueezeStrip.setPixelColor(i, SqueezeStrip.Color(0, 0, 0)); //Red
    }
    SqueezeStrip.show();

//     Serial.println('4');
  }


}

