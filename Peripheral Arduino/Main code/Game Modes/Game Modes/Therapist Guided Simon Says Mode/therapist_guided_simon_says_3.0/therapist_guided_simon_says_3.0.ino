#include <MusicPlayer.h>
#include <pins_config.h>
#include <vs10xx.h>

#include "Adafruit_NeoPixel.h"
#include "Adafruit_STMPE610.h"

#include <SPI.h>
#include <SD.h>
#include <Wire.h>

// I believe these coordinates were taken experimentally
// (found to be used in the old LED_maxtrix code)
#define MIN_X 108
#define MAX_X 4006
#define MIN_Y 189
#define MAX_Y 3989

// Use the default constructor with empty () to use I2C
Adafruit_STMPE610 touch = Adafruit_STMPE610();

//Define sensor pins
int TapSensorPin = A1;
int SqueezeSensorPin = A2;
int SpinSensorPin = A3;

//define LED strip variables
#define SpinPin 8
#define TapPin 6

#define N_LEDS 30 // 5 meter reel @ 30 LEDs/m
#define SIZE 10

//define LED pins for squeeze
#define SQ_red 1
#define SQ_green 2
#define SQ_blue 3

//set up LED strips
Adafruit_NeoPixel TapStrip = Adafruit_NeoPixel(N_LEDS, TapPin, NEO_GRB + NEO_KHZ800);
Adafruit_NeoPixel SpinStrip = Adafruit_NeoPixel(N_LEDS, SpinPin, NEO_GRB + NEO_KHZ800);

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

void setup() {
  // put your setup code here, to run once:
  Serial.begin(9600);
  Serial.flush();

  pinMode(10, OUTPUT);

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

  // Loop until STMPE is initialized properly
  while (! touch.begin()) {
    Serial.println("STMPE not found!");
    delay(100);
  }
}

void loop() {

  //Touchscreen variables
  uint8_t z;
  uint16_t x, y;
  // Quadrant information
  uint16_t xQ, yQ;
  unsigned char quadrant;

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

  // Touchscreen controls

  if (touch.touched()) {
    // read x & y & z;
    while (! touch.bufferEmpty()) {
      touch.readData(&x, &y, &z);
      // This maps the coordinates to 0 or 1 quadrant info
      xQ = map(x, MIN_X, MAX_X, 0, 2);
      yQ = map(y, MIN_Y, MAX_Y, 0, 2);

      // Tap quadrant is pressed
      if ((xQ == 0) && (yQ == 0)) {
        quadrant = 1;

        for (int i = 0; i < N_LEDS; i++) {
          //Turn on LED strip
          TapStrip.setPixelColor(i, TapStrip.Color(255, 0, 0)); //Red
        }
        TapStrip.show();
      }

      // Squeeze quadrant is pressed
      else if ((xQ == 1) && (yQ == 0)) {
        quadrant = 2;

        analogWrite(SQ_red, 255);
        analogWrite(SQ_green, 0);
        analogWrite(SQ_blue, 0);
      }

      // Spin quadrant is pressed
      if ((xQ == 0) && (yQ == 1)) {
        quadrant = 3;
        for (int i = 0; i < N_LEDS; i++) {
          //Turn on LED strip
          SpinStrip.setPixelColor(i, TapStrip.Color(255, 0, 0)); //Red
        }
        SpinStrip.show();
      }

    }
    touch.writeRegister8(STMPE_INT_STA, 0xFF); // reset all ints
  }
  ////////////////////////////////////////////////////////////////////////////////////////////////////

  //Check if patient interacted with peripherals


  if (TapReading > Tap_1 && quadrant == 1) {

    //Turn off LED strip
    for (int i = 0; i < N_LEDS; i++) {
      TapStrip.setPixelColor(i, TapStrip.Color(0, 0, 0));
    }
    TapStrip.show();

    player.playOne("PianoG3.mp3");
  }

  if (SqueezeReading > Squeeze_1 && quadrant == 2) {

    analogWrite(SQ_red, 0);
    analogWrite(SQ_green, 0);
    analogWrite(SQ_blue, 0);

    player.playOne("PianoC3.mp3");
  }

  if (SpinReading > Spin_1 && quadrant == 3) {

    //Turn off LED strip
    for (int i = 0; i < N_LEDS; i++) {
      SpinStrip.setPixelColor(i, SpinStrip.Color(0, 0, 0));
    }
    SpinStrip.show();

    player.playOne("PianoF3.mp3");
  }

}

