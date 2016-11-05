#include "Adafruit_NeoPixel.h"

#define TapPin    8
#define N_LEDS 30 // 5 meter reel @ 30 LEDs/m
#define SIZE 10

Adafruit_NeoPixel TapStrip = Adafruit_NeoPixel(N_LEDS, TapPin, NEO_GRB + NEO_KHZ800);

char serial_val=' '; // Data received from the serial port
int ledPin = 13; // Set the pin to digital I/O 13

void setup() {
  // put your setup code here, to run once:
  Serial.begin(9600);

  TapStrip.begin();
}

void loop() {
  // put your main code here, to run repeatedly:
  if (Serial.available())
  { // If data is available to read,
    serial_val = Serial.read(); // read it and store it in val
  }


  if (serial_val == 'squeeze')
  { // If 1 was received
    digitalWrite(ledPin, HIGH); // turn the LED on
    for (int i = 0; i < N_LEDS; i++) {
      TapStrip.setPixelColor(i, TapStrip.Color(256, 0, 0)); //Red
    }
    TapStrip.show();
  } else {
    digitalWrite(ledPin, LOW); // otherwise turn it off
  }
  delay(10); // Wait 10 milliseconds for next reading
}

