/* Play_song
 * Date: October 2, 2016
 * Author: Jose Martinez Castro
 * Description:
 */

// use this option for Windows and Linux:

#include "pitches.h"
#include "simple_melodies.h"

int song[]=song_1;

int tempo[]=tempo_1;



int count = 0;

void setup() {
	
	pinMode(8, OUTPUT);
	// initialize control over the keyboard:
	Serial.begin(9600);
}

void loop() { 
	//     if (Serial.read()==1){
	for (int thisNote = 0; thisNote < sizeof(song)-1; thisNote++) {
		
		// to calculate the note duration, take one second
		// divided by the note type.
		//e.g. quarter note = 1000 / 4, eighth note = 1000/8, etc.
		int noteDuration = 1000 / tempo[thisNote];
		tone(8, song[thisNote], noteDuration);
		
		// to distinguish the notes, set a minimum time between them.
		// the note's duration + 30% seems to work well:
		int pauseBetweenNotes = noteDuration * 1.30;
		delay(pauseBetweenNotes);
		// stop the tone playing:
		noTone(8);
	}
}

void loop() { 
  //     if (Serial.read()==1){
  for (int thisNote = 0; thisNote < sizeof(song)-1; thisNote++) {
    note(thisNote);
    
  }
}

void note(int count) {
//    // to calculate the note duration, take one second
    // divided by the note type.
    //e.g. quarter note = 1000 / 4, eighth note = 1000/8, etc.
    int noteDuration = 1000 / tempo[thisNote];
    tone(8, song[thisNote], noteDuration);
    
    // to distinguish the notes, set a minimum time between them.
    // the note's duration + 30% seems to work well:
    int pauseBetweenNotes = noteDuration * 1.3;
    delay(pauseBetweenNotes);
    // stop the tone playing:
    noTone(8);
}
