#!/usr/bin/python -u

import sys
import random
import time

if len(sys.argv) != 3:
    print('E|Usage: {} --device mac_address'.format(sys.argv[0]))
    sys.exit()

mac_address = sys.argv[2]

print('T|MAC address {}'.format(mac_address))

if mac_address.endswith('0'):
    print('N|Sensor unreachable')
elif mac_address.endswith('2'):
    time.sleep(30)
else:
    print('D|temperature|{}'.format(random.uniform(10, 30)))
    print('D|humidity|{}'.format(random.uniform(30, 100)))
    if mac_address.endswith('1'):
        print('D|battery_voltage|{}'.format(0))
        print('D|battery_level|{}'.format(0))
    else:
        print('D|battery_voltage|{}'.format(random.uniform(0, 3)))
        print('D|battery_level|{}'.format(random.randint(0, 100)))

print('E|Error trace')