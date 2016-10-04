#include "Adafruit_NeoPixel.h"
#define TapPin    6
#define SpinPin    9
#define N_LEDS 30 // 5 meter reel @ 30 LEDs/m
#define SIZE 10
Adafruit_NeoPixel TapStrip = Adafruit_NeoPixel(N_LEDS, TapPin, NEO_GRB + NEO_KHZ800);
Adafruit_NeoPixel SpinStrip = Adafruit_NeoPixel(N_LEDS, SpinPin, NEO_GRB + NEO_KHZ800);
int serialread;
int TapSensorPin = A1; //analog pin 1
int SqueezeSensorPin = A2; //analog pin 2
int SpinSensorPin = A3; //analog pin 3

// int SensorValue = 0; // variable to store the value coming from the sensor
int TapReading = 0;
int SqueezeReading=0;
int SpinReading=0;
int i = 0;
int randomPeripheral = 0; //value generated by the random number generator to determine which of the general 
bool done = true; // boolean that determines if the user has interacted with the device or not
int TapLEDON=0;
int previous =0;

int TapThreshold=650;
int SqueezeThreshold=80;
int SpinThreshold=400;

void setup() {
 Serial.begin(9600);
 //RGB LED for Squeeze
 pinMode(3, OUTPUT);
 pinMode(4, OUTPUT);
 pinMode(5, OUTPUT);
 
  TapStrip.begin();
  SpinStrip.begin();

  randomSeed(analogRead(A0));
}

void loop() {

  TapReading = analogRead(TapSensorPin);
  SpinReading = analogRead(SpinSensorPin);
  SqueezeReading = analogRead(SqueezeSensorPin);
  
  Serial.print(TapReading);
  Serial.print(' ');
  Serial.print(SqueezeReading);
  Serial.print(' ');
  Serial.println(SpinReading);
  delay(50);
  
  //first, determine a new random peripheral to setup if the user had a previous success
 if(done) {
  delay(500);

  while (randomPeripheral == previous)
  randomPeripheral = random(0, 3); //generates a number between 0 and 2  
                                   //0: tap, 1: squeeze, 2: spin
  previous = randomPeripheral;
  done = false;                     //the user has not successfully interacted with the
                                     //new peripheral at this point, hence done = false
  turnOffAllLeds();                                   
  delay(1000);                       //a 1 second delay to tell users that a new peripheral is starting                                  
}


//if the generater is 0, initiate tap sequence
 if(randomPeripheral == 0)
    tapInit();

  //if generator is 1, initiate squeeze sequence
 else if(randomPeripheral == 1)
    squeezeInit();
    
//if neither of the above, initiate spin sequence
 else
     spinInit();

}
 
void tapInit() {
   //set LEDs around Tap to white
   
   for(int i=0;i<N_LEDS;i++) {
      TapStrip.setPixelColor(i, TapStrip.Color(255,0,0)); //WHITE
   }
   TapStrip.show();
   
   TapReading = analogRead(TapSensorPin);

  //if the user has successfully interacted with the tap,
  //then turn the led strip to green and set a new peripheral
   if (TapReading<TapThreshold){
    done = true;
    for(int i=0;i<N_LEDS;i++) {
      TapStrip.setPixelColor(i, TapStrip.Color(0,255,0)); //Green
    }
    TapStrip.show(); //Sends updated pixel color to hardware
    }
}


void squeezeInit() {
  analogWrite(3,255);
    analogWrite(4,0);
    analogWrite(5,0);
  
  SqueezeReading = analogRead(SqueezeSensorPin);
  if (SqueezeReading <SqueezeThreshold){
    done = true;
    analogWrite(3,0);
    analogWrite(4,255);
    analogWrite(5,0);
  }
}

void spinInit() {
  //set LEDs around Spin to white
   for(int i=0;i<N_LEDS;i++) {
      SpinStrip.setPixelColor(i, SpinStrip.Color(255,255,255)); //WHITE
    }
   SpinStrip.show();

   
     SpinReading = analogRead(SpinSensorPin);
     
     if (SpinReading < SpinThreshold) {
      done = true;
    for(int i=0;i<N_LEDS;i++) {
      SpinStrip.setPixelColor(i, SpinStrip.Color(0,255,0)); //Green
    }
    SpinStrip.show(); //Sends updated pixel color to hardware
    }
}

void turnOffAllLeds() {
  
  analogWrite(3,0);
    analogWrite(4,0);
    analogWrite(5,0);
  for(int i=0;i<N_LEDS;i++) {
      SpinStrip.setPixelColor(i, 0); //Off  
      }
    SpinStrip.show(); //Sends updated pixel color to hardware
    for(int i=0;i<N_LEDS;i++) {
    TapStrip.setPixelColor(i, TapStrip.Color(0,0,0)); //WHITE
    }
   TapStrip.show();  
}