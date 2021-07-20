#-*- encoding: utf-8 -*-
from bs4 import BeautifulSoup
import time, sys, base64;start = time.time()
from pprint import pprint
import requests
import sys
import json

input = args[0]

output = "{ "


html = requests.get("https://search.naver.com/search.naver?query="+input+"+날씨")

soup = BeautifulSoup(html.text, 'html.parser')

data1 = soup.find('div', {'class': 'weather_box'})

find_address = data1.find('span', {'class':'btn_select'}).text
output += "\"currentLocation\" : \"" +find_address +"\", "

find_currenttemp = data1.find('span',{'class': 'todaytemp'}).text
output += "\"currentTemper\" : \"" +find_currenttemp+'℃' +"\", "

find_mintemp = data1.find('span',{'class': 'min'}).text
output += "\"minTemper\" : \"" +find_mintemp+'℃' +"\", "

find_maxtemp = data1.find('span',{'class': 'max'}).text
output += "\"maxTemper\" : \"" +find_maxtemp+'℃' +"\", "

data2 = data1.findAll('dd')
find_dust = data2[0].find('span', {'class':'num'}).text
find_ultra_dust = data2[1].find('span', {'class':'num'}).text
find_ozone = data2[2].find('span', {'class':'num'}).text
output += "\"curDust\" : \"" +find_dust +"\", "
output += "\"curUltraDust\" : \"" +find_ultra_dust +"\", "
output += "\"curOzone\" : \"" +find_ozone +"\" }"

# data3 = data1.findAll('point_time morning')
# find_rain_morning = data3[0].find('span', {'class':'num'}).text
# output += '오전 강수량: '+find_rain_morning

#print(output)
print(base64.b64encode(output.encode('utf-8')))