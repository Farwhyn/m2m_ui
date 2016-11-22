// DONT CONNECT ANYTHING TO PIN 4

#include <MusicPlayer.h>
#include <pins_config.h>
#include <vs10xx.h>

#include "Adafruit_NeoPixel.h"
#include <SD.h>                      // need to include the SD library
//#define SD_ChipSelectPin 53  //example uses hardware SS pin 53 on Mega2560
#define SD_ChipSelectPin 4  //using digital pin 4 on arduino nano 328, can use other pins
#include <SPI.h>

#define Volume 6

#define SQ_red 1
#define SQ_green 2
#define SQ_blue 3


#define TapPin    6
#define SpinPin    8
#define N_LEDS 30 // 5 meter reel @ 30 LEDs/m
#define SIZE 10

//Inititate LED Strips for spin and tap
Adafruit_NeoPixel TapStrip = Adafruit_NeoPixel(N_LEDS, TapPin, NEO_GRB + NEO_KHZ800);
Adafruit_NeoPixel SpinStrip = Adafruit_NeoPixel(N_LEDS, SpinPin, NEO_GRB + NEO_KHZ800);

int serialread;

//Initiate sensors
int TapSensorPin = A3; //analog pin 2
int SqueezeSensorPin = A4; //analog pin 3
int SpinSensorPin = A5; //analog pin 4

//initiate sensor reading
int TapReading = 0;
int SqueezeReading = 0;
int SpinReading = 0;
int i = 0;

//define three threshold levels for tap
#define Tap_1  700
#define Tap_2  600
#define Tap_3  500

//#define Tap_1  900
//#define Tap_2  800
//#define Tap_3  700

//define three threshold levels for squeeze
#define Squeeze_1 140
#define Squeeze_2 120
#define Squeeze_3 100

//#define Squeeze_1 340
//#define Squeeze_2 310
//#define Squeeze_3 280

//#define Squeeze_1 250
//#define Squeeze_2 220
//#define Squeeze_3 220

//define three threshold levels for spin
#define Spin_1 500
#define Spin_2 580
#define Spin_3 680

int tap_state = false;
int squeeze_state = false;
int spin_state = false;

void setup() {
  Serial.begin(9600);

  //will initialize music shield and set default mode to be normal.
  player.begin();

  //initiate RGB LED for Squeeze
  pinMode(SQ_red, OUTPUT);
  pinMode(SQ_green, OUTPUT);
  pinMode(SQ_blue, OUTPUT);

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
  Serial.print(',');
  Serial.print(SqueezeReading);
  Serial.print(',');
  Serial.println(SpinReading);
  delay(50);

  //Tap

  //Check whether tap sensor reading is within the three threshold levels
  if (TapReading < Tap_1 && TapReading > Tap_2 && tap_state == false) {
    for (int i = 0; i < N_LEDS; i++) {
      TapStrip.setPixelColor(i, TapStrip.Color(100, 0, 0)); //Red
    }
    TapStrip.show();
    player.playOne("PianoC3.mp3");

    tap_state = true;
  }
  else if (TapReading < Tap_2 && TapReading > Tap_3 && tap_state == false) {
    for (int i = 0; i < N_LEDS; i++) {
      TapStrip.setPixelColor(i, TapStrip.Color(0, 0, 100)); //Blue
    }
    TapStrip.show();
    player.playOne("PianoF3.mp3");

    tap_state = true;
  }
  else if (TapReading < Tap_3 && tap_state == false) {
    for (int i = 0; i < N_LEDS; i++) {
      TapStrip.setPixelColor(i, TapStrip.Color(0, 100, 0)); //Green
    }
    TapStrip.show();
    player.playOne("PianoG3.mp3");

    tap_state = true;
  }
  else if (TapReading > Tap_1) {
    for (int i = 0; i < N_LEDS; i++) {
      TapStrip.setPixelColor(i, TapStrip.Color(0, 0, 0)); //Lights off
    }
    TapStrip.show();

    tap_state = false;
  }

  //Squeeze

  //Check whether squeeze sensor reading is within the three threshold levels

  if (SqueezeReading < Squeeze_1 && SqueezeReading > Squeeze_2 && squeeze_state == false) {

    analogWrite(SQ_red, 0);
    analogWrite(SQ_green, 0);
    analogWrite(SQ_blue, 255);

    player.playOne("PianoC3.mp3");

    squeeze_state = true;
  }
  else if (SqueezeReading < Squeeze_2 && SqueezeReading > Squeeze_3 && squeeze_state == false) {

    analogWrite(SQ_red, 255);
    analogWrite(SQ_green, 255);
    analogWrite(SQ_blue, 0);

    player.playOne("PianoF3.mp3");

    squeeze_state = true;

  }
  else if (SqueezeReading < Squeeze_3 && squeeze_state == false) {

    analogWrite(SQ_red, 0);
    analogWrite(SQ_green, 255);
    analogWrite(SQ_blue, 0);

    player.playOne("PianoG3.mp3");

    squeeze_state = true;

  }
  else if (SqueezeReading > Squeeze_1) {
    analogWrite(SQ_red, 0);
    analogWrite(SQ_green, 0);
    analogWrite(SQ_blue, 0);

    squeeze_state = false;
  }


  //Spin

  //Check whether squeeze sensor reading is within the three threshold levels
  if (SpinReading > Spin_1 && spin_state == false) {
    for (int i = 0; i < N_LEDS; i++) {
      SpinStrip.setPixelColor(i, SpinStrip.Color(255, 255, 0)); //Red
    }
    SpinStrip.show(); //Sends updated pixel color to hardware

    spin_state = true;

    player.playOne("PianoG3.mp3");
  }
  else if (SpinReading < Spin_1) {
    for (int i = 0; i < N_LEDS; i++) {
      SpinStrip.setPixelColor(i, 0); //Off
    }
    SpinStrip.show(); //Sends updated pixel color to hardware
    spin_state = false;
  }

}
