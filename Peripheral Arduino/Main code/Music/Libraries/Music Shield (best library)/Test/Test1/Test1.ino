#include <MusicPlayer.h>
#include <pins_config.h>
#include <vs10xx.h>

/*
 * playWithName.ino
 * Example sketch for MusicShield 2.0
 *
 * Copyright (c) 2012 seeed technology inc.
 * Website    : www.seeed.cc
 * Author     : Jack Shao (jacky.shaoxg@gmail.com)
 * Create Time: Mar 2014
 * Change Log :
 *
 * The MIT License (MIT)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */


#include <SD.h>
#include <SPI.h>

#define SD_ChipSelectPin 4 

int incomingByte = 0; 
void setup(void)
{
  Serial.begin(9600);
 

  player.begin();                      //will initialize the hardware and set default mode to be normal.
//  player.setPlayMode(MODE_NORMAL); //set mode to repeat playing a song
        player.playOne("PianoC3.mp3");  //do some leisurely job//play a song with its name
        player.playOne("PianoF3.mp3");  //do some leisurely job//play a song with its name
        player.playOne("PianoG3.mp3");  //do some leisurely job//play a song with its name



}
void loop(void)
{
  player.play();

 if (Serial.available() > 0) {
                // read the incoming byte:
                incomingByte = Serial.read();

                if (incomingByte> 0)
                 player.playOne("PianoC3.mp3");
                 incomingByte=0;
        }
}
