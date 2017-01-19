delete(instrfindall);
clear;
clc;
close();

%Connect with Arduino Serial Port and create arduino object
arduino=serial('COM8','BaudRate',9600);
fopen(arduino);

while(true)
    pause(1);  
    fprintf(arduino,'1');
    
end
    