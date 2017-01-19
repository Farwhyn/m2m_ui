function varargout = M2M_RESNA_GUI(varargin)
% M2M_RESNA_GUI MATLAB code for M2M_RESNA_GUI.fig
%      M2M_RESNA_GUI, by itself, creates a new M2M_RESNA_GUI or raises the existing
%      singleton*.
%
%      H = M2M_RESNA_GUI returns the handle to a new M2M_RESNA_GUI or the handle to
%      the existing singleton*.
%
%      M2M_RESNA_GUI('CALLBACK',hObject,eventData,handles,...) calls the local
%      function named CALLBACK in M2M_RESNA_GUI.M with the given input arguments.
%
%      M2M_RESNA_GUI('Property','Value',...) creates a new M2M_RESNA_GUI or raises the
%      existing singleton*.  Starting from the left, property value pairs are
%      applied to the GUI before M2M_RESNA_GUI_OpeningFcn gets called.  An
%      unrecognized property name or invalid value makes property application
%      stop.  All inputs are passed to M2M_RESNA_GUI_OpeningFcn via varargin.
%
%      *See GUI Options on GUIDE's Tools menu.  Choose "GUI allows only one
%      instance to run (singleton)".
%
% See also: GUIDE, GUIDATA, GUIHANDLES

% Edit the above text to modify the response to help M2M_RESNA_GUI

% Last Modified by GUIDE v2.5 11-Jul-2016 13:52:47

% Begin initialization code - DO NOT EDIT
gui_Singleton = 1;
gui_State = struct('gui_Name',       mfilename, ...
                   'gui_Singleton',  gui_Singleton, ...
                   'gui_OpeningFcn', @M2M_RESNA_GUI_OpeningFcn, ...
                   'gui_OutputFcn',  @M2M_RESNA_GUI_OutputFcn, ...
                   'gui_LayoutFcn',  [] , ...
                   'gui_Callback',   []);
if nargin && ischar(varargin{1})
    gui_State.gui_Callback = str2func(varargin{1});
end

if nargout
    [varargout{1:nargout}] = gui_mainfcn(gui_State, varargin{:});
else
    gui_mainfcn(gui_State, varargin{:});
end
% End initialization code - DO NOT EDIT


% --- Executes just before M2M_RESNA_GUI is made visible.
function M2M_RESNA_GUI_OpeningFcn(hObject, eventdata, handles, varargin)
% This function has no output args, see OutputFcn.
% hObject    handle to figure
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)
% varargin   command line arguments to M2M_RESNA_GUI (see VARARGIN)

% Choose default command line output for M2M_RESNA_GUI
handles.output = hObject;

% Update handles structure
guidata(hObject, handles);

% UIWAIT makes M2M_RESNA_GUI wait for user response (see UIRESUME)
% uiwait(handles.figure1);

delete(instrfindall);

arduino=serial('COM4','BaudRate',9600);
fopen(arduino);

handles.arduino=arduino;
handles.flag=0;
guidata(hObject,handles);


% --- Outputs from this function are returned to the command line.
function varargout = M2M_RESNA_GUI_OutputFcn(hObject, eventdata, handles) 
% varargout  cell array for returning output args (see VARARGOUT);
% hObject    handle to figure
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)

% Get default command line output from handles structure
varargout{1} = handles.output;


% --- Executes on button press in FreePlay.
function FreePlay_Callback(hObject, eventdata, handles)
% hObject    handle to FreePlay (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)
arduino = handles.arduino;
button_state = get(hObject,'Value');
flag1=0;
flag2=0;
flag3=0;

button_state
if button_state == get(hObject,'Max')

tic;
set(hObject,'BackgroundColor',[1 0 0]);
set(hObject,'String','STOP');

t=[];
x = 1:100;
y=zeros(100);
TapSensor=[];
SqueezeSensor=[];
SpinSensor=[];
sensor=[];

TapThreshold=650;
SqueezeThreshold=220;
SpinThreshold=480;

[TapNote,C] = audioread('Piano_C.wav');
[SqueezeNote,D] = audioread('Piano_D.wav');
[SpinNote,E] = audioread('Piano_E.wav');

% [TapNote,C] = audioread('Drum.wav');
% [SqueezeNote,D] = audioread('Beat1.wav');
% [SpinNote,E] = audioread('Beat2.wav');


plot(handles.axes1,x,y);
plot(handles.axes3,x,y);
plot(handles.axes4,x,y);

StripChart('Initialize',handles.axes1)
xlabel(handles.axes1,'Time(ms)');
ylabel(handles.axes1,'Mapped Input Voltage');
axis(handles.axes1,[0 inf 400 1400]);

StripChart('Initialize',handles.axes3)
xlabel(handles.axes3,'Time(ms)');
ylabel(handles.axes3,'Mapped Input Voltage');
axis(handles.axes3,[0 inf 180 350]);

StripChart('Initialize',handles.axes4)
xlabel(handles.axes4,'Time(ms)');
ylabel(handles.axes4,'Mapped Input Voltage');
axis(handles.axes4,[0 inf 320 525]);
 
while button_state == get(hObject,'Max') 

button_state = get(hObject,'Value');
try
    sensor(1:3)=fscanf(arduino,'%f %f %f',[1,3]);
catch 
    sensor(1:3)=[0,0,0];
end

TapSensor=[TapSensor,sensor(1)];
SqueezeSensor=[SqueezeSensor,sensor(2)];
SpinSensor=[SpinSensor,sensor(3)];


if length(TapSensor)==10
StripChart('Update',handles.axes1,TapSensor)
StripChart('Update',handles.axes3,SqueezeSensor)
StripChart('Update',handles.axes4,SpinSensor)

TapSensor=[];
SqueezeSensor=[];
SpinSensor=[];
end
% 
% if length(TapSensor) > 50
%     TapSensor(1:50)=TapSensor(2:51);
%     SqueezeSensor(1:50)=SqueezeSensor(2:51);
%     SpinSensor(1:50)=SpinSensor(2:51);
% end
%     
% 
% plot(handles.axes1,TapSensor,'b-');
% xlabel(handles.axes1,'Time(ms)');
% ylabel(handles.axes1,'Mapped Input Voltage');
% axis(handles.axes1,[0 inf 200 1400]);
% 
% plot(handles.axes3,SqueezeSensor,'r-');
% xlabel(handles.axes3,'Time(ms)');
% ylabel(handles.axes3,'Mapped Input Voltage');
% axis(handles.axes3,[0 inf 0 200]);
% 
% plot(handles.axes4,SpinSensor,'g');
% xlabel(handles.axes4,'Time(ms)');
% ylabel(handles.axes4,'Mapped Input Voltage');
% axis(handles.axes4,[0 inf 380 525]);
% 
% drawnow;

if sensor(1)<TapThreshold 
   if flag1 == 0
   sound(TapNote,C);
   flag1=1;
   end
else
   flag1=0;
end

if sensor(2)<SqueezeThreshold
   if flag2 == 0
   sound(SqueezeNote,D); 
   flag2=1;
   end
else
   flag2=0;
end

if sensor(3)>SpinThreshold 
   if flag3 ==0
   sound(SpinNote,E);
   flag3=1;
   end
else
   flag3=0;
end

end

else 
    set(hObject,'BackgroundColor',[0 1 0]);
    set(hObject,'String','START');
end


% --- Executes on button press in Progressive.
function Progressive_Callback(hObject, eventdata, handles)
% hObject    handle to Progressive (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)


% --- Executes on button press in Automatic.
function Automatic_Callback(hObject, eventdata, handles)
% hObject    handle to Automatic (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)


% --- Executes on button press in TherapistGuided.
function TherapistGuided_Callback(hObject, eventdata, handles)
% hObject    handle to TherapistGuided (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)


% --- Executes on button press in TapActive.
function TapActive_Callback(hObject, eventdata, handles)
% hObject    handle to TapActive (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)
set(hObject,'BackgroundColor',[0 1 0]);
set(handles.SqueezeActive,'BackgroundColor',[1 .5 .5]);
set(handles.SpinActive,'BackgroundColor',[1 .5 .5]);


% --- Executes on button press in SqueezeActive.
function SqueezeActive_Callback(hObject, eventdata, handles)
% hObject    handle to SqueezeActive (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)
set(hObject,'BackgroundColor',[0 1 0]);
set(handles.SpinActive,'BackgroundColor',[1 .5 .5]);
set(handles.TapActive,'BackgroundColor',[1 .5 .5]);

% --- Executes on button press in SpinActive.
function SpinActive_Callback(hObject, eventdata, handles)
% hObject    handle to SpinActive (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)
set(hObject,'BackgroundColor',[0 1 0]);
set(handles.SqueezeActive,'BackgroundColor',[1 .5 .5]);
set(handles.TapActive,'BackgroundColor',[1 .5 .5]);
