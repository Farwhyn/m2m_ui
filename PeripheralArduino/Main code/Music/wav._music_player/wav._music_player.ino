// Creator: Jose Martinez Castro
// 
// Comments: Works well, yet quality of music is not ideal. 
//           Also requires wire connection rather than aux cord.




#include <SD.h>                      // need to include the SD library
//#define SD_ChipSelectPin 53  //example uses hardware SS pin 53 on Mega2560
#define SD_ChipSelectPin 4  //using digital pin 4 on arduino nano 328, can use other pins
#include <TMRpcm.h>           //  also need to include this library...
#include <SPI.h>

TMRpcm tmrpcm;   // create an object for use in this sketch


void setup(){

  tmrpcm.speakerPin = 9; //5,6,11 or 46 on Mega, 9 on Uno, Nano, etc

  tmrpcm.setVolume(5);  
  Serial.begin(9600);
  if (!SD.begin(SD_ChipSelectPin)) {  // see if the card is present and can be initialized:
    Serial.println("SD fail");  
    return;   // don't do anything more if not
  }
  Serial.println("SD initialized");  
//  tmrpcm.play("Drake.wav"); //the sound file "music" will play each time the arduino powers up, or is reset
  tmrpcm.play("Piano_C.wav");
}



void loop(){  

  if(Serial.available()){    
    if(Serial.read() == 'p'){ //send the letter p over the serial monitor to start playback
        tmrpcm.play("C.wav");
    }
  }

}
