# 3. Buzzer 
import time 
import RPi.GPIO as GPIO  
 
TRUE = 1 
 
buzzer = 3 
 
GPIO.setmode(GPIO.BCM) 
GPIO.setup(buzzer,GPIO.OUT) 
 
def buzzerState(val): 
    GPIO.output(buzzer,val) 
     
     
try: 
    while TRUE: 
        buzzerState(1) 
        time.sleep(1) 
        buzzerState(0) 
        time.sleep(1) 
     
     
# If CTRL+C is pressed the main loop is broken 
except KeyboardInterrupt: 
    RUNNING = False 
    print ("\program closed") 
  
# Actions under 'finally' will always be called 
finally: 
    # Stop and finish cleanly so the pins 
    # are available to be used again 
    GPIO.cleanup()