#include "Adafruit_NeoPixel.h"

#define TapPin    6
#define SpinPin    9
#define N_LEDS 30 // 5 meter reel @ 30 LEDs/m
#define SIZE 10
Adafruit_NeoPixel TapStrip = Adafruit_NeoPixel(N_LEDS, TapPin, NEO_GRB + NEO_KHZ800);
Adafruit_NeoPixel SpinStrip = Adafruit_NeoPixel(N_LEDS, SpinPin, NEO_GRB + NEO_KHZ800);

int serialread;
int TapSensorPin = A1; //analog pin 2
int SqueezeSensorPin = A2; //analog pin 2
int SpinSensorPin = A3; //analog pin 0

// int SensorValue = 0; // variable to store the value coming from the sensor
int TapReading = 0;
int SqueezeReading=0;
int SpinReading=0;
int i = 0;

int TapThreshold=650;
int SqueezeThreshold=80;
int SpinThreshold=480;

void setup() {
 Serial.begin(9600);

 //RGB LED for Squeeze
 pinMode(3, OUTPUT);
 pinMode(4, OUTPUT);
 pinMode(5, OUTPUT);
 
  TapStrip.begin();
  SpinStrip.begin();

}

void loop() {
  
  TapReading = analogRead(TapSensorPin);
  SpinReading = analogRead(SpinSensorPin);
  SqueezeReading = analogRead(SqueezeSensorPin);
  
  Serial.print(TapReading);
  Serial.print(' ');
  Serial.print(SqueezeReading);
  Serial.print(' ');
  Serial.println(SpinReading);
  delay(50);


//Tap
    if (TapReading<TapThreshold){
    for(int i=0;i<N_LEDS;i++) {
      TapStrip.setPixelColor(i, TapStrip.Color(0,255,0)); //Green
    }
    TapStrip.show(); //Sends updated pixel color to hardware
    }
    else{
    for(int i=0;i<N_LEDS;i++) {
      TapStrip.setPixelColor(i, 0); //Off  
    }
    TapStrip.show(); //Sends updated pixel color to hardware  
    }
//Squeeze
if (SqueezeReading <SqueezeThreshold){
    analogWrite(3,0);
    analogWrite(4,255);
    analogWrite(5,0);

    delay(1000);
  }
  else{
    analogWrite(2,0);
    analogWrite(3,0);
    analogWrite(4,0);
  }
   
//Spin
    if (SpinReading > SpinThreshold) {
    for(int i=0;i<N_LEDS;i++) {
      SpinStrip.setPixelColor(i, SpinStrip.Color(0,255,0)); //Green
    }
    SpinStrip.show(); //Sends updated pixel color to hardware
    }
    else{
      for(int i=0;i<N_LEDS;i++) {
      SpinStrip.setPixelColor(i, 0); //Off  
      }
    SpinStrip.show(); //Sends updated pixel color to hardware  
    }
   

}
