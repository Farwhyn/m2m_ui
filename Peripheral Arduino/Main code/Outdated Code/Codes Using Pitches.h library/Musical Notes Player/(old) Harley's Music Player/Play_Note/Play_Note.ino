/* Squeeze with Sound
 * Date: April 25 2016
 * Author: May Liang
 * Author: Jose Martinez Castro
 * Description: Updated code to have music notes play when squeezed. Also changed averaging function to set up and included a while 
 * loop to will prevent simulataneous light up and notes from a single squeeze.
 * Open serial monitor best to start.
 */ 

#include "pitches.h"

int noteDurations[] = {
  4, 8, 8, 4, 4, 4, 4, 4
};


void setup() {
  pinMode(8, OUTPUT);
 
}


 
void loop() {
  
     note();
     delay(1000);
  
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
