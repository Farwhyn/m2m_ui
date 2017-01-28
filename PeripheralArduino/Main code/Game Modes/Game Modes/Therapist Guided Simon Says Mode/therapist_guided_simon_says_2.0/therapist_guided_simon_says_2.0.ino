#include <MusicPlayer.h>
#include <pins_config.h>
#include <vs10xx.h>

#include "Adafruit_NeoPixel.h"

#define SQ_red 1
#define SQ_green 2
#define SQ_blue 3

//define LED strip variables
#define SpinPin    8
#define TapPin 6
#define N_LEDS 30 // 5 meter reel @ 30 LEDs/m
#define SIZE 10

//set up LED strips
Adafruit_NeoPixel SpinStrip = Adafruit_NeoPixel(N_LEDS, SpinPin, NEO_GRB + NEO_KHZ800);
Adafruit_NeoPixel TapStrip = Adafruit_NeoPixel(N_LEDS, TapPin, NEO_GRB + NEO_KHZ800);

//Define sensor pins
int SqueezeSensorPin = A3;
int TapSensorPin = A4;
int SpinSensorPin = A5;

//define variables to hold sensor values
int TapReading;
int SqueezeReading;
int SpinReading;

//define three threshold levels for tap
#define Tap_1  700
#define Tap_2  600
#define Tap_3  500

//define three threshold levels for squeeze
#define Squeeze_1 140
#define Squeeze_2 120
#define Squeeze_3 100

//define three threshold levels for spin
#define Spin_1 500
#define Spin_2 580
#define Spin_3 680

//
boolean TapPressed = 0;
boolean SqueezedPressed = 0;
boolean SpinPressed = 0;

void setup() {
  // put your setup code here, to run once:
  Serial.begin(9600);

  //will initialize music shield and set default mode to be normal.
  player.begin();

  //initiate RGB LED for Squeeze
  pinMode(SQ_red, OUTPUT);
  pinMode(SQ_green, OUTPUT);
  pinMode(SQ_blue, OUTPUT);

  //initiate RGB LED for Squeeze
  pinMode(SQ_red, OUTPUT);
  pinMode(SQ_green, OUTPUT);
  pinMode(SQ_blue, OUTPUT);


  //initiate LED strips
  TapStrip.begin();
  SpinStrip.begin();
}

void loop() {
  // put your main code here, to run repeatedly:

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

  // Tap quadrant is pressed
  if (0) {
    TapPressed = 1;

    //Turn on LED strip
    TapStrip.setPixelColor(i, TapStrip.Color(255, 0, 0)); //Red
    TapStrip.show();
  }

  // Squeeze quadrant is pressed
  if (0) {
    SqueezePressed = 1;
    analogWrite(SQ_red, 255);
    analogWrite(SQ_green, 0);
    analogWrite(SQ_blue, 0);

  }

  // Spin quadrant is pressed
  if (0) {
    SpinPressed = 1;

    //Turn on LED strip
    SpinStrip.setPixelColor(i, TapStrip.Color(255, 0, 0)); //Red
    SpinStrip.show();
  }
////////////////////////////////////////////////////////////////////////////////////////////////////

//Check if patient interacted with peripherals


if(TapReading > Tap1 && TapPressed = 1){

  TapPressed = 0;

  //Turn off LED strip
  for (int i = 0; i < N_LEDS; i++) {
    TapStrip.setPixelColor(i, SqueezeStrip.Color(0, 0, 0)); 
  }
  SqueezeStrip.show();

  player.playOne("PianoG3.mp3");
}

if(SqueezeReading > Squeeze1 && SqueezePressed = 1){

  SqueezePressed = 0;

  //Turn off LED strip
  for (int i = 0; i < N_LEDS; i++) {
    TapStrip.setPixelColor(i, SqueezeStrip.Color(0, 0, 0)); 
  }
  SqueezeStrip.show();

  player.playOne("PianoG3.mp3");
}

if(TapReading > Tap1 && TapPressed = 1){

  TapPressed = 0;

  //Turn off LED strip
  for (int i = 0; i < N_LEDS; i++) {
    TapStrip.setPixelColor(i, SqueezeStrip.Color(0, 0, 0)); 
  }
  SqueezeStrip.show();

  player.playOne("PianoG3.mp3");
}

}

