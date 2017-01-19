PShape panels;

PShape tap;
PShape squeeze;
PShape spin;

PShape panel_1;
PShape panel_2;
PShape panel_3;

PShape tap_1;
PShape tap_2;

PShape spin_1;
PShape spin_2;

PShape squeeze_1;
PShape squeeze_2;
PShape squeeze_3;

boolean panel_activated[]={false,false,false};

void setup() {
  size(900, 300);
  
  panels = createShape(GROUP);
  tap = createShape(GROUP);
  squeeze = createShape(GROUP);
  spin = createShape(GROUP);
  
  fill(color(255));
  stroke(100);
  strokeWeight(6);
  
  //  set up panels
  panel_1 = createShape(RECT,0,0,300,300);
  panel_2 = createShape(RECT,300,0,300,300);
  panel_3 = createShape(RECT,600,0,300,300);
  
  
  panels.addChild(panel_1);
  panels.addChild(panel_2);
  panels.addChild(panel_3);

  //  draw tap device
  tap_1=createShape(ELLIPSE,width/6,height/2,180,180);
  tap_2=createShape(ELLIPSE,width/6,height/2,150,150);
  
  tap.addChild(tap_1);
  tap.addChild(tap_2);
  
  //  draw squeeze device
  squeeze_1 = createShape(ARC,width/2, 6*height/10, 120, 100, 0, PI);
  squeeze_2 = createShape(ARC,width/2, 6*height/10, 120, 240, PI , 2*PI );
  
  
  squeeze.addChild(squeeze_1);
  squeeze.addChild(squeeze_2);
  
  //  draw spin device
  rectMode(CENTER);
  
  spin_1= createShape(RECT,5*width/6,2.5*height/8,180,20);
  spin_2= createShape(RECT,5*width/6,4.5*height/8,180,100);
  
  spin.addChild(spin_1);
  spin.addChild(spin_2);
  
  update();
}

void draw() {

  
  
}

void mousePressed() {
  if (dist(mouseX,mouseY, width/6,height/2) < 150 && panel_activated[0] == false) {
     panels.getChild(0).setFill(color(0,255,0,100));
     update();
     
     panel_activated[0]=true;
  }
  else if (dist(mouseX,mouseY, width/2,height/2) < 150 && panel_activated[1] == false) {
     panels.getChild(1).setFill(color(0,255,0,100));
     update();
     
     panel_activated[1]=true;
  }
  else if (dist(mouseX,mouseY, 5*width/6,height/2) < 150 && panel_activated[2] == false) {
     panels.getChild(2).setFill(color(0,255,0,100));
     update();
     
     panel_activated[2]=true;
  }
}

void keyPressed() {
  
  if (key == '1' && panel_activated[0] == true){
    panels.getChild(0).setFill(color(255));
    tap.getChild(0).setFill(color(0,255,0,100)); 
    
     update();
     panel_activated[0]=false;
     
     
     
     tap.getChild(0).setFill(color(255)); 
  
    
     update();
  }
  else if (key == '2' && panel_activated[1] == true){
    panels.getChild(1).setFill(color(255));
    
    
     update();
     panel_activated[1]=false;
     
    
  }
  else if (key == '3' && panel_activated[2] == true){
    panels.getChild(2).setFill(color(255));
    
     
     update();
     panel_activated[2]=false;
     
   
  }
}

void update() {
   shape(panels);
   shape(tap);
   shape(squeeze);
   shape(spin);
}