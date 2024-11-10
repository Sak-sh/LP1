# 4. IRLED  
import time 
import RPi.GPIO as GPIO  
 
RUNNING = True 
 
HIGH  = 1 
LOW  = 0 
DetectPin = 5 
led = 8 
 
def InitSystem(): 
    GPIO.setmode(GPIO.BCM) 
    GPIO.setup(DetectPin,GPIO.IN,pull_up_down=GPIO.PUD_UP) 
    GPIO.setup(led,GPIO.OUT) 
    return 
 
 
def DetectPerson(): 
    while True: 
        input_state = GPIO.input(DetectPin) 
        time.sleep(0.3)  
        if input_state == 0: 
            return LOW 
        else: 
            return HIGH 
             
     
             
             
try: 
    print ("\nCounting using IR LED\n") 
    print  ("-----------------------------------------------\n")     
    InitSystem() 
    count =0; 
    while RUNNING: 
        state = DetectPerson() 
        if state == LOW: 
            count+=1 
            print ("person count =%d" %count) 
            GPIO.output(led,LOW) 
            time.sleep(1) 
            GPIO.output(led,HIGH) 
     
# If CTRL+C is pressed the main loop is broken 
except KeyboardInterrupt: 
    RUNNING = False 
 
  
# Actions under 'finally' will always be called 
finally: 
    # Stop and finish cleanly so the pins 
    # are available to be used again 
    GPIO.cleanup()