
/*
  SD card read/write

 This example shows how to read and write data to and from an SD card file
 The circuit:
 * SD card attached to SPI bus as follows:
 ** MOSI - pin 11
 ** MISO - pin 12
 ** CLK - pin 13
 ** CS - pin 4

 created   Nov 2010
 by David A. Mellis
 modified 9 Apr 2012
 by Tom Igoe

 This example code is in the public domain.

 */

#include <SPI.h>
#include <SD.h>
#include <TMRpcm.h>   

string wavfile = "PIANO_C.WAV";

TMRpcm tmrpcm;   // create an object for use in this sketch

void setup() {
 
  tmrpcm.speakerPin = 9; //11 on Mega, 9 on Uno, Nano, etc

  tmrpcm.volume(1);
  
  // Open serial communications and wait for port to open:
  Serial.begin(9600);
  while (!Serial) {
    ; // wait for serial port to connect. Needed for native USB port only
  }


  Serial.print("Initializing SD card...");

  if (!SD.begin(4)) {
    Serial.println("initialization failed!");
    return;
  }
  Serial.println("initialization done.");

void loop() {
  // put your main code here, to run repeatedly:
  tmrpcm.play(wavfile);
  pause(1000);
}
