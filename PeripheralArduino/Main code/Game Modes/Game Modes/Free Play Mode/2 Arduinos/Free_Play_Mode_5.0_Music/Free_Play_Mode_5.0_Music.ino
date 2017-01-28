// DONT CONNECT ANYTHING TO PIN 4

#include <MusicPlayer.h>
#include <pins_config.h>
#include <vs10xx.h>

#include <SD.h>                      // need to include the SD library
//#define SD_ChipSelectPin 53  //example uses hardware SS pin 53 on Mega2560
#define SD_ChipSelectPin 4  //using digital pin 4 on arduino nano 328, can use other pins
#include <SPI.h>

int TapReading;
int SqueezeReading;
int SpinReading;

#define Tap_1  130
#define Squeeze_1  100
#define Spin_1  100


void setup() {
  Serial.begin(9600);

  //will initialize music shield and set default mode to be normal.
  player.begin();
  //  player.setPlayMode(PM_NORMAL_PLAY);

}


void loop() {

  // if there's any serial available, read it:
  while (Serial.available() > 0) {


    if (Serial.read() == '\n') {
      // look for the next valid integer in the incoming serial stream:
      int TapReading = Serial.parseInt();
      // do it again:
      int SqueezeReading = Serial.parseInt();
      // do it again:
      int SpinReading = Serial.parseInt();

      Serial.println(TapReading);
      // look for the newline. That's the end of your
      // sentence:

      //    if (Serial.read() == '\n') {

      //Tap

      //Check whether tap sensor reading is within the three threshold levels
      if (TapReading < Tap_1) {

        player.opStop();
        player.playOne("PianoG3.mp3");
        player.play();

      }

//            //Squeeze
//      
//            //Check whether squeeze sensor reading is within the three threshold levels
//      
//            if (SqueezeReading < Squeeze_1) {
//      
//              player.opStop();
//              player.playOne("PianoF3.mp3");
//              player.play();
//            }
//            //Spin
//      
//            //Check whether squeeze sensor reading is within the three threshold levels
//            if (SpinReading > Spin_1) {
//      
//              player.opStop();
//              player.playOne("PianoC3.mp3");
//              player.play();
//            }

    }

  }
}
