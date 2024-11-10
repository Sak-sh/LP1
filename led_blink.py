# 1 LED Blinking 
import time 
import RPi.GPIO as GPIO  
 
TRUE = 1 
 
led1 = 20 
led2 = 21 
led3 = 22 
led4 = 23 
led5 = 24 
led6 = 25 
led7 = 26 
led8 = 27 
 
myled=[led1,led2,led3,led4,led5,led6,led7,led8] 
 
GPIO.setmode(GPIO.BCM) 
GPIO.setup(led1,GPIO.OUT) 
GPIO.setup(led2,GPIO.OUT) 
GPIO.setup(led3,GPIO.OUT) 
GPIO.setup(led4,GPIO.OUT) 
GPIO.setup(led5,GPIO.OUT) 
GPIO.setup(led6,GPIO.OUT) 
GPIO.setup(led7,GPIO.OUT) 
GPIO.setup(led8,GPIO.OUT) 
 
def ledState(led,a): 
    GPIO.output(myled[led],a) 
 
try: 
    for i in range(0,8): 
        ledState(i,1) 
             
    while TRUE: 
        for i in range(0,8): 
            ledState(i,1) 
            time.sleep(0.5) 
            ledState(i,0) 
            time.sleep(0.5) 
                     
     
# If CTRL+C is pressed the main loop is broken 
except KeyboardInterrupt: 
    RUNNING = False 
    print ("\program closed") 
  
# Actions under 'finally' will always be called 
finally: 
    # Stop and finish cleanly so the pins 
    # are available to be used again 
    GPIO.cleanup()