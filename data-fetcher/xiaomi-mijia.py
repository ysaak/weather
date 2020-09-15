#!/usr/bin/python3 -u
#-u to unbuffer output. Otherwise when calling with nohup or redirecting output things are printed very lately or would even mixup

from bluepy import btle
import argparse
import os
import re
import threading
import time
import signal
import math

def trace(text):
	print('T|' + text);

def signal_handler(sig, frame):
        os._exit(0)
		
def watchDog_Thread():
	global unconnectedTime
	global connected
	global pid
	while True:
		now = int(time.time())
		if (unconnectedTime is not None) and ((now - unconnectedTime) > 60): #could also check connected is False, but this is more fault proof
			pstree=os.popen("pstree -p " + str(pid)).read() #we want to kill only bluepy from our own process tree, because other python scripts have there own bluepy-helper process
			try:
				bluepypid=re.findall(r'bluepy-helper\((.*)\)',pstree)[0] #Store the bluepypid, to kill it later
			except IndexError: #Should not happen since we're now connected
				print("E|Couldn't find pid of bluepy-helper")
			os.system("kill " + bluepypid)
			trace("Killed bluepy with pid: " + str(bluepypid))
			unconnectedTime = now #reset unconnectedTime to prevent multiple killings in a row
		time.sleep(5)

mode="round"
class MyDelegate(btle.DefaultDelegate):
	def __init__(self, params):
		btle.DefaultDelegate.__init__(self)
		# ... initialise here
	
	def handleNotification(self, cHandle, data):
		try:
			temp = int.from_bytes(data[0:2], byteorder='little', signed=True) / 100
			humidity = int.from_bytes(data[2:3], byteorder='little')
			voltage = int.from_bytes(data[3:5], byteorder='little') / 1000.
			batteryLevel = min(int(round((voltage - 2.1), 2) * 100), 100) #3.1 or above --> 100% 2.1 --> 0 %

			print('D|temperature|{}'.format(temp))
			print('D|humidity|{}'.format(humidity))
			print('D|battery_voltage|{}'.format(voltage))
			print('D|battery_level|{}'.format(batteryLevel))
		except Exception as e:
			print("E|Error while reading data from notification: {}".format(e))

# Initialisation  -------
def connect():
	#print("Interface: " + str(args.interface))
	p = btle.Peripheral(adress) #,iface=args.interface)	
	val=b'\x01\x00'
	p.writeCharacteristic(0x0038,val,True) #enable notifications of Temperature, Humidity and Battery voltage
	p.writeCharacteristic(0x0046,b'\xf4\x01\x00',True)
	p.withDelegate(MyDelegate("abc"))
	return p

# Main loop --------
parser=argparse.ArgumentParser()
parser.add_argument("--device","-d", help="Set the device MAC-Address in format AA:BB:CC:DD:EE:FF",metavar='AA:BB:CC:DD:EE:FF')

args=parser.parse_args()
if args.device:
	if re.match("[0-9a-fA-F]{2}([:]?)[0-9a-fA-F]{2}(\\1[0-9a-fA-F]{2}){4}$",args.device):
		adress=args.device
	else:
		print("Please specify device MAC-Address in format AA:BB:CC:DD:EE:FF")
		os._exit(1)
else:
	parser.print_help()
	os._exit(1)

p=btle.Peripheral()
cnt=0
unreachable_count=3

signal.signal(signal.SIGINT, signal_handler)	
connected=False
pid=os.getpid()	
bluepypid=None
unconnectedTime=None
connectionLostCounter=0

watchdogThread = threading.Thread(target=watchDog_Thread)
watchdogThread.start()

while True:
	try:
		if not connected:
			#Bluepy sometimes hangs and makes it even impossible to connect with gatttool as long it is running
			#on every new connection a new bluepy-helper is called
			#we now make sure that the old one is really terminated. Even if it hangs a simple kill signal was sufficient to terminate it
					
			trace('Trying to connect to {}'.format(adress))
			p=connect()
			connected=True
			unconnectedTime=None
			
		if p.waitForNotifications(2000):
			# handleNotification() was called
			
			cnt += 1
			trace('Measurements collected. Exiting in a moment.')
			p.disconnect()
			time.sleep(5)
			#It seems that sometimes bluepy-helper remains and thus prevents a reconnection, so we try killing our own bluepy-helper
			pstree = os.popen("pstree -p " + str(pid)).read() #we want to kill only bluepy from our own process tree, because other python scripts have there own bluepy-helper process
			bluepypid = 0
			try:
				bluepypid = re.findall(r'bluepy-helper\((.*)\)', pstree)[0] #Store the bluepypid, to kill it later
			except IndexError: #Should normally occur because we're disconnected
				print("E|Couldn't find pid of bluepy-helper")
			if bluepypid != 0:
				os.system("kill " + bluepypid)
				trace("Killed bluepy with pid: " + str(bluepypid))
			os._exit(0)
			continue
	except Exception as e:
		trace('Connection lost')
		connectionLostCounter +=1
		if connected is True: #First connection abort after connected
			unconnectedTime=int(time.time())
			connected=False
		if connectionLostCounter >= unreachable_count:
			print("N|Maximum numbers of unsuccessful connections reaches, exiting")
			os._exit(0)
		time.sleep(1)
		
	print ('T|Waiting...')

