// This code test the functionality of the Tap circuitry.
// The components connected to the circuit are:
// - LED Strip (Pin 6)
// - Vibratrional Motor (Pin 3)
// - Velostat Sensor (Pin A1)


#include "Adafruit_NeoPixel.h"

#define LEDPin 6
#define MotorPin 3

// Intitialize LED Strip
#define N_LEDS 30 // 5 meter reel @ 30 LEDs/m
#define SIZE 10
Adafruit_NeoPixel LEDStrip = Adafruit_NeoPixel(N_LEDS, LEDPin, NEO_GRB + NEO_KHZ800);
//

int VeloPin = A1;

// Stores reading from velostat sensor
int VeloRead = 0;
//

int Timer = 0;

void setup() {
  // put your setup code here, to run once:

  Serial.begin(9600);

  LEDStrip.begin();

}

void loop() {
  // put your main code here, to run repeatedly:

  VeloRead = analogRead(VeloPin);

  Serial.println(VeloRead);
  delay(100);

  // Begin timer
  if ( Timer == 0 ) {
    Timer = millis();
  }
  //

  if ( millis() - Timer > 2000 ) {

    // Turn LED Strip from off to white color
    for (int i = 0; i < N_LEDS; i++) {
      LEDStrip.setPixelColor(i, LEDStrip.Color(255, 255, 255)); //White
    }
    LEDStrip.show(); //Sends updated pixel color to hardware
    //

    // Turn on vibrational motor
    analogWrite(MotorPin, 153);
    //

    // Wait for 2 seconds before turning off LED strip and motor
    delay(200);
    //

    //Turn off LED Strip
    for (int i = 0; i < N_LEDS; i++) {
      LEDStrip.setPixelColor(i, 0); //Off
    }
    LEDStrip.show(); //Sends updated pixel color to hardware

    //Turn off motor
    analogWrite(MotorPin, 0);
    //

    // Reset Timer
    Timer = 0;
    //
  }


}






