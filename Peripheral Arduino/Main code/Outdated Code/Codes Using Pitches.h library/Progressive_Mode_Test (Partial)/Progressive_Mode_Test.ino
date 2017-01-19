/* Squeeze with Sound
 * Date: April 25 2016
 * Author: May Liang
 * Author: Jose Martinez Castro
 * Description: Player can type 1 on the serial port to play the next note
 */ 
 
// use this option for Windows and Linux:


#include "pitches.h"

int noteDurations[] = {
  4, 8, 8, 4, 4, 4, 4, 4
};

int melody[] = {
  NOTE_C4, NOTE_G3, NOTE_G3, NOTE_A3, NOTE_G3, 0, NOTE_B3, NOTE_C4
};
int count = 0;

int incomingByte=0;
void setup() {
  pinMode(8, OUTPUT);
// initialize control over the keyboard:
  Serial.begin(9600);
}

void loop() { 
if (Serial.available() > 0) {
        // read the incoming byte:
        incomingByte = Serial.read();
        Serial.write(incomingByte);
}
     
     if (incomingByte=='1'){
        
        note(count);
        count++;
        delay(100);  
     }
     incomingByte=0;

if (count>7)
  count=0;

}


void note(int count) {
    int thisNote = melody[count];
    int noteDuration = 1000 / noteDurations[4];
    int pauseBetweenNotes = noteDuration * 2; //1.3 before
    tone(8, thisNote, noteDuration);
    delay(pauseBetweenNotes);
    noTone(8);
//}
}
