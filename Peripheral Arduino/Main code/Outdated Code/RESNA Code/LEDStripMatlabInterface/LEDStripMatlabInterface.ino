#include <Adafruit_NeoPixel.h>

#define SpinPin 6
#define TapPin 9
#define N_LEDS 30 // 5 meter reel @ 30 LEDs/m
#define SIZE 10
Adafruit_NeoPixel SpinStrip = Adafruit_NeoPixel(N_LEDS, SpinPin, NEO_GRB + NEO_KHZ800);
Adafruit_NeoPixel TapStrip = Adafruit_NeoPixel(N_LEDS, TapPin, NEO_GRB + NEO_KHZ800);

String serialreading;

void setup() {
 // put your setup code here, to run once:
 Serial.begin(9600);
 // pinMode(VoltageSensorPin, INPUT);
 pinMode(2, OUTPUT);
 pinMode(3, OUTPUT);
 pinMode(4, OUTPUT);
 SpinStrip.begin();
 TapStrip.begin();

}

void loop() {
  // put your main code here, to run repeatedly:
  // send data only when you receive data:
        if (Serial.available() > 0) {
                // read the incoming byte:
                serialreading = Serial.read();
        }

        if (serialreading == "SpinGreen"){
           for(int i=0;i<N_LEDS;i++) {
           SpinStrip.setPixelColor(i, SpinStrip.Color(0,255,0)); //Green
        }
        SpinStrip.show(); //Sends updated pixel color to hardwar
        delay(100);
        }

        if (serialreading == "SpinWhite"){
           for(int i=0;i<N_LEDS;i++) {
           SpinStrip.setPixelColor(i, SpinStrip.Color(0,255,0)); //White
        }
        SpinStrip.show(); //Sends updated pixel color to hardwar
        delay(100);
        }

        if (serialreading == "SpinOff"){
           for(int i=0;i<N_LEDS;i++) {
           SpinStrip.setPixelColor(i, SpinStrip.Color(0,0,0)); //Off
        }
        SpinStrip.show(); //Sends updated pixel color to hardwar
        delay(100);
        }

        if (serialreading == "TapGreen"){
           for(int i=0;i<N_LEDS;i++) {
           TapStrip.setPixelColor(i, TapStrip.Color(0,255,0)); //Green
        }
        TapStrip.show(); //Sends updated pixel color to hardwar
        delay(100);
        }

        if (serialreading == "TapWhite"){
           for(int i=0;i<N_LEDS;i++) {
           TapStrip.setPixelColor(i, TapStrip.Color(255,255,255)); //White
        }
        TapStrip.show(); //Sends updated pixel color to hardwar
        delay(100);
        }

        if (serialreading == "TapOff"){
           for(int i=0;i<N_LEDS;i++) {
           TapStrip.setPixelColor(i, TapStrip.Color(255,255,255)); //Off
        }
        TapStrip.show(); //Sends updated pixel color to hardwar
        delay(100);
        }

        
}
