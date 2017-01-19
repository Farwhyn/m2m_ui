#include "Adafruit_NeoPixel.h"
#include <SD.h>                      // need to include the SD library
//#define SD_ChipSelectPin 53  //example uses hardware SS pin 53 on Mega2560
#define SD_ChipSelectPin 4  //using digital pin 4 on arduino nano 328, can use other pins
#include <TMRpcm.h>           //  also need to include this library...
#include <SPI.h>

#define Volume 3

TMRpcm tmrpcm;   // create an object for use in this sketch

#define TapPin    6
#define SpinPin    8
#define N_LEDS 30 // 5 meter reel @ 30 LEDs/m
#define SIZE 10

//Inititate LED Strips for spin and tap
Adafruit_NeoPixel TapStrip = Adafruit_NeoPixel(N_LEDS, TapPin, NEO_GRB + NEO_KHZ800);
Adafruit_NeoPixel SpinStrip = Adafruit_NeoPixel(N_LEDS, SpinPin, NEO_GRB + NEO_KHZ800);

int serialread;

//Initiate sensors
int TapSensorPin = A1; //analog pin 2
int SqueezeSensorPin = A2; //analog pin 2
int SpinSensorPin = A3; //analog pin 0

//initiate sensor reading
int TapReading = 0;
int SqueezeReading = 0;
int SpinReading = 0;
int i = 0;

//define three threshold levels for tap
#define Tap_1  700
#define Tap_2  600
#define Tap_3  500

//#define Tap_1  360
//#define Tap_2  400
//#define Tap_3  550

//define three threshold levels for squeeze
#define Squeeze_1 150
#define Squeeze_2 120
#define Squeeze_3 100

//define three threshold levels for spin
#define Spin_1 480
#define Spin_2 580
#define Spin_3 680

int tap_state=false;
int squeeze_state=false;
int spin_state=false;

void setup() {
  Serial.begin(9600);

  tmrpcm.speakerPin = 9; //5,6,11 or 46 on Mega, 9 on Uno, Nano, etc

  tmrpcm.setVolume(Volume);  

if (!SD.begin(SD_ChipSelectPin)) {  // see if the card is present and can be initialized:
    Serial.println("SD fail");  
    return;   // don't do anything more if not
  }
  Serial.println("SD initialized"); 
  
  //initiate RGB LED for Squeeze
  pinMode(3, OUTPUT);
  pinMode(4, OUTPUT);
  pinMode(5, OUTPUT);

  //initiate LED strips
  TapStrip.begin();
  SpinStrip.begin();

}

void loop() {

  //Take voltage readings from sensors
  TapReading = analogRead(TapSensorPin);
  SpinReading = analogRead(SpinSensorPin);
  SqueezeReading = analogRead(SqueezeSensorPin);

  //Print sensor readings to serial port
  Serial.print(TapReading);
  Serial.print(' ');
  Serial.print(SqueezeReading);
  Serial.print(' ');
  Serial.println(SpinReading);
  delay(50);

  //Tap

  //Check whether tap sensor reading is within the three threshold levels
  if (TapReading < Tap_1 && TapReading > Tap_2 && tap_state == false) {
    for (int i = 0; i < N_LEDS; i++) {
      TapStrip.setPixelColor(i, TapStrip.Color(100, 0, 100)); //Green
    }
    TapStrip.show();
    tmrpcm.play("Piano_C.wav");

    tap_state=true;
  }
    else if (TapReading < Tap_2 && TapReading > Tap_3 && tap_state == false) {
      for (int i = 0; i < N_LEDS; i++) {
        TapStrip.setPixelColor(i, TapStrip.Color(0, 0, 100)); //Green
      }
      TapStrip.show();
      tmrpcm.play("Piano_D.wav");

      tap_state=true;
    }
      else if (TapReading < Tap_3 && tap_state == false) {
        for (int i = 0; i < N_LEDS; i++) {
          TapStrip.setPixelColor(i, TapStrip.Color(0, 100, 0)); //Green
        }
        TapStrip.show();
        tmrpcm.play("Piano_E.wav");

        tap_state=true;
      }
//      else if (TapReading > Tap_1) {
//        for (int i = 0; i < N_LEDS; i++) {
//          TapStrip.setPixelColor(i, TapStrip.Color(0, 0, 0)); //Lights off
//        } 
//        TapStrip.show();
//        
//        tap_state=false;
      }

     

      

    //Squeeze

    if (SqueezeReading < Squeeze_1 && SqueezeReading > Squeeze_2 && squeeze_state == false) {
      
      analogWrite(3, 255);
      analogWrite(4, 0);
      analogWrite(5, 0);

      tmrpcm.play("Piano_C.wav");

      squeeze_state=true;
    }
    else if (SqueezeReading < Squeeze_2 && SqueezeReading > Squeeze_3 && squeeze_state == false) {
      
      analogWrite(3, 255);
      analogWrite(4, 255);
      analogWrite(5, 0);

      tmrpcm.play("Piano_D.wav");

      squeeze_state=true;
      
    }
    else if (SqueezeReading < Squeeze_3 && squeeze_state == false) {
      
      analogWrite(3, 0);
      analogWrite(4, 255);
      analogWrite(5, 0);

      tmrpcm.play("Piano_E.wav");

      squeeze_state=true;
      
    }
    else if (SqueezeReading > Squeeze_1){
      analogWrite(2, 0);
      analogWrite(3, 0);
      analogWrite(4, 0);

      squeeze_state=false;
    }

    //Spin
    if (SpinReading > Spin_1) {
      for (int i = 0; i < N_LEDS; i++) {
        SpinStrip.setPixelColor(i, SpinStrip.Color(0, 255, 0)); //Green
      }
      SpinStrip.show(); //Sends updated pixel color to hardware
    }
    else {
      for (int i = 0; i < N_LEDS; i++) {
        SpinStrip.setPixelColor(i, 0); //Off
      }
      SpinStrip.show(); //Sends updated pixel color to hardware
    }


  }
