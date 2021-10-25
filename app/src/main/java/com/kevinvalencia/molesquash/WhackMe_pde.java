package com.kevinvalencia.molesquash;

import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class WhackMe_pde extends PApplet {

/** 
 Whack Me
 github/godzilla5123, (c) 2021
 Whack a mole but not really i guess
 Tap the mole. Become a gardener.
 
 
 */

class Points
{
  public float pointX;
  public float pointY;
  public Boolean containsMole;
}

ArrayList<Points> holePoints;
ArrayList<float[]> depPoints; // to be deprecated 

PImage mole;
Boolean gameStarted = false;
int score = 0;
float centerHoleX = (530);
float centerHoleY = (1200);
float holeSize = 300;
int timeOfNextChange;
int currentMolePos;

public void setup() {
  gameStarted = false;
  mole = loadImage("mole.png");
  timeOfNextChange = 3000;

  /* 
   to-do: readjust
   */
  depPoints = new ArrayList<float[]>();
  depPoints.add(new float[]{centerHoleX, centerHoleY});
  depPoints.add(new float[]{centerHoleX, (float) (centerHoleY-(holeSize *1.2))});
  depPoints.add(new float[]{centerHoleX, (float) (centerHoleY+(holeSize *1.2))});
  depPoints.add(new float[]{(float) (centerHoleX + (holeSize *1.2)), centerHoleY});
  depPoints.add(new float[]{(float) (centerHoleX+(holeSize *1.2)), (float) (centerHoleY-(holeSize *1.2))});
  depPoints.add(new float[]{(float) (centerHoleX+(holeSize *1.2)), (float) (centerHoleY+(holeSize *1.2))});
  depPoints.add(new float[]{(float) (centerHoleX-(holeSize *1.2)), centerHoleY});
  depPoints.add(new float[]{(float) (centerHoleX-(holeSize *1.2)), (float) (centerHoleY-(holeSize *1.2))});
  depPoints.add(new float[]{(float) (centerHoleX-(holeSize *1.2)), (float) (centerHoleY+(holeSize *1.2))});
  // begin better code

  holePoints = new ArrayList<Points>();
  for (int holes = 0; holes <= 8; holes++) {
    holePoints.add(new Points());
    holePoints.get(holes).pointX = depPoints.get(holes)[0];
    holePoints.get(holes).pointY = depPoints.get(holes)[1];
    holePoints.get(holes).containsMole = false;
  }
}

public void draw() {
  mainNav();
}

public void mainNav() {
  if (!gameStarted) {
    background(86, 147, 252);
    homeScreen();
  } else {
    background (205, 117, 240);
    gameRuntime();
  }
}

public void homeScreen() {
  fill(133, 166, 222);
  fill(0);
  stroke(0);
  textSize(96);
  text("Whack Me", width/3, 130);
  button((width/2), (height*.65f), (int) (holeSize * 2), color(255, 0, 0), "Play", false);

  // button settings ONLY FOR HOME SCREEN
  if (mousePressed) {
    if (sqrt(sq((width/2)- mouseX) + sq((height*.65f)- mouseY)) < (holeSize * 2)/2) {
      print("click :DDDDDD");
      gameStarted = true;
    }
  }
}




public void button(float buttonX, float buttonY, int size, int colr, String buttonText, Boolean molePresent) {
  fill(colr);
  circle(buttonX, buttonY, size);
  fill(0);
  text(buttonText, buttonX-75, buttonY+20);

  if (molePresent) {
    imageMode(CENTER);
    image(mole, buttonX, buttonY, size * 0.95f, size * 0.95f);
  }
  //float disX = buttonX - mouseX;
  //float disY = buttonY - mouseY;
  //if (mousePressed) {
  //  if (sqrt(sq(disX) + sq(disY)) < size/2) {
  //    if () {
  //    }
  //  }
  //}
}

public void gameRuntime() {

  textSize(48);
  text("Score: " + score,66,420);
  for (int i = 0; i < holePoints.size(); i++) {
    button(holePoints.get(i).pointX, holePoints.get(i).pointY, (int) holeSize, color(200, 72, 128), "", holePoints.get(i).containsMole);
    //debugging 
    //println("The point " + (i+1) + " is located at (" + depPoints.get(i)[0] + "," + depPoints.get(i)[0] + ") under the old system." );
    //println("The point " + (i+1) + " is located at (" + holePoints.get(i).pointX + "," + holePoints.get(i).pointY + ") under the new system.");
  }
  if (millis() >= timeOfNextChange) {
    moleMove(false);
  }
}

public void mousePressed()
{
  if (gameStarted) {
    for (int i = 0; i < holePoints.size(); i++) {
      if (sqrt(sq((holePoints.get(i).pointX)-mouseX) + sq((holePoints.get(i).pointY)-mouseY)) < holeSize/2) {
        if (holePoints.get(i).containsMole) {
          print("squash");
          moleMove(true);
        } else {
          print("you suck lol");
        }
      }
    }
  }
}


public void moleMove(Boolean squash) {
  holePoints.get(currentMolePos).containsMole = false;
  currentMolePos = (round(random(0, 8)));
    // print(currentMolePos); // debug
  holePoints.get(currentMolePos).containsMole = true;
  if (squash) {
    score++;
    print(score);
  }
  timeOfNextChange = millis() + 2000;
}
}
