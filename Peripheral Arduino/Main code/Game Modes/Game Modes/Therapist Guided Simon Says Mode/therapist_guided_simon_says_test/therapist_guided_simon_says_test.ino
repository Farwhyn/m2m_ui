#include "Adafruit_NeoPixel.h"

#define TapPin    8
#define N_LEDS 30 // 5 meter reel @ 30 LEDs/m
#define SIZE 10

#define tap_num 1
#define squeeze_num 2
#define spin_num 3

Adafruit_NeoPixel TapStrip = Adafruit_NeoPixel(N_LEDS, TapPin, NEO_GRB + NEO_KHZ800);

int serial_val; // Data received from the serial port

int SqueezeSensorPin = A3; //analog pin 3

int SqueezeReading;

void setup() {
  // put your setup code here, to run once:
  Serial.begin(9600);

  TapStrip.begin();

  for (int i = 0; i < N_LEDS; i++) {
    TapStrip.setPixelColor(i, TapStrip.Color(0, 0, 0)); //Red
  }
  TapStrip.show();
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
      TapStrip.setPixelColor(i, TapStrip.Color(255, 0, 0)); //Red
    }
    TapStrip.show();
    serial_val=0;
  }
  else if (SqueezeReading > 450) {
    for (int i = 0; i < N_LEDS; i++) {
      TapStrip.setPixelColor(i, TapStrip.Color(0, 0, 0)); //Red
    }
    TapStrip.show();
  }


}

