/* Squeeze with Sound
 * Date: April 25 2016
 * Author: May Liang
 * Author: Jose Martinez Castro
 * Description: Updated code to have music notes play when squeezed. Also changed averaging function to set up and included a while 
 * loop to will prevent simulataneous light up and notes from a single squeeze.
 * Open serial monitor best to start.
 */ 

#include <Adafruit_NeoPixel.h>
#include "pitches.h"

#define PIN    6
#define N_LEDS 23 // 5 meter reel @ 30 LEDs/m
#define SIZE 15
Adafruit_NeoPixel strip = Adafruit_NeoPixel(N_LEDS, PIN, NEO_GRB + NEO_KHZ800);

int noteDurations[] = {
  4, 8, 8, 4, 4, 4, 4, 4
};

const int motorPin = 3;
int flexSensorPin = A0; //analog pin 0
int count = 0;
int sum=0;
int average = 0;
 int i = 0;
 int j=0;
 
int flexReading[SIZE];
void setup() {
  pinMode(motorPin, OUTPUT);
  Serial.begin(9600);
  strip.begin();
  for (i=0; i<SIZE; i++) {
    flexReading[i] = analogRead(flexSensorPin); 
    sum = 0;
    for (j=0; j<SIZE; j++)
      sum += flexReading[j];
      average = sum/SIZE;
    //if (flexReading[i]>average+2){
       //chase(0x7F00FF); // Purple
       //note();
       //delay(100);
  //}
  //else{
  //chase(0); // Off
  //}
   Serial.println("Initializing");
 }
}


 
void loop() {
   flexReading[1] = analogRead(flexSensorPin); 
   if (flexReading[1]>average+5){
     chase(0x7F00FF); // Purple
     note();
     delay(100);
  }
  else{
  chase(0); // Off
  }
  Serial.println(flexReading[1]);
  while (flexReading[1] > average+3) {     //stops a squeeze on the sensor from showing lots of numbers
    flexReading[1] = analogRead(flexSensorPin);
    Serial.println("High");
  }
}
 
static void chase(uint32_t c) {
  for(uint16_t i=0; i<strip.numPixels(); i++) {
      strip.setPixelColor(i  ,c ); // Draw new pixel
      //strip.setPixelColor(i-2, 0); // Erase pixel a few steps back
      strip.show();
      delay(20);
  }
}

void note() {
    int thisNote = 262;
    int noteDuration = 1000 / noteDurations[4];
    int pauseBetweenNotes = noteDuration * 2; //1.3 before
    tone(8, thisNote, noteDuration);
    delay(pauseBetweenNotes);
    noTone(8);
//}
}
