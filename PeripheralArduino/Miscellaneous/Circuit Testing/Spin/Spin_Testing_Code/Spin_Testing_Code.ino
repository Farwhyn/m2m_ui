#include <Adafruit_NeoPixel.h>

#define PIN    6
#define N_LEDS 16 // 5 meter reel @ 30 LEDs/m
#define SIZE 10
Adafruit_NeoPixel strip = Adafruit_NeoPixel(N_LEDS, PIN, NEO_GRB + NEO_KHZ800);

int VoltageSensorPin = A0; //analog pin 0
// int SensorValue = 0; // variable to store the value coming from the sensor
int SensorReading = 0;
int i = 0;

void setup() {
  Serial.begin(9600);
 // pinMode(VoltageSensorPin, INPUT);
  strip.begin();

}

void loop() {
  
 // chase(0xFF0000); // Red
 // chase(0x00FF00); // Green
 // chase(0x0000FF); // Blue
  SensorReading = analogRead(VoltageSensorPin);

  if(SensorReading > 300 && SensorReading < 350)
    chase(0xFF0000);
  else if(SensorReading >=350 && SensorReading < 400)
    chase(0xFFA200);
  else if(SensorReading >= 400 && SensorReading < 450)
    chase(0xFFF700);
  else if(SensorReading >= 450 && SensorReading < 500)
    chase(0xD1FF00);
  else if(SensorReading >= 500 && SensorReading <550)
    chase(0x00FFDE);
  else if(SensorReading >= 550)
    chase(0x0000FF);
  
  Serial.println(SensorReading);
}

static void chase(uint32_t c) {
  for (uint16_t i = 0; i < strip.numPixels() + 4; i++) {
    strip.setPixelColor(i  , c); // Draw new pixel
    strip.setPixelColor(i - 15, 0); // Erase pixel a few steps back
    strip.show();
    //   if(i == 10){
    //     delay(500);//25);
    //   }
    delay(25);
  }
}

